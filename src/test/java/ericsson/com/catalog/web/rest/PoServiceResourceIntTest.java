package ericsson.com.catalog.web.rest;

import ericsson.com.catalog.OnCatalogApp;

import ericsson.com.catalog.domain.PoService;
import ericsson.com.catalog.repository.PoServiceRepository;
import ericsson.com.catalog.repository.search.PoServiceSearchRepository;
import ericsson.com.catalog.service.PoServiceService;
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

import ericsson.com.catalog.domain.enumeration.PoServiceType;
/**
 * Test class for the PoServiceResource REST controller.
 *
 * @see PoServiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnCatalogApp.class)
public class PoServiceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final PoServiceType DEFAULT_TYPE = PoServiceType.BASIC;
    private static final PoServiceType UPDATED_TYPE = PoServiceType.SERVICE;

    @Autowired
    private PoServiceRepository poServiceRepository;

    @Autowired
    private PoServiceService poServiceService;

    /**
     * This repository is mocked in the ericsson.com.catalog.repository.search test package.
     *
     * @see ericsson.com.catalog.repository.search.PoServiceSearchRepositoryMockConfiguration
     */
    @Autowired
    private PoServiceSearchRepository mockPoServiceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restPoServiceMockMvc;

    private PoService poService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoServiceResource poServiceResource = new PoServiceResource(poServiceService);
        this.restPoServiceMockMvc = MockMvcBuilders.standaloneSetup(poServiceResource)
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
    public static PoService createEntity() {
        PoService poService = new PoService()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE);
        return poService;
    }

    @Before
    public void initTest() {
        poServiceRepository.deleteAll();
        poService = createEntity();
    }

    @Test
    public void createPoService() throws Exception {
        int databaseSizeBeforeCreate = poServiceRepository.findAll().size();

        // Create the PoService
        restPoServiceMockMvc.perform(post("/api/po-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poService)))
            .andExpect(status().isCreated());

        // Validate the PoService in the database
        List<PoService> poServiceList = poServiceRepository.findAll();
        assertThat(poServiceList).hasSize(databaseSizeBeforeCreate + 1);
        PoService testPoService = poServiceList.get(poServiceList.size() - 1);
        assertThat(testPoService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPoService.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the PoService in Elasticsearch
        verify(mockPoServiceSearchRepository, times(1)).save(testPoService);
    }

    @Test
    public void createPoServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poServiceRepository.findAll().size();

        // Create the PoService with an existing ID
        poService.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoServiceMockMvc.perform(post("/api/po-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poService)))
            .andExpect(status().isBadRequest());

        // Validate the PoService in the database
        List<PoService> poServiceList = poServiceRepository.findAll();
        assertThat(poServiceList).hasSize(databaseSizeBeforeCreate);

        // Validate the PoService in Elasticsearch
        verify(mockPoServiceSearchRepository, times(0)).save(poService);
    }

    @Test
    public void getAllPoServices() throws Exception {
        // Initialize the database
        poServiceRepository.save(poService);

        // Get all the poServiceList
        restPoServiceMockMvc.perform(get("/api/po-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poService.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    public void getPoService() throws Exception {
        // Initialize the database
        poServiceRepository.save(poService);

        // Get the poService
        restPoServiceMockMvc.perform(get("/api/po-services/{id}", poService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poService.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    public void getNonExistingPoService() throws Exception {
        // Get the poService
        restPoServiceMockMvc.perform(get("/api/po-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePoService() throws Exception {
        // Initialize the database
        poServiceService.save(poService);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockPoServiceSearchRepository);

        int databaseSizeBeforeUpdate = poServiceRepository.findAll().size();

        // Update the poService
        PoService updatedPoService = poServiceRepository.findById(poService.getId()).get();
        updatedPoService
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE);

        restPoServiceMockMvc.perform(put("/api/po-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPoService)))
            .andExpect(status().isOk());

        // Validate the PoService in the database
        List<PoService> poServiceList = poServiceRepository.findAll();
        assertThat(poServiceList).hasSize(databaseSizeBeforeUpdate);
        PoService testPoService = poServiceList.get(poServiceList.size() - 1);
        assertThat(testPoService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPoService.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the PoService in Elasticsearch
        verify(mockPoServiceSearchRepository, times(1)).save(testPoService);
    }

    @Test
    public void updateNonExistingPoService() throws Exception {
        int databaseSizeBeforeUpdate = poServiceRepository.findAll().size();

        // Create the PoService

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoServiceMockMvc.perform(put("/api/po-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poService)))
            .andExpect(status().isBadRequest());

        // Validate the PoService in the database
        List<PoService> poServiceList = poServiceRepository.findAll();
        assertThat(poServiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PoService in Elasticsearch
        verify(mockPoServiceSearchRepository, times(0)).save(poService);
    }

    @Test
    public void deletePoService() throws Exception {
        // Initialize the database
        poServiceService.save(poService);

        int databaseSizeBeforeDelete = poServiceRepository.findAll().size();

        // Delete the poService
        restPoServiceMockMvc.perform(delete("/api/po-services/{id}", poService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PoService> poServiceList = poServiceRepository.findAll();
        assertThat(poServiceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PoService in Elasticsearch
        verify(mockPoServiceSearchRepository, times(1)).deleteById(poService.getId());
    }

    @Test
    public void searchPoService() throws Exception {
        // Initialize the database
        poServiceService.save(poService);
        when(mockPoServiceSearchRepository.search(queryStringQuery("id:" + poService.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(poService), PageRequest.of(0, 1), 1));
        // Search the poService
        restPoServiceMockMvc.perform(get("/api/_search/po-services?query=id:" + poService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poService.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoService.class);
        PoService poService1 = new PoService();
        poService1.setId("id1");
        PoService poService2 = new PoService();
        poService2.setId(poService1.getId());
        assertThat(poService1).isEqualTo(poService2);
        poService2.setId("id2");
        assertThat(poService1).isNotEqualTo(poService2);
        poService1.setId(null);
        assertThat(poService1).isNotEqualTo(poService2);
    }
}
