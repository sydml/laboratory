package sydml.mybatis.service;

import com.sydml.common.api.dto.LoginInfo;
import com.sydml.common.api.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author Liuym
 * @date 2019/5/12 0012
 */
@FeignClient(value = "authorization")
public interface IUserLoginService {
    @PostMapping(value = "user/login")
    void login(LoginInfo loginInfo);

    @RequestMapping(value = "user/find-by-id", method = RequestMethod.GET)
    UserDTO findById(@RequestParam("id") Long id);

    @RequestMapping(value = "user/find-by-username", method = RequestMethod.GET)
    UserDTO findByUsername(@RequestParam("username") String username);

    @RequestMapping(value = "user/save", method = RequestMethod.POST)
    UserDTO save(UserDTO userDTO);

}
