package cyw.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cyw.usercenter.model.domain.Likes;
import cyw.usercenter.model.domain.Notes;
import cyw.usercenter.model.domain.Users;
import cyw.usercenter.model.request.GetLikePostsIDResponse;
import cyw.usercenter.model.request.PostLikesResponse;
import cyw.usercenter.service.LikesService;
import cyw.usercenter.Mapper.LikesMapper;
import cyw.usercenter.service.NotesService;
import cyw.usercenter.service.UsersService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author 陈誉文
* @description 针对表【likes】的数据库操作Service实现
* @createDate 2024-12-16 16:22:07
*/
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes>
    implements LikesService{

    @Resource
    NotesService notesService;

    @Resource
    UsersService usersService;

    @Override
    public PostLikesResponse PostLikes(int userId, int postId, boolean liked) {
        PostLikesResponse response = new PostLikesResponse();
        boolean result;
        //检查user和post是否存在
        QueryWrapper<Notes> qW1 = new QueryWrapper<>();
        qW1.eq("id", postId);
        Notes notes = notesService.getOne(qW1);
        if(notes == null) {
            response.setStatus("other");
            return response;
        }

        QueryWrapper<Users> qW2 = new QueryWrapper<>();
        qW2.eq("id", userId);
        Users users = usersService.getOne(qW2);
        if(users == null) {
            response.setStatus("other");
            return response;
        }

        if(liked) {
            QueryWrapper<Likes> qW3 = new QueryWrapper<>();
            qW3.eq("userId", userId);
            qW3.eq("noteId", postId);
            Likes likes = getOne(qW3);
            if(likes != null) {
                response.setStatus("other");
                return response;
            }

            likes = new Likes();
            likes.setUserId(userId);
            likes.setNoteId(postId);
            likes.setUploadTime(new Date());
            result = this.save(likes);

            //更新notes表里的likes
            notes.setLikes(notes.getLikes() + 1);
            notesService.saveOrUpdate(notes);
        }
        else {
            QueryWrapper<Likes> qW3 = new QueryWrapper<>();
            qW3.eq("userId", userId);
            qW3.eq("noteId", postId);
            result = this.remove(qW3);

            notes.setLikes(notes.getLikes() - 1);
            notesService.saveOrUpdate(notes);
        }

        if(result){
            response.setStatus("success");
        }
        else
            response.setStatus("other");

        return response;
    }

    public GetLikePostsIDResponse getLikePostsID(int userid) {
        List<Integer> likesPostsID = new ArrayList<Integer>();
        QueryWrapper<Likes> qW= new QueryWrapper<>();
        qW.eq("userId", userid);
        List<Likes> list = this.list(qW);
        for(Likes like : list){
            likesPostsID.add(like.getNoteId());
        }
        GetLikePostsIDResponse response = new GetLikePostsIDResponse();
        response.setPostID(likesPostsID);
        return response;

    }
}




