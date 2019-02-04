package ericsson.com.catalog.service;

import ericsson.com.catalog.domain.NetResource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing NetResource.
 */
public interface NetResourceService {

    /**
     * Save a netResource.
     *
     * @param netResource the entity to save
     * @return the persisted entity
     */
    NetResource save(NetResource netResource);

    /**
     * Get all the netResources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<NetResource> findAll(Pageable pageable);


    /**
     * Get the "id" netResource.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<NetResource> findOne(String id);

    /**
     * Delete the "id" netResource.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the netResource corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<NetResource> search(String query, Pageable pageable);
}
