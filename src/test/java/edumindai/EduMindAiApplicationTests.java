package edumindai;

import edumindai.utils.RegexCheckUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EduMindAiApplicationTests {

    @Test
    void contextLoads() {

        System.out.println(RegexCheckUtil.isPhone("19835930193"));
    }



}
