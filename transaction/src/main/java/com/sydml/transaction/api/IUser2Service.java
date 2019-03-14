package com.sydml.transaction.api;

import com.sydml.transaction.domain.User2;

import java.util.List;

/**
 * @author Liuym
 * @date 2019/3/13 0013
 */
public interface IUser2Service {
    void save(User2 user);

    void batchSave(List<User2> user2List);

    void saveUserWithTrx(User2 user2);

    User2 saveUserWithoutTrx(User2 user2);
}
