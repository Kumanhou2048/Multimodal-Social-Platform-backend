package cyw.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cyw.usercenter.model.domain.Images;
import cyw.usercenter.service.ImagesService;
import cyw.usercenter.mapper.ImagesMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 陈誉文
* @description 针对表【images】的数据库操作Service实现
* @createDate 2024-12-11 10:48:50
*/
@Service
public class ImagesServiceImpl extends ServiceImpl<ImagesMapper, Images>
    implements ImagesService{


    @Override
    public List<String> getPostPicture(int postId) {
        QueryWrapper<Images> qw = new QueryWrapper<Images>();
        qw.eq("noteId", postId);
        List<Images> allImages = this.list(qw);
        if(allImages.isEmpty()){
            System.out.println("请求的笔记不存在!");
            return null;
        }

        List<String> imageUrls = new ArrayList<>();
        for(Images image : allImages){
            imageUrls.add(image.getImageUrl());
        }

        return imageUrls;
    }

}




