package com.jiangtj.mailsender.hander;

import com.jiangtj.mailsender.SenderException;
import com.jiangtj.mailsender.dto.Result;
import com.jiangtj.mailsender.properties.TemplateProperties;
import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2019/9/18.
 */
@Slf4j
public class TemplateHandler {

    private final Configuration cfg;

    public TemplateHandler(TemplateProperties properties) throws IOException {
        String version = properties.getVersion();
        Configuration cfg = new Configuration(StringUtils.hasLength(version) ? new Version(version) : Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setDirectoryForTemplateLoading(ResourceUtils.getFile(properties.getDir()));
        cfg.setDefaultEncoding(properties.getEncode());
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(properties.isLogTemplateExceptions());
        cfg.setWrapUncheckedExceptions(properties.isWrapUncheckedExceptions());
        cfg.setFallbackOnNullLoopVariable(properties.isFallbackOnNullLoopVariable());
        this.cfg = cfg;
    }

    public String handle(String name, Map<String,Object> params) {
        StringWriter writer = new StringWriter();
        if (!StringUtils.hasLength(name)) {
            return (String) params.get("body");
        }
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
