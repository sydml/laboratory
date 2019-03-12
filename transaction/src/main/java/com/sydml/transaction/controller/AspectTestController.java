package com.sydml.transaction.controller;

import com.sydml.common.utils.StreamUtil;
import com.sydml.transaction.annotations.Permission;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Liuym
 * @date 2019/3/11 0011
 */
@RestController
@RequestMapping("/aspect-test")
public class AspectTestController {

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    @ResponseBody
    @Permission
    public void say(@RequestParam(value="name") String name,@RequestParam(value="content") String content) throws IOException {
        System.out.println(name+ ":"  + content);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void testRequestAndResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String string = StreamUtil.getString(inputStream);
        response.getWriter().write(string);
    }
}
