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
    public void testErrorRender() {
        testRender(
                "no-render",
                "test *YYYY*, [my blog](https://www.dnocm.com)",
                "test *YYYY*, [my blog](https://www.dnocm.com)"
        );
    }

    @Test
    public void testMarkdown() {
        testRender(
                "md",
                "test *YYYY*, [my blog](https://www.dnocm.com)",
                "<p>test <em>YYYY</em>, <a href=\"https://www.dnocm.com\">my blog</a></p>\n"
        );
    }

    @Test
    public void testAsciidoc() {
        testRender(
                "adoc",
                "= Hello, AsciiDoc!\n" +
                        "Doc Writer <doc@example.com>\n" +
                        "\n" +
                        "An introduction to http://asciidoc.org[AsciiDoc].\n" +
                        "\n" +
                        "== First Section\n" +
                        "\n" +
                        "* item 1\n" +
                        "* item 2\n" +
                        "\n" +
                        "[source,ruby]\n" +
                        "puts \"Hello, World!\"",
                "<div id=\"preamble\">\n" +
                        "<div class=\"sectionbody\">\n" +
                        "<div class=\"paragraph\">\n" +
                        "<p>An introduction to <a href=\"http://asciidoc.org\">AsciiDoc</a>.</p>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "<div class=\"sect1\">\n" +
                        "<h2 id=\"_first_section\">First Section</h2>\n" +
                        "<div class=\"sectionbody\">\n" +
                        "<div class=\"ulist\">\n" +
                        "<ul>\n" +
                        "<li>\n" +
                        "<p>item 1</p>\n" +
                        "</li>\n" +
                        "<li>\n" +
                        "<p>item 2</p>\n" +
                        "</li>\n" +
                        "</ul>\n" +
                        "</div>\n" +
                        "<div class=\"listingblock\">\n" +
                        "<div class=\"content\">\n" +
                        "<pre class=\"highlight\"><code class=\"language-ruby\" data-lang=\"ruby\">puts \"Hello, World!\"</code></pre>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>\n" +
                        "</div>"
        );
    }

    public void testRender(String renderName, String content, String result) {
        SendDto dto = new SendDto();
        dto.setRender(renderName);
        dto.setContent(content);
        webTestClient.post().uri("/render")
                .body(BodyInserters.fromObject(dto))
                .exchange()
                .expectBody(String.class)
                .isEqualTo(result);
    }

}
