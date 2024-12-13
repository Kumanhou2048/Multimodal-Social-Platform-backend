package cyw.usercenter.service.impl;

import cyw.usercenter.model.domain.Comments;
import cyw.usercenter.service.CommentsService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommentsServiceImplTest {

    @Resource
    private CommentsService commentsService;

    @Test
    void saveComment() {
        Comments comment = new Comments();
        comment.setUploadTime(new Date());
        comment.setUserId(4);
        comment.setNoteId(13);
        comment.setContent("发这么多原始，全球又要通货膨胀了（悲");
        boolean result = commentsService.save(comment);
        assertTrue(result);
    }
}