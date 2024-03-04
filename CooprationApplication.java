package com.example.cooperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CooprationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CooprationApplication.class, args);
    }

}
