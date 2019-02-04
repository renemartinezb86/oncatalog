package ericsson.com.catalog.web.rest;
import ericsson.com.catalog.domain.NetResource;
import ericsson.com.catalog.service.NetResourceService;
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
 * REST controller for managing NetResource.
 */
@RestController
@RequestMapping("/api")
public class NetResourceResource {

    private final Logger log = LoggerFactory.getLogger(NetResourceResource.class);

    private static final String ENTITY_NAME = "netResource";

    private final NetResourceService netResourceService;

    public NetResourceResource(NetResourceService netResourceService) {
        this.netResourceService = netResourceService;
    }

    /**
     * POST  /net-resources : Create a new netResource.
     *
     * @param netResource the netResource to create
     * @return the ResponseEntity with status 201 (Created) and with body the new netResource, or with status 400 (Bad Request) if the netResource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/net-resources")
    public ResponseEntity<NetResource> createNetResource(@RequestBody NetResource netResource) throws URISyntaxException {
        log.debug("REST request to save NetResource : {}", netResource);
        if (netResource.getId() != null) {
            throw new BadRequestAlertException("A new netResource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NetResource result = netResourceService.save(netResource);
        return ResponseEntity.created(new URI("/api/net-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /net-resources : Updates an existing netResource.
     *
     * @param netResource the netResource to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated netResource,
     * or with status 400 (Bad Request) if the netResource is not valid,
     * or with status 500 (Internal Server Error) if the netResource couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/net-resources")
    public ResponseEntity<NetResource> updateNetResource(@RequestBody NetResource netResource) throws URISyntaxException {
        log.debug("REST request to update NetResource : {}", netResource);
        if (netResource.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NetResource result = netResourceService.save(netResource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, netResource.getId().toString()))
            .body(result);
    }

    /**
     * GET  /net-resources : get all the netResources.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of netResources in body
     */
    @GetMapping("/net-resources")
    public ResponseEntity<List<NetResource>> getAllNetResources(Pageable pageable) {
        log.debug("REST request to get a page of NetResources");
        Page<NetResource> page = netResourceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/net-resources");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /net-resources/:id : get the "id" netResource.
     *
     * @param id the id of the netResource to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the netResource, or with status 404 (Not Found)
     */
    @GetMapping("/net-resources/{id}")
    public ResponseEntity<NetResource> getNetResource(@PathVariable String id) {
        log.debug("REST request to get NetResource : {}", id);
        Optional<NetResource> netResource = netResourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(netResource);
    }

    /**
     * DELETE  /net-resources/:id : delete the "id" netResource.
     *
     * @param id the id of the netResource to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/net-resources/{id}")
    public ResponseEntity<Void> deleteNetResource(@PathVariable String id) {
        log.debug("REST request to delete NetResource : {}", id);
        netResourceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/net-resources?query=:query : search for the netResource corresponding
     * to the query.
     *
     * @param query the query of the netResource search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/net-resources")
    public ResponseEntity<List<NetResource>> searchNetResources(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of NetResources for query {}", query);
        Page<NetResource> page = netResourceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/net-resources");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
