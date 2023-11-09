package fr.it_akademy_jobapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy_jobapp.IntegrationTest;
import fr.it_akademy_jobapp.domain.Personnage;
import fr.it_akademy_jobapp.repository.PersonnageRepository;
import fr.it_akademy_jobapp.service.dto.PersonnageDTO;
import fr.it_akademy_jobapp.service.mapper.PersonnageMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersonnageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonnageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final Instant DEFAULT_BIRTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/personnages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonnageRepository personnageRepository;

    @Autowired
    private PersonnageMapper personnageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonnageMockMvc;

    private Personnage personnage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personnage createEntity(EntityManager em) {
        Personnage personnage = new Personnage().name(DEFAULT_NAME).age(DEFAULT_AGE).birthday(DEFAULT_BIRTHDAY);
        return personnage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personnage createUpdatedEntity(EntityManager em) {
        Personnage personnage = new Personnage().name(UPDATED_NAME).age(UPDATED_AGE).birthday(UPDATED_BIRTHDAY);
        return personnage;
    }

    @BeforeEach
    public void initTest() {
        personnage = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonnage() throws Exception {
        int databaseSizeBeforeCreate = personnageRepository.findAll().size();
        // Create the Personnage
        PersonnageDTO personnageDTO = personnageMapper.toDto(personnage);
        restPersonnageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personnageDTO)))
            .andExpect(status().isCreated());

        // Validate the Personnage in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeCreate + 1);
        Personnage testPersonnage = personnageList.get(personnageList.size() - 1);
        assertThat(testPersonnage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPersonnage.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPersonnage.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
    }

    @Test
    @Transactional
    void createPersonnageWithExistingId() throws Exception {
        // Create the Personnage with an existing ID
        personnage.setId(1L);
        PersonnageDTO personnageDTO = personnageMapper.toDto(personnage);

        int databaseSizeBeforeCreate = personnageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonnageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personnageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Personnage in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPersonnages() throws Exception {
        // Initialize the database
        personnageRepository.saveAndFlush(personnage);

        // Get all the personnageList
        restPersonnageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personnage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())));
    }

    @Test
    @Transactional
    void getPersonnage() throws Exception {
        // Initialize the database
        personnageRepository.saveAndFlush(personnage);

        // Get the personnage
        restPersonnageMockMvc
            .perform(get(ENTITY_API_URL_ID, personnage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personnage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPersonnage() throws Exception {
        // Get the personnage
        restPersonnageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPersonnage() throws Exception {
        // Initialize the database
        personnageRepository.saveAndFlush(personnage);

        int databaseSizeBeforeUpdate = personnageRepository.findAll().size();

        // Update the personnage
        Personnage updatedPersonnage = personnageRepository.findById(personnage.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPersonnage are not directly saved in db
        em.detach(updatedPersonnage);
        updatedPersonnage.name(UPDATED_NAME).age(UPDATED_AGE).birthday(UPDATED_BIRTHDAY);
        PersonnageDTO personnageDTO = personnageMapper.toDto(updatedPersonnage);

        restPersonnageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personnageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personnageDTO))
            )
            .andExpect(status().isOk());

        // Validate the Personnage in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeUpdate);
        Personnage testPersonnage = personnageList.get(personnageList.size() - 1);
        assertThat(testPersonnage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonnage.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPersonnage.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void putNonExistingPersonnage() throws Exception {
        int databaseSizeBeforeUpdate = personnageRepository.findAll().size();
        personnage.setId(longCount.incrementAndGet());

        // Create the Personnage
        PersonnageDTO personnageDTO = personnageMapper.toDto(personnage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonnageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personnageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personnageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personnage in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonnage() throws Exception {
        int databaseSizeBeforeUpdate = personnageRepository.findAll().size();
        personnage.setId(longCount.incrementAndGet());

        // Create the Personnage
        PersonnageDTO personnageDTO = personnageMapper.toDto(personnage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonnageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personnageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personnage in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonnage() throws Exception {
        int databaseSizeBeforeUpdate = personnageRepository.findAll().size();
        personnage.setId(longCount.incrementAndGet());

        // Create the Personnage
        PersonnageDTO personnageDTO = personnageMapper.toDto(personnage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonnageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personnageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Personnage in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonnageWithPatch() throws Exception {
        // Initialize the database
        personnageRepository.saveAndFlush(personnage);

        int databaseSizeBeforeUpdate = personnageRepository.findAll().size();

        // Update the personnage using partial update
        Personnage partialUpdatedPersonnage = new Personnage();
        partialUpdatedPersonnage.setId(personnage.getId());

        partialUpdatedPersonnage.name(UPDATED_NAME);

        restPersonnageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonnage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonnage))
            )
            .andExpect(status().isOk());

        // Validate the Personnage in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeUpdate);
        Personnage testPersonnage = personnageList.get(personnageList.size() - 1);
        assertThat(testPersonnage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonnage.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPersonnage.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
    }

    @Test
    @Transactional
    void fullUpdatePersonnageWithPatch() throws Exception {
        // Initialize the database
        personnageRepository.saveAndFlush(personnage);

        int databaseSizeBeforeUpdate = personnageRepository.findAll().size();

        // Update the personnage using partial update
        Personnage partialUpdatedPersonnage = new Personnage();
        partialUpdatedPersonnage.setId(personnage.getId());

        partialUpdatedPersonnage.name(UPDATED_NAME).age(UPDATED_AGE).birthday(UPDATED_BIRTHDAY);

        restPersonnageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonnage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonnage))
            )
            .andExpect(status().isOk());

        // Validate the Personnage in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeUpdate);
        Personnage testPersonnage = personnageList.get(personnageList.size() - 1);
        assertThat(testPersonnage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonnage.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPersonnage.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void patchNonExistingPersonnage() throws Exception {
        int databaseSizeBeforeUpdate = personnageRepository.findAll().size();
        personnage.setId(longCount.incrementAndGet());

        // Create the Personnage
        PersonnageDTO personnageDTO = personnageMapper.toDto(personnage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonnageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personnageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personnageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personnage in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonnage() throws Exception {
        int databaseSizeBeforeUpdate = personnageRepository.findAll().size();
        personnage.setId(longCount.incrementAndGet());

        // Create the Personnage
        PersonnageDTO personnageDTO = personnageMapper.toDto(personnage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonnageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personnageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Personnage in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonnage() throws Exception {
        int databaseSizeBeforeUpdate = personnageRepository.findAll().size();
        personnage.setId(longCount.incrementAndGet());

        // Create the Personnage
        PersonnageDTO personnageDTO = personnageMapper.toDto(personnage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonnageMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(personnageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Personnage in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonnage() throws Exception {
        // Initialize the database
        personnageRepository.saveAndFlush(personnage);

        int databaseSizeBeforeDelete = personnageRepository.findAll().size();

        // Delete the personnage
        restPersonnageMockMvc
            .perform(delete(ENTITY_API_URL_ID, personnage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
