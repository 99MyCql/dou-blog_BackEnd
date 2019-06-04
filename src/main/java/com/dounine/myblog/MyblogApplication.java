package com.dounine.myblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class MyblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyblogApplication.class, args);
    }

}
