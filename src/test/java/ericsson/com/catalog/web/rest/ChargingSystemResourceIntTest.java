package ericsson.com.catalog.web.rest;

import ericsson.com.catalog.OnCatalogApp;

import ericsson.com.catalog.domain.ChargingSystem;
import ericsson.com.catalog.repository.ChargingSystemRepository;
import ericsson.com.catalog.repository.search.ChargingSystemSearchRepository;
import ericsson.com.catalog.service.ChargingSystemService;
import ericsson.com.catalog.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.Collections;
import java.util.List;


import static ericsson.com.catalog.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ChargingSystemResource REST controller.
 *
 * @see ChargingSystemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnCatalogApp.class)
public class ChargingSystemResourceIntTest {

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OFFER_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_OFFER_TEMPLATE = "BBBBBBBBBB";

    @Autowired
    private ChargingSystemRepository chargingSystemRepository;

    @Autowired
    private ChargingSystemService chargingSystemService;

    /**
     * This repository is mocked in the ericsson.com.catalog.repository.search test package.
     *
     * @see ericsson.com.catalog.repository.search.ChargingSystemSearchRepositoryMockConfiguration
     */
    @Autowired
    private ChargingSystemSearchRepository mockChargingSystemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restChargingSystemMockMvc;

    private ChargingSystem chargingSystem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChargingSystemResource chargingSystemResource = new ChargingSystemResource(chargingSystemService);
        this.restChargingSystemMockMvc = MockMvcBuilders.standaloneSetup(chargingSystemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChargingSystem createEntity() {
        ChargingSystem chargingSystem = new ChargingSystem()
            .serviceName(DEFAULT_SERVICE_NAME)
            .offerTemplate(DEFAULT_OFFER_TEMPLATE);
        return chargingSystem;
    }

    @Before
    public void initTest() {
        chargingSystemRepository.deleteAll();
        chargingSystem = createEntity();
    }

    @Test
    public void createChargingSystem() throws Exception {
        int databaseSizeBeforeCreate = chargingSystemRepository.findAll().size();

        // Create the ChargingSystem
        restChargingSystemMockMvc.perform(post("/api/charging-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chargingSystem)))
            .andExpect(status().isCreated());

        // Validate the ChargingSystem in the database
        List<ChargingSystem> chargingSystemList = chargingSystemRepository.findAll();
        assertThat(chargingSystemList).hasSize(databaseSizeBeforeCreate + 1);
        ChargingSystem testChargingSystem = chargingSystemList.get(chargingSystemList.size() - 1);
        assertThat(testChargingSystem.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testChargingSystem.getOfferTemplate()).isEqualTo(DEFAULT_OFFER_TEMPLATE);

        // Validate the ChargingSystem in Elasticsearch
        verify(mockChargingSystemSearchRepository, times(1)).save(testChargingSystem);
    }

    @Test
    public void createChargingSystemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chargingSystemRepository.findAll().size();

        // Create the ChargingSystem with an existing ID
        chargingSystem.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restChargingSystemMockMvc.perform(post("/api/charging-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chargingSystem)))
            .andExpect(status().isBadRequest());

        // Validate the ChargingSystem in the database
        List<ChargingSystem> chargingSystemList = chargingSystemRepository.findAll();
        assertThat(chargingSystemList).hasSize(databaseSizeBeforeCreate);

        // Validate the ChargingSystem in Elasticsearch
        verify(mockChargingSystemSearchRepository, times(0)).save(chargingSystem);
    }

    @Test
    public void getAllChargingSystems() throws Exception {
        // Initialize the database
        chargingSystemRepository.save(chargingSystem);

        // Get all the chargingSystemList
        restChargingSystemMockMvc.perform(get("/api/charging-systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chargingSystem.getId())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].offerTemplate").value(hasItem(DEFAULT_OFFER_TEMPLATE.toString())));
    }
    
    @Test
    public void getChargingSystem() throws Exception {
        // Initialize the database
        chargingSystemRepository.save(chargingSystem);

        // Get the chargingSystem
        restChargingSystemMockMvc.perform(get("/api/charging-systems/{id}", chargingSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chargingSystem.getId()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME.toString()))
            .andExpect(jsonPath("$.offerTemplate").value(DEFAULT_OFFER_TEMPLATE.toString()));
    }

    @Test
    public void getNonExistingChargingSystem() throws Exception {
        // Get the chargingSystem
        restChargingSystemMockMvc.perform(get("/api/charging-systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateChargingSystem() throws Exception {
        // Initialize the database
        chargingSystemService.save(chargingSystem);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockChargingSystemSearchRepository);

        int databaseSizeBeforeUpdate = chargingSystemRepository.findAll().size();

        // Update the chargingSystem
        ChargingSystem updatedChargingSystem = chargingSystemRepository.findById(chargingSystem.getId()).get();
        updatedChargingSystem
            .serviceName(UPDATED_SERVICE_NAME)
            .offerTemplate(UPDATED_OFFER_TEMPLATE);

        restChargingSystemMockMvc.perform(put("/api/charging-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChargingSystem)))
            .andExpect(status().isOk());

        // Validate the ChargingSystem in the database
        List<ChargingSystem> chargingSystemList = chargingSystemRepository.findAll();
        assertThat(chargingSystemList).hasSize(databaseSizeBeforeUpdate);
        ChargingSystem testChargingSystem = chargingSystemList.get(chargingSystemList.size() - 1);
        assertThat(testChargingSystem.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testChargingSystem.getOfferTemplate()).isEqualTo(UPDATED_OFFER_TEMPLATE);

        // Validate the ChargingSystem in Elasticsearch
        verify(mockChargingSystemSearchRepository, times(1)).save(testChargingSystem);
    }

    @Test
    public void updateNonExistingChargingSystem() throws Exception {
        int databaseSizeBeforeUpdate = chargingSystemRepository.findAll().size();

        // Create the ChargingSystem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChargingSystemMockMvc.perform(put("/api/charging-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chargingSystem)))
            .andExpect(status().isBadRequest());

        // Validate the ChargingSystem in the database
        List<ChargingSystem> chargingSystemList = chargingSystemRepository.findAll();
        assertThat(chargingSystemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ChargingSystem in Elasticsearch
        verify(mockChargingSystemSearchRepository, times(0)).save(chargingSystem);
    }

    @Test
    public void deleteChargingSystem() throws Exception {
        // Initialize the database
        chargingSystemService.save(chargingSystem);

        int databaseSizeBeforeDelete = chargingSystemRepository.findAll().size();

        // Delete the chargingSystem
        restChargingSystemMockMvc.perform(delete("/api/charging-systems/{id}", chargingSystem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChargingSystem> chargingSystemList = chargingSystemRepository.findAll();
        assertThat(chargingSystemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ChargingSystem in Elasticsearch
        verify(mockChargingSystemSearchRepository, times(1)).deleteById(chargingSystem.getId());
    }

    @Test
    public void searchChargingSystem() throws Exception {
        // Initialize the database
        chargingSystemService.save(chargingSystem);
        when(mockChargingSystemSearchRepository.search(queryStringQuery("id:" + chargingSystem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(chargingSystem), PageRequest.of(0, 1), 1));
        // Search the chargingSystem
        restChargingSystemMockMvc.perform(get("/api/_search/charging-systems?query=id:" + chargingSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chargingSystem.getId())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].offerTemplate").value(hasItem(DEFAULT_OFFER_TEMPLATE)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChargingSystem.class);
        ChargingSystem chargingSystem1 = new ChargingSystem();
        chargingSystem1.setId("id1");
        ChargingSystem chargingSystem2 = new ChargingSystem();
        chargingSystem2.setId(chargingSystem1.getId());
        assertThat(chargingSystem1).isEqualTo(chargingSystem2);
        chargingSystem2.setId("id2");
        assertThat(chargingSystem1).isNotEqualTo(chargingSystem2);
        chargingSystem1.setId(null);
        assertThat(chargingSystem1).isNotEqualTo(chargingSystem2);
    }
}
