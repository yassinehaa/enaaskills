package org.enaa.enaaskills.Services;

import org.enaa.enaaskills.DTO.CompetenceDTO;
import org.enaa.enaaskills.DTO.SubCompetenceDTO;
import org.enaa.enaaskills.Models.Apprenant;
import org.enaa.enaaskills.Models.Competence;
import org.enaa.enaaskills.Models.SubCompetence;
import org.enaa.enaaskills.repos.CompetenceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompetenceService {
    @Autowired
    private CompetenceRepository competenceRepository;


    @Autowired
    private ModelMapper modelMapper;

    public CompetenceDTO createCompetence(CompetenceDTO competenceDTO) {
        Competence competence = modelMapper.map(competenceDTO, Competence.class);
        Competence savedCompetence = competenceRepository.save(competence);
        return modelMapper.map(savedCompetence, CompetenceDTO.class);
    }

        public CompetenceDTO addSubCompetence(Long competenceId, SubCompetenceDTO subCompetenceDTO) {
        Competence competence = competenceRepository.findById(competenceId)
                .orElseThrow(() -> new RuntimeException("Competence not found"));
        competence.getSousCompetences().add(new SubCompetence(subCompetenceDTO));
        Competence savedCompetence = competenceRepository.save(competence);
        return modelMapper.map(savedCompetence, CompetenceDTO.class);
    }


    public CompetenceDTO updateCompetence(Long competenceId, CompetenceDTO competenceDTO) {
        Competence existing = competenceRepository.findCompetenceById(competenceId);
        modelMapper.map(competenceDTO, existing);
        Competence saved = competenceRepository.save(existing);
        return modelMapper.map(saved, CompetenceDTO.class);
    }

    public void deleteCompetence(Long competenceId) {
        competenceRepository.deleteById(competenceId);
    }


}