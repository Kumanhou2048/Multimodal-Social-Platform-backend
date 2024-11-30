package cyw.usercenter.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import cyw.usercenter.model.domain.Users;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UsersServiceTest {
    @Resource
    private UsersService usersService;

    @Test
    public void testDeleteUser() {
        boolean result=usersService.removeById(2);
        Assertions.assertTrue(result);
    }

    @Test
    public void testAddUser(){
        Users user = new Users();
        user.setUsername("bly");
        user.setUserAccount("22331999");
        user.setUserPassword("12345556");
        user.setAvatarUrl("");
        user.setGender(1);
        user.setUserRole(1);
        boolean result=usersService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    public void testUpdateUser(){
        // 假设有一个 UpdateWrapper 对象，设置更新条件为 userAccount='22331009'，更新字段为 userRole=1
        UpdateWrapper<Users> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userAccount", "22331009").set("userRole", 1);
        boolean result = usersService.update(updateWrapper); // 调用 update 方法
        if (result) {
            System.out.println("Record updated successfully.");
        } else {
            System.out.println("Failed to update record.");
        }
    }
}