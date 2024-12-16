package cyw.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cyw.usercenter.model.domain.Likes;
import cyw.usercenter.service.LikesService;
import cyw.usercenter.Mapper.LikesMapper;
import org.springframework.stereotype.Service;

/**
* @author 陈誉文
* @description 针对表【likes】的数据库操作Service实现
* @createDate 2024-12-16 16:22:07
*/
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes>
    implements LikesService{

}




