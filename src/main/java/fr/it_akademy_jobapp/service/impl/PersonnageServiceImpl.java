package fr.it_akademy_jobapp.service.impl;

import fr.it_akademy_jobapp.domain.Personnage;
import fr.it_akademy_jobapp.repository.PersonnageRepository;
import fr.it_akademy_jobapp.service.PersonnageService;
import fr.it_akademy_jobapp.service.dto.PersonnageDTO;
import fr.it_akademy_jobapp.service.mapper.PersonnageMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy_jobapp.domain.Personnage}.
 */
@Service
@Transactional
public class PersonnageServiceImpl implements PersonnageService {

    private final Logger log = LoggerFactory.getLogger(PersonnageServiceImpl.class);

    private final PersonnageRepository personnageRepository;

    private final PersonnageMapper personnageMapper;

    public PersonnageServiceImpl(PersonnageRepository personnageRepository, PersonnageMapper personnageMapper) {
        this.personnageRepository = personnageRepository;
        this.personnageMapper = personnageMapper;
    }

    @Override
    public PersonnageDTO save(PersonnageDTO personnageDTO) {
        log.debug("Request to save Personnage : {}", personnageDTO);
        Personnage personnage = personnageMapper.toEntity(personnageDTO);
        personnage = personnageRepository.save(personnage);
        return personnageMapper.toDto(personnage);
    }

    @Override
    public PersonnageDTO update(PersonnageDTO personnageDTO) {
        log.debug("Request to update Personnage : {}", personnageDTO);
        Personnage personnage = personnageMapper.toEntity(personnageDTO);
        personnage = personnageRepository.save(personnage);
        return personnageMapper.toDto(personnage);
    }

    @Override
    public Optional<PersonnageDTO> partialUpdate(PersonnageDTO personnageDTO) {
        log.debug("Request to partially update Personnage : {}", personnageDTO);

        return personnageRepository
            .findById(personnageDTO.getId())
            .map(existingPersonnage -> {
                personnageMapper.partialUpdate(existingPersonnage, personnageDTO);

                return existingPersonnage;
            })
            .map(personnageRepository::save)
            .map(personnageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonnageDTO> findAll() {
        log.debug("Request to get all Personnages");
        return personnageRepository.findAll().stream().map(personnageMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonnageDTO> findOne(Long id) {
        log.debug("Request to get Personnage : {}", id);
        return personnageRepository.findById(id).map(personnageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personnage : {}", id);
        personnageRepository.deleteById(id);
    }
}
