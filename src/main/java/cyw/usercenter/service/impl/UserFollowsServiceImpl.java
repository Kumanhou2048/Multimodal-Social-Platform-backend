package cyw.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cyw.usercenter.model.domain.UserFollows;
import cyw.usercenter.mapper.UserFollowsMapper;
import cyw.usercenter.model.request.GetFollowResponse;
import cyw.usercenter.service.UserFollowsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class UserFollowsServiceImpl extends ServiceImpl<UserFollowsMapper, UserFollows> implements UserFollowsService {

    /**
     * 关注 / 取消关注
     */
    @Transactional
    public boolean toggleFollow(String followerAccount, String followingAccount) {
        if (followerAccount.equals(followingAccount)) {
            throw new RuntimeException("你不能关注你自己");
        }

        // 1. 查询是否已关注 (使用修正后的字段名)
        LambdaQueryWrapper<UserFollows> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFollows::getFollowerAccount, followerAccount)
                .eq(UserFollows::getFollowingAccount, followingAccount);
        UserFollows oldFollow = this.getOne(queryWrapper);

        if (oldFollow != null) {
            // 2. 已关注，则取消关注
            return this.removeById(oldFollow.getId());
        } else {
            // 3. 未关注，则新增关注
            UserFollows follow = new UserFollows();
            follow.setFollowerAccount(followerAccount);
            follow.setFollowingAccount(followingAccount);
            follow.setCreateTime(LocalDateTime.now());
            return this.save(follow);
        }
    }

    /**
     * 获取粉丝列表 (谁关注了我)
     */
    public List<GetFollowResponse> getFollowers(String userId) {
        LambdaQueryWrapper<UserFollows> qW = new LambdaQueryWrapper<>();
        // 查询 被关注者 是当前用户的所有记录
        qW.eq(UserFollows::getFollowingAccount, userId);

        List<UserFollows> list = this.list(qW);
        if (list == null) return new ArrayList<>();

        // 使用 Stream 流简化转换过程
        return list.stream().map(follow -> {
            GetFollowResponse response = new GetFollowResponse();
            response.setAccount(follow.getFollowerAccount()); // 返回关注者的账号
            return response;
        }).collect(Collectors.toList());
    }

    /**
     * 获取关注列表 (我关注了谁)
     */
    public List<GetFollowResponse> getFollowings(String userId) {
        LambdaQueryWrapper<UserFollows> qW = new LambdaQueryWrapper<>();
        // 查询 关注者 是当前用户的所有记录
        qW.eq(UserFollows::getFollowerAccount, userId);

        List<UserFollows> list = this.list(qW);
        if (list == null) return new ArrayList<>();

        return list.stream().map(follow -> {
            GetFollowResponse response = new GetFollowResponse();
            response.setAccount(follow.getFollowingAccount()); // 返回被关注者的账号
            return response;
        }).collect(Collectors.toList());
    }
}