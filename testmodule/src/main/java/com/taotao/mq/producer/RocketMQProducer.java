package com.taotao.mq.producer;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

public class RocketMQProducer {

    private DefaultMQProducer defaultMQProducer;
    private String producerGroup;
    private String namesrcAddr;

    public void init() throws MQClientException {
        this.defaultMQProducer = new DefaultMQProducer(this.producerGroup);
        defaultMQProducer.setNamesrvAddr(this.namesrcAddr);
        defaultMQProducer.start();
    }

    public void destroy(){
        defaultMQProducer.shutdown();
    }

    public DefaultMQProducer getDefaultMQProducer() {
        return defaultMQProducer;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public void setNamesrcAddr(String namesrvAddr) {
        this.namesrcAddr = namesrvAddr;
    }

    public void setInstanceName(String instanceName) {
        //this.instanceName = instanceName;
    }

    public void setRetryTimes(int retryTimes) {
        //this.retryTimes = retryTimes;
    }
}
