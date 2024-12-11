package cyw.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cyw.usercenter.model.domain.Images;
import cyw.usercenter.model.domain.Notes;
import cyw.usercenter.service.ImagesService;
import cyw.usercenter.service.NotesService;
import cyw.usercenter.Mapper.NotesMapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 陈誉文
* @description 针对表【notes】的数据库操作Service实现
* @createDate 2024-12-10 21:57:59
*/
@Service
public class NotesServiceImpl extends ServiceImpl<NotesMapper, Notes>
    implements NotesService{
    @Resource
    ImagesService imagesService;

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
}




