package cyw.usercenter.controller;

import cyw.usercenter.model.request.GetPostCommentRequest;
import cyw.usercenter.model.request.simpleRequest;
import cyw.usercenter.model.request.makeACommentRequest;
import cyw.usercenter.service.CommentsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CommentsController {

    @Resource
    private CommentsService commentsService;

    @PostMapping("/makeAComment")
    public simpleRequest makeAComment(@RequestBody makeACommentRequest request) {
        int userId = request.getUserId();
        int postId = request.getPostId();
        String content = request.getContent();

        String res =  commentsService.saveComment(userId, postId, content);
        simpleRequest response = new simpleRequest();
        response.setStatus(res);

        return response;
    }

    @PostMapping("/getPostComment")
    public List<GetPostCommentRequest> getPostComment(@RequestBody simpleRequest request) {
        int id = request.getId();
        return commentsService.getCommentsByPostId(id);
    }

}
