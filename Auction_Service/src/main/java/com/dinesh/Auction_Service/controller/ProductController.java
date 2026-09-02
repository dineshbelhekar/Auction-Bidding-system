package com.dinesh.Auction_Service.controller;

import com.dinesh.Auction_Service.entity.Product;
import com.dinesh.Auction_Service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product){
        productService.addNewProduct(product);
        return new ResponseEntity<>("Product added Successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@RequestParam int id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("product deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<String> getById(@RequestParam int id){
        productService.getById(id);
        return new ResponseEntity<>("Product found",HttpStatus.FOUND);
    }


}
