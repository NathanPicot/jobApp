package fr.it_akademy_jobapp.service.impl;

import fr.it_akademy_jobapp.domain.Enterprise;
import fr.it_akademy_jobapp.repository.EnterpriseRepository;
import fr.it_akademy_jobapp.service.EnterpriseService;
import fr.it_akademy_jobapp.service.dto.EnterpriseDTO;
import fr.it_akademy_jobapp.service.mapper.EnterpriseMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy_jobapp.domain.Enterprise}.
 */
@Service
@Transactional
public class EnterpriseServiceImpl implements EnterpriseService {

    private final Logger log = LoggerFactory.getLogger(EnterpriseServiceImpl.class);

    private final EnterpriseRepository enterpriseRepository;

    private final EnterpriseMapper enterpriseMapper;

    public EnterpriseServiceImpl(EnterpriseRepository enterpriseRepository, EnterpriseMapper enterpriseMapper) {
        this.enterpriseRepository = enterpriseRepository;
        this.enterpriseMapper = enterpriseMapper;
    }

    @Override
    public EnterpriseDTO save(EnterpriseDTO enterpriseDTO) {
        log.debug("Request to save Enterprise : {}", enterpriseDTO);
        Enterprise enterprise = enterpriseMapper.toEntity(enterpriseDTO);
        enterprise = enterpriseRepository.save(enterprise);
        return enterpriseMapper.toDto(enterprise);
    }

    @Override
    public EnterpriseDTO update(EnterpriseDTO enterpriseDTO) {
        log.debug("Request to update Enterprise : {}", enterpriseDTO);
        Enterprise enterprise = enterpriseMapper.toEntity(enterpriseDTO);
        enterprise = enterpriseRepository.save(enterprise);
        return enterpriseMapper.toDto(enterprise);
    }

    @Override
    public Optional<EnterpriseDTO> partialUpdate(EnterpriseDTO enterpriseDTO) {
        log.debug("Request to partially update Enterprise : {}", enterpriseDTO);

        return enterpriseRepository
            .findById(enterpriseDTO.getId())
            .map(existingEnterprise -> {
                enterpriseMapper.partialUpdate(existingEnterprise, enterpriseDTO);

                return existingEnterprise;
            })
            .map(enterpriseRepository::save)
            .map(enterpriseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnterpriseDTO> findAll() {
        log.debug("Request to get all Enterprises");
        return enterpriseRepository.findAll().stream().map(enterpriseMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EnterpriseDTO> findOne(Long id) {
        log.debug("Request to get Enterprise : {}", id);
        return enterpriseRepository.findById(id).map(enterpriseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Enterprise : {}", id);
        enterpriseRepository.deleteById(id);
    }
}
