package org.enaa.enaaskills.Services;

import jakarta.transaction.Transactional;
import org.enaa.enaaskills.DTO.CompetenceDTO;
import org.enaa.enaaskills.DTO.SubCompetenceDTO;
import org.enaa.enaaskills.Models.Competence;
import org.enaa.enaaskills.Models.SubCompetence;
import org.enaa.enaaskills.repos.CompetenceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetenceService {
    @Autowired
    private CompetenceRepository competenceRepository;


    @Autowired
    private ModelMapper modelMapper;

    public CompetenceDTO createCompetence(CompetenceDTO competenceDTO) {
        Competence competence = modelMapper.map(competenceDTO, Competence.class);

        competence.setValide(false);
        if (competence.getSousCompetences() != null) {
            competence.getSousCompetences().forEach(sc -> sc.setValide(false));
        }
        Competence savedCompetence = competenceRepository.save(competence);
        CompetenceDTO response = modelMapper.map(savedCompetence, CompetenceDTO.class);
        response.setValide(false);

        return response;
    }

    @Transactional
    public CompetenceDTO addSubCompetence(Long competenceId, SubCompetenceDTO subCompetenceDTO) {
        Competence competence = competenceRepository.findCompetenceById(competenceId);
        // Map SubCompetenceDTO to SubCompetence and set default validation status
        SubCompetence subCompetence = modelMapper.map(subCompetenceDTO, SubCompetence.class);
        subCompetence.setValide(false);
        subCompetence.setCompetenceId(competenceId); // Set the foreign key

        competence.getSousCompetences().add(subCompetence);

        Competence savedCompetence = competenceRepository.save(competence);

        long total = savedCompetence.getSousCompetences().size();
        long validCount = savedCompetence.getSousCompetences().stream().filter(SubCompetence::isValide).count();
        boolean isCompetenceValid = validCount >= (total / 2);
        competenceRepository.updateCompetenceValidationStatus(competenceId, isCompetenceValid);

        return modelMapper.map(savedCompetence, CompetenceDTO.class);
    }
    public CompetenceDTO getAllCompetences() {
        List<Competence> competences = competenceRepository.findAll();
        return modelMapper.map(competences, CompetenceDTO.class);
    }
    public Optional<Competence> getCompetenceById(Long competenceId) {
        return competenceRepository.findById(competenceId);
    }


    @Transactional
    public CompetenceDTO updateCompetence(Long competenceId, CompetenceDTO competenceDTO) {
        Competence existing = competenceRepository.findCompetenceById(competenceId);
        existing.setNom(competenceDTO.getNom());
        existing.setValide(competenceDTO.isValide());

        List<SubCompetence> existingSubCompetences = existing.getSousCompetences();
        existingSubCompetences.clear();

        // Add new sub-competences from DTO
        List<SubCompetenceDTO> newSubCompetences = competenceDTO.getSousCompetences();
        if (newSubCompetences != null) {
            for (SubCompetenceDTO subCompetenceDTO : newSubCompetences) {
                SubCompetence subCompetence = modelMapper.map(subCompetenceDTO, SubCompetence.class);
                subCompetence.setCompetenceId(competenceId);
                existingSubCompetences.add(subCompetence);
            }
        }

        // Save the updated competence
        Competence saved = competenceRepository.save(existing);

        // Map to DTO
        return modelMapper.map(saved, CompetenceDTO.class);
    }

    public void deleteCompetence(Long competenceId) {
        competenceRepository.deleteById(competenceId);
    }



}