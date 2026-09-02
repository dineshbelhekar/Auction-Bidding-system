package com.dinesh.Auction_Service.service;


import com.dinesh.Auction_Service.entity.Auctions;
import com.dinesh.Auction_Service.entity.Product;
import com.dinesh.Auction_Service.repository.AuctionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private AuctionService auctionService;

    private Product product;
    private Auctions auction;

    @BeforeEach
    void setUp() {

        product = new Product();
        product.setId(1);
        product.setSellerId(10);
        product.setDescription("iPhone 15");

        auction = new Auctions();
        auction.setBasePrice(50000.0);
        auction.setCurrentPrice(50000.0);
        auction.setStatus(true);
    }

    @Test
    void saveNewAuction_shouldCreateAuctionSuccessfully() {

        // Arrange
        when(productService.getById(1)).thenReturn(product);

        // Act
        auctionService.saveNewAuction(1, auction);

        // Assert
        assertEquals(product, auction.getProduct());
        assertEquals(product.getSellerId(), auction.getSellerID());

        verify(productService, times(1)).getById(1);
        verify(auctionRepository, times(1)).save(auction);
    }


}
