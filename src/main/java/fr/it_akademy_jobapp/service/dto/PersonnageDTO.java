package fr.it_akademy_jobapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy_jobapp.domain.Personnage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonnageDTO implements Serializable {

    private Long id;

    private String name;

    private Integer age;

    private Instant birthday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonnageDTO)) {
            return false;
        }

        PersonnageDTO personnageDTO = (PersonnageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personnageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonnageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", age=" + getAge() +
            ", birthday='" + getBirthday() + "'" +
            "}";
    }
}
