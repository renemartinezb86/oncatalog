package ericsson.com.catalog.service.impl;

import ericsson.com.catalog.service.ChargingSystemService;
import ericsson.com.catalog.domain.ChargingSystem;
import ericsson.com.catalog.repository.ChargingSystemRepository;
import ericsson.com.catalog.repository.search.ChargingSystemSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ChargingSystem.
 */
@Service
public class ChargingSystemServiceImpl implements ChargingSystemService {

    private final Logger log = LoggerFactory.getLogger(ChargingSystemServiceImpl.class);

    private final ChargingSystemRepository chargingSystemRepository;

    private final ChargingSystemSearchRepository chargingSystemSearchRepository;

    public ChargingSystemServiceImpl(ChargingSystemRepository chargingSystemRepository, ChargingSystemSearchRepository chargingSystemSearchRepository) {
        this.chargingSystemRepository = chargingSystemRepository;
        this.chargingSystemSearchRepository = chargingSystemSearchRepository;
    }

    /**
     * Save a chargingSystem.
     *
     * @param chargingSystem the entity to save
     * @return the persisted entity
     */
    @Override
    public ChargingSystem save(ChargingSystem chargingSystem) {
        log.debug("Request to save ChargingSystem : {}", chargingSystem);
        ChargingSystem result = chargingSystemRepository.save(chargingSystem);
        chargingSystemSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the chargingSystems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ChargingSystem> findAll(Pageable pageable) {
        log.debug("Request to get all ChargingSystems");
        return chargingSystemRepository.findAll(pageable);
    }


    /**
     * Get one chargingSystem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<ChargingSystem> findOne(String id) {
        log.debug("Request to get ChargingSystem : {}", id);
        return chargingSystemRepository.findById(id);
    }

    /**
     * Delete the chargingSystem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete ChargingSystem : {}", id);        chargingSystemRepository.deleteById(id);
        chargingSystemSearchRepository.deleteById(id);
    }

    /**
     * Search for the chargingSystem corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<ChargingSystem> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ChargingSystems for query {}", query);
        return chargingSystemSearchRepository.search(queryStringQuery(query), pageable);    }
}
