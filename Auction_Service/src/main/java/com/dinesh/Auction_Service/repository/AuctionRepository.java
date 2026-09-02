package com.dinesh.Auction_Service.repository;

import com.dinesh.Auction_Service.entity.Auctions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auctions, Integer> {

    List<Auctions> findAllBysellerID(Integer id);
}
