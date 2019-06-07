package com.dounine.myblog;

/***
 * @project myblog
 * @class MyblogApplication
 * @author douNine
 * @date 2019/6/7 16:09
 * @description TODO
 */

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
