package ericsson.com.catalog.web.rest;
import ericsson.com.catalog.domain.Characteristic;
import ericsson.com.catalog.service.CharacteristicService;
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
 * REST controller for managing Characteristic.
 */
@RestController
@RequestMapping("/api")
public class CharacteristicResource {

    private final Logger log = LoggerFactory.getLogger(CharacteristicResource.class);

    private static final String ENTITY_NAME = "characteristic";

    private final CharacteristicService characteristicService;

    public CharacteristicResource(CharacteristicService characteristicService) {
        this.characteristicService = characteristicService;
    }

    /**
     * POST  /characteristics : Create a new characteristic.
     *
     * @param characteristic the characteristic to create
     * @return the ResponseEntity with status 201 (Created) and with body the new characteristic, or with status 400 (Bad Request) if the characteristic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/characteristics")
    public ResponseEntity<Characteristic> createCharacteristic(@RequestBody Characteristic characteristic) throws URISyntaxException {
        log.debug("REST request to save Characteristic : {}", characteristic);
        if (characteristic.getId() != null) {
            throw new BadRequestAlertException("A new characteristic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Characteristic result = characteristicService.save(characteristic);
        return ResponseEntity.created(new URI("/api/characteristics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /characteristics : Updates an existing characteristic.
     *
     * @param characteristic the characteristic to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated characteristic,
     * or with status 400 (Bad Request) if the characteristic is not valid,
     * or with status 500 (Internal Server Error) if the characteristic couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/characteristics")
    public ResponseEntity<Characteristic> updateCharacteristic(@RequestBody Characteristic characteristic) throws URISyntaxException {
        log.debug("REST request to update Characteristic : {}", characteristic);
        if (characteristic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Characteristic result = characteristicService.save(characteristic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, characteristic.getId().toString()))
            .body(result);
    }

    /**
     * GET  /characteristics : get all the characteristics.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of characteristics in body
     */
    @GetMapping("/characteristics")
    public ResponseEntity<List<Characteristic>> getAllCharacteristics(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Characteristics");
        Page<Characteristic> page;
        if (eagerload) {
            page = characteristicService.findAllWithEagerRelationships(pageable);
        } else {
            page = characteristicService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/characteristics?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /characteristics/:id : get the "id" characteristic.
     *
     * @param id the id of the characteristic to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the characteristic, or with status 404 (Not Found)
     */
    @GetMapping("/characteristics/{id}")
    public ResponseEntity<Characteristic> getCharacteristic(@PathVariable String id) {
        log.debug("REST request to get Characteristic : {}", id);
        Optional<Characteristic> characteristic = characteristicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(characteristic);
    }

    /**
     * DELETE  /characteristics/:id : delete the "id" characteristic.
     *
     * @param id the id of the characteristic to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/characteristics/{id}")
    public ResponseEntity<Void> deleteCharacteristic(@PathVariable String id) {
        log.debug("REST request to delete Characteristic : {}", id);
        characteristicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/characteristics?query=:query : search for the characteristic corresponding
     * to the query.
     *
     * @param query the query of the characteristic search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/characteristics")
    public ResponseEntity<List<Characteristic>> searchCharacteristics(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Characteristics for query {}", query);
        Page<Characteristic> page = characteristicService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/characteristics");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
