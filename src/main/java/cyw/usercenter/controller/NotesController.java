package cyw.usercenter.controller;

import cyw.usercenter.model.request.*;
import cyw.usercenter.service.NotesService;
import cyw.usercenter.service.AiEmbeddingService;
import cyw.usercenter.service.RedisVectorStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@Slf4j
public class NotesController {
    @Resource
    private NotesService notesService;

    @PostMapping("/uploadNote")
    public int uploadNote(@RequestBody NotesUploadRequest noteUploadRequest ) {
        if(noteUploadRequest == null){
            return -1;//null ereor
        }
        String useraccount = noteUploadRequest.getUserAccount();
        String title = noteUploadRequest.getTitle();
        String content = noteUploadRequest.getContent();
        int noteType = noteUploadRequest.getNoteType();
        int imagecount = noteUploadRequest.getImageCount();
        List<String> imageUrl = noteUploadRequest.getImageUrls();
        boolean isAiGenerated = noteUploadRequest.getIsAiGenerated();

        if(StringUtils.isAnyBlank(useraccount, title)){
            return -2;//缺少必要字段
        }
        if(title.length() > 50) {
            return -3;//标题长度超过50！
        }
        if(content.length() > 500) {
            return -4;//内容长度超过500！
        }

        int id = notesService.setNewNote(useraccount, title, content, noteType, imagecount, imageUrl,isAiGenerated);
        if (id > 0) {
            // 异步处理，不阻塞主流程
            CompletableFuture.runAsync(() -> {
                // 取出第一张图片的 Base64
                String firstImgBase64 = (imageUrl != null && !imageUrl.isEmpty()) ? imageUrl.get(0) : "";

                if (!firstImgBase64.isEmpty()) {
                    float[] vector = AiEmbeddingService.getPostEmbedding(firstImgBase64, content);
                    if (vector != null) {
                        // 存入 RedisStack 数据库
                        RedisVectorStore.saveVector(id, vector);
                    }
                }
            });
        }
        if(id == -1){
            return -5;//"保存失败！"
        }

        return 1;//"笔记上传成功"
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

    @PostMapping("/getUserPost")
    public List<GetBriefNotesRequest> getUserPage(@RequestBody GetUserPostRequest request) {

        String userAccount = request.getUserAccount();
        List<GetBriefNotesRequest> userNotes;
        userNotes = notesService.getUserNotes(userAccount);
        if(userNotes == null){
            System.out.println("请求的笔记数量超限！");
            return null;
        }
        return userNotes;
    }

    @PostMapping("/deleteNote")
    public int deleteNote(@RequestBody deleteNoteRequest request){
        int id=request.getId();
        return notesService.deleteNote(id);
    }
}
