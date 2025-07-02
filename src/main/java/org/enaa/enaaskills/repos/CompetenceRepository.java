package org.enaa.enaaskills.repos;

import org.enaa.enaaskills.Models.Competence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetenceRepository extends JpaRepository<Competence,Long> {
    Competence findCompetenceById(Long CompetenceId);
}
