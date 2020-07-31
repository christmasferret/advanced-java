package com.ferret.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

public class ConnectionHolder {
    //map存放的数据源与连接管道的映射
    private Map<DataSource, Connection> map = new HashMap<DataSource, Connection>();

    //根据dataSource获取Connection
    public Connection getConnectionByDataSource(DataSource datasource) throws SQLException {
        Connection connection = map.get(datasource);
        if (connection == null || connection.isClosed()) {
            connection = datasource.getConnection();
            map.put(datasource, connection);
        }
        return connection;
    }
}