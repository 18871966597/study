package com.ll.eurakclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EurakClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurakClientApplication.class, args);
    }

}
