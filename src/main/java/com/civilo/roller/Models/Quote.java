package com.civilo.roller.Models;

import lombok.*;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import java.util.Serializable;
import java.util.List;

@Entity
@Table(name = "QUOTES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Quote {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long quoteID;
    private String productName;
    private int amount;
    private int value;
    private String description;
    private float commission;

} 
