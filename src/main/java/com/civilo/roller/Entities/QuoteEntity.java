package com.civilo.roller.Entities;

import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.*;


@Entity
@Table(name = "quotes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuoteEntity {
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long quoteID;
    private int amount;
    private String description;

    private float valueSquareMeters;
    private float width;
    private float height;
    private float totalSquareMeters;
    private float totalFabrics;

    private float bracket;
    private float cap;
    private float pipe;
    private float counterweight;
    private float band;
    private float chain;
    private float totalMaterials;

    private float assembly;
    private float installation;
    private float totalLabor;

    private float productionCost;
    private float saleValue;
    private float percentageDiscount;
    private float IVA;
    private float total;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "SELLER_sellerid")
    private SellerEntity seller;

    @ManyToOne
    @JoinColumn(name = "CURTAINS")
    CurtainEntity curtain;
}