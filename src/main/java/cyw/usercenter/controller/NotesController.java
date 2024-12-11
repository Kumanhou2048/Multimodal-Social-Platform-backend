package cyw.usercenter.controller;

import cyw.usercenter.model.request.NotesUploadRequest;
import cyw.usercenter.model.request.UserRegistRequest;
import cyw.usercenter.service.NotesService;
import cyw.usercenter.service.UsersService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

}
