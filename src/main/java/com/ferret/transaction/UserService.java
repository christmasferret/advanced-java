package com.ferret.transaction;

import javax.sql.DataSource;
public class UserService {
    private UserAccountDao userAccountDao;
    private UserOrderDao userOrderDao;
    private TransactionManager transactionManager;

    public UserService(DataSource dataSource) {
        userAccountDao = new UserAccountDao(dataSource);
        userOrderDao = new UserOrderDao(dataSource);
        transactionManager = new TransactionManager(dataSource);
    }
    public void action() {
        try {
            //进行购买，下单操作
            transactionManager.start();
            userAccountDao.buy();
            userOrderDao.order();
            transactionManager.close();
        }catch(Exception e) {
            //发生异常，则事务回滚
            e.printStackTrace();
            transactionManager.rollback();
        }
    }
}