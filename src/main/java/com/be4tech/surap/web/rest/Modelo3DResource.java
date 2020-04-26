package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.Modelo3D;
import com.be4tech.surap.service.Modelo3DService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.Modelo3DCriteria;
import com.be4tech.surap.service.Modelo3DQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.Modelo3D}.
 */
@RestController
@RequestMapping("/api")
public class Modelo3DResource {

    private final Logger log = LoggerFactory.getLogger(Modelo3DResource.class);

    private static final String ENTITY_NAME = "modelo3D";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Modelo3DService modelo3DService;

    private final Modelo3DQueryService modelo3DQueryService;

    public Modelo3DResource(Modelo3DService modelo3DService, Modelo3DQueryService modelo3DQueryService) {
        this.modelo3DService = modelo3DService;
        this.modelo3DQueryService = modelo3DQueryService;
    }

    /**
     * {@code POST  /modelo-3-ds} : Create a new modelo3D.
     *
     * @param modelo3D the modelo3D to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modelo3D, or with status {@code 400 (Bad Request)} if the modelo3D has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/modelo-3-ds")
    public ResponseEntity<Modelo3D> createModelo3D(@RequestBody Modelo3D modelo3D) throws URISyntaxException {
        log.debug("REST request to save Modelo3D : {}", modelo3D);
        if (modelo3D.getId() != null) {
            throw new BadRequestAlertException("A new modelo3D cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Modelo3D result = modelo3DService.save(modelo3D);
        return ResponseEntity.created(new URI("/api/modelo-3-ds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /modelo-3-ds} : Updates an existing modelo3D.
     *
     * @param modelo3D the modelo3D to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelo3D,
     * or with status {@code 400 (Bad Request)} if the modelo3D is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modelo3D couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/modelo-3-ds")
    public ResponseEntity<Modelo3D> updateModelo3D(@RequestBody Modelo3D modelo3D) throws URISyntaxException {
        log.debug("REST request to update Modelo3D : {}", modelo3D);
        if (modelo3D.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Modelo3D result = modelo3DService.save(modelo3D);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modelo3D.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /modelo-3-ds} : get all the modelo3DS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modelo3DS in body.
     */
    @GetMapping("/modelo-3-ds")
    public ResponseEntity<List<Modelo3D>> getAllModelo3DS(Modelo3DCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Modelo3DS by criteria: {}", criteria);
        Page<Modelo3D> page = modelo3DQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /modelo-3-ds/count} : count all the modelo3DS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/modelo-3-ds/count")
    public ResponseEntity<Long> countModelo3DS(Modelo3DCriteria criteria) {
        log.debug("REST request to count Modelo3DS by criteria: {}", criteria);
        return ResponseEntity.ok().body(modelo3DQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /modelo-3-ds/:id} : get the "id" modelo3D.
     *
     * @param id the id of the modelo3D to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modelo3D, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/modelo-3-ds/{id}")
    public ResponseEntity<Modelo3D> getModelo3D(@PathVariable Long id) {
        log.debug("REST request to get Modelo3D : {}", id);
        Optional<Modelo3D> modelo3D = modelo3DService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modelo3D);
    }

    /**
     * {@code DELETE  /modelo-3-ds/:id} : delete the "id" modelo3D.
     *
     * @param id the id of the modelo3D to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/modelo-3-ds/{id}")
    public ResponseEntity<Void> deleteModelo3D(@PathVariable Long id) {
        log.debug("REST request to delete Modelo3D : {}", id);
        modelo3DService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
