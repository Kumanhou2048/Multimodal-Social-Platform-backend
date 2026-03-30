package cyw.usercenter.controller;


import cyw.usercenter.Result;
import cyw.usercenter.model.request.FollowRequest;
import cyw.usercenter.model.request.GetFollowResponse;
import cyw.usercenter.model.request.GetLikePostsIDResponse;
import cyw.usercenter.model.request.simpleRequest;
import cyw.usercenter.service.UserFollowsService;
import cyw.usercenter.service.impl.UserFollowsServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Kumanhou
 * @since 2026-03-02
 */
@RestController
public class UserFollowsController {
    @Resource
    private UserFollowsService userFollowService;

    //关注用户
    @PostMapping("/Follows")
    public Result doFollow(@RequestBody FollowRequest request) {
        String followerAccount = request.getFollowerAccount();
        String followingAccount = request.getFollowingAccount();

        boolean result = userFollowService.toggleFollow(followerAccount, followingAccount);
        return Result.success(result ? "关注成功" : "已取消关注");
    }

    //获取粉丝（仅account
    @PostMapping("/getFollowers")
    public Result getFollow(@RequestBody GetFollowResponse request) {
        String userid = request.getAccount();
        List<GetFollowResponse> list = userFollowService.getFollowers(userid);
        return Result.success(list);
    }

    //获取关注（仅account
    @PostMapping("/getFollowings")
    public Result getFollowings(@RequestBody GetFollowResponse request) {
        System.out.println("前端传来的账号是: " + request.getAccount());
        String userid = request.getAccount();
        List<GetFollowResponse> list = userFollowService.getFollowings(userid);
        return Result.success(list);
    }
}
