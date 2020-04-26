package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.Fisiometria1;
import com.be4tech.surap.service.Fisiometria1Service;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.Fisiometria1Criteria;
import com.be4tech.surap.service.Fisiometria1QueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.Fisiometria1}.
 */
@RestController
@RequestMapping("/api")
public class Fisiometria1Resource {

    private final Logger log = LoggerFactory.getLogger(Fisiometria1Resource.class);

    private static final String ENTITY_NAME = "fisiometria1";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Fisiometria1Service fisiometria1Service;

    private final Fisiometria1QueryService fisiometria1QueryService;

    public Fisiometria1Resource(Fisiometria1Service fisiometria1Service, Fisiometria1QueryService fisiometria1QueryService) {
        this.fisiometria1Service = fisiometria1Service;
        this.fisiometria1QueryService = fisiometria1QueryService;
    }

    /**
     * {@code POST  /fisiometria-1-s} : Create a new fisiometria1.
     *
     * @param fisiometria1 the fisiometria1 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fisiometria1, or with status {@code 400 (Bad Request)} if the fisiometria1 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fisiometria-1-s")
    public ResponseEntity<Fisiometria1> createFisiometria1(@RequestBody Fisiometria1 fisiometria1) throws URISyntaxException {
        log.debug("REST request to save Fisiometria1 : {}", fisiometria1);
        if (fisiometria1.getId() != null) {
            throw new BadRequestAlertException("A new fisiometria1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fisiometria1 result = fisiometria1Service.save(fisiometria1);
        return ResponseEntity.created(new URI("/api/fisiometria-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fisiometria-1-s} : Updates an existing fisiometria1.
     *
     * @param fisiometria1 the fisiometria1 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fisiometria1,
     * or with status {@code 400 (Bad Request)} if the fisiometria1 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fisiometria1 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fisiometria-1-s")
    public ResponseEntity<Fisiometria1> updateFisiometria1(@RequestBody Fisiometria1 fisiometria1) throws URISyntaxException {
        log.debug("REST request to update Fisiometria1 : {}", fisiometria1);
        if (fisiometria1.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fisiometria1 result = fisiometria1Service.save(fisiometria1);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fisiometria1.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fisiometria-1-s} : get all the fisiometria1S.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fisiometria1S in body.
     */
    @GetMapping("/fisiometria-1-s")
    public ResponseEntity<List<Fisiometria1>> getAllFisiometria1S(Fisiometria1Criteria criteria, Pageable pageable) {
        log.debug("REST request to get Fisiometria1S by criteria: {}", criteria);
        Page<Fisiometria1> page = fisiometria1QueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fisiometria-1-s/count} : count all the fisiometria1S.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fisiometria-1-s/count")
    public ResponseEntity<Long> countFisiometria1S(Fisiometria1Criteria criteria) {
        log.debug("REST request to count Fisiometria1S by criteria: {}", criteria);
        return ResponseEntity.ok().body(fisiometria1QueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fisiometria-1-s/:id} : get the "id" fisiometria1.
     *
     * @param id the id of the fisiometria1 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fisiometria1, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fisiometria-1-s/{id}")
    public ResponseEntity<Fisiometria1> getFisiometria1(@PathVariable Long id) {
        log.debug("REST request to get Fisiometria1 : {}", id);
        Optional<Fisiometria1> fisiometria1 = fisiometria1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(fisiometria1);
    }

    /**
     * {@code DELETE  /fisiometria-1-s/:id} : delete the "id" fisiometria1.
     *
     * @param id the id of the fisiometria1 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fisiometria-1-s/{id}")
    public ResponseEntity<Void> deleteFisiometria1(@PathVariable Long id) {
        log.debug("REST request to delete Fisiometria1 : {}", id);
        fisiometria1Service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
