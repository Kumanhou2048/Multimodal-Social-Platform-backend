package cyw.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cyw.usercenter.model.domain.Images;
import cyw.usercenter.service.ImagesService;
import cyw.usercenter.Mapper.ImagesMapper;
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




