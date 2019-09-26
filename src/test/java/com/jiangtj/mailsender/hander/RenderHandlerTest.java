package com.jiangtj.mailsender.hander;

import com.jiangtj.mailsender.render.AsciidocRender;
import com.jiangtj.mailsender.render.MarkdownRender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/18 22:52 End.
 */
@Slf4j
class RenderHandlerTest {

    private RenderHandler renderHandler;

    @Test
    void testErrorRender() {
        renderHandler = new RenderHandler(Collections.emptyList());
        String html = renderHandler.render("no-render",
                "test *YYYY*, [my blog](https://www.dnocm.com)");
        assertEquals(html, "test *YYYY*, [my blog](https://www.dnocm.com)");
    }

    @ParameterizedTest
    @ValueSource(strings = {"md", "markdown"})
    void testMarkdown(String renderName) {
        renderHandler = new RenderHandler(Collections.singletonList(new MarkdownRender()));
        String html = renderHandler.render(renderName,
                "test *YYYY*, [my blog](https://www.dnocm.com)");
        assertEquals(html, "<p>test <em>YYYY</em>, <a href=\"https://www.dnocm.com\">my blog</a></p>\n");
        log.info("Render:{} tested ok !", renderName);
    }

    @ParameterizedTest
    @ValueSource(strings = {"adoc", "asciidoc"})
    void testAsciidoc(String renderName) {
        renderHandler = new RenderHandler(Collections.singletonList(new AsciidocRender()));
        String html = renderHandler.render(renderName,
                "test *YYYY*, https://www.dnocm.com[my blog]");
        assertEquals(html, "<div class=\"paragraph\">\n<p>test <strong>YYYY</strong>, <a href=\"https://www.dnocm.com\">my blog</a></p>\n</div>");
        log.info("Render:{} tested ok !", renderName);
    }
}