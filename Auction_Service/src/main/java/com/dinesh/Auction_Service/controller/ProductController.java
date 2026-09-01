package com.dinesh.Auction_Service.controller;

import com.dinesh.Auction_Service.entity.Product;
import com.dinesh.Auction_Service.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody Product product){
        productService.addNewProduct(product);
    }
}
