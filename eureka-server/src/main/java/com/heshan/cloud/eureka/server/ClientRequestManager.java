package com.heshan.cloud.eureka.server;

import com.heshan.cloud.eureka.core.ExtendedResponse;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;

import java.util.Map;
import java.util.UUID;
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

    public ClientRequestManager(PeerAwareInstanceRegistry registry) {
        this.registry = registry;

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
            if (this.runId.equals(client.runId())) {
                //Always return full copy if a request come with a different runId
                fillFullCopy(client);
                return;
            } else if (client.epoch() > epoch || client.epoch() <= 0) {
                //Always return full copy if a request come with a larger epoch,
                //because this may be a illegal request
                fillFullCopy(client);
                return;
            } else if (client.epoch() < epoch - 100) {
                //Return full copy if a request come with a fall behind epoch
                fillFullCopy(client);
                return;
            } else if (client.epoch() < epoch) {
                //Fill the change list from cache and return immediately
                fillFullCopy(client);
                return;
            } else {
                //Client come with same epoch, add to waiting list
                clientMap.put(client, MAP_VALUE);
            }
        } finally {
            readLock.unlock();
        }
    }

    private void fillFullCopy(ClientRequest client) {
        if (!client.deferredResult().isSetOrExpired()) {
            client.deferredResult().setResult(createFullResponse());
        }
    }

    private ExtendedResponse createFullResponse() {
        ExtendedResponse response = new ExtendedResponse();
        response.setRunId(this.runId);
        response.setEpoch(this.epoch);
        response.setApplications(registry.getApplications().getRegisteredApplications());

        return response;
    }
}
