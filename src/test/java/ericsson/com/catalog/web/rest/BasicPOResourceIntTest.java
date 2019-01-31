package ericsson.com.catalog.web.rest;

import ericsson.com.catalog.OnCatalogApp;

import ericsson.com.catalog.domain.BasicPO;
import ericsson.com.catalog.repository.BasicPORepository;
import ericsson.com.catalog.repository.search.BasicPOSearchRepository;
import ericsson.com.catalog.service.BasicPOService;
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
 * Test class for the BasicPOResource REST controller.
 *
 * @see BasicPOResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnCatalogApp.class)
public class BasicPOResourceIntTest {

    private static final String DEFAULT_PO_ID = "AAAAAAAAAA";
    private static final String UPDATED_PO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BasicPORepository basicPORepository;

    @Mock
    private BasicPORepository basicPORepositoryMock;

    @Mock
    private BasicPOService basicPOServiceMock;

    @Autowired
    private BasicPOService basicPOService;

    /**
     * This repository is mocked in the ericsson.com.catalog.repository.search test package.
     *
     * @see ericsson.com.catalog.repository.search.BasicPOSearchRepositoryMockConfiguration
     */
    @Autowired
    private BasicPOSearchRepository mockBasicPOSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restBasicPOMockMvc;

    private BasicPO basicPO;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BasicPOResource basicPOResource = new BasicPOResource(basicPOService);
        this.restBasicPOMockMvc = MockMvcBuilders.standaloneSetup(basicPOResource)
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
    public static BasicPO createEntity() {
        BasicPO basicPO = new BasicPO()
            .poId(DEFAULT_PO_ID)
            .name(DEFAULT_NAME);
        return basicPO;
    }

    @Before
    public void initTest() {
        basicPORepository.deleteAll();
        basicPO = createEntity();
    }

