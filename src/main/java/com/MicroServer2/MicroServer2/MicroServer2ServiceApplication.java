package com.MicroServer2.MicroServer2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MicroServer2ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServer2ServiceApplication.class, args);
    }
}
