package fr.it_akademy_jobapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Job.
 */
@Entity
@Table(name = "job")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name_1")
    private String name1;

    @Column(name = "salary")
    private Double salary;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_job__task", joinColumns = @JoinColumn(name = "job_id"), inverseJoinColumns = @JoinColumn(name = "task_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "jobs" }, allowSetters = true)
    private Set<Enterprise> tasks = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "jobs" }, allowSetters = true)
    private Personnage personnage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Job id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName1() {
        return this.name1;
    }

    public Job name1(String name1) {
        this.setName1(name1);
        return this;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public Double getSalary() {
        return this.salary;
    }

    public Job salary(Double salary) {
        this.setSalary(salary);
        return this;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Set<Enterprise> getTasks() {
        return this.tasks;
    }

    public void setTasks(Set<Enterprise> enterprises) {
        this.tasks = enterprises;
    }

    public Job tasks(Set<Enterprise> enterprises) {
        this.setTasks(enterprises);
        return this;
    }

    public Job addTask(Enterprise enterprise) {
        this.tasks.add(enterprise);
        return this;
    }

    public Job removeTask(Enterprise enterprise) {
        this.tasks.remove(enterprise);
        return this;
    }

    public Personnage getPersonnage() {
        return this.personnage;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }

    public Job personnage(Personnage personnage) {
        this.setPersonnage(personnage);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Job)) {
            return false;
        }
        return getId() != null && getId().equals(((Job) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Job{" +
            "id=" + getId() +
            ", name1='" + getName1() + "'" +
            ", salary=" + getSalary() +
            "}";
    }
}
