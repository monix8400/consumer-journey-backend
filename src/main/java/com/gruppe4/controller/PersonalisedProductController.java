package com.gruppe4.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppe4.model.HoneyProduct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/personalisedProduct")
public class PersonalisedProductController {

    @GetMapping(value = "products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HoneyProduct> getHoneyList() throws IOException {
        // Read JSON file
        String jsonFilePath = new ClassPathResource("personalisierte_produkte.json").getFile().getPath();
        byte[] jsonData = Files.readAllBytes(Path.of(jsonFilePath));

        // Convert JSON data to list of Honey objects
        ObjectMapper objectMapper = new ObjectMapper();
        List<HoneyProduct> honeyList = objectMapper.readValue(jsonData, new TypeReference<>() {
        });

        return honeyList;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HoneyProduct getHoneyProductByName(@RequestParam String productName) throws IOException {
        // Read JSON file
        String jsonFilePath = new ClassPathResource("personalisierte_produkte.json").getFile().getPath();
        byte[] jsonData = Files.readAllBytes(Path.of(jsonFilePath));

        // Convert JSON data to list of Honey objects
        ObjectMapper objectMapper = new ObjectMapper();
        List<HoneyProduct> honeyList = objectMapper.readValue(jsonData, new TypeReference<>() {
        });

        // Find the product by name
        Optional<HoneyProduct> matchingProduct = honeyList.stream()
                .filter(product -> product.getName().equalsIgnoreCase(productName))
                .findFirst();

        return matchingProduct.orElse(null); // Return null if product not found
    }


}
