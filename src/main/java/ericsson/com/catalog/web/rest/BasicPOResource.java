package ericsson.com.catalog.web.rest;
import ericsson.com.catalog.domain.BasicPO;
import ericsson.com.catalog.service.BasicPOService;
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
 * REST controller for managing BasicPO.
 */
@RestController
@RequestMapping("/api")
public class BasicPOResource {

    private final Logger log = LoggerFactory.getLogger(BasicPOResource.class);

    private static final String ENTITY_NAME = "basicPO";

    private final BasicPOService basicPOService;

    public BasicPOResource(BasicPOService basicPOService) {
        this.basicPOService = basicPOService;
    }

    /**
     * POST  /basic-pos : Create a new basicPO.
     *
     * @param basicPO the basicPO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new basicPO, or with status 400 (Bad Request) if the basicPO has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/basic-pos")
    public ResponseEntity<BasicPO> createBasicPO(@RequestBody BasicPO basicPO) throws URISyntaxException {
        log.debug("REST request to save BasicPO : {}", basicPO);
        if (basicPO.getId() != null) {
            throw new BadRequestAlertException("A new basicPO cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BasicPO result = basicPOService.save(basicPO);
        return ResponseEntity.created(new URI("/api/basic-pos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /basic-pos : Updates an existing basicPO.
     *
     * @param basicPO the basicPO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated basicPO,
     * or with status 400 (Bad Request) if the basicPO is not valid,
     * or with status 500 (Internal Server Error) if the basicPO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/basic-pos")
    public ResponseEntity<BasicPO> updateBasicPO(@RequestBody BasicPO basicPO) throws URISyntaxException {
        log.debug("REST request to update BasicPO : {}", basicPO);
        if (basicPO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BasicPO result = basicPOService.save(basicPO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, basicPO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /basic-pos : get all the basicPOS.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of basicPOS in body
     */
    @GetMapping("/basic-pos")
    public ResponseEntity<List<BasicPO>> getAllBasicPOS(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of BasicPOS");
        Page<BasicPO> page;
        if (eagerload) {
            page = basicPOService.findAllWithEagerRelationships(pageable);
        } else {
            page = basicPOService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/basic-pos?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /basic-pos/:id : get the "id" basicPO.
     *
     * @param id the id of the basicPO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the basicPO, or with status 404 (Not Found)
     */
    @GetMapping("/basic-pos/{id}")
    public ResponseEntity<BasicPO> getBasicPO(@PathVariable String id) {
        log.debug("REST request to get BasicPO : {}", id);
        Optional<BasicPO> basicPO = basicPOService.findOne(id);
        return ResponseUtil.wrapOrNotFound(basicPO);
    }

    /**
     * DELETE  /basic-pos/:id : delete the "id" basicPO.
     *
     * @param id the id of the basicPO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/basic-pos/{id}")
    public ResponseEntity<Void> deleteBasicPO(@PathVariable String id) {
        log.debug("REST request to delete BasicPO : {}", id);
        basicPOService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/basic-pos?query=:query : search for the basicPO corresponding
     * to the query.
     *
     * @param query the query of the basicPO search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/basic-pos")
    public ResponseEntity<List<BasicPO>> searchBasicPOS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BasicPOS for query {}", query);
        Page<BasicPO> page = basicPOService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/basic-pos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
