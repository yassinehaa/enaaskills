package org.enaa.enaaskills.Services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.enaa.enaaskills.Models.SubCompetence;
import org.enaa.enaaskills.repos.CompetenceRepository;
import org.enaa.enaaskills.repos.SubCompetenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubCompetenceService {
    private final SubCompetenceRepository subCompetenceRepository;
    private final CompetenceRepository competenceRepository;

    @Transactional
    public void updateSubCompetenceValidationStatus(Long competenceId, Long subCompetenceId, boolean valid) {
        SubCompetence subCompetence = subCompetenceRepository.findById(subCompetenceId)
                .orElseThrow(() -> new RuntimeException("SubCompetence not found"));

        subCompetence.setValide(valid);
        subCompetenceRepository.save(subCompetence);

        List<SubCompetence> subCompetences = subCompetenceRepository.findAllByCompetenceId(competenceId);

        long total = subCompetences.size();
        long validCount = subCompetences.stream().filter(SubCompetence::isValide).count();
        boolean isCompetenceValid = validCount >= (total / 2);
        competenceRepository.updateCompetenceValidationStatus(competenceId, isCompetenceValid);
    }
}
