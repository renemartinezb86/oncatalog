package ericsson.com.catalog.web.rest;

import ericsson.com.catalog.OnCatalogApp;

import ericsson.com.catalog.domain.OptionalService;
import ericsson.com.catalog.repository.OptionalServiceRepository;
import ericsson.com.catalog.repository.search.OptionalServiceSearchRepository;
import ericsson.com.catalog.service.OptionalServiceService;
import ericsson.com.catalog.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

import java.util.ArrayList;
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
 * Test class for the OptionalServiceResource REST controller.
 *
 * @see OptionalServiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnCatalogApp.class)
public class OptionalServiceResourceIntTest {

    private static final String DEFAULT_SERVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CARDINALITY = "AAAAAAAAAA";
    private static final String UPDATED_CARDINALITY = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_GROUP = "BBBBBBBBBB";

    @Autowired
    private OptionalServiceRepository optionalServiceRepository;

    @Mock
    private OptionalServiceRepository optionalServiceRepositoryMock;

    @Mock
    private OptionalServiceService optionalServiceServiceMock;

    @Autowired
    private OptionalServiceService optionalServiceService;

    /**
     * This repository is mocked in the ericsson.com.catalog.repository.search test package.
     *
     * @see ericsson.com.catalog.repository.search.OptionalServiceSearchRepositoryMockConfiguration
     */
    @Autowired
    private OptionalServiceSearchRepository mockOptionalServiceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restOptionalServiceMockMvc;

    private OptionalService optionalService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OptionalServiceResource optionalServiceResource = new OptionalServiceResource(optionalServiceService);
        this.restOptionalServiceMockMvc = MockMvcBuilders.standaloneSetup(optionalServiceResource)
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
    public static OptionalService createEntity() {
        OptionalService optionalService = new OptionalService()
            .serviceId(DEFAULT_SERVICE_ID)
            .cardinality(DEFAULT_CARDINALITY)
            .group(DEFAULT_GROUP);
        return optionalService;
    }

    @Before
    public void initTest() {
        optionalServiceRepository.deleteAll();
        optionalService = createEntity();
    }

