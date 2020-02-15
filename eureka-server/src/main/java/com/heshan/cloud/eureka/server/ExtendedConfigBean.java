package com.heshan.cloud.eureka.server;

/**
 * ExtendConfigBean
 *
 * @author heshan
 * @date 2020/2/15
 */
public class ExtendedConfigBean {

    private int changeQueueSize = 20;

    public int getChangeQueueSize() {
        return changeQueueSize;
    }

    public void setChangeQueueSize(int changeQueueSize) {
        this.changeQueueSize = changeQueueSize;
    }
}
