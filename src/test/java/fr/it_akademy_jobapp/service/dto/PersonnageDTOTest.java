package fr.it_akademy_jobapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy_jobapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonnageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonnageDTO.class);
        PersonnageDTO personnageDTO1 = new PersonnageDTO();
        personnageDTO1.setId(1L);
        PersonnageDTO personnageDTO2 = new PersonnageDTO();
        assertThat(personnageDTO1).isNotEqualTo(personnageDTO2);
        personnageDTO2.setId(personnageDTO1.getId());
        assertThat(personnageDTO1).isEqualTo(personnageDTO2);
        personnageDTO2.setId(2L);
        assertThat(personnageDTO1).isNotEqualTo(personnageDTO2);
        personnageDTO1.setId(null);
        assertThat(personnageDTO1).isNotEqualTo(personnageDTO2);
    }
}
