package fr.it_akademy_jobapp.service.mapper;

import fr.it_akademy_jobapp.domain.Enterprise;
import fr.it_akademy_jobapp.domain.Job;
import fr.it_akademy_jobapp.domain.Personnage;
import fr.it_akademy_jobapp.service.dto.EnterpriseDTO;
import fr.it_akademy_jobapp.service.dto.JobDTO;
import fr.it_akademy_jobapp.service.dto.PersonnageDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Job} and its DTO {@link JobDTO}.
 */
@Mapper(componentModel = "spring")
public interface JobMapper extends EntityMapper<JobDTO, Job> {
    @Mapping(target = "tasks", source = "tasks", qualifiedByName = "enterpriseIdSet")
    @Mapping(target = "personnage", source = "personnage", qualifiedByName = "personnageId")
    JobDTO toDto(Job s);

    @Mapping(target = "removeTask", ignore = true)
    Job toEntity(JobDTO jobDTO);

    @Named("enterpriseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EnterpriseDTO toDtoEnterpriseId(Enterprise enterprise);

    @Named("enterpriseIdSet")
    default Set<EnterpriseDTO> toDtoEnterpriseIdSet(Set<Enterprise> enterprise) {
        return enterprise.stream().map(this::toDtoEnterpriseId).collect(Collectors.toSet());
    }

    @Named("personnageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonnageDTO toDtoPersonnageId(Personnage personnage);
}
