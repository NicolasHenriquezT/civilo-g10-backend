package com.civilo.roller.Entities;

import lombok.*;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.*;

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

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "ROLE")
    RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "REQUEST")
    RequestEntity request;



    //Constructor
    public UserEntity(Long userID, String name, String surname, String email, String password, String phoneNumber, String commune, LocalDate birthDate, int age) {
    }
}
