package com.dpisarenko;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class DispatcherEngineApplication{
    public static void main(String... args){
        SpringApplication.run(DispatcherEngineApplication.class, args);
    }
}
