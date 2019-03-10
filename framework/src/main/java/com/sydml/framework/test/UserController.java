package com.sydml.framework.test;

import com.sydml.framework.annotation.Autowired;
import com.sydml.framework.annotation.Controller;

/**
 * @author Liuym
 * @date 2019/3/10 0010
 */
@Controller
public class UserController {

    @Autowired("userService2")
    private IUserService userService;
}
