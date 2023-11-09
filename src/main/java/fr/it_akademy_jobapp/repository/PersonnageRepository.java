package fr.it_akademy_jobapp.repository;

import fr.it_akademy_jobapp.domain.Personnage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Personnage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonnageRepository extends JpaRepository<Personnage, Long> {}
