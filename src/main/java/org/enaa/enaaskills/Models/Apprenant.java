package org.enaa.enaaskills.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apprenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ElementCollection
    @MapKeyColumn(name = "competence_subcompetence")
    @Column(name = "valide")
    private Map<String, Boolean> validations = new HashMap<>();
}