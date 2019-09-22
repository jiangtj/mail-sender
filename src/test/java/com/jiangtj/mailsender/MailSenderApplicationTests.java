package com.jiangtj.mailsender;

import com.jiangtj.mailsender.dto.SendRequestBody;
import com.jiangtj.mailsender.dto.SendStream;
import com.jiangtj.mailsender.dto.TemplateDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.HashMap;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigureRestDocs
public class MailSenderApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void contextLoads() {
        webTestClient.get().uri("/")
                .exchange()
                .expectBody(String.class)
                .isEqualTo("hi hi!")
                .consumeWith(document("run"));
    }

    @Test
    public void send() {
        TemplateDto template = new TemplateDto();
        template.setName("simple");
        template.setParams(new HashMap<>());
        SendRequestBody body =  SendRequestBody.builder()
                .to("jiangtjtest@outlook.com")
                .subject("Test Email!")
                .render("md")
                .content("test *YYYY*, [my blog](https://www.dnocm.com)")
                .template(template)
                .build();
        webTestClient.post().uri("/send")
                .syncBody(body)
                .exchange()
                .expectStatus().isOk()
                .expectBody(SendStream.class)
                .consumeWith(document("send",
                        requestFields(
                                fieldWithPath("to").description("Send mail address."),
                                fieldWithPath("subject").description("Mail title."),
                                fieldWithPath("render").description("Render engine name, available value: md,markdown,adoc,asciidoc.").optional(),
                                fieldWithPath("content").description("Need rendered content."),
                                fieldWithPath("template.name").description("Template name, should be simple now.").optional(),
                                fieldWithPath("template.params").description("Template params, it is used when rendering template.").optional()
                        ),
                        responseFields(
                                fieldWithPath("status").description("Http status."),
                                fieldWithPath("message").type("String").optional().description("Some info for fail reason or others.")
                        )
                ));
    }

}
