package sydml.mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sydml.mybatis.base.ResultMap;
import sydml.mybatis.dao.UserMapper;

import java.util.List;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-18
 * 时间： 19:51
 */
@RestController
@RequestMapping("get-all")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping
    public List<ResultMap> getAll(){
        long id = Thread.currentThread().getId();
        System.out.println("getAll:" + id);
        List<ResultMap> resultMap= userMapper.selectAll();
        return  resultMap;
    }
}
