package cyw.usercenter.service.impl;

import cyw.usercenter.model.domain.Images;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ImagesServiceImplTest {
    @Resource
    private ImagesServiceImpl imagesService;

    @Test
    void testAddImage() {
        Images images = new Images();
        images.setNoteId(2);
        images.setImageIndex(1);
        images.setImageUrl("/avatar/2.jpg");
        boolean result = imagesService.save(images);
        assertTrue(result);
    }
}