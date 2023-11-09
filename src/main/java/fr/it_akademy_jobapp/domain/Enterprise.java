package fr.it_akademy_jobapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Enterprise.
 */
@Entity
@Table(name = "enterprise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Enterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name_2")
    private String name2;

    @Column(name = "nb_employee")
    private Double nbEmployee;

    @Column(name = "international")
    private Boolean international;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enterprise")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "enterprise" }, allowSetters = true)
    private Set<Application> apps = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tasks")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tasks", "personnage" }, allowSetters = true)
    private Set<Job> jobs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Enterprise id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName2() {
        return this.name2;
    }

    public Enterprise name2(String name2) {
        this.setName2(name2);
        return this;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public Double getNbEmployee() {
        return this.nbEmployee;
    }

    public Enterprise nbEmployee(Double nbEmployee) {
        this.setNbEmployee(nbEmployee);
        return this;
    }

    public void setNbEmployee(Double nbEmployee) {
        this.nbEmployee = nbEmployee;
    }

    public Boolean getInternational() {
        return this.international;
    }

    public Enterprise international(Boolean international) {
        this.setInternational(international);
        return this;
    }

    public void setInternational(Boolean international) {
        this.international = international;
    }

    public Set<Application> getApps() {
        return this.apps;
    }

    public void setApps(Set<Application> applications) {
        if (this.apps != null) {
            this.apps.forEach(i -> i.setEnterprise(null));
        }
        if (applications != null) {
            applications.forEach(i -> i.setEnterprise(this));
        }
        this.apps = applications;
    }

    public Enterprise apps(Set<Application> applications) {
        this.setApps(applications);
        return this;
    }

    public Enterprise addApp(Application application) {
        this.apps.add(application);
        application.setEnterprise(this);
        return this;
    }

    public Enterprise removeApp(Application application) {
        this.apps.remove(application);
        application.setEnterprise(null);
        return this;
    }

    public Set<Job> getJobs() {
        return this.jobs;
    }

    public void setJobs(Set<Job> jobs) {
        if (this.jobs != null) {
            this.jobs.forEach(i -> i.removeTask(this));
        }
        if (jobs != null) {
            jobs.forEach(i -> i.addTask(this));
        }
        this.jobs = jobs;
    }

    public Enterprise jobs(Set<Job> jobs) {
        this.setJobs(jobs);
        return this;
    }

    public Enterprise addJob(Job job) {
        this.jobs.add(job);
        job.getTasks().add(this);
        return this;
    }

    public Enterprise removeJob(Job job) {
        this.jobs.remove(job);
        job.getTasks().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enterprise)) {
            return false;
        }
        return getId() != null && getId().equals(((Enterprise) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Enterprise{" +
            "id=" + getId() +
            ", name2='" + getName2() + "'" +
            ", nbEmployee=" + getNbEmployee() +
            ", international='" + getInternational() + "'" +
            "}";
    }
}
