package cyw.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cyw.usercenter.model.domain.Notes;
import cyw.usercenter.service.NotesService;
import cyw.usercenter.Mapper.NotesMapper;
import org.springframework.stereotype.Service;

/**
* @author 陈誉文
* @description 针对表【notes】的数据库操作Service实现
* @createDate 2024-12-10 21:57:59
*/
@Service
public class NotesServiceImpl extends ServiceImpl<NotesMapper, Notes>
    implements NotesService{

}




