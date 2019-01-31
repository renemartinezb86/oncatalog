package ericsson.com.catalog.service.impl;

import ericsson.com.catalog.service.OptionalServiceService;
import ericsson.com.catalog.domain.OptionalService;
import ericsson.com.catalog.repository.OptionalServiceRepository;
import ericsson.com.catalog.repository.search.OptionalServiceSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OptionalService.
 */
@Service
public class OptionalServiceServiceImpl implements OptionalServiceService {

    private final Logger log = LoggerFactory.getLogger(OptionalServiceServiceImpl.class);

    private final OptionalServiceRepository optionalServiceRepository;

    private final OptionalServiceSearchRepository optionalServiceSearchRepository;

    public OptionalServiceServiceImpl(OptionalServiceRepository optionalServiceRepository, OptionalServiceSearchRepository optionalServiceSearchRepository) {
        this.optionalServiceRepository = optionalServiceRepository;
        this.optionalServiceSearchRepository = optionalServiceSearchRepository;
    }

    /**
     * Save a optionalService.
     *
     * @param optionalService the entity to save
     * @return the persisted entity
     */
    @Override
    public OptionalService save(OptionalService optionalService) {
        log.debug("Request to save OptionalService : {}", optionalService);
        OptionalService result = optionalServiceRepository.save(optionalService);
        optionalServiceSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the optionalServices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<OptionalService> findAll(Pageable pageable) {
        log.debug("Request to get all OptionalServices");
        return optionalServiceRepository.findAll(pageable);
    }


    /**
     * Get one optionalService by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<OptionalService> findOne(String id) {
        log.debug("Request to get OptionalService : {}", id);
        return optionalServiceRepository.findById(id);
    }

    /**
     * Delete the optionalService by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete OptionalService : {}", id);        optionalServiceRepository.deleteById(id);
        optionalServiceSearchRepository.deleteById(id);
    }

    /**
     * Search for the optionalService corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<OptionalService> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OptionalServices for query {}", query);
        return optionalServiceSearchRepository.search(queryStringQuery(query), pageable);    }
}
