package com.sydml.framework.orm;

import org.apache.commons.dbutils.ResultSetHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author Liuym
 * @date 2019/3/20 0020
 */
public class BeanHandler implements ResultSetHandler{

    private Class<?> clazz;

    public BeanHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object handle(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            return null;
        }
        try {
            Object bean = clazz.newInstance();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 0; i <columnCount ; i++) {
                String columnName = metaData.getColumnName(i + 1);
                Object columnData = rs.getObject(i + 1);
                Field field = clazz.getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(bean, columnData);
            }
            return bean;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
