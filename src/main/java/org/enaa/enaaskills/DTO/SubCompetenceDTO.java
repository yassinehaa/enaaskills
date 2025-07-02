package org.enaa.enaaskills.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubCompetenceDTO {
    private Long id;
    private String nom;
    private boolean valide;
}