package cyw.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cyw.usercenter.model.domain.Users;
import cyw.usercenter.service.UsersService;
import cyw.usercenter.Mapper.UsersMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static cyw.usercenter.constant.UserConstant.USER_LOGIN_STATUS;

/**
* @author 陈誉文
* @description 针对表【users】的数据库操作Service实现
* @createDate 2024-11-14 16:46:11
*/
@Service
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UsersService{

    private static final String salt = "cyw";
    // 使用SHA-256进行加密，并将结果进行Base64编码
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest((salt + password).getBytes());
            // 使用Base64编码确保生成的密码长度相对固定
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error while hashing the password", e);
            throw new RuntimeException("Error while hashing the password", e);
        }
    }

    @Override
    public int userRegister(String username,String userAccount, String userPassword, String checkPassword,String gender) {
        //1.校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return -1;
        }
        if(userAccount.length()<3||userAccount.length()>10){
            return -1;
        }
        if(userPassword.length()<6||userPassword.length()>20){
            return -1;
        }
        //账号没有特殊字符
        if (!userAccount.matches("[a-zA-Z0-9\u4e00-\u9fa5]+")){
            return -1;
        }
        //密码和校验码一样
        if(!checkPassword.equals(userPassword)){
            return -1;
        }
        //账户不能重复
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if(count>0){
            return -2;//账号已经存在
        }
        //2.加密
        //2.加密 - 使用SHA-256加密并Base64编码
        String newPassword = hashPassword(userPassword);
        //3.插入数据
        Users user = new Users();
        user.setUsername(username);
        if(gender.equals("男")){
            user.setGender(0);
        }else{
            user.setGender(1);
        }
        user.setUserAccount(userAccount);
        user.setUserPassword(newPassword);
        this.save(user);
        return user.getId();
    }

    @Override
    public Users userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            request.setAttribute("errorMessage", "账号或密码不能为空");
            return null;
        }
        if(userAccount.length()<3||userAccount.length()>10){
            request.setAttribute("errorMessage", "账号长度需在3到10个字符之间");
            return null;
        }
        if(userPassword.length()<6||userPassword.length()>20){
            request.setAttribute("errorMessage", "密码长度需在6到20个字符之间");
            return null;
        }
        //账号没有特殊字符
        if (!userAccount.matches("[a-zA-Z0-9\u4e00-\u9fa5]+")){
            request.setAttribute("errorMessage", "账号只能包含字母、数字或汉字");
            return null;
        }
        //2.加密
        String newPassword= hashPassword(userPassword);
        //查询用户是否存在
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", newPassword);
        Users user=this.getOne(queryWrapper);
        if(user==null) {
            request.setAttribute("errorMessage", "账号不存在");
            log.info("login in fail");
            return null;
        }
        //3.记录用户登录状态
        request.getSession().setAttribute(USER_LOGIN_STATUS, user);
        return user;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATUS);
        return 1;
    }

    @Override
    public int userResetPassword(String userAccount, String userPassword, String checkPassword) {
        //校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return -1;
        }
        if(userAccount.length()<3||userAccount.length()>10){
            return -1;
        }
        if(userPassword.length()<6||userPassword.length()>20){
            return -1;
        }
        //密码和校验码一样
        if(!checkPassword.equals(userPassword)){
            return -1;
        }
        //查询用户是否存在
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        Users user=this.getOne(queryWrapper);
        if(user==null) {
            log.info("userAccount not exist");
            return -2;//表明账号不存在
        }
        //加密新密码
        String newPassword = hashPassword(userPassword);
        //重置密码
        UpdateWrapper<Users> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userAccount", userAccount).set("userPassword", newPassword);
        boolean result = this.update(updateWrapper); // 调用 update 方法
        if (result) {
            System.out.println(userAccount+"reset password successfully.");
            return 1;
        } else {
            System.out.println(userAccount+"Failed to reset password.");
            return -1;
        }
    }

    @Override
    public int userUpdateUserInfo(String userAccount, String username, String gender) {
        int genderValue=1;
        if(gender.equals("男")){
            genderValue=0;
        }
        //根据账号，更新用户信息
        UpdateWrapper<Users> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userAccount", userAccount).set("username", username);
        updateWrapper.eq("userAccount", userAccount).set("gender", genderValue);
        boolean result = this.update(updateWrapper); // 调用 update 方法
        return 1;
    }
}




