package cyw.usercenter.service;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * AI 多模态向量化服务
 * 用于将帖子图文转换为 1024 维语义向量
 */
@Service
@Slf4j
public class AiEmbeddingService {

    private static final String pythonServiceUrl = "http://localhost:9000/get_vector";

    /**
     * 核心接口：获取帖子图文融合向量
     * @param relativeImgPath 数据库存的图片相对路径，如 "/avatar/xxx.jpg"
     * @param content 帖子文字内容
     * @return 1024维 float数组
     */
    public static float[] getPostEmbedding(String relativeImgPath, String content) {
        try {
            log.info("正在为帖子生成向量，图片路径: {}, 内容长度: {}", relativeImgPath, content.length());

            // 2. 将相对路径指向的【文件内容】转为 Base64
            String base64Img = convertPathToBase64(relativeImgPath);
            if (base64Img == null || base64Img.isEmpty()) {
                log.error("图片转 Base64 失败，路径可能不存在: {}", relativeImgPath);
                return null;
            }

            // 3. 构造请求参数（确保字段名与 Python 脚本中的 img_base64 一致）
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("img_base64", base64Img);
            requestBody.put("text", content);

            // 4. 发送请求给 Python FastAPI 服务
            String response = HttpUtil.post(pythonServiceUrl, JSONUtil.toJsonStr(requestBody));

            // 5. 解析并返回结果
            JSONObject json = JSONUtil.parseObj(response);
            float[] vector = json.getByPath("embedding", float[].class);
            return vector;

        } catch (Exception e) {
            log.error("AI Embedding 流程异常: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 将本地磁盘上的图片文件读取并转换为 Base64 字符串
     */
    private static String convertPathToBase64(String relativePath) {
        try {
            String projectPath = System.getProperty("user.dir");

            File file = new File(projectPath, "../frontend/public" + relativePath);

            if (!file.exists()) {
                // 如果还是找不到，打印出尝试访问的完整路径，方便你对比
                log.error("图片文件依然找不到！尝试访问的绝对路径为: {}", file.getCanonicalPath());
                return null;
            }

            // 3. 读取并转码
            byte[] bytes = FileUtil.readBytes(file);
            log.info("成功读取图片文件，准备上传给 AI 服务...");
            return "data:image/jpeg;base64," + Base64.encode(bytes);

        } catch (Exception e) {
            log.error("读取图片并转码失败: {}", e.getMessage());
            return null;
        }
    }
}
