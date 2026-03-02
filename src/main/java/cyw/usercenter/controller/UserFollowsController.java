package cyw.usercenter.controller;


import cyw.usercenter.service.UserFollowsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Kumanhou
 * @since 2026-03-02
 */
@RestController
@RequestMapping("/user-follows")
public class UserFollowsController {
    @Resource
    private UserFollowsService userFollowsService;


}
