package com.dpisarenko;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class Worker2EngineApplication {
    public static void main(String... args){
        SpringApplication.run(Worker2EngineApplication.class, args);
    }
}