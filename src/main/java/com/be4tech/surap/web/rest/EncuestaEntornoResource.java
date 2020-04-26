package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.EncuestaEntorno;
import com.be4tech.surap.service.EncuestaEntornoService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.EncuestaEntornoCriteria;
import com.be4tech.surap.service.EncuestaEntornoQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.EncuestaEntorno}.
 */
@RestController
@RequestMapping("/api")
public class EncuestaEntornoResource {

    private final Logger log = LoggerFactory.getLogger(EncuestaEntornoResource.class);

    private static final String ENTITY_NAME = "encuestaEntorno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EncuestaEntornoService encuestaEntornoService;

    private final EncuestaEntornoQueryService encuestaEntornoQueryService;

    public EncuestaEntornoResource(EncuestaEntornoService encuestaEntornoService, EncuestaEntornoQueryService encuestaEntornoQueryService) {
        this.encuestaEntornoService = encuestaEntornoService;
        this.encuestaEntornoQueryService = encuestaEntornoQueryService;
    }

    /**
     * {@code POST  /encuesta-entornos} : Create a new encuestaEntorno.
     *
     * @param encuestaEntorno the encuestaEntorno to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new encuestaEntorno, or with status {@code 400 (Bad Request)} if the encuestaEntorno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/encuesta-entornos")
    public ResponseEntity<EncuestaEntorno> createEncuestaEntorno(@RequestBody EncuestaEntorno encuestaEntorno) throws URISyntaxException {
        log.debug("REST request to save EncuestaEntorno : {}", encuestaEntorno);
        if (encuestaEntorno.getId() != null) {
            throw new BadRequestAlertException("A new encuestaEntorno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EncuestaEntorno result = encuestaEntornoService.save(encuestaEntorno);
        return ResponseEntity.created(new URI("/api/encuesta-entornos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /encuesta-entornos} : Updates an existing encuestaEntorno.
     *
     * @param encuestaEntorno the encuestaEntorno to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encuestaEntorno,
     * or with status {@code 400 (Bad Request)} if the encuestaEntorno is not valid,
     * or with status {@code 500 (Internal Server Error)} if the encuestaEntorno couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/encuesta-entornos")
    public ResponseEntity<EncuestaEntorno> updateEncuestaEntorno(@RequestBody EncuestaEntorno encuestaEntorno) throws URISyntaxException {
        log.debug("REST request to update EncuestaEntorno : {}", encuestaEntorno);
        if (encuestaEntorno.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EncuestaEntorno result = encuestaEntornoService.save(encuestaEntorno);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, encuestaEntorno.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /encuesta-entornos} : get all the encuestaEntornos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of encuestaEntornos in body.
     */
    @GetMapping("/encuesta-entornos")
    public ResponseEntity<List<EncuestaEntorno>> getAllEncuestaEntornos(EncuestaEntornoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EncuestaEntornos by criteria: {}", criteria);
        Page<EncuestaEntorno> page = encuestaEntornoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /encuesta-entornos/count} : count all the encuestaEntornos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/encuesta-entornos/count")
    public ResponseEntity<Long> countEncuestaEntornos(EncuestaEntornoCriteria criteria) {
        log.debug("REST request to count EncuestaEntornos by criteria: {}", criteria);
        return ResponseEntity.ok().body(encuestaEntornoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /encuesta-entornos/:id} : get the "id" encuestaEntorno.
     *
     * @param id the id of the encuestaEntorno to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the encuestaEntorno, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/encuesta-entornos/{id}")
    public ResponseEntity<EncuestaEntorno> getEncuestaEntorno(@PathVariable Long id) {
        log.debug("REST request to get EncuestaEntorno : {}", id);
        Optional<EncuestaEntorno> encuestaEntorno = encuestaEntornoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(encuestaEntorno);
    }

    /**
     * {@code DELETE  /encuesta-entornos/:id} : delete the "id" encuestaEntorno.
     *
     * @param id the id of the encuestaEntorno to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/encuesta-entornos/{id}")
    public ResponseEntity<Void> deleteEncuestaEntorno(@PathVariable Long id) {
        log.debug("REST request to delete EncuestaEntorno : {}", id);
        encuestaEntornoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
