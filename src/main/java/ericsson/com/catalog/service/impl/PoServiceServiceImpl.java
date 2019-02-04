package ericsson.com.catalog.service.impl;

import ericsson.com.catalog.service.PoServiceService;
import ericsson.com.catalog.domain.PoService;
import ericsson.com.catalog.repository.PoServiceRepository;
import ericsson.com.catalog.repository.search.PoServiceSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PoService.
 */
@Service
public class PoServiceServiceImpl implements PoServiceService {

    private final Logger log = LoggerFactory.getLogger(PoServiceServiceImpl.class);

    private final PoServiceRepository poServiceRepository;

    private final PoServiceSearchRepository poServiceSearchRepository;

    public PoServiceServiceImpl(PoServiceRepository poServiceRepository, PoServiceSearchRepository poServiceSearchRepository) {
        this.poServiceRepository = poServiceRepository;
        this.poServiceSearchRepository = poServiceSearchRepository;
    }

    /**
     * Save a poService.
     *
     * @param poService the entity to save
     * @return the persisted entity
     */
    @Override
    public PoService save(PoService poService) {
        log.debug("Request to save PoService : {}", poService);
        PoService result = poServiceRepository.save(poService);
        poServiceSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the poServices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<PoService> findAll(Pageable pageable) {
        log.debug("Request to get all PoServices");
        return poServiceRepository.findAll(pageable);
    }


    /**
     * Get one poService by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<PoService> findOne(String id) {
        log.debug("Request to get PoService : {}", id);
        return poServiceRepository.findById(id);
    }

    /**
     * Delete the poService by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete PoService : {}", id);        poServiceRepository.deleteById(id);
        poServiceSearchRepository.deleteById(id);
    }

    /**
     * Search for the poService corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<PoService> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PoServices for query {}", query);
        return poServiceSearchRepository.search(queryStringQuery(query), pageable);    }
}
