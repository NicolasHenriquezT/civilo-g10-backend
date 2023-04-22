package com.civilo.roller.Models;

import lombok.*;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.*;
import java.util.Serializable;
import java.util.List;

@Entity
@Table(name = "COVERAGES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Coverage {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long coverageID;
    private List<String> commune;

}
