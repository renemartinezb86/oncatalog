package ericsson.com.catalog.service;

import ericsson.com.catalog.domain.ChargingSystem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ChargingSystem.
 */
public interface ChargingSystemService {

    /**
     * Save a chargingSystem.
     *
     * @param chargingSystem the entity to save
     * @return the persisted entity
     */
    ChargingSystem save(ChargingSystem chargingSystem);

    /**
     * Get all the chargingSystems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ChargingSystem> findAll(Pageable pageable);


    /**
     * Get the "id" chargingSystem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ChargingSystem> findOne(String id);

    /**
     * Delete the "id" chargingSystem.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the chargingSystem corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ChargingSystem> search(String query, Pageable pageable);
}
