package fr.it_akademy_jobapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class PersonnageMapperTest {

    private PersonnageMapper personnageMapper;

    @BeforeEach
    public void setUp() {
        personnageMapper = new PersonnageMapperImpl();
    }
}
