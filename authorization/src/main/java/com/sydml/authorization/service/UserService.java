package com.sydml.authorization.service;

import com.sydml.authorization.domain.User;
import com.sydml.authorization.repository.UserRepository;
import com.sydml.authorization.util.BeanMapper;
import com.sydml.common.api.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Liuym
 * @date 2019/3/25 0025
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private void streamRead() {
        jdbcTemplate.query(con -> {
            PreparedStatement preparedStatement = con.prepareStatement("select * from table", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setFetchSize(Integer.MIN_VALUE);
            preparedStatement.setFetchDirection(ResultSet.FETCH_FORWARD);
            return preparedStatement;
        }, rs -> {
            while (rs.next()) {
                System.err.println(rs.getString("id"));
            }
        });

    }

    @Override
//    @RequestMapping(value="user/find-by-username",method = RequestMethod.GET)
    public UserDTO findByUsername(String username) {
        return beanMapper.map(userRepository.findByUsername(username).get(0), UserDTO.class);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        return beanMapper.map(userRepository.save(beanMapper.map(userDTO, User.class)), UserDTO.class);
    }

    @Override
    public UserDTO findById(Long id) {
        return beanMapper.map(userRepository.findById(id).orElseGet(null), UserDTO.class);
    }


}
