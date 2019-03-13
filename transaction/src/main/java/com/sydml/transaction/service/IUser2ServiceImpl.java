package com.sydml.transaction.service;

import com.sydml.transaction.api.IUser2Service;
import com.sydml.transaction.domain.User1;
import com.sydml.transaction.domain.User2;
import com.sydml.transaction.repository.User1Repository;
import com.sydml.transaction.repository.User2Repository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author Liuym
 * @date 2019/3/13 0013
 */
@Service
public class IUser2ServiceImpl implements IUser2Service,ApplicationContextAware {

    @Autowired
    private User2Repository user2Repository;

    private ApplicationContext applicationContext;

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public void save(User2 user) {
        user2Repository.save(user);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public void batchSave(List<User2> user2List) {
        for (User2 user2 : user2List) {
            save(user2);
        }
    }

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public void saveUserWithTrx(User2 user2){
        IUser2ServiceImpl user2Service = applicationContext.getBean(IUser2ServiceImpl.class);
        user2Service.save(user2);
        throw new RuntimeException("trx error");
    }

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public void saveUserWithoutTrx(User2 user2){
        save(user2);
        throw new RuntimeException("non trx error");
    }

}
