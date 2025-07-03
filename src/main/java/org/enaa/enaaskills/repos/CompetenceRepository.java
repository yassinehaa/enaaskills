package org.enaa.enaaskills.repos;

import org.enaa.enaaskills.Models.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    Competence findCompetenceById(Long competenceId);

    @Modifying
    @Query("UPDATE Competence c SET c.valide = :valide WHERE c.id = :competenceId")
    void updateCompetenceValidationStatus(@Param("competenceId") Long competenceId, @Param("valide") boolean valide);
}