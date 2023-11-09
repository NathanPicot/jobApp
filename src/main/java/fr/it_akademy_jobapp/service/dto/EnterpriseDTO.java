package fr.it_akademy_jobapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy_jobapp.domain.Enterprise} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EnterpriseDTO implements Serializable {

    private Long id;

    private String name2;

    private Double nbEmployee;

    private Boolean international;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public Double getNbEmployee() {
        return nbEmployee;
    }

    public void setNbEmployee(Double nbEmployee) {
        this.nbEmployee = nbEmployee;
    }

    public Boolean getInternational() {
        return international;
    }

    public void setInternational(Boolean international) {
        this.international = international;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnterpriseDTO)) {
            return false;
        }

        EnterpriseDTO enterpriseDTO = (EnterpriseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, enterpriseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnterpriseDTO{" +
            "id=" + getId() +
            ", name2='" + getName2() + "'" +
            ", nbEmployee=" + getNbEmployee() +
            ", international='" + getInternational() + "'" +
            "}";
    }
}
