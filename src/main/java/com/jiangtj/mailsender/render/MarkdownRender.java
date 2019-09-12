package com.jiangtj.mailsender.render;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/12 20:27 End.
 */
public class MarkdownRender implements Render {

    @Override
    public String render(String content) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    @Override
    public List<String> name() {
        return Arrays.asList("md","markdown");
    }

}
