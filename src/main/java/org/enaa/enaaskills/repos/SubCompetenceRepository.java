package org.enaa.enaaskills.repos;

import org.enaa.enaaskills.Models.SubCompetence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubCompetenceRepository extends JpaRepository<SubCompetence, Long> {
    List<SubCompetence> findAllByCompetenceId(Long competenceId);

    @Modifying
    @Query("UPDATE SubCompetence sc SET sc.valide = :valid WHERE sc.competenceId = :competenceId")
    void updateValidationStatusByCompetenceId(@Param("competenceId") Long competenceId, @Param("valid") boolean valid);
}