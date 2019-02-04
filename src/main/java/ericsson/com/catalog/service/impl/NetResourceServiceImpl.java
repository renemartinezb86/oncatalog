package ericsson.com.catalog.service.impl;

import ericsson.com.catalog.service.NetResourceService;
import ericsson.com.catalog.domain.NetResource;
import ericsson.com.catalog.repository.NetResourceRepository;
import ericsson.com.catalog.repository.search.NetResourceSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing NetResource.
 */
@Service
public class NetResourceServiceImpl implements NetResourceService {

    private final Logger log = LoggerFactory.getLogger(NetResourceServiceImpl.class);

    private final NetResourceRepository netResourceRepository;

    private final NetResourceSearchRepository netResourceSearchRepository;

    public NetResourceServiceImpl(NetResourceRepository netResourceRepository, NetResourceSearchRepository netResourceSearchRepository) {
        this.netResourceRepository = netResourceRepository;
        this.netResourceSearchRepository = netResourceSearchRepository;
    }

    /**
     * Save a netResource.
     *
     * @param netResource the entity to save
     * @return the persisted entity
     */
    @Override
    public NetResource save(NetResource netResource) {
        log.debug("Request to save NetResource : {}", netResource);
        NetResource result = netResourceRepository.save(netResource);
        netResourceSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the netResources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<NetResource> findAll(Pageable pageable) {
        log.debug("Request to get all NetResources");
        return netResourceRepository.findAll(pageable);
    }


    /**
     * Get one netResource by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<NetResource> findOne(String id) {
        log.debug("Request to get NetResource : {}", id);
        return netResourceRepository.findById(id);
    }

    /**
     * Delete the netResource by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete NetResource : {}", id);        netResourceRepository.deleteById(id);
        netResourceSearchRepository.deleteById(id);
    }

    /**
     * Search for the netResource corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<NetResource> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of NetResources for query {}", query);
        return netResourceSearchRepository.search(queryStringQuery(query), pageable);    }
}
