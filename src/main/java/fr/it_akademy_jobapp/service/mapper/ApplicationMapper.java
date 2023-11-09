package fr.it_akademy_jobapp.service.mapper;

import fr.it_akademy_jobapp.domain.Application;
import fr.it_akademy_jobapp.domain.Enterprise;
import fr.it_akademy_jobapp.service.dto.ApplicationDTO;
import fr.it_akademy_jobapp.service.dto.EnterpriseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Application} and its DTO {@link ApplicationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApplicationMapper extends EntityMapper<ApplicationDTO, Application> {
    @Mapping(target = "enterprise", source = "enterprise", qualifiedByName = "enterpriseId")
    ApplicationDTO toDto(Application s);

    @Named("enterpriseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EnterpriseDTO toDtoEnterpriseId(Enterprise enterprise);
}
