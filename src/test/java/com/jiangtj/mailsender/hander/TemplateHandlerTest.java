package com.jiangtj.mailsender.hander;

import com.jiangtj.mailsender.properties.TemplateProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/18.
 */
@Slf4j
class TemplateHandlerTest {

    private TemplateHandler templateHandler;

    @BeforeEach
    void setUp() throws Exception {
        TemplateProperties properties = new TemplateProperties();
        templateHandler = new TemplateHandler(properties);
    }

    @Test
    void testHandleHtmlWithoutName() {
        Map<String, Object> params = new HashMap<>();
        String result1 = templateHandler.handle(null, params);
        assertNull(result1);
        params.put("body", "b");
        String result2 = templateHandler.handle(null, params);
        assertEquals("b", result2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "simple"})
    void testHandleHtmlWithTemplate(String templateName) {
        Map<String, Object> params = new HashMap<>();
        params.put("user", "Jack");
        params.put("body", "<p>test <em>YYYY</em>, <a href=\"https://www.dnocm.com\">my blog</a></p>");
        String handledHtml = templateHandler.handle(templateName, params);
        log.info("Handled html:\n" + handledHtml);
        assertNotNull(handledHtml);
    }
}