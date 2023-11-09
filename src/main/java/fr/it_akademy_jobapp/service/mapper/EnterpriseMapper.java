package fr.it_akademy_jobapp.service.mapper;

import fr.it_akademy_jobapp.domain.Enterprise;
import fr.it_akademy_jobapp.service.dto.EnterpriseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Enterprise} and its DTO {@link EnterpriseDTO}.
 */
@Mapper(componentModel = "spring")
public interface EnterpriseMapper extends EntityMapper<EnterpriseDTO, Enterprise> {}
