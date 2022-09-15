package com.dpisarenko.basiccamundaapp;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("ВывестиПроцессуальныеПеременныеВКонсоль")
public class PrintProcessVariablesToConsole implements JavaDelegate {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Override
    public void execute(final DelegateExecution delEx) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("Доступные процессуальные переменные (начало)")
                .append(LINE_SEPARATOR);

        Map<String, Object> variables = delEx.getVariables();
        variables.entrySet().stream().forEach(entry -> {
            sb.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append(LINE_SEPARATOR);
        });

        sb.append("Доступные процессуальные переменные (конец)")
                .append(LINE_SEPARATOR);
        System.out.println(sb.toString());
    }
}