package cyw.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cyw.usercenter.model.domain.Comments;
import cyw.usercenter.model.domain.Notes;
import cyw.usercenter.model.domain.Users;
import cyw.usercenter.model.request.GetPostCommentRequest;
import cyw.usercenter.service.CommentsService;
import cyw.usercenter.Mapper.CommentsMapper;
import cyw.usercenter.service.NotesService;
import cyw.usercenter.service.UsersService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author 陈誉文
* @description 针对表【comments】的数据库操作Service实现
* @createDate 2024-12-13 15:10:04
*/
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments>
    implements CommentsService{

    @Resource
    UsersService usersService;

    @Resource
    NotesService notesService;

    @Override
    public String saveComment(int userId, int postId, String content) {
        //检查笔记和用户是否存在
        QueryWrapper<Users> qW1 = new QueryWrapper<>();
        qW1.eq("id", userId);
        Users user = usersService.getOne(qW1);
        if(user == null){
            return "用户不存在！";
        }

        QueryWrapper<Notes> qW2 = new QueryWrapper<>();
        qW2.eq("id", postId);
        Notes note = notesService.getOne(qW2);
        if(note == null){
            return "笔记不存在！";
        }

        if(content.equals("")){
            return "评论内容不能为空！";
        }

        Comments comment = new Comments();
        comment.setContent(content);
        comment.setUserId(userId);
        comment.setNoteId(postId);
        comment.setUploadTime(new Date());
        this.save(comment);
        return "success";
    }

    @Override
    public List<GetPostCommentRequest> getCommentsByPostId(int postId) {
        QueryWrapper<Comments> qW = new QueryWrapper<>();
        qW.eq("noteId", postId);
        List<Comments> comments = this.list(qW);
        if(comments == null){
            System.out.println("请求的笔记未被评论！");
            return null;
        }

        List<GetPostCommentRequest> commentlist = new ArrayList<>();
        for(Comments comment : comments){
            GetPostCommentRequest request = new GetPostCommentRequest();
            request.setContent(comment.getContent());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedTime = sdf.format(comment.getUploadTime());
            request.setTime(formattedTime);

            //查询评论者的头像，名称
            int userId = comment.getUserId();
            Users user = usersService.getById(userId);
            if(user != null){
                request.setName(user.getUsername());
                request.setAvatarUrl(user.getAvatarUrl());
            }
            commentlist.add(request);

        }
        return commentlist;
    }

}




