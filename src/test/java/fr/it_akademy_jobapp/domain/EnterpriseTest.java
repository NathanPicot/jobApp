package fr.it_akademy_jobapp.domain;

import static fr.it_akademy_jobapp.domain.EnterpriseTestSamples.*;
import static fr.it_akademy_jobapp.domain.JobTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy_jobapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EnterpriseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enterprise.class);
        Enterprise enterprise1 = getEnterpriseSample1();
        Enterprise enterprise2 = new Enterprise();
        assertThat(enterprise1).isNotEqualTo(enterprise2);

        enterprise2.setId(enterprise1.getId());
        assertThat(enterprise1).isEqualTo(enterprise2);

        enterprise2 = getEnterpriseSample2();
        assertThat(enterprise1).isNotEqualTo(enterprise2);
    }

    @Test
    void jobTest() throws Exception {
        Enterprise enterprise = getEnterpriseRandomSampleGenerator();
        Job jobBack = getJobRandomSampleGenerator();

        enterprise.addJob(jobBack);
        assertThat(enterprise.getJobs()).containsOnly(jobBack);
        assertThat(jobBack.getTasks()).containsOnly(enterprise);

        enterprise.removeJob(jobBack);
        assertThat(enterprise.getJobs()).doesNotContain(jobBack);
        assertThat(jobBack.getTasks()).doesNotContain(enterprise);

        enterprise.jobs(new HashSet<>(Set.of(jobBack)));
        assertThat(enterprise.getJobs()).containsOnly(jobBack);
        assertThat(jobBack.getTasks()).containsOnly(enterprise);

        enterprise.setJobs(new HashSet<>());
        assertThat(enterprise.getJobs()).doesNotContain(jobBack);
        assertThat(jobBack.getTasks()).doesNotContain(enterprise);
    }
}
