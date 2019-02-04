package ericsson.com.catalog.service;

import ericsson.com.catalog.domain.PoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PoService.
 */
public interface PoServiceService {

    /**
     * Save a poService.
     *
     * @param poService the entity to save
     * @return the persisted entity
     */
    PoService save(PoService poService);

    /**
     * Get all the poServices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PoService> findAll(Pageable pageable);


    /**
     * Get the "id" poService.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PoService> findOne(String id);

    /**
     * Delete the "id" poService.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the poService corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PoService> search(String query, Pageable pageable);
}
