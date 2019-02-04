package ericsson.com.catalog.service;

import ericsson.com.catalog.domain.BSCS;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing BSCS.
 */
public interface BSCSService {

    /**
     * Save a bSCS.
     *
     * @param bSCS the entity to save
     * @return the persisted entity
     */
    BSCS save(BSCS bSCS);

    /**
     * Get all the bSCS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BSCS> findAll(Pageable pageable);


    /**
     * Get the "id" bSCS.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BSCS> findOne(String id);

    /**
     * Delete the "id" bSCS.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the bSCS corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BSCS> search(String query, Pageable pageable);
}
