package com.civilo.roller.Models;

import lombok.*;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import java.util.Serializable;
import java.util.List;

@Entity
@Table(name = "SELLERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Seller {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long sellerID;
    private String companyName;
    private boolean availability;

}
