package com.aysaml.transaction.message.demo.mq;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RocketMQ TransactionListener implement.
 *
 * @author aysaml
 * @date 2020/7/8
 */
@Component
public class DemoTransactionListener implements TransactionListener {

  @Autowired private DemoDao demoDao;

  private Map<String, LocalTransactionState> map = new ConcurrentHashMap<>();

  @Override
  public LocalTransactionState executeLocalTransaction(Message message, Object o) {
    try {
      demoDao.deleteAll();
      map.put(message.getTransactionId(), LocalTransactionState.COMMIT_MESSAGE);
    } catch (Exception e) {
        map.put(message.getTransactionId(), LocalTransactionState.ROLLBACK_MESSAGE);
      return LocalTransactionState.ROLLBACK_MESSAGE;
    }
    return LocalTransactionState.COMMIT_MESSAGE;
  }

  @Override
  public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
    return map.get(messageExt.getTransactionId());
  }
}
