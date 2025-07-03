package org.enaa.enaaskills.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.enaa.enaaskills.DTO.SubCompetenceDTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubCompetence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private boolean valide;
    @Column(name = "competence_id")
    private Long competenceId;


    public SubCompetence(SubCompetenceDTO subCompetenceDTO) {
    }
}