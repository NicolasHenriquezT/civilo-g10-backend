package com.civilo.roller.Entities;

import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestEntity {
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

    @ElementCollection
    private List<Integer> userID;

    //Relaciones
    @ManyToOne
    @JoinColumn(name = "USERS")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "STATUS")
    StatusEntity status;
}
