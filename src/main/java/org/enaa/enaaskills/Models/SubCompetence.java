package org.enaa.enaaskills.Models;

import jakarta.persistence.*;
import lombok.*;
import org.enaa.enaaskills.DTO.SubCompetenceDTO;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sub_competence")
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