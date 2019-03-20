package sydml.mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sydml.mybatis.dao.ArticleContentMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-18
 * 时间： 19:51
 */
@RestController
@RequestMapping("get-all")
public class ArticleContentController {
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @GetMapping
    public List<Map<String,Object>> getAll(){
        long id = Thread.currentThread().getId();
        System.out.println("getAll:" + id);
        List<Map<String,Object>> hashMap = articleContentMapper.selectAll();
        return  hashMap;
    }
}
