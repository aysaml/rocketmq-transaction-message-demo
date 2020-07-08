package com.aysaml.transaction.message.demo.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.stereotype.Component;

/**
 * RocketMQ transaction message producer manager.
 *
 * @author aysaml
 * @date 2020/7/1
 */
@Slf4j
@Component
public class RocketMqTransactionProducerManager {

  private TransactionMQProducer producer;

  public RocketMqTransactionProducerManager init(RocketMqTransactionProducerConfig config) {
    this.producer = new TransactionMQProducer(config.getGroup());
    this.producer.setNamesrvAddr(config.getNameServer());
    this.producer.setExecutorService(config.getExecutorService());
    this.producer.setTransactionListener(config.getTransactionListener());
    return this;
  }

  public void start() throws MQClientException {
    this.producer.start();
  }

  public void destory() {
    this.producer.shutdown();
  }

  public TransactionMQProducer getProducer() {
    return this.producer;
  }
}
