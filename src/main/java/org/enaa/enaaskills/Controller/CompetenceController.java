package org.enaa.enaaskills.Controller;

import org.enaa.enaaskills.DTO.CompetenceDTO;
import org.enaa.enaaskills.DTO.SubCompetenceDTO;
import org.enaa.enaaskills.Models.Competence;
import org.enaa.enaaskills.Services.CompetenceService;
import org.enaa.enaaskills.Services.SubCompetenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/competences")
public class CompetenceController {
    @Autowired
    private CompetenceService competenceService;
    @Autowired
    private SubCompetenceService subCompetenceService;

    @PostMapping
    public CompetenceDTO createcompetence(@RequestBody CompetenceDTO competenceDTO){
        return competenceService.createCompetence(competenceDTO);
    }

    @PostMapping("/{competenceId}/sous-competences")
    public CompetenceDTO createsubcompetence(@PathVariable Long competenceId, @RequestBody SubCompetenceDTO subCompetenceDTO){
        return competenceService.addSubCompetence(competenceId,subCompetenceDTO);
    }




    @PutMapping("/{competenceId}")
    public CompetenceDTO updateCompetence(
            @PathVariable Long competenceId,
            @RequestBody CompetenceDTO competenceDTO) {
        return competenceService.updateCompetence(competenceId, competenceDTO);
    }

    @DeleteMapping("/{competenceId}")
    public ResponseEntity<Void> deleteCompetence(@PathVariable Long competenceId) {
        try {
            competenceService.deleteCompetence(competenceId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{competenceId}/sous-competences/{subCompetenceId}/validation")
    public void updateValidationStatus(
            @PathVariable Long competenceId,
            @PathVariable Long subCompetenceId,
            @RequestBody boolean valid) {
        subCompetenceService.updateSubCompetenceValidationStatus(competenceId, subCompetenceId, valid);
    }
    @GetMapping
    public CompetenceDTO getAllCompetences() {
        return competenceService.getAllCompetences();
    }
    @GetMapping("/{competenceId}")
    public Optional<Competence> getCompetenceById(@PathVariable Long competenceId) {
        return competenceService.getCompetenceById(competenceId);
    }

}
