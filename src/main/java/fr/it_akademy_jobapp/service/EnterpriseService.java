package fr.it_akademy_jobapp.service;

import fr.it_akademy_jobapp.service.dto.EnterpriseDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy_jobapp.domain.Enterprise}.
 */
public interface EnterpriseService {
    /**
     * Save a enterprise.
     *
     * @param enterpriseDTO the entity to save.
     * @return the persisted entity.
     */
    EnterpriseDTO save(EnterpriseDTO enterpriseDTO);

    /**
     * Updates a enterprise.
     *
     * @param enterpriseDTO the entity to update.
     * @return the persisted entity.
     */
    EnterpriseDTO update(EnterpriseDTO enterpriseDTO);

    /**
     * Partially updates a enterprise.
     *
     * @param enterpriseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EnterpriseDTO> partialUpdate(EnterpriseDTO enterpriseDTO);

    /**
     * Get all the enterprises.
     *
     * @return the list of entities.
     */
    List<EnterpriseDTO> findAll();

    /**
     * Get the "id" enterprise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EnterpriseDTO> findOne(Long id);

    /**
     * Delete the "id" enterprise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
