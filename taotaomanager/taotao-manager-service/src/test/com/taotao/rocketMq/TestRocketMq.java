package com.taotao.rocketMq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.junit.Test;

import java.util.List;

public class TestRocketMq {


    @Test
    public void producer() {
        DefaultMQProducer producer = new DefaultMQProducer("Producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        try {
            producer.start();

            Message msg = new Message("PushTopic",
                    "push",
                    "1",
                    "Just for test.".getBytes());

            SendResult result = producer.send(msg);
            System.out.println("id:" + result.getMsgId() +
                    " result:" + result.getSendStatus());

            msg = new Message("PushTopic",
                    "push",
                    "2",
                    "Just for test.".getBytes());

            result = producer.send(msg);
            System.out.println("id:" + result.getMsgId() +
                    " result:" + result.getSendStatus());

            msg = new Message("PushTopic",
                    "push",
                    "1",
                    "Just for test.".getBytes());

            result = producer.send(msg);
            System.out.println("id:" + result.getMsgId() +
                    " result:" + result.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            producer.shutdown();
        }
    }

    @Test
    public void customer(){
        DefaultMQPushConsumer consumer =
                new DefaultMQPushConsumer("PushConsumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe("PushTopic", "push");

            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(
                    ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                                                 public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                                                     Message msg = list.get(0);
                            System.out.println(msg.toString());

                                                     String topic = msg.getTopic();
                                                     System.out.println("topic = " + topic);
                                                     byte[] body = msg.getBody();
                                                     System.out.println("body:  " + new String(body));
                                                     String keys = msg.getKeys();
                                                     System.out.println("keys = " + keys);
                                                     String tags = msg.getTags();
                                                     System.out.println("tags = " + tags);
                                                     System.out.println("-----------------------------------------------");

                                                     return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                                                 }
                                             }
            );
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("===========");
    }

    public static void main(String[] args){
        DefaultMQPushConsumer consumer =
                new DefaultMQPushConsumer("PushConsumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe("PushTopic", "push");

            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(
                    ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                                                 public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                                                     Message msg = list.get(0);
                                                     System.out.println(msg.toString());

                                                     String topic = msg.getTopic();
                                                     System.out.println("topic = " + topic);
                                                     byte[] body = msg.getBody();
                                                     System.out.println("body:  " + new String(body));
                                                     String keys = msg.getKeys();
                                                     System.out.println("keys = " + keys);
                                                     String tags = msg.getTags();
                                                     System.out.println("tags = " + tags);
                                                     System.out.println("-----------------------------------------------");

                                                     return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                                                 }
                                             }
            );
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("===========");
    }
}
