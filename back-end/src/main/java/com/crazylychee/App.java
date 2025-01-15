package com.crazylychee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.crazylychee"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.out.println("**************************************");
        System.out.println("**************系统启动成功*************");
        System.out.println("**************************************");
    }
}
