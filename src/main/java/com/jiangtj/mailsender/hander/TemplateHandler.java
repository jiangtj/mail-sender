package com.jiangtj.mailsender.hander;

import com.jiangtj.mailsender.SenderProperties;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/18.
 */
public class TemplateHandler {

    private Configuration cfg;

    public TemplateHandler(SenderProperties properties) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(ResourceUtils.getFile(properties.getTemplateDir()));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        this.cfg = cfg;
    }

    public String handle(String name, Map<String,Object> params) throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        if (!name.contains(".")) {
            name = name + ".ftlh";
        }
        Template temp = cfg.getTemplate(name);
        temp.process(params, writer);
        return writer.toString();
    }

}
