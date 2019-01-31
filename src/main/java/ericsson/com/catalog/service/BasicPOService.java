package ericsson.com.catalog.service;

import ericsson.com.catalog.domain.BasicPO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing BasicPO.
 */
public interface BasicPOService {

    /**
     * Save a basicPO.
     *
     * @param basicPO the entity to save
     * @return the persisted entity
     */
    BasicPO save(BasicPO basicPO);

    /**
     * Get all the basicPOS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BasicPO> findAll(Pageable pageable);

    /**
     * Get all the BasicPO with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<BasicPO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" basicPO.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BasicPO> findOne(String id);

    /**
     * Delete the "id" basicPO.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the basicPO corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BasicPO> search(String query, Pageable pageable);
}
