package com.gruppe4.service;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DMNService {

    private final DmnEngine dmnEngine;

    public DMNService() {
        DmnEngineConfiguration engineConfiguration = DmnEngineConfiguration.createDefaultDmnEngineConfiguration();
        this.dmnEngine = engineConfiguration.buildEngine();
    }

    public List<Map<String, Object>> evaluateDMN(InputStream dmnFile, String decisionKey, Map<String, Object> variables) {
        try {
            DmnDecision decision = dmnEngine.parseDecision(decisionKey, dmnFile);

            VariableMap variableMap = Variables.createVariables();
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                variableMap.putValue(entry.getKey(), entry.getValue());
            }

            DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variableMap);

            List<Map<String, Object>> resultList = new ArrayList<>();
            for (Map<String, Object> ruleResult : result) {
                Map<String, Object> resultEntry = new HashMap<>(ruleResult);
                resultList.add(resultEntry);
            }

            return resultList;
        } catch (Exception e) {
            // Handle exception
        }
        return null;
    }
}
