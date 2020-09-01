package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
// @SpringBootApplication 等同于下面三个注解
// @EnableAutoConfiguration 激活自动装配
// @Configuration  声明被标注为配置类
// @ComponentScan 激活@Componment的扫描
@SpringBootApplication
@ServletComponentScan("demo.filter")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
