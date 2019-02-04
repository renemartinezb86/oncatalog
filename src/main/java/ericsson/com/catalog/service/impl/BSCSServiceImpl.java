package ericsson.com.catalog.service.impl;

import ericsson.com.catalog.service.BSCSService;
import ericsson.com.catalog.domain.BSCS;
import ericsson.com.catalog.repository.BSCSRepository;
import ericsson.com.catalog.repository.search.BSCSSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BSCS.
 */
@Service
public class BSCSServiceImpl implements BSCSService {

    private final Logger log = LoggerFactory.getLogger(BSCSServiceImpl.class);

    private final BSCSRepository bSCSRepository;

    private final BSCSSearchRepository bSCSSearchRepository;

    public BSCSServiceImpl(BSCSRepository bSCSRepository, BSCSSearchRepository bSCSSearchRepository) {
        this.bSCSRepository = bSCSRepository;
        this.bSCSSearchRepository = bSCSSearchRepository;
    }

    /**
     * Save a bSCS.
     *
     * @param bSCS the entity to save
     * @return the persisted entity
     */
    @Override
    public BSCS save(BSCS bSCS) {
        log.debug("Request to save BSCS : {}", bSCS);
        BSCS result = bSCSRepository.save(bSCS);
        bSCSSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the bSCS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<BSCS> findAll(Pageable pageable) {
        log.debug("Request to get all BSCS");
        return bSCSRepository.findAll(pageable);
    }


    /**
     * Get one bSCS by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<BSCS> findOne(String id) {
        log.debug("Request to get BSCS : {}", id);
        return bSCSRepository.findById(id);
    }

    /**
     * Delete the bSCS by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete BSCS : {}", id);        bSCSRepository.deleteById(id);
        bSCSSearchRepository.deleteById(id);
    }

    /**
     * Search for the bSCS corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<BSCS> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BSCS for query {}", query);
        return bSCSSearchRepository.search(queryStringQuery(query), pageable);    }
}
