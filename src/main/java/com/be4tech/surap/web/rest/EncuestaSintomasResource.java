package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.EncuestaSintomas;
import com.be4tech.surap.service.EncuestaSintomasService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.EncuestaSintomasCriteria;
import com.be4tech.surap.service.EncuestaSintomasQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.EncuestaSintomas}.
 */
@RestController
@RequestMapping("/api")
public class EncuestaSintomasResource {

    private final Logger log = LoggerFactory.getLogger(EncuestaSintomasResource.class);

    private static final String ENTITY_NAME = "encuestaSintomas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EncuestaSintomasService encuestaSintomasService;

    private final EncuestaSintomasQueryService encuestaSintomasQueryService;

    public EncuestaSintomasResource(EncuestaSintomasService encuestaSintomasService, EncuestaSintomasQueryService encuestaSintomasQueryService) {
        this.encuestaSintomasService = encuestaSintomasService;
        this.encuestaSintomasQueryService = encuestaSintomasQueryService;
    }

    /**
     * {@code POST  /encuesta-sintomas} : Create a new encuestaSintomas.
     *
     * @param encuestaSintomas the encuestaSintomas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new encuestaSintomas, or with status {@code 400 (Bad Request)} if the encuestaSintomas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/encuesta-sintomas")
    public ResponseEntity<EncuestaSintomas> createEncuestaSintomas(@RequestBody EncuestaSintomas encuestaSintomas) throws URISyntaxException {
        log.debug("REST request to save EncuestaSintomas : {}", encuestaSintomas);
        if (encuestaSintomas.getId() != null) {
            throw new BadRequestAlertException("A new encuestaSintomas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EncuestaSintomas result = encuestaSintomasService.save(encuestaSintomas);
        return ResponseEntity.created(new URI("/api/encuesta-sintomas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /encuesta-sintomas} : Updates an existing encuestaSintomas.
     *
     * @param encuestaSintomas the encuestaSintomas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encuestaSintomas,
     * or with status {@code 400 (Bad Request)} if the encuestaSintomas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the encuestaSintomas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/encuesta-sintomas")
    public ResponseEntity<EncuestaSintomas> updateEncuestaSintomas(@RequestBody EncuestaSintomas encuestaSintomas) throws URISyntaxException {
        log.debug("REST request to update EncuestaSintomas : {}", encuestaSintomas);
        if (encuestaSintomas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EncuestaSintomas result = encuestaSintomasService.save(encuestaSintomas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, encuestaSintomas.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /encuesta-sintomas} : get all the encuestaSintomas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of encuestaSintomas in body.
     */
    @GetMapping("/encuesta-sintomas")
    public ResponseEntity<List<EncuestaSintomas>> getAllEncuestaSintomas(EncuestaSintomasCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EncuestaSintomas by criteria: {}", criteria);
        Page<EncuestaSintomas> page = encuestaSintomasQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /encuesta-sintomas/count} : count all the encuestaSintomas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/encuesta-sintomas/count")
    public ResponseEntity<Long> countEncuestaSintomas(EncuestaSintomasCriteria criteria) {
        log.debug("REST request to count EncuestaSintomas by criteria: {}", criteria);
        return ResponseEntity.ok().body(encuestaSintomasQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /encuesta-sintomas/:id} : get the "id" encuestaSintomas.
     *
     * @param id the id of the encuestaSintomas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the encuestaSintomas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/encuesta-sintomas/{id}")
    public ResponseEntity<EncuestaSintomas> getEncuestaSintomas(@PathVariable Long id) {
        log.debug("REST request to get EncuestaSintomas : {}", id);
        Optional<EncuestaSintomas> encuestaSintomas = encuestaSintomasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(encuestaSintomas);
    }

    /**
     * {@code DELETE  /encuesta-sintomas/:id} : delete the "id" encuestaSintomas.
     *
     * @param id the id of the encuestaSintomas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/encuesta-sintomas/{id}")
    public ResponseEntity<Void> deleteEncuestaSintomas(@PathVariable Long id) {
        log.debug("REST request to delete EncuestaSintomas : {}", id);
        encuestaSintomasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
