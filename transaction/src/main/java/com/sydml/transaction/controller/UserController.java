package com.sydml.transaction.controller;

import com.sydml.transaction.api.IUser1Service;
import com.sydml.transaction.api.IUser2Service;
import com.sydml.transaction.domain.User2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Liuym
 * @date 2019/3/13 0013
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUser1Service user1Service;

    @Autowired
    private IUser2Service user2Service;

    @RequestMapping("/trx")
    @ResponseBody
    public void saveUserTrx() {
//        User1 user1 = new User1();
//        user1.setName("aa");
        User2 user2 = new User2();
        user2.setName("trx");
        user2Service.saveUserWithTrx(user2);

    }

    @RequestMapping("/non-trx")
    @ResponseBody
    public void saveUserNonTrx() {
//        User1 user1 = new User1();
//        user1.setName("aa");
        User2 user2 = new User2();
        user2.setName("non-trx");
        user2Service.saveUserWithoutTrx(user2);
    }
}