package com.example.RestAssuredProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is required")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be a positive value")
    @Column(nullable = false)
    private Double price;

    private String category;

    @Column(length = 1000) // Allow long descriptions
    private String description;
}
