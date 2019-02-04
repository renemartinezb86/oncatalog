package ericsson.com.catalog.web.rest;
import ericsson.com.catalog.domain.PoService;
import ericsson.com.catalog.service.PoServiceService;
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
 * REST controller for managing PoService.
 */
@RestController
@RequestMapping("/api")
public class PoServiceResource {

    private final Logger log = LoggerFactory.getLogger(PoServiceResource.class);

    private static final String ENTITY_NAME = "poService";

    private final PoServiceService poServiceService;

    public PoServiceResource(PoServiceService poServiceService) {
        this.poServiceService = poServiceService;
    }

    /**
     * POST  /po-services : Create a new poService.
     *
     * @param poService the poService to create
     * @return the ResponseEntity with status 201 (Created) and with body the new poService, or with status 400 (Bad Request) if the poService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/po-services")
    public ResponseEntity<PoService> createPoService(@RequestBody PoService poService) throws URISyntaxException {
        log.debug("REST request to save PoService : {}", poService);
        if (poService.getId() != null) {
            throw new BadRequestAlertException("A new poService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoService result = poServiceService.save(poService);
        return ResponseEntity.created(new URI("/api/po-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /po-services : Updates an existing poService.
     *
     * @param poService the poService to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated poService,
     * or with status 400 (Bad Request) if the poService is not valid,
     * or with status 500 (Internal Server Error) if the poService couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/po-services")
    public ResponseEntity<PoService> updatePoService(@RequestBody PoService poService) throws URISyntaxException {
        log.debug("REST request to update PoService : {}", poService);
        if (poService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PoService result = poServiceService.save(poService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, poService.getId().toString()))
            .body(result);
    }

    /**
     * GET  /po-services : get all the poServices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of poServices in body
     */
    @GetMapping("/po-services")
    public ResponseEntity<List<PoService>> getAllPoServices(Pageable pageable) {
        log.debug("REST request to get a page of PoServices");
        Page<PoService> page = poServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/po-services");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /po-services/:id : get the "id" poService.
     *
     * @param id the id of the poService to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the poService, or with status 404 (Not Found)
     */
    @GetMapping("/po-services/{id}")
    public ResponseEntity<PoService> getPoService(@PathVariable String id) {
        log.debug("REST request to get PoService : {}", id);
        Optional<PoService> poService = poServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(poService);
    }

    /**
     * DELETE  /po-services/:id : delete the "id" poService.
     *
     * @param id the id of the poService to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/po-services/{id}")
    public ResponseEntity<Void> deletePoService(@PathVariable String id) {
        log.debug("REST request to delete PoService : {}", id);
        poServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/po-services?query=:query : search for the poService corresponding
     * to the query.
     *
     * @param query the query of the poService search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/po-services")
    public ResponseEntity<List<PoService>> searchPoServices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PoServices for query {}", query);
        Page<PoService> page = poServiceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/po-services");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
