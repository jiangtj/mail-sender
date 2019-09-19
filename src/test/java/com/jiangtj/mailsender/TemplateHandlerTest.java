package com.jiangtj.mailsender;

import com.jiangtj.mailsender.hander.TemplateHandler;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/18.
 */
@Slf4j
public class TemplateHandlerTest {

    private TemplateHandler templateHandler;

    @Before
    public void setUp() throws Exception {
        SenderProperties properties = new SenderProperties();
        templateHandler = new TemplateHandler(properties);
    }

    @Test
    public void handle() {
        Map<String, Object> params = new HashMap<>();
        params.put("user", "Jack");
        try {
            String handledHtml = templateHandler.handle("test", params);
            log.info("Handled html:\n" + handledHtml);
        } catch (IOException | TemplateException e) {
            log.error("Handle error", e);
        }
    }
}