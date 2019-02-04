package ericsson.com.catalog.web.rest;
import ericsson.com.catalog.domain.ChargingSystem;
import ericsson.com.catalog.service.ChargingSystemService;
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
 * REST controller for managing ChargingSystem.
 */
@RestController
@RequestMapping("/api")
public class ChargingSystemResource {

    private final Logger log = LoggerFactory.getLogger(ChargingSystemResource.class);

    private static final String ENTITY_NAME = "chargingSystem";

    private final ChargingSystemService chargingSystemService;

    public ChargingSystemResource(ChargingSystemService chargingSystemService) {
        this.chargingSystemService = chargingSystemService;
    }

    /**
     * POST  /charging-systems : Create a new chargingSystem.
     *
     * @param chargingSystem the chargingSystem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chargingSystem, or with status 400 (Bad Request) if the chargingSystem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/charging-systems")
    public ResponseEntity<ChargingSystem> createChargingSystem(@RequestBody ChargingSystem chargingSystem) throws URISyntaxException {
        log.debug("REST request to save ChargingSystem : {}", chargingSystem);
        if (chargingSystem.getId() != null) {
            throw new BadRequestAlertException("A new chargingSystem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChargingSystem result = chargingSystemService.save(chargingSystem);
        return ResponseEntity.created(new URI("/api/charging-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /charging-systems : Updates an existing chargingSystem.
     *
     * @param chargingSystem the chargingSystem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chargingSystem,
     * or with status 400 (Bad Request) if the chargingSystem is not valid,
     * or with status 500 (Internal Server Error) if the chargingSystem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/charging-systems")
    public ResponseEntity<ChargingSystem> updateChargingSystem(@RequestBody ChargingSystem chargingSystem) throws URISyntaxException {
        log.debug("REST request to update ChargingSystem : {}", chargingSystem);
        if (chargingSystem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChargingSystem result = chargingSystemService.save(chargingSystem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chargingSystem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /charging-systems : get all the chargingSystems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chargingSystems in body
     */
    @GetMapping("/charging-systems")
    public ResponseEntity<List<ChargingSystem>> getAllChargingSystems(Pageable pageable) {
        log.debug("REST request to get a page of ChargingSystems");
        Page<ChargingSystem> page = chargingSystemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/charging-systems");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /charging-systems/:id : get the "id" chargingSystem.
     *
     * @param id the id of the chargingSystem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chargingSystem, or with status 404 (Not Found)
     */
    @GetMapping("/charging-systems/{id}")
    public ResponseEntity<ChargingSystem> getChargingSystem(@PathVariable String id) {
        log.debug("REST request to get ChargingSystem : {}", id);
        Optional<ChargingSystem> chargingSystem = chargingSystemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chargingSystem);
    }

    /**
     * DELETE  /charging-systems/:id : delete the "id" chargingSystem.
     *
     * @param id the id of the chargingSystem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/charging-systems/{id}")
    public ResponseEntity<Void> deleteChargingSystem(@PathVariable String id) {
        log.debug("REST request to delete ChargingSystem : {}", id);
        chargingSystemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/charging-systems?query=:query : search for the chargingSystem corresponding
     * to the query.
     *
     * @param query the query of the chargingSystem search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/charging-systems")
    public ResponseEntity<List<ChargingSystem>> searchChargingSystems(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ChargingSystems for query {}", query);
        Page<ChargingSystem> page = chargingSystemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/charging-systems");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
