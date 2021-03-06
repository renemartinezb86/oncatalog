package ericsson.com.catalog.service.impl;

import ericsson.com.catalog.service.BasicPOService;
import ericsson.com.catalog.domain.BasicPO;
import ericsson.com.catalog.repository.BasicPORepository;
import ericsson.com.catalog.repository.search.BasicPOSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BasicPO.
 */
@Service
public class BasicPOServiceImpl implements BasicPOService {

    private final Logger log = LoggerFactory.getLogger(BasicPOServiceImpl.class);

    private final BasicPORepository basicPORepository;

    private final BasicPOSearchRepository basicPOSearchRepository;

    public BasicPOServiceImpl(BasicPORepository basicPORepository, BasicPOSearchRepository basicPOSearchRepository) {
        this.basicPORepository = basicPORepository;
        this.basicPOSearchRepository = basicPOSearchRepository;
    }

    /**
     * Save a basicPO.
     *
     * @param basicPO the entity to save
     * @return the persisted entity
     */
    @Override
    public BasicPO save(BasicPO basicPO) {
        log.debug("Request to save BasicPO : {}", basicPO);
        BasicPO result = basicPORepository.save(basicPO);
        basicPOSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the basicPOS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<BasicPO> findAll(Pageable pageable) {
        log.debug("Request to get all BasicPOS");
        return basicPORepository.findAll(pageable);
    }

    /**
     * Get all the BasicPO with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<BasicPO> findAllWithEagerRelationships(Pageable pageable) {
        return basicPORepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one basicPO by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<BasicPO> findOne(String id) {
        log.debug("Request to get BasicPO : {}", id);
        return basicPORepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the basicPO by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete BasicPO : {}", id);        basicPORepository.deleteById(id);
        basicPOSearchRepository.deleteById(id);
    }

    /**
     * Search for the basicPO corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<BasicPO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BasicPOS for query {}", query);
        return basicPOSearchRepository.search(queryStringQuery(query), pageable);    }
}
