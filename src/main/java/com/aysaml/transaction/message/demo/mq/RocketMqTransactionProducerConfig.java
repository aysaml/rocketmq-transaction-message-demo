package com.aysaml.transaction.message.demo.mq;

import lombok.Data;
import org.apache.rocketmq.client.producer.TransactionListener;

import java.util.concurrent.ExecutorService;

/**
 * RocketMQ transaction producer config.
 *
 * @author aysaml
 * @date 2020/7/1
 */
@Data
public class RocketMqTransactionProducerConfig {

    private String group;

    private String nameServer;

    private ExecutorService executorService;

    private TransactionListener transactionListener;
}
