package fr.it_akademy_jobapp.service.impl;

import fr.it_akademy_jobapp.domain.Application;
import fr.it_akademy_jobapp.repository.ApplicationRepository;
import fr.it_akademy_jobapp.service.ApplicationService;
import fr.it_akademy_jobapp.service.dto.ApplicationDTO;
import fr.it_akademy_jobapp.service.mapper.ApplicationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy_jobapp.domain.Application}.
 */
@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final Logger log = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    private final ApplicationRepository applicationRepository;

    private final ApplicationMapper applicationMapper;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
    }

    @Override
    public ApplicationDTO save(ApplicationDTO applicationDTO) {
        log.debug("Request to save Application : {}", applicationDTO);
        Application application = applicationMapper.toEntity(applicationDTO);
        application = applicationRepository.save(application);
        return applicationMapper.toDto(application);
    }

    @Override
    public ApplicationDTO update(ApplicationDTO applicationDTO) {
        log.debug("Request to update Application : {}", applicationDTO);
        Application application = applicationMapper.toEntity(applicationDTO);
        application = applicationRepository.save(application);
        return applicationMapper.toDto(application);
    }

    @Override
    public Optional<ApplicationDTO> partialUpdate(ApplicationDTO applicationDTO) {
        log.debug("Request to partially update Application : {}", applicationDTO);

        return applicationRepository
            .findById(applicationDTO.getId())
            .map(existingApplication -> {
                applicationMapper.partialUpdate(existingApplication, applicationDTO);

                return existingApplication;
            })
            .map(applicationRepository::save)
            .map(applicationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDTO> findAll() {
        log.debug("Request to get all Applications");
        return applicationRepository.findAll().stream().map(applicationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationDTO> findOne(Long id) {
        log.debug("Request to get Application : {}", id);
        return applicationRepository.findById(id).map(applicationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Application : {}", id);
        applicationRepository.deleteById(id);
    }
}
