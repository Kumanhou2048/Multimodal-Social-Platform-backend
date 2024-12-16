package cyw.usercenter.controller;


import cyw.usercenter.model.request.GetLikePostsIDResponse;
import cyw.usercenter.model.request.PostLikesRequest;
import cyw.usercenter.model.request.PostLikesResponse;
import cyw.usercenter.model.request.simpleRequest;
import cyw.usercenter.service.LikesService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LikesController {
    @Resource
    private LikesService likesService;


    @PostMapping("/PostLikes")
    public PostLikesResponse PostLikes(@RequestBody PostLikesRequest request) {
        int userid = request.getUserID();
        int postid = request.getPostID();
        boolean liked = request.isStatus();
        return likesService.PostLikes(userid, postid, liked);
    }

    @PostMapping("/getLikePostsID")
    public List<GetLikePostsIDResponse> getLikePostsID(@RequestBody simpleRequest request) {
        int userid = request.getId();
        return likesService.getLikePostsID(userid);
    }
}
