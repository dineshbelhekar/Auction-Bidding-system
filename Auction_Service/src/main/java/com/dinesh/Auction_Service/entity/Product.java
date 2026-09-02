package com.dinesh.Auction_Service.entity;

import com.dinesh.Auction_Service.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.util.Date;


@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String sellerId;

    private String description;

    private String brand;

    private Date releaseDate;

    private boolean productAvailable;

    private String imageURL;

    @Enumerated(EnumType.STRING)
    private Category category;

    @CreationTimestamp
    private Timestamp createdAt;

}
