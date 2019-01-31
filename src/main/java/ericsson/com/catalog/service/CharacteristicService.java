package ericsson.com.catalog.service;

import ericsson.com.catalog.domain.Characteristic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Characteristic.
 */
public interface CharacteristicService {

    /**
     * Save a characteristic.
     *
     * @param characteristic the entity to save
     * @return the persisted entity
     */
    Characteristic save(Characteristic characteristic);

    /**
     * Get all the characteristics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Characteristic> findAll(Pageable pageable);


    /**
     * Get the "id" characteristic.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Characteristic> findOne(String id);

    /**
     * Delete the "id" characteristic.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the characteristic corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Characteristic> search(String query, Pageable pageable);
}