    @Test
    public void createBasicPO() throws Exception {
        int databaseSizeBeforeCreate = basicPORepository.findAll().size();

        // Create the BasicPO
        restBasicPOMockMvc.perform(post("/api/basic-pos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basicPO)))
            .andExpect(status().isCreated());

        // Validate the BasicPO in the database
        List<BasicPO> basicPOList = basicPORepository.findAll();
        assertThat(basicPOList).hasSize(databaseSizeBeforeCreate + 1);
        BasicPO testBasicPO = basicPOList.get(basicPOList.size() - 1);
        assertThat(testBasicPO.getPoId()).isEqualTo(DEFAULT_PO_ID);
        assertThat(testBasicPO.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the BasicPO in Elasticsearch
        verify(mockBasicPOSearchRepository, times(1)).save(testBasicPO);
    }

    @Test
    public void createBasicPOWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = basicPORepository.findAll().size();

        // Create the BasicPO with an existing ID
        basicPO.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restBasicPOMockMvc.perform(post("/api/basic-pos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basicPO)))
            .andExpect(status().isBadRequest());

        // Validate the BasicPO in the database
        List<BasicPO> basicPOList = basicPORepository.findAll();
        assertThat(basicPOList).hasSize(databaseSizeBeforeCreate);

        // Validate the BasicPO in Elasticsearch
        verify(mockBasicPOSearchRepository, times(0)).save(basicPO);
    }

    @Test
    public void getAllBasicPOS() throws Exception {
        // Initialize the database
        basicPORepository.save(basicPO);

        // Get all the basicPOList
        restBasicPOMockMvc.perform(get("/api/basic-pos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(basicPO.getId())))
            .andExpect(jsonPath("$.[*].poId").value(hasItem(DEFAULT_PO_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllBasicPOSWithEagerRelationshipsIsEnabled() throws Exception {
        BasicPOResource basicPOResource = new BasicPOResource(basicPOServiceMock);
        when(basicPOServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restBasicPOMockMvc = MockMvcBuilders.standaloneSetup(basicPOResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBasicPOMockMvc.perform(get("/api/basic-pos?eagerload=true"))
        .andExpect(status().isOk());

        verify(basicPOServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllBasicPOSWithEagerRelationshipsIsNotEnabled() throws Exception {
        BasicPOResource basicPOResource = new BasicPOResource(basicPOServiceMock);
            when(basicPOServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restBasicPOMockMvc = MockMvcBuilders.standaloneSetup(basicPOResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restBasicPOMockMvc.perform(get("/api/basic-pos?eagerload=true"))
        .andExpect(status().isOk());

            verify(basicPOServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getBasicPO() throws Exception {
        // Initialize the database
        basicPORepository.save(basicPO);

        // Get the basicPO
        restBasicPOMockMvc.perform(get("/api/basic-pos/{id}", basicPO.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(basicPO.getId()))
            .andExpect(jsonPath("$.poId").value(DEFAULT_PO_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingBasicPO() throws Exception {
        // Get the basicPO
        restBasicPOMockMvc.perform(get("/api/basic-pos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBasicPO() throws Exception {
        // Initialize the database
        basicPOService.save(basicPO);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockBasicPOSearchRepository);

        int databaseSizeBeforeUpdate = basicPORepository.findAll().size();

        // Update the basicPO
        BasicPO updatedBasicPO = basicPORepository.findById(basicPO.getId()).get();
        updatedBasicPO
            .poId(UPDATED_PO_ID)
            .name(UPDATED_NAME);

        restBasicPOMockMvc.perform(put("/api/basic-pos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBasicPO)))
            .andExpect(status().isOk());

        // Validate the BasicPO in the database
        List<BasicPO> basicPOList = basicPORepository.findAll();
        assertThat(basicPOList).hasSize(databaseSizeBeforeUpdate);
        BasicPO testBasicPO = basicPOList.get(basicPOList.size() - 1);
        assertThat(testBasicPO.getPoId()).isEqualTo(UPDATED_PO_ID);
        assertThat(testBasicPO.getName()).isEqualTo(UPDATED_NAME);

        // Validate the BasicPO in Elasticsearch
        verify(mockBasicPOSearchRepository, times(1)).save(testBasicPO);
    }

    @Test
    public void updateNonExistingBasicPO() throws Exception {
        int databaseSizeBeforeUpdate = basicPORepository.findAll().size();

        // Create the BasicPO

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBasicPOMockMvc.perform(put("/api/basic-pos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basicPO)))
            .andExpect(status().isBadRequest());

        // Validate the BasicPO in the database
        List<BasicPO> basicPOList = basicPORepository.findAll();
        assertThat(basicPOList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BasicPO in Elasticsearch
        verify(mockBasicPOSearchRepository, times(0)).save(basicPO);
    }

    @Test
    public void deleteBasicPO() throws Exception {
        // Initialize the database
        basicPOService.save(basicPO);

        int databaseSizeBeforeDelete = basicPORepository.findAll().size();

        // Delete the basicPO
        restBasicPOMockMvc.perform(delete("/api/basic-pos/{id}", basicPO.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BasicPO> basicPOList = basicPORepository.findAll();
        assertThat(basicPOList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BasicPO in Elasticsearch
        verify(mockBasicPOSearchRepository, times(1)).deleteById(basicPO.getId());
    }

    @Test
    public void searchBasicPO() throws Exception {
        // Initialize the database
        basicPOService.save(basicPO);
        when(mockBasicPOSearchRepository.search(queryStringQuery("id:" + basicPO.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(basicPO), PageRequest.of(0, 1), 1));
        // Search the basicPO
        restBasicPOMockMvc.perform(get("/api/_search/basic-pos?query=id:" + basicPO.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(basicPO.getId())))
            .andExpect(jsonPath("$.[*].poId").value(hasItem(DEFAULT_PO_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BasicPO.class);
        BasicPO basicPO1 = new BasicPO();
        basicPO1.setId("id1");
        BasicPO basicPO2 = new BasicPO();
        basicPO2.setId(basicPO1.getId());
        assertThat(basicPO1).isEqualTo(basicPO2);
        basicPO2.setId("id2");
        assertThat(basicPO1).isNotEqualTo(basicPO2);
        basicPO1.setId(null);
        assertThat(basicPO1).isNotEqualTo(basicPO2);
    }
}
