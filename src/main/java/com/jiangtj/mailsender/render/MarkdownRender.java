package com.jiangtj.mailsender.render;

import com.jiangtj.common.commonmarkspringstarter.CommonMark;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/12 20:27 End.
 */
@Service
public class MarkdownRender implements Render {

    private final CommonMark commonMark;

    public MarkdownRender(CommonMark commonMark) {
        this.commonMark = commonMark;
    }

    @Override
    public String render(String content) {
        return commonMark.render(content);
    }

    @Override
    public List<String> name() {
        return Arrays.asList("md","markdown");
    }

}
