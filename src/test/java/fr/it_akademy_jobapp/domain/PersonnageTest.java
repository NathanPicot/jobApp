package fr.it_akademy_jobapp.domain;

import static fr.it_akademy_jobapp.domain.JobTestSamples.*;
import static fr.it_akademy_jobapp.domain.PersonnageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy_jobapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PersonnageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personnage.class);
        Personnage personnage1 = getPersonnageSample1();
        Personnage personnage2 = new Personnage();
        assertThat(personnage1).isNotEqualTo(personnage2);

        personnage2.setId(personnage1.getId());
        assertThat(personnage1).isEqualTo(personnage2);

        personnage2 = getPersonnageSample2();
        assertThat(personnage1).isNotEqualTo(personnage2);
    }

    @Test
    void jobsTest() throws Exception {
        Personnage personnage = getPersonnageRandomSampleGenerator();
        Job jobBack = getJobRandomSampleGenerator();

        personnage.addJobs(jobBack);
        assertThat(personnage.getJobs()).containsOnly(jobBack);
        assertThat(jobBack.getPersonnage()).isEqualTo(personnage);

        personnage.removeJobs(jobBack);
        assertThat(personnage.getJobs()).doesNotContain(jobBack);
        assertThat(jobBack.getPersonnage()).isNull();

        personnage.jobs(new HashSet<>(Set.of(jobBack)));
        assertThat(personnage.getJobs()).containsOnly(jobBack);
        assertThat(jobBack.getPersonnage()).isEqualTo(personnage);

        personnage.setJobs(new HashSet<>());
        assertThat(personnage.getJobs()).doesNotContain(jobBack);
        assertThat(jobBack.getPersonnage()).isNull();
    }
}
