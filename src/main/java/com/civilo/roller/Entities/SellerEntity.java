package com.civilo.roller.Entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sellers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SellerEntity extends UserEntity {
    //Atributos
    private String companyName;
    private boolean disponibility;
    private String rut;
    private String bank;
    private String bankAccountType;
    private int bankAccountNumber; 

    @ElementCollection
    private List<Integer> coverageID;

    //Relaciones


    @OneToMany(mappedBy = "quoteID", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuoteEntity> quoteEntities;



    //Constructor
    public SellerEntity(Long userID, String name, String surname, String email,
                        String password, String phoneNumber, String commune,
                        LocalDate birthDate, int age, RoleEntity role, String companyName, boolean disponibility,
                        String rut, String bank, String bankAccountType, int bankAccountNumber) {
        super(userID, name, surname, email, password, phoneNumber, commune, birthDate, age, role);
        this.companyName = companyName;
        this.disponibility = disponibility;
        this.rut = rut;
        this.bank = bank;
        this.bankAccountType = bankAccountType;
        this.bankAccountNumber = bankAccountNumber;
    }

}
