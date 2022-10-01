package com.dpisarenko.basiccamundaapp;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.execute;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.externalTask;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.job;
import static org.camunda.community.mockito.CamundaMockito.registerJavaDelegateMock;
import static org.camunda.community.mockito.CamundaMockito.verifyJavaDelegateMock;
import static org.mockito.Mockito.times;


public class Lesson13ProcessTest {

//    https://github.com/camunda/camunda-bpm-platform/tree/master/test-utils/assert
//    https://docs.camunda.org/manual/7.17/user-guide/testing/
//    https://camunda.com/blog/2020/11/testing-process-dependencies/
//    https://github.com/camunda-community-hub/camunda-platform-7-mockito

    @Rule
    public ProcessEngineRule processEngineRule = new ProcessEngineRule();

    @Test
    @Deployment(resources = {"lesson13_test_process.bpmn"})
    public void givenError_whenRunningLesson13Bpmn_thenProcessError() throws Exception {
        RuntimeService runtimeService = processEngineRule.getRuntimeService();
        registerJavaDelegateMock("SomeInternalServiceTask");
        registerJavaDelegateMock("ProcessError");

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Lesson13_Process");
        assertThat(pi).isStarted();

        assertThat(pi).isWaitingAt("call_internal_service_task");
        execute(job());
        verifyJavaDelegateMock("SomeInternalServiceTask").executed(times(1));

        assertThat(pi).isWaitingAt("call_external_service_task");
        execute(job());
        complete(externalTask("call_external_service_task"));

        assertThat(pi).isWaitingAt("some_message_received_from_external_system");
        execute(job());
        processEngineRule.getRuntimeService()
                .createMessageCorrelation("some_message")
                .processInstanceId(pi.getProcessInstanceId())
                .setVariable("SUCCESS", true)
                .correlate();

        assertThat(pi).isWaitingAt("gateway_open");
        execute(job());

        assertThat(pi).isWaitingAt("gateway_close");
        execute(job());

        assertThat(pi).isEnded();
    }

    @Test
    @Deployment(resources = {"lesson13_test_process.bpmn"})
    public void givenError_whenRunningLesson13Bpmn_thenProcessError2() throws Exception {
        RuntimeService runtimeService = processEngineRule.getRuntimeService();
        registerJavaDelegateMock("SomeInternalServiceTask");
        registerJavaDelegateMock("ProcessError");

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("Lesson13_Process");
        assertThat(pi).isStarted();

        assertThat(pi).isWaitingAt("call_internal_service_task");
        execute(job());
        verifyJavaDelegateMock("SomeInternalServiceTask").executed(times(1));

        assertThat(pi).isWaitingAt("call_external_service_task");
        execute(job());
        complete(externalTask("call_external_service_task"));

        assertThat(pi).isWaitingAt("some_message_received_from_external_system");
        execute(job());
        processEngineRule.getRuntimeService()
                .createMessageCorrelation("some_message")
                .processInstanceId(pi.getProcessInstanceId())
                .setVariable("SUCCESS", false)
                .correlate();

        assertThat(pi).isWaitingAt("gateway_open");
        execute(job());

        assertThat(pi).isWaitingAt("process_error");
        execute(job());
        verifyJavaDelegateMock("ProcessError").executed(times(1));

        assertThat(pi).isWaitingAt("gateway_close");
        execute(job());

        assertThat(pi).isEnded();
    }
}
