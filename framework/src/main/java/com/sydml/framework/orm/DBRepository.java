package com.sydml.framework.orm;

import com.sydml.framework.transaction.handler.DatabaseHandler;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Liuym
 * @date 2019/3/20 0020
 */
public class DBRepository {
    /**
     * update
     * @param sql
     * @param params
     */
    public static void update(String sql,List<Object> params) {
        PreparedStatement st;
        ResultSet rs = null;
        DatabaseHandler.beginTransaction();
        Connection connection;
        try {
            connection = DatabaseHandler.getConnection();
            st = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHandler.commitTransaction();
        }
    }

    /**
     * query
     * @param sql
     * @param params
     * @param rsh
     * @return
     */
    public static Object query(String sql, List<Object> params, ResultSetHandler rsh) {
        DatabaseHandler.beginTransaction();
        Connection connection;
        PreparedStatement st;
        ResultSet rs;
        connection = DatabaseHandler.getConnection();
        try {
            st = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i+1 , params.get(i));
            }
            rs = st.executeQuery();
            return rsh.handle(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DatabaseHandler.commitTransaction();
        }

    }
}
