package sydml.mybatis.dao;

import sydml.mybatis.base.ResultMap;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<ResultMap> selectAll();
}