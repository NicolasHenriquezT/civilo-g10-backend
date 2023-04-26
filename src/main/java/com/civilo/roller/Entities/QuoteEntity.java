package com.civilo.roller.Entities;

import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.*;


@Entity
@Table(name = "QUOTES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuoteEntity {

    ////////////////////////////////////////////// ATRIBUTOS Y MÉTODOS POR VALIDAR //////////////////////////////////////////////

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

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "SELLER_sellerid")
    private SellerEntity seller;

} 
