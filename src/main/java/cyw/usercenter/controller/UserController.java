package cyw.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cyw.usercenter.model.domain.Users;
import cyw.usercenter.model.request.UserLoginRequest;
import cyw.usercenter.model.request.UserRegistRequest;
import cyw.usercenter.model.request.UserResetPasswordRequest;
import cyw.usercenter.service.UsersService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static cyw.usercenter.constant.UserConstant.ADMIN_ROLE;
import static cyw.usercenter.constant.UserConstant.USER_LOGIN_STATUS;

@RestController
@Slf4j
public class UserController {

    @Resource
    private UsersService usersService;

    @PostMapping("/register")
    public int userRegister(@RequestBody UserRegistRequest userRegistRequest) {
        if(userRegistRequest==null){
            return 0;
        }
        String username = userRegistRequest.getUsername();
        String userAccount=userRegistRequest.getUserAccount();
        String userPassword=userRegistRequest.getUserPassword();
        String checkPassword=userRegistRequest.getCheckPassword();
        String gender=userRegistRequest.getGender();
        return usersService.userRegister(username,userAccount,userPassword,checkPassword,gender);
    }

    @PostMapping("/login")
    public Users userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        log.info("userLoginRequest:{}",userLoginRequest);
        if(userLoginRequest==null){
            return null;
        }
        String userAccount=userLoginRequest.getUserAccount();
        String userPassword=userLoginRequest.getUserPassword();
        return usersService.userLogin(userAccount,userPassword,request);
    }

    @GetMapping("/search")
    public List<Users> userSearch(String username,HttpServletRequest request) {
        if(!isAdmin(request)){
            System.out.println("search error,not admin");
            return null;
        }
        QueryWrapper<Users> queeryWrapper=new QueryWrapper();
        if(StringUtils.isNotBlank(username)){
            queeryWrapper.like("username",username);
        }
        return usersService.list(queeryWrapper);
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody int id,HttpServletRequest request) {
        if(!isAdmin(request)){
            System.out.println("delete error,not admin");
            return false;
        }
        if(id<=0){
            return false;
        }
        return usersService.removeById(id);
    }

    //是否为管理员
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj=request.getSession().getAttribute(USER_LOGIN_STATUS);
        Users user=(Users)userObj;
        return user !=null && user.getUserRole()==ADMIN_ROLE;
    }

    @GetMapping("/current")
    public Users getCurrentUser(HttpServletRequest request) {
        Object userObj=request.getSession().getAttribute(USER_LOGIN_STATUS);
        Users user=(Users)userObj;
        if(user==null){
            return null;
        }
        int id=user.getId();
        Users currentUser=usersService.getById(id);
        return currentUser;
    }

    @PostMapping("/outLogin")
    public Integer userLogout(HttpServletRequest request) {
        if(request==null){
            return 0;
        }
        return usersService.userLogout(request);
    }

    @PostMapping("/reset")
    public int userPasswordRest(@RequestBody UserResetPasswordRequest userResetPasswordRequest) {
        if(userResetPasswordRequest==null){
            return 0;
        }
        String userAccount=userResetPasswordRequest.getUserAccount();
        String userPassword=userResetPasswordRequest.getUserPassword();
        String checkPassword=userResetPasswordRequest.getCheckPassword();
        return usersService.userResetPassword(userAccount,userPassword,checkPassword);
    }
}
