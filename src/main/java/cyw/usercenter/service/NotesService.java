package cyw.usercenter.service;

import cyw.usercenter.model.domain.Notes;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 陈誉文
* @description 针对表【notes】的数据库操作Service
* @createDate 2024-12-10 21:57:59
*/
public interface NotesService extends IService<Notes> {

     int setNewNote(String useraccount, String title, String content, int noteType, int imagecount, List<String> imageUrl);
}
