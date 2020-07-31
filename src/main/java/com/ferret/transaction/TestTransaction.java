package com.ferret.transaction;


import org.apache.commons.dbcp.BasicDataSource;

public class TestTransaction {
    public static final String jdbcDriver = "com.mysql.jdbc.Driver";
    public static final String jdbcURL = "jdbc:mysql://database1.coa9c2bs7yig.us-east-2.rds.amazonaws.com:3306/mmall?characterEncoding=utf-8";
    public static final String jdbcUsername = "xxxxx";//mysql用户名
    public static final String jdbcPassword = "xxxxx";//密码

    public static void main(String[] args) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(jdbcDriver);
        basicDataSource.setUsername(jdbcUsername);
        basicDataSource.setPassword(jdbcPassword);
        basicDataSource.setUrl(jdbcURL);
        final UserService userService = new UserService(basicDataSource);

        //模拟用户并发请求
        for (int i = 0; i < 10; i++) {
            new Thread((Runnable) () -> {
                userService.action();
            }).start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}