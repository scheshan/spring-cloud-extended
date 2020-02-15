package com.heshan.cloud.eureka.server;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

/**
 * InstanceRegistryEvent
 *
 * @author heshan
 * @date 2020/2/15
 */
public class InstanceRegistryEvent extends ApplicationEvent {

    private Type type;

    private InstanceInfo instance;

    private String appName;

    private String instanceId;

    public InstanceRegistryEvent(Object source, Type type, InstanceInfo instance) {
        super(source);

        Objects.requireNonNull(type);
        Objects.requireNonNull(instance);

        this.type = type;
        this.instance = instance;
        this.appName = instance.getAppName();
        this.instanceId = instance.getAppName();
    }

    public InstanceRegistryEvent(Object source, Type type, String appName, String instanceId) {
        super(source);

        Objects.requireNonNull(type);
        Objects.requireNonNull(appName);
        Objects.requireNonNull(instanceId);

        this.type = type;
        this.appName = appName;
        this.instanceId = instanceId;
    }

    public Type type() {
        return type;
    }

    public InstanceInfo instance() {
        return instance;
    }

    public String appName() {
        return appName;
    }

    public String instanceId() {
        return instanceId;
    }

    public enum Type {

        REGISTER,

        UNREGISTER
    }
}