    @Test
    public void createOptionalService() throws Exception {
        int databaseSizeBeforeCreate = optionalServiceRepository.findAll().size();

        // Create the OptionalService
        restOptionalServiceMockMvc.perform(post("/api/optional-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionalService)))
            .andExpect(status().isCreated());

        // Validate the OptionalService in the database
        List<OptionalService> optionalServiceList = optionalServiceRepository.findAll();
        assertThat(optionalServiceList).hasSize(databaseSizeBeforeCreate + 1);
        OptionalService testOptionalService = optionalServiceList.get(optionalServiceList.size() - 1);
        assertThat(testOptionalService.getServiceId()).isEqualTo(DEFAULT_SERVICE_ID);
        assertThat(testOptionalService.getCardinality()).isEqualTo(DEFAULT_CARDINALITY);
        assertThat(testOptionalService.getGroup()).isEqualTo(DEFAULT_GROUP);

        // Validate the OptionalService in Elasticsearch
        verify(mockOptionalServiceSearchRepository, times(1)).save(testOptionalService);
    }

    @Test
    public void createOptionalServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optionalServiceRepository.findAll().size();

        // Create the OptionalService with an existing ID
        optionalService.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionalServiceMockMvc.perform(post("/api/optional-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionalService)))
            .andExpect(status().isBadRequest());

        // Validate the OptionalService in the database
        List<OptionalService> optionalServiceList = optionalServiceRepository.findAll();
        assertThat(optionalServiceList).hasSize(databaseSizeBeforeCreate);

        // Validate the OptionalService in Elasticsearch
        verify(mockOptionalServiceSearchRepository, times(0)).save(optionalService);
    }

    @Test
    public void getAllOptionalServices() throws Exception {
        // Initialize the database
        optionalServiceRepository.save(optionalService);

        // Get all the optionalServiceList
        restOptionalServiceMockMvc.perform(get("/api/optional-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionalService.getId())))
            .andExpect(jsonPath("$.[*].serviceId").value(hasItem(DEFAULT_SERVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].cardinality").value(hasItem(DEFAULT_CARDINALITY.toString())))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllOptionalServicesWithEagerRelationshipsIsEnabled() throws Exception {
        OptionalServiceResource optionalServiceResource = new OptionalServiceResource(optionalServiceServiceMock);
        when(optionalServiceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restOptionalServiceMockMvc = MockMvcBuilders.standaloneSetup(optionalServiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOptionalServiceMockMvc.perform(get("/api/optional-services?eagerload=true"))
        .andExpect(status().isOk());

        verify(optionalServiceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllOptionalServicesWithEagerRelationshipsIsNotEnabled() throws Exception {
        OptionalServiceResource optionalServiceResource = new OptionalServiceResource(optionalServiceServiceMock);
            when(optionalServiceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restOptionalServiceMockMvc = MockMvcBuilders.standaloneSetup(optionalServiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOptionalServiceMockMvc.perform(get("/api/optional-services?eagerload=true"))
        .andExpect(status().isOk());

            verify(optionalServiceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getOptionalService() throws Exception {
        // Initialize the database
        optionalServiceRepository.save(optionalService);

        // Get the optionalService
        restOptionalServiceMockMvc.perform(get("/api/optional-services/{id}", optionalService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(optionalService.getId()))
            .andExpect(jsonPath("$.serviceId").value(DEFAULT_SERVICE_ID.toString()))
            .andExpect(jsonPath("$.cardinality").value(DEFAULT_CARDINALITY.toString()))
            .andExpect(jsonPath("$.group").value(DEFAULT_GROUP.toString()));
    }

    @Test
    public void getNonExistingOptionalService() throws Exception {
        // Get the optionalService
        restOptionalServiceMockMvc.perform(get("/api/optional-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOptionalService() throws Exception {
        // Initialize the database
        optionalServiceService.save(optionalService);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockOptionalServiceSearchRepository);

        int databaseSizeBeforeUpdate = optionalServiceRepository.findAll().size();

        // Update the optionalService
        OptionalService updatedOptionalService = optionalServiceRepository.findById(optionalService.getId()).get();
        updatedOptionalService
            .serviceId(UPDATED_SERVICE_ID)
            .cardinality(UPDATED_CARDINALITY)
            .group(UPDATED_GROUP);

        restOptionalServiceMockMvc.perform(put("/api/optional-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOptionalService)))
            .andExpect(status().isOk());

        // Validate the OptionalService in the database
        List<OptionalService> optionalServiceList = optionalServiceRepository.findAll();
        assertThat(optionalServiceList).hasSize(databaseSizeBeforeUpdate);
        OptionalService testOptionalService = optionalServiceList.get(optionalServiceList.size() - 1);
        assertThat(testOptionalService.getServiceId()).isEqualTo(UPDATED_SERVICE_ID);
        assertThat(testOptionalService.getCardinality()).isEqualTo(UPDATED_CARDINALITY);
        assertThat(testOptionalService.getGroup()).isEqualTo(UPDATED_GROUP);

        // Validate the OptionalService in Elasticsearch
        verify(mockOptionalServiceSearchRepository, times(1)).save(testOptionalService);
    }

    @Test
    public void updateNonExistingOptionalService() throws Exception {
        int databaseSizeBeforeUpdate = optionalServiceRepository.findAll().size();

        // Create the OptionalService

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionalServiceMockMvc.perform(put("/api/optional-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionalService)))
            .andExpect(status().isBadRequest());

        // Validate the OptionalService in the database
        List<OptionalService> optionalServiceList = optionalServiceRepository.findAll();
        assertThat(optionalServiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OptionalService in Elasticsearch
        verify(mockOptionalServiceSearchRepository, times(0)).save(optionalService);
    }

    @Test
    public void deleteOptionalService() throws Exception {
        // Initialize the database
        optionalServiceService.save(optionalService);

        int databaseSizeBeforeDelete = optionalServiceRepository.findAll().size();

        // Delete the optionalService
        restOptionalServiceMockMvc.perform(delete("/api/optional-services/{id}", optionalService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OptionalService> optionalServiceList = optionalServiceRepository.findAll();
        assertThat(optionalServiceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OptionalService in Elasticsearch
        verify(mockOptionalServiceSearchRepository, times(1)).deleteById(optionalService.getId());
    }

    @Test
    public void searchOptionalService() throws Exception {
        // Initialize the database
        optionalServiceService.save(optionalService);
        when(mockOptionalServiceSearchRepository.search(queryStringQuery("id:" + optionalService.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(optionalService), PageRequest.of(0, 1), 1));
        // Search the optionalService
        restOptionalServiceMockMvc.perform(get("/api/_search/optional-services?query=id:" + optionalService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionalService.getId())))
            .andExpect(jsonPath("$.[*].serviceId").value(hasItem(DEFAULT_SERVICE_ID)))
            .andExpect(jsonPath("$.[*].cardinality").value(hasItem(DEFAULT_CARDINALITY)))
            .andExpect(jsonPath("$.[*].group").value(hasItem(DEFAULT_GROUP)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionalService.class);
        OptionalService optionalService1 = new OptionalService();
        optionalService1.setId("id1");
        OptionalService optionalService2 = new OptionalService();
        optionalService2.setId(optionalService1.getId());
        assertThat(optionalService1).isEqualTo(optionalService2);
        optionalService2.setId("id2");
        assertThat(optionalService1).isNotEqualTo(optionalService2);
        optionalService1.setId(null);
        assertThat(optionalService1).isNotEqualTo(optionalService2);
    }
}
