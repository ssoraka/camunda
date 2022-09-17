package com.dpisarenko.basiccamundaapp;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DmnTest1 {
    @Rule
    public ProcessEngineRule processEngineRule = new ProcessEngineRule();

    @Test
    @Deployment(resources = {"lesson9_table.dmn"})
    public void givenCountry_whenEvaluatingDmnTable_thenReturnCorrectResult() {
        DecisionService decisionService = processEngineRule.getDecisionService();
        DmnDecisionTableResult actRes = decisionService.evaluateDecisionTableByKey(
                "EU_Membership",
                Variables.createVariables().putValue("country", "Германия"));
        assertEquals(1, actRes.size());
        assertEquals(Boolean.TRUE, actRes.getSingleResult().getEntry("eu_member"));
    }
}