package com.sydml.authorization.controller;

import com.sydml.common.utils.JsonUtil;
import com.sydml.authorization.dto.LoginInfo;
import com.sydml.authorization.dto.UserDTO;
import com.sydml.authorization.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author Liuym
 * @date 2019/3/25 0025
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IUserService userService;

    @PostMapping("login")
    public void login(@RequestBody(required = true) LoginInfo info) {
        UserDTO userDTO = userService.findByUsername(info.getUsername());
        redisTemplate.opsForValue().set(userDTO.getUsername(), JsonUtil.encodeJson(userDTO), 60 * 60, TimeUnit.SECONDS);

    }
}
