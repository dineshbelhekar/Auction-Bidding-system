package com.dinesh.Auction_Service.service;

import com.dinesh.Auction_Service.entity.Auctions;
import com.dinesh.Auction_Service.entity.Product;
import com.dinesh.Auction_Service.exception.AuctionException;
import com.dinesh.Auction_Service.repository.AuctionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ProductService productService;

    public AuctionService(AuctionRepository auctionRepository,
                          ProductService productService){
        this.auctionRepository = auctionRepository;
        this.productService = productService;
    }


    @Transactional
    public void saveNewAuction(int id, Auctions auction) {
        try {
            Product product = productService.getById(id);
            auction.setProduct(product);
            auction.setSellerID(product.getSellerId());
            auctionRepository.save(auction);
        } catch (AuctionException ex){
            throw new AuctionException("failed to create auction");
        }
    }

    @Transactional
    public void deleteAuction(int id) {
        try{
            auctionRepository.deleteById(id);
        } catch (AuctionException e) {
            throw new AuctionException("auction is not deleted");
        }
    }

    public List<Auctions> getAuctions() {
        try {
            Authentication auth = SecurityContextHolder
                    .getContext()
                    .getAuthentication();

             return auctionRepository.
                     findAllBysellerID(Integer
                             .parseInt((String) auth.getPrincipal())
             );

        } catch (AuctionException ex){
            throw new AuctionException("auction not found");
        }
    }
}
