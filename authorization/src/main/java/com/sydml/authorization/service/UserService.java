package com.sydml.authorization.service;

import com.sydml.authorization.domain.User;
import com.sydml.authorization.dto.UserDTO;
import com.sydml.authorization.repository.UserRepository;
import com.sydml.authorization.util.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Liuym
 * @date 2019/3/25 0025
 */
@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BeanMapper beanMapper;


    @Override
    public UserDTO findByUsername(String username) {
        return beanMapper.map(userRepository.findByUsername(username).get(0), UserDTO.class);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        return beanMapper.map(userRepository.save(beanMapper.map(userDTO, User.class)), UserDTO.class);
    }

    @Override
    public UserDTO findById(Long id) {
        return beanMapper.map(userRepository.findById(id), UserDTO.class);
    }


}
