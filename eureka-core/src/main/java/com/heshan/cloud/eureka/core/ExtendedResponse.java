package com.heshan.cloud.eureka.core;

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

    private List<Application> applications;

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

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
