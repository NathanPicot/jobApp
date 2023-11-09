package fr.it_akademy_jobapp.service;

import fr.it_akademy_jobapp.service.dto.PersonnageDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy_jobapp.domain.Personnage}.
 */
public interface PersonnageService {
    /**
     * Save a personnage.
     *
     * @param personnageDTO the entity to save.
     * @return the persisted entity.
     */
    PersonnageDTO save(PersonnageDTO personnageDTO);

    /**
     * Updates a personnage.
     *
     * @param personnageDTO the entity to update.
     * @return the persisted entity.
     */
    PersonnageDTO update(PersonnageDTO personnageDTO);

    /**
     * Partially updates a personnage.
     *
     * @param personnageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonnageDTO> partialUpdate(PersonnageDTO personnageDTO);

    /**
     * Get all the personnages.
     *
     * @return the list of entities.
     */
    List<PersonnageDTO> findAll();

    /**
     * Get the "id" personnage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonnageDTO> findOne(Long id);

    /**
     * Delete the "id" personnage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
