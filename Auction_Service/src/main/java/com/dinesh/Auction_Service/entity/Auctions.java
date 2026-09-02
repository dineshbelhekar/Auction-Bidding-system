package com.dinesh.Auction_Service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class Auctions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer sellerID;

    private Double basePrice;

    private Double currentPrice;

    private Timestamp startTime;

    private Timestamp endTime;

    private Boolean status;

    private Integer winnerId;

    @CreationTimestamp
    private Timestamp createdAt;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

}
