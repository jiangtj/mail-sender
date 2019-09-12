package com.jiangtj.mailsender.render;

import java.util.List;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/12 20:19 End.
 */
public interface Render {

    /**
     * Core function that render content to html
     * @param content need render content
     * @return html
     */
    String render(String content);

    /**
     * Render's name that can be point in request param.
     * Default value is the class name that removing `Render` suffix.
     * @return render names
     */
    default List<String> name() {
        return null;
    }

}
