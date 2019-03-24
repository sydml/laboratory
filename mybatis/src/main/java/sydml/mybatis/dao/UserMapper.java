package sydml.mybatis.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Select;
import sydml.mybatis.base.ResultMap;
import sydml.mybatis.dto.User;

import java.util.List;

public interface UserMapper {
    List<ResultMap> queryAll();

    @Select("SELECT * FROM USER")
    List<User> queryAllPageDetail();

    @Select("SELECT * FROM USER")
    Page<User> queryAllPage();
}