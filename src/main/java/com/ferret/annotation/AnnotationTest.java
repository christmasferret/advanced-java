package com.ferret.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationTest {
    // 1.首先考虑代码怎么与数据库做映射 filter是与数据库关系最紧密的类-转到filter
    // 2需要考虑query方法如何实现
    public static void main(String[] args) {
        Filter f1 = new Filter();
        f1.setId(10);// 查询id为10的用户
        Filter f2 = new Filter();
        f2.setUserName("Tom");// 查询用户名为tom的用户
        f2.setAge(18);
        Filter f3 = new Filter();
        f3.setEmail("123@qq.com,haha@126.com");
        String sql1 = query(f1);
        String sql2 = query(f2);
        String sql3 = query(f3);
        System.out.println("sql1:" + sql1);
        System.out.println("sql2:" + sql2);
        System.out.println("sql3:" + sql3);
        //写一个filter可以应用到不同的表中
        Filter2 filter2= new Filter2();
        filter2.setId(1);
        filter2.setDepartmentName("技术");
        System.out.println(query(filter2));
    }

    private static String query(Object f) {
        // 首先用一个StringBuilder来存储字符串表示sql语句
        StringBuilder sb = new StringBuilder();
        // 1获取class
        Class c = f.getClass();
        // 2.获取table名
        boolean isCExis = c.isAnnotationPresent(Table.class);
        if (!isCExis) {
            return null;
        }
        Table t = (Table) c.getAnnotation(Table.class);
        String tableName = t.value();
        sb.append("select * from ").append(tableName).append(" where 1=1");
        // 3.遍历所有字段
        Field[] field = c.getDeclaredFields();
        for (Field fi : field) {
            // 4.处理每个字段对应的sql
            // 4.1拿到字段的名字
            boolean isFExis = fi.isAnnotationPresent(Column.class);
            if (!isFExis) {
                continue;
            }
            Column col = fi.getAnnotation(Column.class);
            String columnName = col.value();
            // 4.2拿到字段的值(反射)
            String fieldName = fi.getName();// 字段名
            // 先拿到get方法的名字
            String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Object fieldValue = null;
            try {
                Method getMethod = c.getMethod(getMethodName);
                fieldValue = getMethod.invoke(f);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // 4.3拼接
            if (fieldValue == null || (fieldValue instanceof Integer && (Integer) fieldValue == 0)) {
                continue;
            }
            sb.append(" and ").append(columnName);
            if (fieldValue instanceof Integer) {
                sb.append(" = ").append(fieldValue);
            } else if (fieldValue instanceof String) {
                if (((String) fieldValue).contains(",")) {// 邮箱格式包含，表示有多种选择
                    String[] string = ((String) fieldValue).split(",");
                    sb.append(" in (");
                    for(String str:string){
                        sb.append("'").append(str).append("'").append(",");
                    }
                    sb.deleteCharAt(sb.length()-1);
                    sb.append(")");
                } else {
                    sb.append(" = '").append(fieldValue).append("'");
                }

            }
        }
        return sb.toString();
    }
}