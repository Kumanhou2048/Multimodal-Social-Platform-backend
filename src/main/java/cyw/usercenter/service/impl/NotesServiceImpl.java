package cyw.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huaban.analysis.jieba.JiebaSegmenter;
import cyw.usercenter.model.domain.Images;
import cyw.usercenter.model.domain.Notes;
import cyw.usercenter.model.domain.Users;
import cyw.usercenter.model.request.GetBriefNotesRequest;
import cyw.usercenter.model.request.GetDetailedNotesRequest;
import cyw.usercenter.service.ImagesService;
import cyw.usercenter.service.NotesService;
import cyw.usercenter.Mapper.NotesMapper;
import cyw.usercenter.service.UsersService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author 陈誉文
* @description 针对表【notes】的数据库操作Service实现
* @createDate 2024-12-10 21:57:59
*/
@Service
public class NotesServiceImpl extends ServiceImpl<NotesMapper, Notes>  implements NotesService{
    @Resource
    ImagesService imagesService;
    @Resource
    UsersService usersService;


    @Override
    public int setNewNote(String useraccount, String title, String content, int noteType, int imageCount, List<String> imageUrl) {
        Notes note = new Notes();
        note.setUserAccount(useraccount); // 设置用户账号
        note.setUploadTime(new Date()); // 使用当前时间作为上传时间示例
        note.setTitle(title);
        note.setContent(content);
        note.setImageCount(imageCount);
        note.setNoteType(noteType);
        note.setNoteStatus(1); //默认为审核通过
        note.setLikes(0); // 初始点赞数设为0
        note.setCollection(0); // 初始收藏数设为0
        note.setComments(0); // 初始评论数设为0

        boolean result = this.save(note);
        int noteId;
        if(result) {
            noteId = note.getId();
        }
        else
            return -1;

        int imageIndex = 1;
        for(String url : imageUrl) {
            Images image = new Images();
            image.setNoteId(noteId);
            image.setImageIndex(imageIndex++);
            image.setImageUrl(url);
            if(!imagesService.save(image)) {
                System.out.println("保存图片时出错！");
                noteId = -1;
            }
        }

        return noteId;
    }

    @Override
    public List<GetBriefNotesRequest> getHomePageNotes(int index) {
        QueryWrapper<Notes> qW1 = new QueryWrapper<Notes>();
        qW1.orderByDesc("uploadTime");
        List<Notes> allNotes = this.list(qW1);
        List<GetBriefNotesRequest> HomePageNotes = new ArrayList<GetBriefNotesRequest>();
        for(int i=(index-1)*12; i<
                index*12; i++) {
            if(i >= allNotes.size()) {
                break;
            }
            Notes note = allNotes.get(i);
            GetBriefNotesRequest getBriefNotesRequest = new GetBriefNotesRequest();
            getBriefNotesRequest.setId(note.getId());
            getBriefNotesRequest.setTitle(note.getTitle());
            getBriefNotesRequest.setLikes(note.getLikes());

            //从user获取头像url和用户名
            String useraccount = note.getUserAccount();
            QueryWrapper<Users> qW2 = new QueryWrapper<>();
            qW2.eq("userAccount", useraccount);
            Users user = usersService.getOne(qW2);
            getBriefNotesRequest.setUsername(user.getUsername());
            getBriefNotesRequest.setAvatarUrl(user.getAvatarUrl());

            //从images表获取第一张图片作为封面
            QueryWrapper<Images> qW3 = new QueryWrapper<>();
            qW3.eq("noteId", note.getId());
            qW3.eq("imageIndex", 1);
            Images image = imagesService.getOne(qW3);
            if(image == null)
                getBriefNotesRequest.setImageUrl(null);
            else
                getBriefNotesRequest.setImageUrl(image.getImageUrl());

            HomePageNotes.add(getBriefNotesRequest);
        }
        if(HomePageNotes.isEmpty()) {
            HomePageNotes = null;
            System.out.println("index is tobig");
        }
        return HomePageNotes;
    }

    @Override
    public int getTotalPostsCount() {
        List<Notes> allNotes = this.list();
        return allNotes.size();
    }

