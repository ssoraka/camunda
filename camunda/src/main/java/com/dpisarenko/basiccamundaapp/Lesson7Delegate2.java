package com.dpisarenko.basiccamundaapp;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("Делегат2ИзУрока7")
public class Lesson7Delegate2 implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Delegate 2");
    }
}