package cyw.usercenter.service;

import cyw.usercenter.model.domain.UserFollows;
import com.baomidou.mybatisplus.extension.service.IService;
import cyw.usercenter.model.request.GetFollowResponse;

import java.util.List;

/**
 * @author Kumanhou
 * @since 2026-03-02
 */
public interface UserFollowsService extends IService<UserFollows> {
    /**
     * 切换关注状态（已关注则取消，未关注则新增）
     *
     * @param followerId  关注者 ID
     * @param followingId 被关注者 ID
     * @return 操作是否成功
     */
    boolean toggleFollow(String followerId, String followingId);

    /**
     * 获取指定用户的粉丝列表
     *
     * @param userId 用户 ID
     * @return 粉丝记录列表
     */
    List<GetFollowResponse> getFollowers(String userId);

    /**
     * 获取指定用户的关注列表
     *
     * @param userId 用户 ID
     * @return 关注记录列表
     */
    List<GetFollowResponse> getFollowings(String userId);
}
