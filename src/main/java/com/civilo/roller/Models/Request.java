package com.civilo.roller.Models;

import lombok.*;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import java.util.Serializable;
import java.util.List;

@Entity
@Table(name = "REQUEST")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Request {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long requestID;
    private String description;
    private String admissionDate;
    private String closingDate;
    private String commune;
    private String reason;

}
