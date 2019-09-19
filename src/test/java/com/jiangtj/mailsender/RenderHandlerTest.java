package com.jiangtj.mailsender;

import com.jiangtj.mailsender.hander.RenderHandler;
import com.jiangtj.mailsender.render.AsciidocRender;
import com.jiangtj.mailsender.render.MarkdownRender;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/18 22:52 End.
 */
@Slf4j
public class RenderHandlerTest {

    private RenderHandler renderHandler;

    @Test
    public void testErrorRender() {
        renderHandler = new RenderHandler(Collections.emptyList());
        String html = renderHandler.render("no-render",
                "test *YYYY*, [my blog](https://www.dnocm.com)");
        assertEquals(html, "test *YYYY*, [my blog](https://www.dnocm.com)");
    }

    @Test
    public void testMarkdown() {
        renderHandler = new RenderHandler(Collections.singletonList(new MarkdownRender()));
        Arrays.asList("md","markdown").forEach(name -> {
            String html = renderHandler.render("md",
                    "test *YYYY*, [my blog](https://www.dnocm.com)");
            assertEquals(html, "<p>test <em>YYYY</em>, <a href=\"https://www.dnocm.com\">my blog</a></p>\n");
            log.info("Render:{} tested ok !", name);
        });
    }

    @Test
    public void testAsciidoc() {
        renderHandler = new RenderHandler(Collections.singletonList(new AsciidocRender()));
        Arrays.asList("adoc", "asciidoc").forEach(name -> {
            String html = renderHandler.render("adoc",
                    "test *YYYY*, https://www.dnocm.com[my blog]");
            assertEquals(html, "<div class=\"paragraph\">\n" +
                    "<p>test <strong>YYYY</strong>, <a href=\"https://www.dnocm.com\">my blog</a></p>\n" +
                    "</div>");
            log.info("Render:{} tested ok !", name);
        });
    }
}