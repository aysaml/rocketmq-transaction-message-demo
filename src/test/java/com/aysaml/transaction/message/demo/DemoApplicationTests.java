package com.aysaml.transaction.message.demo;

import com.aysaml.transaction.message.demo.mq.DemoTransactionListener;
import com.aysaml.transaction.message.demo.mq.RocketMqTransactionProducerConfig;
import com.aysaml.transaction.message.demo.mq.RocketMqTransactionProducerManager;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class DemoApplicationTests {

  @Value("${rocketmq.name-server}")
  private String nameServer;

  @Autowired private RocketMqTransactionProducerManager rocketMqTransactionProducerManager;

  @Autowired private DemoTransactionListener demoTransactionListener;

  @Test
  void contextLoads() {}

  @Test
  void send() throws MQClientException {
    RocketMqTransactionProducerConfig config = new RocketMqTransactionProducerConfig();
    config.setGroup("demoGroup");
    config.setNameServer(nameServer);
    config.setExecutorService(
        new ThreadPoolExecutor(1, 2, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024)));
    config.setTransactionListener(demoTransactionListener);
    rocketMqTransactionProducerManager.init(config);
    rocketMqTransactionProducerManager.getProducer().start();
    Message message = new Message("testTopic", "211211121".getBytes());
    SendResult result =
        rocketMqTransactionProducerManager.getProducer().sendMessageInTransaction(message, null);
  }
}
