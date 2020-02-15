package com.heshan.cloud.eureka.core;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;

import java.util.List;

/**
 * ExtendedResponse
 *
 * @author heshan
 * @date 2020/2/15
 */
public class ExtendedResponse {

    private long epoch;

    private String runId;

    private List<Application> full;

    private List<InstanceInfo> added;

    private List<DeletedInstanceInfo> deleted;

    public long getEpoch() {
        return epoch;
    }

    public void setEpoch(long epoch) {
        this.epoch = epoch;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public List<Application> getFull() {
        return full;
    }

    public void setFull(List<Application> full) {
        this.full = full;
    }

    public List<InstanceInfo> getAdded() {
        return added;
    }

    public void setAdded(List<InstanceInfo> added) {
        this.added = added;
    }

    public List<DeletedInstanceInfo> getDeleted() {
        return deleted;
    }

    public void setDeleted(List<DeletedInstanceInfo> deleted) {
        this.deleted = deleted;
    }
}
