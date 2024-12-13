package cyw.usercenter.controller;

import cyw.usercenter.model.request.*;
import cyw.usercenter.service.NotesService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
public class NotesController {
    @Resource
    private NotesService notesService;

    @PostMapping("/uploadNote")
    public String uploadNote(@RequestBody NotesUploadRequest noteUploadRequest ) {
        if(noteUploadRequest == null){
            return "null error";
        }
        String useraccount = noteUploadRequest.getUserAccount();
        String title = noteUploadRequest.getTitle();
        String content = noteUploadRequest.getContent();
        int noteType = noteUploadRequest.getNoteType();
        int imagecount = noteUploadRequest.getImageCount();
        List<String> imageUrl = noteUploadRequest.getImageUrl();

        if(StringUtils.isAnyBlank(useraccount, title)){
            return "缺少必要字段！";
        }
        if(title.length() > 50) {
            return "标题长度超过50！";
        }
        if(content.length() > 500) {
            return "内容长度超过500！";
        }

        int id = notesService.setNewNote(useraccount, title, content, noteType, imagecount, imageUrl);
        if(id == -1){
            return "保存失败！";
        }

        return "笔记上传成功";
    }

    @PostMapping("/getHomePagePost")
    public List<GetBriefNotesRequest> getHomePage(@RequestBody getPageRequest request) {
        int index = request.getIndex();
        if(index < 1){
            System.out.println("index is less than 1");
            return null;
        }
        List<GetBriefNotesRequest> HomePageNotes;
        HomePageNotes = notesService.getHomePageNotes(index);
        if(HomePageNotes == null){
            System.out.println("请求的笔记数量超限！");
            return null;
        }
        return HomePageNotes;
    }

    @GetMapping ("/getTotalPosts")
    public int getTotalPosts() {
        return notesService.getTotalPostsCount();
    }

    @PostMapping("/getSearchPagePost")
    public List<GetBriefNotesRequest> getSearchPagePost(@RequestBody getPageRequest request) {
        String key = request.getKey();
        if(key.isEmpty()){
            System.out.println("key is empty or index is less than 1");
            return null;
        }
        return notesService.getSearchPageNotes(key);
    }

    @PostMapping("/getTotalSearchPosts")
    public int getTotalSearchPosts(@RequestBody getPageRequest request) {
        String key = request.getKey();
        if(key.isEmpty()){
            System.out.println("key is empty");
            return 0;
        }
        return notesService.getTotalSearchPostsCount(key);
    }

    @PostMapping("/getPostDetail")
    public GetDetailedNotesRequest getPostDetail(@RequestBody simpleRequest request) {
        int id = request.getId();
        return notesService.getDetailedNotes(id);
    }

}
