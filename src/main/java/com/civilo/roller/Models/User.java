package com.civilo.roller.Models;

import lombok.*;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import java.util.Serializable;
import java.util.List;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class UserEntity {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long userID;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private String commune;
    private LocalDate birthDate;
    private int age;

}
 