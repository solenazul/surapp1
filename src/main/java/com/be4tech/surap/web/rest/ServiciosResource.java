package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.Servicios;
import com.be4tech.surap.service.ServiciosService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.ServiciosCriteria;
import com.be4tech.surap.service.ServiciosQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.Servicios}.
 */
@RestController
@RequestMapping("/api")
public class ServiciosResource {

    private final Logger log = LoggerFactory.getLogger(ServiciosResource.class);

    private static final String ENTITY_NAME = "servicios";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiciosService serviciosService;

    private final ServiciosQueryService serviciosQueryService;

    public ServiciosResource(ServiciosService serviciosService, ServiciosQueryService serviciosQueryService) {
        this.serviciosService = serviciosService;
        this.serviciosQueryService = serviciosQueryService;
    }

    /**
     * {@code POST  /servicios} : Create a new servicios.
     *
     * @param servicios the servicios to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicios, or with status {@code 400 (Bad Request)} if the servicios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/servicios")
    public ResponseEntity<Servicios> createServicios(@RequestBody Servicios servicios) throws URISyntaxException {
        log.debug("REST request to save Servicios : {}", servicios);
        if (servicios.getId() != null) {
            throw new BadRequestAlertException("A new servicios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Servicios result = serviciosService.save(servicios);
        return ResponseEntity.created(new URI("/api/servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /servicios} : Updates an existing servicios.
     *
     * @param servicios the servicios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicios,
     * or with status {@code 400 (Bad Request)} if the servicios is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/servicios")
    public ResponseEntity<Servicios> updateServicios(@RequestBody Servicios servicios) throws URISyntaxException {
        log.debug("REST request to update Servicios : {}", servicios);
        if (servicios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Servicios result = serviciosService.save(servicios);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicios.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /servicios} : get all the servicios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servicios in body.
     */
    @GetMapping("/servicios")
    public ResponseEntity<List<Servicios>> getAllServicios(ServiciosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Servicios by criteria: {}", criteria);
        Page<Servicios> page = serviciosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /servicios/count} : count all the servicios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/servicios/count")
    public ResponseEntity<Long> countServicios(ServiciosCriteria criteria) {
        log.debug("REST request to count Servicios by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviciosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /servicios/:id} : get the "id" servicios.
     *
     * @param id the id of the servicios to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicios, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/servicios/{id}")
    public ResponseEntity<Servicios> getServicios(@PathVariable Long id) {
        log.debug("REST request to get Servicios : {}", id);
        Optional<Servicios> servicios = serviciosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servicios);
    }

    /**
     * {@code DELETE  /servicios/:id} : delete the "id" servicios.
     *
     * @param id the id of the servicios to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/servicios/{id}")
    public ResponseEntity<Void> deleteServicios(@PathVariable Long id) {
        log.debug("REST request to delete Servicios : {}", id);
        serviciosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
