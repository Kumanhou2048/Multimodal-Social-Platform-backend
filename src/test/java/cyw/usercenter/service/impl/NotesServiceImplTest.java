package cyw.usercenter.service.impl;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import cyw.usercenter.model.domain.Notes;
import cyw.usercenter.service.NotesService;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotesServiceImplTest {
    @Resource
    private NotesService notesService;

    @Test
    public void testadd() {
        Notes notes = new Notes();
        notes.setId(1); // 这里示例赋值为1，实际可根据具体情况设定合适的编号
        notes.setUserAccount("22331009"); // 设置示例用户账号，可替换为真实账号
        notes.setUploadTime(new Date()); // 使用当前时间作为上传时间示例，也可按需求设置特定时间
        notes.setTitle("示例笔记标题");
        notes.setContent("这是笔记的具体内容示例");
        notes.setImageCount(3); // 示例图片数量为3，按需修改
        notes.setNoteType(1); // 示例笔记类型，可按实际情况设定对应类型代码
        notes.setNoteStatus(1); // 示例笔记状态，可按需更改
        notes.setLikes(0); // 初始点赞数设为0，后续可根据实际情况变化
        notes.setCollection(0); // 初始收藏数设为0，同样可变动
        notes.setComments(0); // 初始评论数设为0
        boolean result=notesService.save(notes);
        System.out.println(notes.getId());
        Assertions.assertTrue(result);
    }
}