package ericsson.com.catalog.web.rest;
import ericsson.com.catalog.domain.OptionalService;
import ericsson.com.catalog.service.OptionalServiceService;
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
 * REST controller for managing OptionalService.
 */
@RestController
@RequestMapping("/api")
public class OptionalServiceResource {

    private final Logger log = LoggerFactory.getLogger(OptionalServiceResource.class);

    private static final String ENTITY_NAME = "optionalService";

    private final OptionalServiceService optionalServiceService;

    public OptionalServiceResource(OptionalServiceService optionalServiceService) {
        this.optionalServiceService = optionalServiceService;
    }

    /**
     * POST  /optional-services : Create a new optionalService.
     *
     * @param optionalService the optionalService to create
     * @return the ResponseEntity with status 201 (Created) and with body the new optionalService, or with status 400 (Bad Request) if the optionalService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/optional-services")
    public ResponseEntity<OptionalService> createOptionalService(@RequestBody OptionalService optionalService) throws URISyntaxException {
        log.debug("REST request to save OptionalService : {}", optionalService);
        if (optionalService.getId() != null) {
            throw new BadRequestAlertException("A new optionalService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptionalService result = optionalServiceService.save(optionalService);
        return ResponseEntity.created(new URI("/api/optional-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /optional-services : Updates an existing optionalService.
     *
     * @param optionalService the optionalService to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated optionalService,
     * or with status 400 (Bad Request) if the optionalService is not valid,
     * or with status 500 (Internal Server Error) if the optionalService couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/optional-services")
    public ResponseEntity<OptionalService> updateOptionalService(@RequestBody OptionalService optionalService) throws URISyntaxException {
        log.debug("REST request to update OptionalService : {}", optionalService);
        if (optionalService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OptionalService result = optionalServiceService.save(optionalService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, optionalService.getId().toString()))
            .body(result);
    }

    /**
     * GET  /optional-services : get all the optionalServices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of optionalServices in body
     */
    @GetMapping("/optional-services")
    public ResponseEntity<List<OptionalService>> getAllOptionalServices(Pageable pageable) {
        log.debug("REST request to get a page of OptionalServices");
        Page<OptionalService> page = optionalServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/optional-services");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /optional-services/:id : get the "id" optionalService.
     *
     * @param id the id of the optionalService to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the optionalService, or with status 404 (Not Found)
     */
    @GetMapping("/optional-services/{id}")
    public ResponseEntity<OptionalService> getOptionalService(@PathVariable String id) {
        log.debug("REST request to get OptionalService : {}", id);
        Optional<OptionalService> optionalService = optionalServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionalService);
    }

    /**
     * DELETE  /optional-services/:id : delete the "id" optionalService.
     *
     * @param id the id of the optionalService to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/optional-services/{id}")
    public ResponseEntity<Void> deleteOptionalService(@PathVariable String id) {
        log.debug("REST request to delete OptionalService : {}", id);
        optionalServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/optional-services?query=:query : search for the optionalService corresponding
     * to the query.
     *
     * @param query the query of the optionalService search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/optional-services")
    public ResponseEntity<List<OptionalService>> searchOptionalServices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OptionalServices for query {}", query);
        Page<OptionalService> page = optionalServiceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/optional-services");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
