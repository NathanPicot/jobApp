package fr.it_akademy_jobapp.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link fr.it_akademy_jobapp.domain.Job} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobDTO implements Serializable {

    private Long id;

    private String name1;

    private Double salary;

    private Set<EnterpriseDTO> tasks = new HashSet<>();

    private PersonnageDTO personnage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Set<EnterpriseDTO> getTasks() {
        return tasks;
    }

    public void setTasks(Set<EnterpriseDTO> tasks) {
        this.tasks = tasks;
    }

    public PersonnageDTO getPersonnage() {
        return personnage;
    }

    public void setPersonnage(PersonnageDTO personnage) {
        this.personnage = personnage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobDTO)) {
            return false;
        }

        JobDTO jobDTO = (JobDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, jobDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobDTO{" +
            "id=" + getId() +
            ", name1='" + getName1() + "'" +
            ", salary=" + getSalary() +
            ", tasks=" + getTasks() +
            ", personnage=" + getPersonnage() +
            "}";
    }
}
