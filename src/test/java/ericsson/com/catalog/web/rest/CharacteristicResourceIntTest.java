package ericsson.com.catalog.web.rest;

import ericsson.com.catalog.OnCatalogApp;

import ericsson.com.catalog.domain.Characteristic;
import ericsson.com.catalog.repository.CharacteristicRepository;
import ericsson.com.catalog.repository.search.CharacteristicSearchRepository;
import ericsson.com.catalog.service.CharacteristicService;
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
 * Test class for the CharacteristicResource REST controller.
 *
 * @see CharacteristicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnCatalogApp.class)
public class CharacteristicResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private CharacteristicRepository characteristicRepository;

    @Autowired
    private CharacteristicService characteristicService;

    /**
     * This repository is mocked in the ericsson.com.catalog.repository.search test package.
     *
     * @see ericsson.com.catalog.repository.search.CharacteristicSearchRepositoryMockConfiguration
     */
    @Autowired
    private CharacteristicSearchRepository mockCharacteristicSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCharacteristicMockMvc;

    private Characteristic characteristic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CharacteristicResource characteristicResource = new CharacteristicResource(characteristicService);
        this.restCharacteristicMockMvc = MockMvcBuilders.standaloneSetup(characteristicResource)
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
    public static Characteristic createEntity() {
        Characteristic characteristic = new Characteristic()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE);
        return characteristic;
    }

    @Before
    public void initTest() {
        characteristicRepository.deleteAll();
        characteristic = createEntity();
    }

    @Test
    public void createCharacteristic() throws Exception {
        int databaseSizeBeforeCreate = characteristicRepository.findAll().size();

        // Create the Characteristic
        restCharacteristicMockMvc.perform(post("/api/characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(characteristic)))
            .andExpect(status().isCreated());

        // Validate the Characteristic in the database
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeCreate + 1);
        Characteristic testCharacteristic = characteristicList.get(characteristicList.size() - 1);
        assertThat(testCharacteristic.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCharacteristic.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the Characteristic in Elasticsearch
        verify(mockCharacteristicSearchRepository, times(1)).save(testCharacteristic);
    }

    @Test
    public void createCharacteristicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = characteristicRepository.findAll().size();

        // Create the Characteristic with an existing ID
        characteristic.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCharacteristicMockMvc.perform(post("/api/characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(characteristic)))
            .andExpect(status().isBadRequest());

        // Validate the Characteristic in the database
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeCreate);

        // Validate the Characteristic in Elasticsearch
        verify(mockCharacteristicSearchRepository, times(0)).save(characteristic);
    }

    @Test
    public void getAllCharacteristics() throws Exception {
        // Initialize the database
        characteristicRepository.save(characteristic);

        // Get all the characteristicList
        restCharacteristicMockMvc.perform(get("/api/characteristics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(characteristic.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    public void getCharacteristic() throws Exception {
        // Initialize the database
        characteristicRepository.save(characteristic);

        // Get the characteristic
        restCharacteristicMockMvc.perform(get("/api/characteristics/{id}", characteristic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(characteristic.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    public void getNonExistingCharacteristic() throws Exception {
        // Get the characteristic
        restCharacteristicMockMvc.perform(get("/api/characteristics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCharacteristic() throws Exception {
        // Initialize the database
        characteristicService.save(characteristic);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCharacteristicSearchRepository);

        int databaseSizeBeforeUpdate = characteristicRepository.findAll().size();

        // Update the characteristic
        Characteristic updatedCharacteristic = characteristicRepository.findById(characteristic.getId()).get();
        updatedCharacteristic
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);

        restCharacteristicMockMvc.perform(put("/api/characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCharacteristic)))
            .andExpect(status().isOk());

        // Validate the Characteristic in the database
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeUpdate);
        Characteristic testCharacteristic = characteristicList.get(characteristicList.size() - 1);
        assertThat(testCharacteristic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCharacteristic.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the Characteristic in Elasticsearch
        verify(mockCharacteristicSearchRepository, times(1)).save(testCharacteristic);
    }

    @Test
    public void updateNonExistingCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = characteristicRepository.findAll().size();

        // Create the Characteristic

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCharacteristicMockMvc.perform(put("/api/characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(characteristic)))
            .andExpect(status().isBadRequest());

        // Validate the Characteristic in the database
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Characteristic in Elasticsearch
        verify(mockCharacteristicSearchRepository, times(0)).save(characteristic);
    }

    @Test
    public void deleteCharacteristic() throws Exception {
        // Initialize the database
        characteristicService.save(characteristic);

        int databaseSizeBeforeDelete = characteristicRepository.findAll().size();

        // Delete the characteristic
        restCharacteristicMockMvc.perform(delete("/api/characteristics/{id}", characteristic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Characteristic in Elasticsearch
        verify(mockCharacteristicSearchRepository, times(1)).deleteById(characteristic.getId());
    }

    @Test
    public void searchCharacteristic() throws Exception {
        // Initialize the database
        characteristicService.save(characteristic);
        when(mockCharacteristicSearchRepository.search(queryStringQuery("id:" + characteristic.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(characteristic), PageRequest.of(0, 1), 1));
        // Search the characteristic
        restCharacteristicMockMvc.perform(get("/api/_search/characteristics?query=id:" + characteristic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(characteristic.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Characteristic.class);
        Characteristic characteristic1 = new Characteristic();
        characteristic1.setId("id1");
        Characteristic characteristic2 = new Characteristic();
        characteristic2.setId(characteristic1.getId());
        assertThat(characteristic1).isEqualTo(characteristic2);
        characteristic2.setId("id2");
        assertThat(characteristic1).isNotEqualTo(characteristic2);
        characteristic1.setId(null);
        assertThat(characteristic1).isNotEqualTo(characteristic2);
    }
}
