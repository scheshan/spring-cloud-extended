package com.heshan.cloud.eureka.server;

import com.heshan.cloud.eureka.core.ExtendedResponse;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ClientRequestManager
 *
 * @author heshan
 * @date 2020/2/15
 */
public class ClientRequestManager {

    private final String runId;

    private volatile long epoch;

    private Map<ClientRequest, Object> clientMap;

    private static final Object MAP_VALUE = new Object();

    private ReadWriteLock lock;

    private Lock readLock;

    private Lock writeLock;

    private PeerAwareInstanceRegistry registry;

    private ExtendConfigBean config;

    private int changeListSize = 1000;

    private Queue<InstanceRegistryEventWrapper> changeList;

    private int maxWaitingClients = 16;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRequestManager.class);

    public ClientRequestManager(PeerAwareInstanceRegistry registry, ExtendConfigBean config) {
        this.registry = registry;
        this.config = config;

        this.changeListSize = Math.max(changeListSize, config.getChangeQueueSize());

        changeList = new LinkedList<>();

        runId = UUID.randomUUID().toString();
        epoch = 1;
        clientMap = new ConcurrentHashMap<>();
        lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    /**
     * Try add a client to {@link ClientRequestManager}
     *
     * @param client
     */
    public void add(ClientRequest client) {
        readLock.lock();
        try {
            if (!this.runId.equals(client.runId())) {
                //Always return full copy if a request come with a different runId
                client.deferredResult().setResult(createFullResponse());
                return;
            } else if (client.epoch() != epoch) {
                //Fill the response and return immediately
                long epoch = client.epoch();
                client.deferredResult().setResult(createEpochResponse(epoch));
                return;
            } else {
                //Client come with same epoch, add to waiting list
                LOGGER.info("Add client: [{}] to waiting list", client);
                client.deferredResult().onCompletion(new ClientRequestCompleteTask(client));
                clientMap.put(client, MAP_VALUE);

                //Not thread-safe, we only need a approximate value to prevent map expansion
                maxWaitingClients = Math.max(maxWaitingClients, clientMap.size());
            }
        } finally {
            readLock.unlock();
        }
    }

    public void remove(ClientRequest client) {
        readLock.lock();
        try {
            LOGGER.info("Remove client: [{}] from waiting list", client);
            clientMap.remove(client);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Handle {@link InstanceRegistryEvent}
     * <p>
     * Increase the epoch, store the change in cache,
     * notify all waiting clients and set their response
     *
     * @param e
     */
    @EventListener
    public void handleInstanceRegistryEvent(InstanceRegistryEvent e) {
        Map<ClientRequest, Object> allClientMap = null;
        ExtendedResponse response = null;

        writeLock.lock();
        try {
            long oldEpoch = epoch;

            epoch++;
            if (epoch < 0) {
                //overflowed, must clear the change list
                epoch = 1;
                changeList.clear();
            }

            changeList.add(new InstanceRegistryEventWrapper(e, epoch));
            if (changeList.size() > changeListSize) {
                changeList.remove();
            }

            allClientMap = clientMap;
            clientMap = new ConcurrentHashMap<>(maxWaitingClients);

            if (!allClientMap.isEmpty()) {
                response = createEpochResponse(oldEpoch);
            }
        } finally {
            writeLock.unlock();
        }

        if (!allClientMap.isEmpty()) {
            for (Map.Entry<ClientRequest, Object> clientEntry : allClientMap.entrySet()) {
                ClientRequest client = clientEntry.getKey();
                if (!client.deferredResult().isSetOrExpired()) {
                    client.deferredResult().setResult(response);
                }
            }
        }
    }

    private ExtendedResponse createResponse() {
        ExtendedResponse response = new ExtendedResponse();
        response.setRunId(this.runId);
        response.setEpoch(this.epoch);

        return response;
    }

    private ExtendedResponse createFullResponse() {
        ExtendedResponse response = createResponse();
        response.setFull(registry.getApplications().getRegisteredApplications());

        return response;
    }

    private ExtendedResponse createEpochResponse(long clientEpoch) {
        if (clientEpoch == epoch) {
            //Should not be here
            return null;
        }

        if (clientEpoch > epoch || clientEpoch <= 0 || clientEpoch < epoch - changeListSize) {
            //Always return full copy if a request
            // 1. come with a larger epoch which means it may be a illegal request
            // 2. come with a negative epoch which means it may be a illegal request
            // 3. come with a very small epoch which means it fall behind and can not build the changes from the cache

            return createFullResponse();
        }

        ExtendedResponse response = createResponse();
        response.setAdded(new LinkedList<>());
        response.setDeleted(new LinkedList<>());

        for (InstanceRegistryEventWrapper change : changeList) {
            if (change.epoch <= clientEpoch) {
                continue;
            }

            InstanceRegistryEvent e = change.event;
            switch (e.type()) {
                case REGISTER:
                    response.getAdded().add(e.instance());
                    break;
                case UNREGISTER:
                    response.getDeleted().add(e.instance());
                    break;
            }
        }

        return response;
    }

    private class InstanceRegistryEventWrapper {

        private InstanceRegistryEvent event;

        private long epoch;

        public InstanceRegistryEventWrapper(InstanceRegistryEvent event, long epoch) {
            this.event = event;
            this.epoch = epoch;
        }
    }

    private class ClientRequestCompleteTask implements Runnable {
        private ClientRequest client;

        public ClientRequestCompleteTask(ClientRequest client) {
            this.client = client;
        }

        @Override
        public void run() {
            remove(client);
        }
    }
}
