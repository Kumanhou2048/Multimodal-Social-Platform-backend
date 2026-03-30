package cyw.usercenter.controller;

import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import cyw.usercenter.common.BaseResponse;
import cyw.usercenter.common.ErrorCode;
import cyw.usercenter.common.ResultUtils;
import cyw.usercenter.model.domain.AIGenerateRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/ai")
@Slf4j
public class AIController {

    /**
     * 统一接口：根据标题（+可选图片）智能生成正文
     * 路径：/ai/generate/content/smart
     */
    @PostMapping("/generate/content/smart")
    public BaseResponse<String> generateSmartContent(@RequestBody AIGenerateRequest request) {
        // 1. 参数校验
        if (request == null || StringUtils.isBlank(request.getTitle())) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "标题不能为空");
        }

        String promptText;
        if (StringUtils.isNotBlank(request.getImageBase64())) {
            // 有图片
            promptText = String.format(
                    "请根据用户提供的图片内容和标题《%s》创作一段300字左右的社交平台笔记正文。\n" +
                            "要求：风格活泼生动，多使用 Emoji；紧扣图片内容，描述图片中的细节或氛围；开头即正文，禁止废话。",
                    request.getTitle()
            );
        } else {
            // 没图片
            promptText = String.format(
                    "请仅根据标题《%s》进行合理联想，创作一段300字左右的社交平台笔记正文。\n" +
                            "要求：风格活泼生动，多使用 Emoji；开头即正文，禁止废话。",
                    request.getTitle()
            );
        }

        List<Map<String, Object>> userContentList = new ArrayList<>();

        // 如果有图片数据，放入图片 Map
        if (StringUtils.isNotBlank(request.getImageBase64())) {
            Map<String, Object> imageMap = new HashMap<>();

            imageMap.put("image", "data:image/png;base64," + request.getImageBase64());
            userContentList.add(imageMap);
            log.info("AI 正在进行图文融合生成，标题: {}", request.getTitle());
        } else {
            log.info("AI 正在进行纯文本生成，标题: {}", request.getTitle());
        }

        Map<String, Object> textMap = new HashMap<>();
        textMap.put("text", promptText);
        userContentList.add(textMap);

        MultiModalMessage systemMsg = MultiModalMessage.builder()
                .role(Role.SYSTEM.getValue())
                .content(Collections.singletonList(Map.of("text", "你是一个资深社交媒体内容创作者。")))
                .build();

        MultiModalMessage userMsg = MultiModalMessage.builder()
                .role(Role.USER.getValue())
                .content(userContentList) // 使用动态构造好的列表
                .build();

        // 参数
        MultiModalConversationParam param = MultiModalConversationParam.builder()
                .apiKey(System.getenv("DASHSCOPE_API_KEY"))
                .model("qwen3.5-flash")
                .messages(Arrays.asList(systemMsg, userMsg))
                .enableThinking(false)
                .build();

        MultiModalConversation conv = new MultiModalConversation();

        try {
            // 调用多模态对话接口
            MultiModalConversationResult result = conv.call(param);

            if (result.getOutput() != null && !result.getOutput().getChoices().isEmpty()) {
                List<Map<String, Object>> contentList = result.getOutput().getChoices().get(0).getMessage().getContent();
                if (contentList != null && !contentList.isEmpty()) {
                    String aiContent = (String) contentList.get(0).get("text");
                    return ResultUtils.success(aiContent);
                }
            }
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "AI 生成内容为空");

        } catch (Exception e) {
            log.error("Qwen智能调用失败: {}", e.getMessage());
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "AI 暂时无法响应，请稍后再试");
        }
    }
}