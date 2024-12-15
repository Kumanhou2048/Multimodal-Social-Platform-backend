package cyw.usercenter.service;

import cyw.usercenter.model.domain.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import cyw.usercenter.model.request.ManagePageUserInfo;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 陈誉文
* @description 针对表【users】的数据库操作Service
* @createDate 2024-11-14 16:46:11
*/
public interface UsersService extends IService<Users> {

    int  userRegister(String username,String userAccount, String userPassword, String checkPassword,String gender);

    Users userLogin(String userAccount, String userPassword, HttpServletRequest request);

    int userLogout(HttpServletRequest request);

    int userResetPassword(String userAccount,String userPassword,String checkPassword);

    int userUpdateUserInfo(String userAccount,String username,String gender);

    ManagePageUserInfo getManagePageUserInfo(String userAccount);
}
