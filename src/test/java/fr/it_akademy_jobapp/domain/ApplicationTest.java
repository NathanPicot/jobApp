package fr.it_akademy_jobapp.domain;

import static fr.it_akademy_jobapp.domain.ApplicationTestSamples.*;
import static fr.it_akademy_jobapp.domain.EnterpriseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy_jobapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Application.class);
        Application application1 = getApplicationSample1();
        Application application2 = new Application();
        assertThat(application1).isNotEqualTo(application2);

        application2.setId(application1.getId());
        assertThat(application1).isEqualTo(application2);

        application2 = getApplicationSample2();
        assertThat(application1).isNotEqualTo(application2);
    }

    @Test
    void enterpriseTest() throws Exception {
        Application application = getApplicationRandomSampleGenerator();
        Enterprise enterpriseBack = getEnterpriseRandomSampleGenerator();

        application.setEnterprise(enterpriseBack);
        assertThat(application.getEnterprise()).isEqualTo(enterpriseBack);

        application.enterprise(null);
        assertThat(application.getEnterprise()).isNull();
    }
}
