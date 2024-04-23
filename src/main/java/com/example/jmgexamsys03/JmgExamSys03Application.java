package com.example.jmgexamsys03;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.jmgexamsys03.mapper")
public class JmgExamSys03Application {

    public static void main(String[] args) {
        System.out.println("success");
        SpringApplication.run(JmgExamSys03Application.class, args);

    }

}
