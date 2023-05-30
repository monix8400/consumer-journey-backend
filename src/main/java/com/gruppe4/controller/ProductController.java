package com.gruppe4.controller;


import com.gruppe4.model.Product;
import com.gruppe4.service.DMNService;
import com.gruppe4.service.ProductService;
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
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final DMNService dmnService;

    private final ResourceLoader resourceLoader;

    private static final Logger log = Logger.getLogger(String.valueOf(ProductController.class));

    @Autowired
    public ProductController(ProductService productService, DMNService dmnService, ResourceLoader resourceLoader) {
        this.productService = productService;
        this.dmnService = dmnService;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
//        product.setId(id);
        Product updatedProduct = productService.updateProduct(product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/dmn/{decisionKey}")
    public ResponseEntity<List<Map<String, Object>>> evaluateDMN(@PathVariable String decisionKey, @RequestBody Map<String, Object> variables) {
        // Load the DMN file from resources
        Resource resource = resourceLoader.getResource("classpath:betterdiag.dmn");
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
