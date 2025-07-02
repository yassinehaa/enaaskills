package org.enaa.enaaskills.Controller;

import org.enaa.enaaskills.DTO.CompetenceDTO;
import org.enaa.enaaskills.DTO.SubCompetenceDTO;
import org.enaa.enaaskills.Models.Apprenant;
import org.enaa.enaaskills.Services.CompetenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competences")
public class CompetenceController {
    @Autowired
    private CompetenceService competenceService;

    @PostMapping
    public CompetenceDTO createcompetence(@RequestBody CompetenceDTO competenceDTO){
        return competenceService.createCompetence(competenceDTO);
    }

    @PostMapping("/{competenceId}/sous-competences")
    public CompetenceDTO createsubcompetence(@PathVariable Long competenceId, @RequestBody SubCompetenceDTO subCompetenceDTO){
        return competenceService.addSubCompetence(competenceId,subCompetenceDTO);
    }

    @PutMapping("/{competenceId}/sous-competences/{subCompetenceId}/apprenants/{apprenantId}")
    public ResponseEntity<Void> toggleSubCompetenceValidation(
            @PathVariable Long competenceId,
            @PathVariable Long subCompetenceId,
            @PathVariable Long apprenantId) {

            competenceService.toggleSubCompetenceValidation(apprenantId, competenceId, subCompetenceId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{competenceId}/apprenants/{apprenantId}/acquise")
    public ResponseEntity<Boolean> isCompetenceAcquise(
            @PathVariable Long competenceId {
            boolean acquise = competenceService.isCompetenceAcquise(competenceId);
            return ResponseEntity.ok(acquise);
    }

    @GetMapping
    public List<CompetenceDTO> getAllCompetences() {
        return competenceService.getAllCompetences();
    }

    @GetMapping("/apprenants")
    public List<Apprenant> getAllApprenants() {
       return competenceService.getAllApprenants();
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

    @GetMapping("/rapport")
    public ResponseEntity<String> generateReport() {
        String report = competenceService.generateReport();
        return ResponseEntity.ok(report);
    }
}
