package org.enaa.enaaskills.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.enaa.enaaskills.DTO.CompetenceDTO;
import org.enaa.enaaskills.DTO.SubCompetenceDTO;
import org.enaa.enaaskills.Models.Competence;
import org.enaa.enaaskills.Services.CompetenceService;
import org.enaa.enaaskills.Services.ExcelExportService;
import org.enaa.enaaskills.Services.SubCompetenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/competences")
public class CompetenceController {
    @Autowired
    private CompetenceService competenceService;
    @Autowired
    private SubCompetenceService subCompetenceService;
    @Autowired
    private ExcelExportService excelExportService;

    @PostMapping
    public CompetenceDTO createcompetence(@RequestBody CompetenceDTO competenceDTO){
        return competenceService.createCompetence(competenceDTO);
    }

    @PostMapping("/{competenceId}/sous-competences")
    public CompetenceDTO createsubcompetence(@PathVariable Long competenceId, @RequestBody SubCompetenceDTO subCompetenceDTO){
        return competenceService.addSubCompetence(competenceId,subCompetenceDTO);
    }
    @GetMapping
    public List<Competence> getAll(){
        return competenceService.ListCompetence();
    }
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        // 1. Définir le type de contenu de la réponse
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // 2. Créer un nom de fichier dynamique
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=rapportcompetences" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        // 3. Récupérer les données à exporter
        List<Competence> competences = competenceService.ListCompetence();

        // 4. Appeler le service d'export
        excelExportService.exportCompetencesToExcel(competences, response.getOutputStream());
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
    @GetMapping("/{competenceId}")
    public Optional<Competence> getCompetenceById(@PathVariable Long competenceId) {
        return competenceService.getCompetenceById(competenceId);
    }

}


