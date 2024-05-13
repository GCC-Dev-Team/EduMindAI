package edumindai;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("edumindai.mapper")

public class EduMindAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduMindAiApplication.class, args);
    }

}
