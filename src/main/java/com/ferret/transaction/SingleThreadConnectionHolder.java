package com.ferret.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class SingleThreadConnectionHolder {
    //ThreadLocal封装Map为线程共享变量
    private  static ThreadLocal<ConnectionHolder> threadLocal = new ThreadLocal<ConnectionHolder>();
    private static ConnectionHolder getConnectionHolder() {
        ConnectionHolder connectionHolder = threadLocal.get();
        if(connectionHolder == null) {
            connectionHolder = new ConnectionHolder();
            threadLocal.set(connectionHolder);
        }
        return connectionHolder;
    }
    public static Connection getConnection(DataSource dataSource) throws SQLException{
        return getConnectionHolder().getConnectionByDataSource(dataSource);
    }
}