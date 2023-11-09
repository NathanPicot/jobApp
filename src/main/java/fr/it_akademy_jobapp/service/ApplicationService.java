package fr.it_akademy_jobapp.service;

import fr.it_akademy_jobapp.service.dto.ApplicationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy_jobapp.domain.Application}.
 */
public interface ApplicationService {
    /**
     * Save a application.
     *
     * @param applicationDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicationDTO save(ApplicationDTO applicationDTO);

    /**
     * Updates a application.
     *
     * @param applicationDTO the entity to update.
     * @return the persisted entity.
     */
    ApplicationDTO update(ApplicationDTO applicationDTO);

    /**
     * Partially updates a application.
     *
     * @param applicationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicationDTO> partialUpdate(ApplicationDTO applicationDTO);

    /**
     * Get all the applications.
     *
     * @return the list of entities.
     */
    List<ApplicationDTO> findAll();

    /**
     * Get the "id" application.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationDTO> findOne(Long id);

    /**
     * Delete the "id" application.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
