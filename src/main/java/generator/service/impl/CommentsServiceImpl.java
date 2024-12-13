package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Comments;
import generator.service.CommentsService;
import generator.mapper.CommentsMapper;
import org.springframework.stereotype.Service;

/**
* @author 陈誉文
* @description 针对表【comments】的数据库操作Service实现
* @createDate 2024-12-13 15:10:04
*/
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments>
    implements CommentsService{

}




