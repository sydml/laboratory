package com.sydml.framework.ioc.test;

import com.sydml.framework.ioc.annotation.Autowired;
import com.sydml.framework.ioc.annotation.Controller;

/**
 * @author Liuym
 * @date 2019/3/10 0010
 */
@Controller
public class UserController {

    @Autowired("userServiceB")
    private IUserService userService;
}
