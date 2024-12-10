package cyw.usercenter.controller;

import cyw.usercenter.model.request.NotesUploadRequest;
import cyw.usercenter.model.request.UserRegistRequest;
import cyw.usercenter.service.UsersService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class NotesController {
    @Resource
    private NotesService notesService;

    @PostMapping("/uploadNote")
    public String uploadNote(@RequestBody NotesUploadRequest noteUploadRequest ) {

    }

}
