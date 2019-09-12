package com.jiangtj.mailsender;

import com.jiangtj.mailsender.dto.SendDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

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
    public void testRender() {
        SendDto dto = new SendDto();
        dto.setRender("md");
        dto.setContent("test *YYYY*, [my blog](https://www.dnocm.com)");
        webTestClient.post().uri("/render")
                .body(BodyInserters.fromObject(dto))
                .exchange()
                .expectBody(String.class)
                .isEqualTo("<p>test <em>YYYY</em>, <a href=\"https://www.dnocm.com\">my blog</a></p>\n")
                .consumeWith(document("test-render"));
    }

}
