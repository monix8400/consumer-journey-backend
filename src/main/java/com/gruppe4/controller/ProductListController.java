package com.gruppe4.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppe4.model.HoneyProduct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/productList")
public class ProductListController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HoneyProduct> getHoneyList() throws IOException {
        // Read JSON file
        String jsonFilePath = new ClassPathResource("list_of_products.json").getFile().getPath();
        byte[] jsonData = Files.readAllBytes(Path.of(jsonFilePath));

        // Convert JSON data to list of Honey objects
        ObjectMapper objectMapper = new ObjectMapper();
        List<HoneyProduct> honeyList = objectMapper.readValue(jsonData, new TypeReference<>() {
        });

        return honeyList;
    }

}
