package fr.it_akademy_jobapp.service.mapper;

import fr.it_akademy_jobapp.domain.Personnage;
import fr.it_akademy_jobapp.service.dto.PersonnageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Personnage} and its DTO {@link PersonnageDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonnageMapper extends EntityMapper<PersonnageDTO, Personnage> {}
