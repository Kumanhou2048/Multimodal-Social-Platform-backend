package cyw.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import cyw.usercenter.model.domain.Users;
import cyw.usercenter.service.UsersService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@Slf4j
public class FileController {
    @Value("${files.uploads.path}")
    private String savePath;

    @Resource
    private UsersService usersService;


    @PostMapping("/uploadAvatar")
    public String uploadAvatar(@RequestParam MultipartFile file, @RequestParam String userAccount) {
        //取得原文件后缀名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //用uuid＋后缀名重新生成文件名
        String newFileName = UUID.randomUUID().toString() + suffix;

        //检查存储目录是否存在
        File dest = new File(savePath + newFileName);
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        // 查询旧头像的 URL
        // 假设有一个 QueryWrapper 对象，设置查询条件为 name = 'John Doe'
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        Users user = usersService.getOne(queryWrapper); // 调用 getOne 方法
        String oldAvatarUrl = user.getAvatarUrl();

        // 删除本地旧头像文件
        if (oldAvatarUrl != null && !oldAvatarUrl.isEmpty() && !"/avatar/default.png".equals(oldAvatarUrl)) {
            String oldFilePath = savePath + oldAvatarUrl.replace("/avatar/", "");
            File oldFile = new File(oldFilePath);
            if (oldFile.exists() && oldFile.isFile()) {
                if (!oldFile.delete()) {
                    log.warn("Failed to delete old avatar file: " + oldFilePath);
                }
            }
        }

        //更新数据库
        UpdateWrapper<Users> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userAccount", userAccount).set("avatarUrl", "/avatar/" + newFileName);
        usersService.update(updateWrapper); // 调用 update 方法

        try {
            file.transferTo(dest);
            //返回图片的相对url地址
            return "/avatar/" + newFileName;
        }catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }

    }

    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam MultipartFile file) {
        //取得原文件后缀名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //用uuid＋后缀名重新生成文件名
        String newFileName = UUID.randomUUID().toString() + suffix;

        //检查存储目录是否存在
        File dest = new File(savePath + newFileName);
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
            //返回图片的相对url地址
            return "/avatar/" + newFileName;
        }catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }

    }

}
