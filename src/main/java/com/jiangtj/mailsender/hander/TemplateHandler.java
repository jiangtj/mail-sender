package com.jiangtj.mailsender.hander;

import com.jiangtj.mailsender.SenderException;
import com.jiangtj.mailsender.SenderProperties;
import com.jiangtj.mailsender.dto.Result;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/18.
 */
@Slf4j
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

    public String handle(String name, Map<String,Object> params) {
        StringWriter writer = new StringWriter();
        if (!name.contains(".")) {
            name = name + ".ftlh";
        }
        try {
            Template temp = cfg.getTemplate(name);
            temp.process(params, writer);
        } catch (IOException | TemplateException e) {
            log.error("TemplateException", e);
            throw new SenderException(Result.serverError("System Error!"));
        }
        return writer.toString();
    }

}
