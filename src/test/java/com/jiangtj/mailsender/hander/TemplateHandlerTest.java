package com.jiangtj.mailsender.hander;

import com.jiangtj.mailsender.SenderProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/18.
 */
@Slf4j
class TemplateHandlerTest {

    private TemplateHandler templateHandler;

    @BeforeEach
    void setUp() throws Exception {
        SenderProperties properties = new SenderProperties(null);
        templateHandler = new TemplateHandler(properties);
    }

    @Test
    void handle() {
        Map<String, Object> params = new HashMap<>();
        params.put("user", "Jack");
        String handledHtml = templateHandler.handle("test", params);
        log.info("Handled html:\n" + handledHtml);
    }

    @Test
    void handleHtml() {
        Map<String, Object> params = new HashMap<>();
        params.put("body", "<p>test <em>YYYY</em>, <a href=\"https://www.dnocm.com\">my blog</a></p>");
        String handledHtml = templateHandler.handle("simple", params);
        log.info("Handled html:\n" + handledHtml);
    }
}