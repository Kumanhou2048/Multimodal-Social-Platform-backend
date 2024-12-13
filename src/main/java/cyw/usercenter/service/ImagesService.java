package cyw.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cyw.usercenter.model.domain.Images;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
* @author 陈誉文
* @description 针对表【images】的数据库操作Service
* @createDate 2024-12-11 10:48:50
*/
public interface ImagesService extends IService<Images> {
    List<String> getPostPicture(int postId);
}
