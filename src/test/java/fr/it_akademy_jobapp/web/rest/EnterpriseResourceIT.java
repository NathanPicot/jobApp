package fr.it_akademy_jobapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy_jobapp.IntegrationTest;
import fr.it_akademy_jobapp.domain.Enterprise;
import fr.it_akademy_jobapp.repository.EnterpriseRepository;
import fr.it_akademy_jobapp.service.dto.EnterpriseDTO;
import fr.it_akademy_jobapp.service.mapper.EnterpriseMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link EnterpriseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnterpriseResourceIT {

    private static final String DEFAULT_NAME_2 = "AAAAAAAAAA";
    private static final String UPDATED_NAME_2 = "BBBBBBBBBB";

    private static final Double DEFAULT_NB_EMPLOYEE = 1D;
    private static final Double UPDATED_NB_EMPLOYEE = 2D;

    private static final Boolean DEFAULT_INTERNATIONAL = false;
    private static final Boolean UPDATED_INTERNATIONAL = true;

    private static final String ENTITY_API_URL = "/api/enterprises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnterpriseMockMvc;

    private Enterprise enterprise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enterprise createEntity(EntityManager em) {
        Enterprise enterprise = new Enterprise().name2(DEFAULT_NAME_2).nbEmployee(DEFAULT_NB_EMPLOYEE).international(DEFAULT_INTERNATIONAL);
        return enterprise;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enterprise createUpdatedEntity(EntityManager em) {
        Enterprise enterprise = new Enterprise().name2(UPDATED_NAME_2).nbEmployee(UPDATED_NB_EMPLOYEE).international(UPDATED_INTERNATIONAL);
        return enterprise;
    }

    @BeforeEach
    public void initTest() {
        enterprise = createEntity(em);
    }

    @Test
    @Transactional
    void createEnterprise() throws Exception {
        int databaseSizeBeforeCreate = enterpriseRepository.findAll().size();
        // Create the Enterprise
        EnterpriseDTO enterpriseDTO = enterpriseMapper.toDto(enterprise);
        restEnterpriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enterpriseDTO)))
            .andExpect(status().isCreated());

        // Validate the Enterprise in the database
        List<Enterprise> enterpriseList = enterpriseRepository.findAll();
        assertThat(enterpriseList).hasSize(databaseSizeBeforeCreate + 1);
        Enterprise testEnterprise = enterpriseList.get(enterpriseList.size() - 1);
        assertThat(testEnterprise.getName2()).isEqualTo(DEFAULT_NAME_2);
        assertThat(testEnterprise.getNbEmployee()).isEqualTo(DEFAULT_NB_EMPLOYEE);
        assertThat(testEnterprise.getInternational()).isEqualTo(DEFAULT_INTERNATIONAL);
    }

    @Test
    @Transactional
    void createEnterpriseWithExistingId() throws Exception {
        // Create the Enterprise with an existing ID
        enterprise.setId(1L);
        EnterpriseDTO enterpriseDTO = enterpriseMapper.toDto(enterprise);

        int databaseSizeBeforeCreate = enterpriseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnterpriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enterpriseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enterprise in the database
        List<Enterprise> enterpriseList = enterpriseRepository.findAll();
        assertThat(enterpriseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnterprises() throws Exception {
        // Initialize the database
        enterpriseRepository.saveAndFlush(enterprise);

        // Get all the enterpriseList
        restEnterpriseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enterprise.getId().intValue())))
            .andExpect(jsonPath("$.[*].name2").value(hasItem(DEFAULT_NAME_2)))
            .andExpect(jsonPath("$.[*].nbEmployee").value(hasItem(DEFAULT_NB_EMPLOYEE.doubleValue())))
            .andExpect(jsonPath("$.[*].international").value(hasItem(DEFAULT_INTERNATIONAL.booleanValue())));
    }

    @Test
    @Transactional
    void getEnterprise() throws Exception {
        // Initialize the database
        enterpriseRepository.saveAndFlush(enterprise);

        // Get the enterprise
        restEnterpriseMockMvc
            .perform(get(ENTITY_API_URL_ID, enterprise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enterprise.getId().intValue()))
            .andExpect(jsonPath("$.name2").value(DEFAULT_NAME_2))
            .andExpect(jsonPath("$.nbEmployee").value(DEFAULT_NB_EMPLOYEE.doubleValue()))
            .andExpect(jsonPath("$.international").value(DEFAULT_INTERNATIONAL.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEnterprise() throws Exception {
        // Get the enterprise
        restEnterpriseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEnterprise() throws Exception {
        // Initialize the database
        enterpriseRepository.saveAndFlush(enterprise);

        int databaseSizeBeforeUpdate = enterpriseRepository.findAll().size();

        // Update the enterprise
        Enterprise updatedEnterprise = enterpriseRepository.findById(enterprise.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEnterprise are not directly saved in db
        em.detach(updatedEnterprise);
        updatedEnterprise.name2(UPDATED_NAME_2).nbEmployee(UPDATED_NB_EMPLOYEE).international(UPDATED_INTERNATIONAL);
        EnterpriseDTO enterpriseDTO = enterpriseMapper.toDto(updatedEnterprise);

        restEnterpriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enterpriseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enterpriseDTO))
            )
            .andExpect(status().isOk());

        // Validate the Enterprise in the database
        List<Enterprise> enterpriseList = enterpriseRepository.findAll();
        assertThat(enterpriseList).hasSize(databaseSizeBeforeUpdate);
        Enterprise testEnterprise = enterpriseList.get(enterpriseList.size() - 1);
        assertThat(testEnterprise.getName2()).isEqualTo(UPDATED_NAME_2);
        assertThat(testEnterprise.getNbEmployee()).isEqualTo(UPDATED_NB_EMPLOYEE);
        assertThat(testEnterprise.getInternational()).isEqualTo(UPDATED_INTERNATIONAL);
    }

    @Test
    @Transactional
    void putNonExistingEnterprise() throws Exception {
        int databaseSizeBeforeUpdate = enterpriseRepository.findAll().size();
        enterprise.setId(longCount.incrementAndGet());

        // Create the Enterprise
        EnterpriseDTO enterpriseDTO = enterpriseMapper.toDto(enterprise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnterpriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enterpriseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enterpriseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enterprise in the database
        List<Enterprise> enterpriseList = enterpriseRepository.findAll();
        assertThat(enterpriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnterprise() throws Exception {
        int databaseSizeBeforeUpdate = enterpriseRepository.findAll().size();
        enterprise.setId(longCount.incrementAndGet());

        // Create the Enterprise
        EnterpriseDTO enterpriseDTO = enterpriseMapper.toDto(enterprise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnterpriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enterpriseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enterprise in the database
        List<Enterprise> enterpriseList = enterpriseRepository.findAll();
        assertThat(enterpriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnterprise() throws Exception {
        int databaseSizeBeforeUpdate = enterpriseRepository.findAll().size();
        enterprise.setId(longCount.incrementAndGet());

        // Create the Enterprise
        EnterpriseDTO enterpriseDTO = enterpriseMapper.toDto(enterprise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnterpriseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enterpriseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enterprise in the database
        List<Enterprise> enterpriseList = enterpriseRepository.findAll();
        assertThat(enterpriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnterpriseWithPatch() throws Exception {
        // Initialize the database
        enterpriseRepository.saveAndFlush(enterprise);

        int databaseSizeBeforeUpdate = enterpriseRepository.findAll().size();

        // Update the enterprise using partial update
        Enterprise partialUpdatedEnterprise = new Enterprise();
        partialUpdatedEnterprise.setId(enterprise.getId());

        partialUpdatedEnterprise.name2(UPDATED_NAME_2);

        restEnterpriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnterprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnterprise))
            )
            .andExpect(status().isOk());

        // Validate the Enterprise in the database
        List<Enterprise> enterpriseList = enterpriseRepository.findAll();
        assertThat(enterpriseList).hasSize(databaseSizeBeforeUpdate);
        Enterprise testEnterprise = enterpriseList.get(enterpriseList.size() - 1);
        assertThat(testEnterprise.getName2()).isEqualTo(UPDATED_NAME_2);
        assertThat(testEnterprise.getNbEmployee()).isEqualTo(DEFAULT_NB_EMPLOYEE);
        assertThat(testEnterprise.getInternational()).isEqualTo(DEFAULT_INTERNATIONAL);
    }

    @Test
    @Transactional
    void fullUpdateEnterpriseWithPatch() throws Exception {
        // Initialize the database
        enterpriseRepository.saveAndFlush(enterprise);

        int databaseSizeBeforeUpdate = enterpriseRepository.findAll().size();

        // Update the enterprise using partial update
        Enterprise partialUpdatedEnterprise = new Enterprise();
        partialUpdatedEnterprise.setId(enterprise.getId());

        partialUpdatedEnterprise.name2(UPDATED_NAME_2).nbEmployee(UPDATED_NB_EMPLOYEE).international(UPDATED_INTERNATIONAL);

        restEnterpriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnterprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnterprise))
            )
            .andExpect(status().isOk());

        // Validate the Enterprise in the database
        List<Enterprise> enterpriseList = enterpriseRepository.findAll();
        assertThat(enterpriseList).hasSize(databaseSizeBeforeUpdate);
        Enterprise testEnterprise = enterpriseList.get(enterpriseList.size() - 1);
        assertThat(testEnterprise.getName2()).isEqualTo(UPDATED_NAME_2);
        assertThat(testEnterprise.getNbEmployee()).isEqualTo(UPDATED_NB_EMPLOYEE);
        assertThat(testEnterprise.getInternational()).isEqualTo(UPDATED_INTERNATIONAL);
    }

    @Test
    @Transactional
    void patchNonExistingEnterprise() throws Exception {
        int databaseSizeBeforeUpdate = enterpriseRepository.findAll().size();
        enterprise.setId(longCount.incrementAndGet());

        // Create the Enterprise
        EnterpriseDTO enterpriseDTO = enterpriseMapper.toDto(enterprise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnterpriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enterpriseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enterpriseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enterprise in the database
        List<Enterprise> enterpriseList = enterpriseRepository.findAll();
        assertThat(enterpriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnterprise() throws Exception {
        int databaseSizeBeforeUpdate = enterpriseRepository.findAll().size();
        enterprise.setId(longCount.incrementAndGet());

        // Create the Enterprise
        EnterpriseDTO enterpriseDTO = enterpriseMapper.toDto(enterprise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnterpriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enterpriseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Enterprise in the database
        List<Enterprise> enterpriseList = enterpriseRepository.findAll();
        assertThat(enterpriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnterprise() throws Exception {
        int databaseSizeBeforeUpdate = enterpriseRepository.findAll().size();
        enterprise.setId(longCount.incrementAndGet());

        // Create the Enterprise
        EnterpriseDTO enterpriseDTO = enterpriseMapper.toDto(enterprise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnterpriseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(enterpriseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Enterprise in the database
        List<Enterprise> enterpriseList = enterpriseRepository.findAll();
        assertThat(enterpriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnterprise() throws Exception {
        // Initialize the database
        enterpriseRepository.saveAndFlush(enterprise);

        int databaseSizeBeforeDelete = enterpriseRepository.findAll().size();

        // Delete the enterprise
        restEnterpriseMockMvc
            .perform(delete(ENTITY_API_URL_ID, enterprise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Enterprise> enterpriseList = enterpriseRepository.findAll();
        assertThat(enterpriseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
