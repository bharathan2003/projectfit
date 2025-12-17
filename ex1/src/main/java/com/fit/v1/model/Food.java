package com.fit.v1.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foodName;
    private Double quantity;

    private Double caloriesConsumed;  // Energy (kcal)
    private Double protein;           // Protein (g)
}
