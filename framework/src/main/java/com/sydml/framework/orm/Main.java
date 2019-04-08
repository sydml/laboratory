package com.sydml.framework.orm;

import com.sydml.common.utils.JsonUtil;
import com.sydml.framework.basebean.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liuym
 * @date 2019/3/20 0020
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        String sql = "select * from user where id in (?)";
        List<Object> params = new ArrayList<>();
        params.add(1L);
//        params.add(2L);
        Object query = DBRepository.query(sql, params, new BeanListHandler(User.class));
        System.out.println(JsonUtil.encodeJson(query));
    }
}
