package com.dinesh.Auction_Service.controller;

import com.dinesh.Auction_Service.entity.Auctions;
import com.dinesh.Auction_Service.service.AuctionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auction")
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService){
        this.auctionService = auctionService;
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<String> createAuction(
            @RequestBody Auctions auction,
            @PathVariable int id){
        auctionService.saveNewAuction(id,auction);
        return new ResponseEntity<>("Auction Created Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAuction(@PathVariable int id){
        auctionService.deleteAuction(id);
        return new ResponseEntity<>("Auction Successfully deleted",HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Auctions>> getByUserID(){
        List<Auctions> list = auctionService.getAuctions();
        return new ResponseEntity<>(list, HttpStatus.FOUND);
    }
}