    @Override
    public List<GetBriefNotesRequest> getSearchPageNotes(String keywords) {
        JiebaSegmenter Segmenter = new JiebaSegmenter();
        List<String> keys = Segmenter.sentenceProcess(keywords);

        QueryWrapper<Notes> qW1 = new QueryWrapper<>();
        qW1.orderByDesc("uploadTime");
        keys.forEach(keyword -> qW1.or().like("title", keyword).or().like("content", keyword));
        List<Notes> allNotes = this.list(qW1);
        if(allNotes.isEmpty()) {
            System.out.println("没有对应查询结果！");
            return null;
        }

        List<GetBriefNotesRequest> searchRequests = new ArrayList<>();
        for(Notes note : allNotes) {
            GetBriefNotesRequest getBriefNotesRequest = new GetBriefNotesRequest();
            getBriefNotesRequest.setId(note.getId());
            getBriefNotesRequest.setTitle(note.getTitle());
            getBriefNotesRequest.setLikes(note.getLikes());

            //从user获取头像url和用户名
            String useraccount = note.getUserAccount();
            QueryWrapper<Users> qW2 = new QueryWrapper<>();
            qW2.eq("userAccount", useraccount);
            Users user = usersService.getOne(qW2);
            getBriefNotesRequest.setUsername(user.getUsername());
            getBriefNotesRequest.setAvatarUrl(user.getAvatarUrl());

            //从images表获取第一张图片作为封面
            QueryWrapper<Images> qW3 = new QueryWrapper<>();
            qW3.eq("noteId", note.getId());
            qW3.eq("imageIndex", 1);
            Images image = imagesService.getOne(qW3);
            getBriefNotesRequest.setImageUrl(image.getImageUrl());

            searchRequests.add(getBriefNotesRequest);
        }

        if(searchRequests.isEmpty()) {
            System.out.println("index is too big");
        }
        return searchRequests;
    }

    @Override
    public int getTotalSearchPostsCount(String keywords) {
        JiebaSegmenter Segmenter = new JiebaSegmenter();
        List<String> keys = Segmenter.sentenceProcess(keywords);

        QueryWrapper<Notes> qW1 = new QueryWrapper<>();
        keys.forEach(keyword -> qW1.or().like("title", keyword).or().like("content", keyword));
        List<Notes> allNotes = this.list(qW1);

        return allNotes.size();
    }

    @Override
    public GetDetailedNotesRequest getDetailedNotes(int noteId) {
        QueryWrapper<Notes> qW1 = new QueryWrapper<>();
        qW1.eq("id", noteId);
        Notes note = this.getOne(qW1);
        if(note == null) {
            return null;
        }

        GetDetailedNotesRequest Request = new GetDetailedNotesRequest();
        Request.setPostContent(note.getContent());
        Request.setPostTitle(note.getTitle());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = sdf.format(note.getUploadTime());
        Request.setPostTime(formattedTime);

        //获取用户头像和名称
        String useraccount = note.getUserAccount();
        QueryWrapper<Users> qW2 = new QueryWrapper<>();
        qW2.eq("userAccount", useraccount);
        Users user = usersService.getOne(qW2);
        if(user == null) {
            System.out.println("笔记对应的用户不存在！");
            return null;
        }
        Request.setPosterName(user.getUsername());
        Request.setPosterAvatarUrl(user.getAvatarUrl());

        return Request;
    }

    @Override
    public List<GetBriefNotesRequest> getUserNotes(String userAccount) {
        // 创建查询条件，根据 userAccount 查找帖子
        QueryWrapper<Notes> qW1 = new QueryWrapper<Notes>();
        qW1.orderByDesc("uploadTime");

        // 如果 userAccount 不为空，则添加筛选条件
        if (userAccount != null && !userAccount.isEmpty()) {
            qW1.eq("userAccount", userAccount);
        }

        // 获取符合条件的帖子列表
        List<Notes> allNotes = this.list(qW1);

        // 初始化结果列表
        List<GetBriefNotesRequest> userNotes = new ArrayList<>();

        // 进行分页处理
        for (Notes note : allNotes) {
            GetBriefNotesRequest getBriefNotesRequest = new GetBriefNotesRequest();
            getBriefNotesRequest.setId(note.getId());
            getBriefNotesRequest.setTitle(note.getTitle());
            getBriefNotesRequest.setLikes(note.getLikes());

            // 从 user 表获取头像 URL 和用户名
            String noteUserAccount = note.getUserAccount();
            QueryWrapper<Users> qW2 = new QueryWrapper<>();
            qW2.eq("userAccount", noteUserAccount);
            Users user = usersService.getOne(qW2);

            // 设置用户相关信息
            if (user != null) {
                getBriefNotesRequest.setUsername(user.getUsername());
                getBriefNotesRequest.setAvatarUrl(user.getAvatarUrl());
            } else {
                getBriefNotesRequest.setUsername("未知用户");
                getBriefNotesRequest.setAvatarUrl(null);
            }

            // 从 images 表获取第一张图片作为封面
            QueryWrapper<Images> qW3 = new QueryWrapper<>();
            qW3.eq("noteId", note.getId());
            qW3.eq("imageIndex", 1);
            Images image = imagesService.getOne(qW3);
            if (image == null) {
                getBriefNotesRequest.setImageUrl(null);
            } else {
                getBriefNotesRequest.setImageUrl(image.getImageUrl());
            }

            // 将结果添加到列表
            userNotes.add(getBriefNotesRequest);
        }

        // 如果结果列表为空，设置为 null 并输出提示
        if (userNotes.isEmpty()) {
            userNotes = null;
            System.out.println("index is too big or no notes found for the given userAccount");
        }

        return userNotes;
    }

    @Override
    public int deleteNote(int noteId) {
        boolean result=this.removeById(noteId);
        if(result) {
            return 1;
        }
        else {
            return -1;
        }
    }


}




