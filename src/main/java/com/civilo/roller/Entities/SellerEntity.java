package com.civilo.roller.Entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
//@Table(name = "SELLERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SellerEntity extends UserEntity {
    //Atributos
    private String companyName;
    private boolean disponibility;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "COVERAGES")
    CoverageEntity coverage;

    @OneToMany(mappedBy = "quoteID", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuoteEntity> quoteEntities;



    //Constructor
    public SellerEntity(Long userID, String name, String surname, String email,
                        String password, String phoneNumber, String commune,
                        LocalDate birthDate, int age, String companyName, boolean disponibility) {
        super(userID, name, surname, email, password, phoneNumber, commune, birthDate, age);
        this.companyName = companyName;
        this.disponibility = disponibility;
    }

}
