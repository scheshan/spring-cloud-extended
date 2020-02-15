package com.heshan.cloud.eureka.core;

/**
 * DeletedInstanceInfo
 *
 * @author heshan
 * @date 2020/2/15
 */
public class DeletedInstanceInfo {

    private String appName;

    private String instanceId;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
