package fr.it_akademy_jobapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy_jobapp.domain.Application} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationDTO implements Serializable {

    private Long id;

    private String name3;

    private EnterpriseDTO enterprise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public EnterpriseDTO getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(EnterpriseDTO enterprise) {
        this.enterprise = enterprise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationDTO)) {
            return false;
        }

        ApplicationDTO applicationDTO = (ApplicationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, applicationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationDTO{" +
            "id=" + getId() +
            ", name3='" + getName3() + "'" +
            ", enterprise=" + getEnterprise() +
            "}";
    }
}
