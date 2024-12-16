package cyw.usercenter.service;

import cyw.usercenter.model.domain.Likes;
import com.baomidou.mybatisplus.extension.service.IService;
import cyw.usercenter.model.request.GetLikePostsIDResponse;
import cyw.usercenter.model.request.PostLikesResponse;

import java.util.List;

/**
* @author 陈誉文
* @description 针对表【likes】的数据库操作Service
* @createDate 2024-12-16 16:22:07
*/
public interface LikesService extends IService<Likes> {
    PostLikesResponse PostLikes(int userId, int postId, boolean liked);

    List<GetLikePostsIDResponse> getLikePostsID(int userid);
}
