package ericsson.com.catalog.service;

import ericsson.com.catalog.domain.OptionalService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing OptionalService.
 */
public interface OptionalServiceService {

    /**
     * Save a optionalService.
     *
     * @param optionalService the entity to save
     * @return the persisted entity
     */
    OptionalService save(OptionalService optionalService);

    /**
     * Get all the optionalServices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OptionalService> findAll(Pageable pageable);

    /**
     * Get all the OptionalService with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<OptionalService> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" optionalService.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OptionalService> findOne(String id);

    /**
     * Delete the "id" optionalService.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the optionalService corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OptionalService> search(String query, Pageable pageable);
}
