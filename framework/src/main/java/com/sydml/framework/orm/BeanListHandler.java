package com.sydml.framework.orm;

import org.apache.commons.dbutils.ResultSetHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liuym
 * @date 2019/3/20 0020
 */
public class BeanListHandler implements ResultSetHandler {

    private Class<?> clazz;

    public BeanListHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object handle(ResultSet rs) throws SQLException {

        List<Object> list = new ArrayList<>();
        try {
            while (rs.next()) {
                Object bean = clazz.newInstance();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    Object value = rs.getObject(columnName);
                    Field field = bean.getClass().getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(bean, value);
                }
                list.add(bean);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}