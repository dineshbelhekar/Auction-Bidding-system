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

            product.setSellerId((String) auth.getPrincipal());

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

    public void getById(int id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new ProductException("unable to found the product");
        }
    }
}
