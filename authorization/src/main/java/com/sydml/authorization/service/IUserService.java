package com.sydml.authorization.service;

import com.sydml.authorization.dto.UserDTO;

/**
 * @author Liuym
 * @date 2019/3/25 0025
 */
public interface IUserService {
    UserDTO findByUsername(String username);

    UserDTO save(UserDTO userDTO);

    UserDTO findById(Long id);
}
