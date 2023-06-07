package com.gruppe4.controller;


import com.gruppe4.service.DMNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/dmn")
public class DMNController {
    private final DMNService dmnService;
    private final ResourceLoader resourceLoader;

    private static final Logger log = Logger.getLogger(String.valueOf(DMNController.class));

    @Autowired
    public DMNController(DMNService dmnService, ResourceLoader resourceLoader) {
        this.dmnService = dmnService;
        this.resourceLoader = resourceLoader;
    }

    @PostMapping("/{decisionKey}")
    public ResponseEntity<List<Map<String, Object>>> evaluateDMN(@PathVariable String decisionKey, @RequestBody Map<String, Object> variables) {
        // Load the DMN file from resources
        Resource resource = resourceLoader.getResource("classpath:personalisedHoneyProductsDecisions.dmn");
        InputStream is = null;
        try {
            is = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Evaluate the DMN
        List<Map<String, Object>> result = dmnService.evaluateDMN(is, decisionKey, variables);
        log.log(Level.INFO, result.toString());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
