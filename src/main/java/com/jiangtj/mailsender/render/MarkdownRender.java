package com.jiangtj.mailsender.render;

import com.jiangtj.common.commonmarkspringstarter.Commonmarks;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/12 20:27 End.
 */
public class MarkdownRender implements Render {

    private final Commonmarks commonmarks;

    public MarkdownRender(Commonmarks commonmarks) {
        this.commonmarks = commonmarks;
    }

    @Override
    public String render(String content) {
        return commonmarks.render(content);
    }

    @Override
    public List<String> name() {
        return Arrays.asList("md","markdown");
    }

}
