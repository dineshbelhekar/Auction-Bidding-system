package com.dinesh.Auction_Service.service;

import com.dinesh.Auction_Service.entity.Product;
import com.dinesh.Auction_Service.exception.ProductException;
import com.dinesh.Auction_Service.repository.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Transactional
    public void addNewProduct(Product product) {
        try {
            Authentication auth = SecurityContextHolder
                    .getContext()
                    .getAuthentication();

            String id = (String) auth.getPrincipal();
            product.setSellerId(Integer.parseInt(id));
            System.out.println(id);
            productRepository.save(product);

        } catch (Exception e) {
            throw new ProductException("Unable to save product");
        }
    }

    @Transactional
    public void deleteProduct(int id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new ProductException("Unable to delete product");
        }
    }

    public Product getById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductException("Product Not found")
                );
    }
}
