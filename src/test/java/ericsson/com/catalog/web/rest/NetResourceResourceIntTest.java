package ericsson.com.catalog.web.rest;

import ericsson.com.catalog.OnCatalogApp;

import ericsson.com.catalog.domain.NetResource;
import ericsson.com.catalog.repository.NetResourceRepository;
import ericsson.com.catalog.repository.search.NetResourceSearchRepository;
import ericsson.com.catalog.service.NetResourceService;
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
 * Test class for the NetResourceResource REST controller.
 *
 * @see NetResourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnCatalogApp.class)
public class NetResourceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARAMETER = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETER = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private NetResourceRepository netResourceRepository;

    @Autowired
    private NetResourceService netResourceService;

    /**
     * This repository is mocked in the ericsson.com.catalog.repository.search test package.
     *
     * @see ericsson.com.catalog.repository.search.NetResourceSearchRepositoryMockConfiguration
     */
    @Autowired
    private NetResourceSearchRepository mockNetResourceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restNetResourceMockMvc;

    private NetResource netResource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NetResourceResource netResourceResource = new NetResourceResource(netResourceService);
        this.restNetResourceMockMvc = MockMvcBuilders.standaloneSetup(netResourceResource)
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
    public static NetResource createEntity() {
        NetResource netResource = new NetResource()
            .name(DEFAULT_NAME)
            .parameter(DEFAULT_PARAMETER)
            .value(DEFAULT_VALUE);
        return netResource;
    }

    @Before
    public void initTest() {
        netResourceRepository.deleteAll();
        netResource = createEntity();
    }

    @Test
    public void createNetResource() throws Exception {
        int databaseSizeBeforeCreate = netResourceRepository.findAll().size();

        // Create the NetResource
        restNetResourceMockMvc.perform(post("/api/net-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(netResource)))
            .andExpect(status().isCreated());

        // Validate the NetResource in the database
        List<NetResource> netResourceList = netResourceRepository.findAll();
        assertThat(netResourceList).hasSize(databaseSizeBeforeCreate + 1);
        NetResource testNetResource = netResourceList.get(netResourceList.size() - 1);
        assertThat(testNetResource.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNetResource.getParameter()).isEqualTo(DEFAULT_PARAMETER);
        assertThat(testNetResource.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the NetResource in Elasticsearch
        verify(mockNetResourceSearchRepository, times(1)).save(testNetResource);
    }

    @Test
    public void createNetResourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = netResourceRepository.findAll().size();

        // Create the NetResource with an existing ID
        netResource.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetResourceMockMvc.perform(post("/api/net-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(netResource)))
            .andExpect(status().isBadRequest());

        // Validate the NetResource in the database
        List<NetResource> netResourceList = netResourceRepository.findAll();
        assertThat(netResourceList).hasSize(databaseSizeBeforeCreate);

        // Validate the NetResource in Elasticsearch
        verify(mockNetResourceSearchRepository, times(0)).save(netResource);
    }

    @Test
    public void getAllNetResources() throws Exception {
        // Initialize the database
        netResourceRepository.save(netResource);

        // Get all the netResourceList
        restNetResourceMockMvc.perform(get("/api/net-resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(netResource.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].parameter").value(hasItem(DEFAULT_PARAMETER.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    public void getNetResource() throws Exception {
        // Initialize the database
        netResourceRepository.save(netResource);

        // Get the netResource
        restNetResourceMockMvc.perform(get("/api/net-resources/{id}", netResource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(netResource.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.parameter").value(DEFAULT_PARAMETER.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    public void getNonExistingNetResource() throws Exception {
        // Get the netResource
        restNetResourceMockMvc.perform(get("/api/net-resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateNetResource() throws Exception {
        // Initialize the database
        netResourceService.save(netResource);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockNetResourceSearchRepository);

        int databaseSizeBeforeUpdate = netResourceRepository.findAll().size();

        // Update the netResource
        NetResource updatedNetResource = netResourceRepository.findById(netResource.getId()).get();
        updatedNetResource
            .name(UPDATED_NAME)
            .parameter(UPDATED_PARAMETER)
            .value(UPDATED_VALUE);

        restNetResourceMockMvc.perform(put("/api/net-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNetResource)))
            .andExpect(status().isOk());

        // Validate the NetResource in the database
        List<NetResource> netResourceList = netResourceRepository.findAll();
        assertThat(netResourceList).hasSize(databaseSizeBeforeUpdate);
        NetResource testNetResource = netResourceList.get(netResourceList.size() - 1);
        assertThat(testNetResource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNetResource.getParameter()).isEqualTo(UPDATED_PARAMETER);
        assertThat(testNetResource.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the NetResource in Elasticsearch
        verify(mockNetResourceSearchRepository, times(1)).save(testNetResource);
    }

    @Test
    public void updateNonExistingNetResource() throws Exception {
        int databaseSizeBeforeUpdate = netResourceRepository.findAll().size();

        // Create the NetResource

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetResourceMockMvc.perform(put("/api/net-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(netResource)))
            .andExpect(status().isBadRequest());

        // Validate the NetResource in the database
        List<NetResource> netResourceList = netResourceRepository.findAll();
        assertThat(netResourceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the NetResource in Elasticsearch
        verify(mockNetResourceSearchRepository, times(0)).save(netResource);
    }

    @Test
    public void deleteNetResource() throws Exception {
        // Initialize the database
        netResourceService.save(netResource);

        int databaseSizeBeforeDelete = netResourceRepository.findAll().size();

        // Delete the netResource
        restNetResourceMockMvc.perform(delete("/api/net-resources/{id}", netResource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NetResource> netResourceList = netResourceRepository.findAll();
        assertThat(netResourceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the NetResource in Elasticsearch
        verify(mockNetResourceSearchRepository, times(1)).deleteById(netResource.getId());
    }

    @Test
    public void searchNetResource() throws Exception {
        // Initialize the database
        netResourceService.save(netResource);
        when(mockNetResourceSearchRepository.search(queryStringQuery("id:" + netResource.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(netResource), PageRequest.of(0, 1), 1));
        // Search the netResource
        restNetResourceMockMvc.perform(get("/api/_search/net-resources?query=id:" + netResource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(netResource.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].parameter").value(hasItem(DEFAULT_PARAMETER)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NetResource.class);
        NetResource netResource1 = new NetResource();
        netResource1.setId("id1");
        NetResource netResource2 = new NetResource();
        netResource2.setId(netResource1.getId());
        assertThat(netResource1).isEqualTo(netResource2);
        netResource2.setId("id2");
        assertThat(netResource1).isNotEqualTo(netResource2);
        netResource1.setId(null);
        assertThat(netResource1).isNotEqualTo(netResource2);
    }
}
