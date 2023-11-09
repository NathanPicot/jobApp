package fr.it_akademy_jobapp.repository;

import fr.it_akademy_jobapp.domain.Enterprise;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Enterprise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {}
