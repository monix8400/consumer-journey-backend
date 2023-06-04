package com.gruppe4.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppe4.model.QuestionAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/faq")
public class FAQController {

    private final ObjectMapper objectMapper;

    @Autowired
    public FAQController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<?> getFAQ() {
        try {
            ClassPathResource resource = new ClassPathResource("q_and_a_englisch.json");
            InputStream inputStream = resource.getInputStream();

            List<QuestionAnswer> faqList = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            return ResponseEntity.ok(faqList);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

