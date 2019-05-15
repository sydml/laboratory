package com.sydml.framework.controller;

import com.sydml.framework.ioc.annotation.Controller;
import com.sydml.framework.ioc.annotation.RequestMapping;
import com.sydml.framework.ioc.annotation.ResponseBody;

/**
 * @author Liuym
 * @date 2019/3/24 0024
 */
@Controller
public class MyTestController {

    @RequestMapping(value = "get:/test")
    @ResponseBody
    public String test() {
        return "hahaha";
    }
}
