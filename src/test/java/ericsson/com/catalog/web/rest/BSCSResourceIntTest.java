package ericsson.com.catalog.web.rest;

import ericsson.com.catalog.OnCatalogApp;

import ericsson.com.catalog.domain.BSCS;
import ericsson.com.catalog.repository.BSCSRepository;
import ericsson.com.catalog.repository.search.BSCSSearchRepository;
import ericsson.com.catalog.service.BSCSService;
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
 * Test class for the BSCSResource REST controller.
 *
 * @see BSCSResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnCatalogApp.class)
public class BSCSResourceIntTest {

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BSCS_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_BSCS_SERVICE = "BBBBBBBBBB";

    @Autowired
    private BSCSRepository bSCSRepository;

    @Autowired
    private BSCSService bSCSService;

    /**
     * This repository is mocked in the ericsson.com.catalog.repository.search test package.
     *
     * @see ericsson.com.catalog.repository.search.BSCSSearchRepositoryMockConfiguration
     */
    @Autowired
    private BSCSSearchRepository mockBSCSSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restBSCSMockMvc;

    private BSCS bSCS;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BSCSResource bSCSResource = new BSCSResource(bSCSService);
        this.restBSCSMockMvc = MockMvcBuilders.standaloneSetup(bSCSResource)
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
    public static BSCS createEntity() {
        BSCS bSCS = new BSCS()
            .serviceName(DEFAULT_SERVICE_NAME)
            .bscsService(DEFAULT_BSCS_SERVICE);
        return bSCS;
    }

    @Before
    public void initTest() {
        bSCSRepository.deleteAll();
        bSCS = createEntity();
    }

    @Test
    public void createBSCS() throws Exception {
        int databaseSizeBeforeCreate = bSCSRepository.findAll().size();

        // Create the BSCS
        restBSCSMockMvc.perform(post("/api/bscs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bSCS)))
            .andExpect(status().isCreated());

        // Validate the BSCS in the database
        List<BSCS> bSCSList = bSCSRepository.findAll();
        assertThat(bSCSList).hasSize(databaseSizeBeforeCreate + 1);
        BSCS testBSCS = bSCSList.get(bSCSList.size() - 1);
        assertThat(testBSCS.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testBSCS.getBscsService()).isEqualTo(DEFAULT_BSCS_SERVICE);

        // Validate the BSCS in Elasticsearch
        verify(mockBSCSSearchRepository, times(1)).save(testBSCS);
    }

    @Test
    public void createBSCSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bSCSRepository.findAll().size();

        // Create the BSCS with an existing ID
        bSCS.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restBSCSMockMvc.perform(post("/api/bscs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bSCS)))
            .andExpect(status().isBadRequest());

        // Validate the BSCS in the database
        List<BSCS> bSCSList = bSCSRepository.findAll();
        assertThat(bSCSList).hasSize(databaseSizeBeforeCreate);

        // Validate the BSCS in Elasticsearch
        verify(mockBSCSSearchRepository, times(0)).save(bSCS);
    }

    @Test
    public void getAllBSCS() throws Exception {
        // Initialize the database
        bSCSRepository.save(bSCS);

        // Get all the bSCSList
        restBSCSMockMvc.perform(get("/api/bscs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bSCS.getId())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].bscsService").value(hasItem(DEFAULT_BSCS_SERVICE.toString())));
    }
    
    @Test
    public void getBSCS() throws Exception {
        // Initialize the database
        bSCSRepository.save(bSCS);

        // Get the bSCS
        restBSCSMockMvc.perform(get("/api/bscs/{id}", bSCS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bSCS.getId()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME.toString()))
            .andExpect(jsonPath("$.bscsService").value(DEFAULT_BSCS_SERVICE.toString()));
    }

    @Test
    public void getNonExistingBSCS() throws Exception {
        // Get the bSCS
        restBSCSMockMvc.perform(get("/api/bscs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBSCS() throws Exception {
        // Initialize the database
        bSCSService.save(bSCS);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockBSCSSearchRepository);

        int databaseSizeBeforeUpdate = bSCSRepository.findAll().size();

        // Update the bSCS
        BSCS updatedBSCS = bSCSRepository.findById(bSCS.getId()).get();
        updatedBSCS
            .serviceName(UPDATED_SERVICE_NAME)
            .bscsService(UPDATED_BSCS_SERVICE);

        restBSCSMockMvc.perform(put("/api/bscs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBSCS)))
            .andExpect(status().isOk());

        // Validate the BSCS in the database
        List<BSCS> bSCSList = bSCSRepository.findAll();
        assertThat(bSCSList).hasSize(databaseSizeBeforeUpdate);
        BSCS testBSCS = bSCSList.get(bSCSList.size() - 1);
        assertThat(testBSCS.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testBSCS.getBscsService()).isEqualTo(UPDATED_BSCS_SERVICE);

        // Validate the BSCS in Elasticsearch
        verify(mockBSCSSearchRepository, times(1)).save(testBSCS);
    }

    @Test
    public void updateNonExistingBSCS() throws Exception {
        int databaseSizeBeforeUpdate = bSCSRepository.findAll().size();

        // Create the BSCS

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBSCSMockMvc.perform(put("/api/bscs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bSCS)))
            .andExpect(status().isBadRequest());

        // Validate the BSCS in the database
        List<BSCS> bSCSList = bSCSRepository.findAll();
        assertThat(bSCSList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BSCS in Elasticsearch
        verify(mockBSCSSearchRepository, times(0)).save(bSCS);
    }

    @Test
    public void deleteBSCS() throws Exception {
        // Initialize the database
        bSCSService.save(bSCS);

        int databaseSizeBeforeDelete = bSCSRepository.findAll().size();

        // Delete the bSCS
        restBSCSMockMvc.perform(delete("/api/bscs/{id}", bSCS.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BSCS> bSCSList = bSCSRepository.findAll();
        assertThat(bSCSList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BSCS in Elasticsearch
        verify(mockBSCSSearchRepository, times(1)).deleteById(bSCS.getId());
    }

    @Test
    public void searchBSCS() throws Exception {
        // Initialize the database
        bSCSService.save(bSCS);
        when(mockBSCSSearchRepository.search(queryStringQuery("id:" + bSCS.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(bSCS), PageRequest.of(0, 1), 1));
        // Search the bSCS
        restBSCSMockMvc.perform(get("/api/_search/bscs?query=id:" + bSCS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bSCS.getId())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].bscsService").value(hasItem(DEFAULT_BSCS_SERVICE)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BSCS.class);
        BSCS bSCS1 = new BSCS();
        bSCS1.setId("id1");
        BSCS bSCS2 = new BSCS();
        bSCS2.setId(bSCS1.getId());
        assertThat(bSCS1).isEqualTo(bSCS2);
        bSCS2.setId("id2");
        assertThat(bSCS1).isNotEqualTo(bSCS2);
        bSCS1.setId(null);
        assertThat(bSCS1).isNotEqualTo(bSCS2);
    }
}
