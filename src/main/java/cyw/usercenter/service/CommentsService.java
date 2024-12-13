package cyw.usercenter.service;

import cyw.usercenter.model.domain.Comments;
import com.baomidou.mybatisplus.extension.service.IService;
import cyw.usercenter.model.request.GetPostCommentRequest;

import java.util.List;

/**
* @author 陈誉文
* @description 针对表【comments】的数据库操作Service
* @createDate 2024-12-13 15:10:04
*/
public interface CommentsService extends IService<Comments> {
    String saveComment(int userId, int postId, String content);

    List<GetPostCommentRequest> getCommentsByPostId(int postId);
}
