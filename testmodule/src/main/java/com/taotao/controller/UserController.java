package com.taotao.controller;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.taotao.mq.producer.RocketMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    @Qualifier("rocketMQProducer")
    private RocketMQProducer producer;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        for(int i = 0;i<100;i++){
            Message msg = new Message("TestTopic1","TAG1",(i+"这是spring集成").getBytes());
            SendResult result = producer.getDefaultMQProducer().send(msg);
            System.out.println(result);
            System.out.println(1);
        }

        return "hello";
    }
}
