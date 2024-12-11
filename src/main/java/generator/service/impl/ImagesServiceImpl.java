package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Images;
import generator.service.ImagesService;
import generator.mapper.ImagesMapper;
import org.springframework.stereotype.Service;

/**
* @author 陈誉文
* @description 针对表【images】的数据库操作Service实现
* @createDate 2024-12-11 10:48:50
*/
@Service
public class ImagesServiceImpl extends ServiceImpl<ImagesMapper, Images>
    implements ImagesService{

}




