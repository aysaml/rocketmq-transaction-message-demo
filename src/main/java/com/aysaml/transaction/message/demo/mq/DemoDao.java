package com.aysaml.transaction.message.demo.mq;

import org.springframework.stereotype.Repository;

/**
 * Dao for demo.
 *
 * @author aysaml
 * @date 2020/7/8
 */
@Repository
public interface DemoDao {

  /** delete all data. */
  public void deleteAll();
}
