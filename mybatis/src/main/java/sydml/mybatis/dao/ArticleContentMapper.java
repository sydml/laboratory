package sydml.mybatis.dao;

import java.util.List;
import java.util.Map;

public interface ArticleContentMapper {
    List<Map<String,Object>> selectAll();
}