package fr.it_akademy_jobapp.domain;

import static fr.it_akademy_jobapp.domain.EnterpriseTestSamples.*;
import static fr.it_akademy_jobapp.domain.JobTestSamples.*;
import static fr.it_akademy_jobapp.domain.PersonnageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy_jobapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class JobTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Job.class);
        Job job1 = getJobSample1();
        Job job2 = new Job();
        assertThat(job1).isNotEqualTo(job2);

        job2.setId(job1.getId());
        assertThat(job1).isEqualTo(job2);

        job2 = getJobSample2();
        assertThat(job1).isNotEqualTo(job2);
    }

    @Test
    void taskTest() throws Exception {
        Job job = getJobRandomSampleGenerator();
        Enterprise enterpriseBack = getEnterpriseRandomSampleGenerator();

        job.addTask(enterpriseBack);
        assertThat(job.getTasks()).containsOnly(enterpriseBack);

        job.removeTask(enterpriseBack);
        assertThat(job.getTasks()).doesNotContain(enterpriseBack);

        job.tasks(new HashSet<>(Set.of(enterpriseBack)));
        assertThat(job.getTasks()).containsOnly(enterpriseBack);

        job.setTasks(new HashSet<>());
        assertThat(job.getTasks()).doesNotContain(enterpriseBack);
    }

    @Test
    void personnageTest() throws Exception {
        Job job = getJobRandomSampleGenerator();
        Personnage personnageBack = getPersonnageRandomSampleGenerator();

        job.setPersonnage(personnageBack);
        assertThat(job.getPersonnage()).isEqualTo(personnageBack);

        job.personnage(null);
        assertThat(job.getPersonnage()).isNull();
    }
}
