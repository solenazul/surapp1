package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.Tableros;
import com.be4tech.surap.service.TablerosService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.TablerosCriteria;
import com.be4tech.surap.service.TablerosQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.be4tech.surap.domain.Tableros}.
 */
@RestController
@RequestMapping("/api")
public class TablerosResource {

    private final Logger log = LoggerFactory.getLogger(TablerosResource.class);

    private static final String ENTITY_NAME = "tableros";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TablerosService tablerosService;

    private final TablerosQueryService tablerosQueryService;

    public TablerosResource(TablerosService tablerosService, TablerosQueryService tablerosQueryService) {
        this.tablerosService = tablerosService;
        this.tablerosQueryService = tablerosQueryService;
    }

    /**
     * {@code POST  /tableros} : Create a new tableros.
     *
     * @param tableros the tableros to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tableros, or with status {@code 400 (Bad Request)} if the tableros has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tableros")
    public ResponseEntity<Tableros> createTableros(@RequestBody Tableros tableros) throws URISyntaxException {
        log.debug("REST request to save Tableros : {}", tableros);
        if (tableros.getId() != null) {
            throw new BadRequestAlertException("A new tableros cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tableros result = tablerosService.save(tableros);
        return ResponseEntity.created(new URI("/api/tableros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tableros} : Updates an existing tableros.
     *
     * @param tableros the tableros to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tableros,
     * or with status {@code 400 (Bad Request)} if the tableros is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tableros couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tableros")
    public ResponseEntity<Tableros> updateTableros(@RequestBody Tableros tableros) throws URISyntaxException {
        log.debug("REST request to update Tableros : {}", tableros);
        if (tableros.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tableros result = tablerosService.save(tableros);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tableros.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tableros} : get all the tableros.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tableros in body.
     */
    @GetMapping("/tableros")
    public ResponseEntity<List<Tableros>> getAllTableros(TablerosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Tableros by criteria: {}", criteria);
        Page<Tableros> page = tablerosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tableros/count} : count all the tableros.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tableros/count")
    public ResponseEntity<Long> countTableros(TablerosCriteria criteria) {
        log.debug("REST request to count Tableros by criteria: {}", criteria);
        return ResponseEntity.ok().body(tablerosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tableros/:id} : get the "id" tableros.
     *
     * @param id the id of the tableros to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tableros, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tableros/{id}")
    public ResponseEntity<Tableros> getTableros(@PathVariable Long id) {
        log.debug("REST request to get Tableros : {}", id);
        Optional<Tableros> tableros = tablerosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tableros);
    }

    /**
     * {@code DELETE  /tableros/:id} : delete the "id" tableros.
     *
     * @param id the id of the tableros to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tableros/{id}")
    public ResponseEntity<Void> deleteTableros(@PathVariable Long id) {
        log.debug("REST request to delete Tableros : {}", id);
        tablerosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
