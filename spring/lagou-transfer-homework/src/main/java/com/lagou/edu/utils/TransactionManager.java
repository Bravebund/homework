package com.lagou.edu.utils;

import com.lagou.edu.stereotype.Autowired;
import com.lagou.edu.stereotype.Component;

import java.sql.SQLException;

/**
 * 事务管理器类：负责手动事务的开启、提交、回滚
 *
 * @author 应癫
 */
@Component
public class TransactionManager {

    @Autowired
    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    /*private TransactionManager(){

    }

    private static TransactionManager transactionManager = new TransactionManager();

    public static TransactionManager getInstance() {
        return  transactionManager;
    }*/


    // 开启手动事务控制
    public void beginTransaction() throws SQLException {
        connectionUtils.getCurrentThreadConn().setAutoCommit(false);
    }


    // 提交事务
    public void commit() throws SQLException {
        connectionUtils.getCurrentThreadConn().commit();
    }


    // 回滚事务
    public void rollback() throws SQLException {
        connectionUtils.getCurrentThreadConn().rollback();
    }
}
