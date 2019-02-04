package ericsson.com.catalog.web.rest;
import ericsson.com.catalog.domain.BSCS;
import ericsson.com.catalog.service.BSCSService;
import ericsson.com.catalog.web.rest.errors.BadRequestAlertException;
import ericsson.com.catalog.web.rest.util.HeaderUtil;
import ericsson.com.catalog.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing BSCS.
 */
@RestController
@RequestMapping("/api")
public class BSCSResource {

    private final Logger log = LoggerFactory.getLogger(BSCSResource.class);

    private static final String ENTITY_NAME = "bSCS";

    private final BSCSService bSCSService;

    public BSCSResource(BSCSService bSCSService) {
        this.bSCSService = bSCSService;
    }

    /**
     * POST  /bscs : Create a new bSCS.
     *
     * @param bSCS the bSCS to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bSCS, or with status 400 (Bad Request) if the bSCS has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bscs")
    public ResponseEntity<BSCS> createBSCS(@RequestBody BSCS bSCS) throws URISyntaxException {
        log.debug("REST request to save BSCS : {}", bSCS);
        if (bSCS.getId() != null) {
            throw new BadRequestAlertException("A new bSCS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BSCS result = bSCSService.save(bSCS);
        return ResponseEntity.created(new URI("/api/bscs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bscs : Updates an existing bSCS.
     *
     * @param bSCS the bSCS to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bSCS,
     * or with status 400 (Bad Request) if the bSCS is not valid,
     * or with status 500 (Internal Server Error) if the bSCS couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bscs")
    public ResponseEntity<BSCS> updateBSCS(@RequestBody BSCS bSCS) throws URISyntaxException {
        log.debug("REST request to update BSCS : {}", bSCS);
        if (bSCS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BSCS result = bSCSService.save(bSCS);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bSCS.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bscs : get all the bSCS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bSCS in body
     */
    @GetMapping("/bscs")
    public ResponseEntity<List<BSCS>> getAllBSCS(Pageable pageable) {
        log.debug("REST request to get a page of BSCS");
        Page<BSCS> page = bSCSService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bscs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /bscs/:id : get the "id" bSCS.
     *
     * @param id the id of the bSCS to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bSCS, or with status 404 (Not Found)
     */
    @GetMapping("/bscs/{id}")
    public ResponseEntity<BSCS> getBSCS(@PathVariable String id) {
        log.debug("REST request to get BSCS : {}", id);
        Optional<BSCS> bSCS = bSCSService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bSCS);
    }

    /**
     * DELETE  /bscs/:id : delete the "id" bSCS.
     *
     * @param id the id of the bSCS to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bscs/{id}")
    public ResponseEntity<Void> deleteBSCS(@PathVariable String id) {
        log.debug("REST request to delete BSCS : {}", id);
        bSCSService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/bscs?query=:query : search for the bSCS corresponding
     * to the query.
     *
     * @param query the query of the bSCS search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/bscs")
    public ResponseEntity<List<BSCS>> searchBSCS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BSCS for query {}", query);
        Page<BSCS> page = bSCSService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/bscs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
