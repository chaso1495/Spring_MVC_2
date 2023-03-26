package hello.itemservice.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {
    @Autowired
    MessageSource ms;

    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageCodeDefaultMessage() {
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    @Test
    void argumentMessage() {
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("안녕 Spring");
    }

    @Test
    void defaultLang() {
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        // locale 정보가 없으면 Local.getDefault()를 사용한다. 이는 기본적으로 ko_KR이므로 실패한 후 messages.properties에 접근한다.
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
        // locale 정보가 있으나 messages_ko.properties가 없으므로 messages.properties에 접근한다.
    }

    @Test
    void enLang() { // Locale.ENGLISH 이므로 message_en.properties 파일을 찾는다.
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH))
                .isEqualTo("hello");
    }
}
