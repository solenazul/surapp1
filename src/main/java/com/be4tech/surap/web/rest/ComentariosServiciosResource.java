package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.ComentariosServicios;
import com.be4tech.surap.service.ComentariosServiciosService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.ComentariosServiciosCriteria;
import com.be4tech.surap.service.ComentariosServiciosQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.ComentariosServicios}.
 */
@RestController
@RequestMapping("/api")
public class ComentariosServiciosResource {

    private final Logger log = LoggerFactory.getLogger(ComentariosServiciosResource.class);

    private static final String ENTITY_NAME = "comentariosServicios";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComentariosServiciosService comentariosServiciosService;

    private final ComentariosServiciosQueryService comentariosServiciosQueryService;

    public ComentariosServiciosResource(ComentariosServiciosService comentariosServiciosService, ComentariosServiciosQueryService comentariosServiciosQueryService) {
        this.comentariosServiciosService = comentariosServiciosService;
        this.comentariosServiciosQueryService = comentariosServiciosQueryService;
    }

    /**
     * {@code POST  /comentarios-servicios} : Create a new comentariosServicios.
     *
     * @param comentariosServicios the comentariosServicios to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comentariosServicios, or with status {@code 400 (Bad Request)} if the comentariosServicios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comentarios-servicios")
    public ResponseEntity<ComentariosServicios> createComentariosServicios(@RequestBody ComentariosServicios comentariosServicios) throws URISyntaxException {
        log.debug("REST request to save ComentariosServicios : {}", comentariosServicios);
        if (comentariosServicios.getId() != null) {
            throw new BadRequestAlertException("A new comentariosServicios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComentariosServicios result = comentariosServiciosService.save(comentariosServicios);
        return ResponseEntity.created(new URI("/api/comentarios-servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comentarios-servicios} : Updates an existing comentariosServicios.
     *
     * @param comentariosServicios the comentariosServicios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comentariosServicios,
     * or with status {@code 400 (Bad Request)} if the comentariosServicios is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comentariosServicios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comentarios-servicios")
    public ResponseEntity<ComentariosServicios> updateComentariosServicios(@RequestBody ComentariosServicios comentariosServicios) throws URISyntaxException {
        log.debug("REST request to update ComentariosServicios : {}", comentariosServicios);
        if (comentariosServicios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComentariosServicios result = comentariosServiciosService.save(comentariosServicios);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comentariosServicios.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comentarios-servicios} : get all the comentariosServicios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comentariosServicios in body.
     */
    @GetMapping("/comentarios-servicios")
    public ResponseEntity<List<ComentariosServicios>> getAllComentariosServicios(ComentariosServiciosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ComentariosServicios by criteria: {}", criteria);
        Page<ComentariosServicios> page = comentariosServiciosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comentarios-servicios/count} : count all the comentariosServicios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/comentarios-servicios/count")
    public ResponseEntity<Long> countComentariosServicios(ComentariosServiciosCriteria criteria) {
        log.debug("REST request to count ComentariosServicios by criteria: {}", criteria);
        return ResponseEntity.ok().body(comentariosServiciosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /comentarios-servicios/:id} : get the "id" comentariosServicios.
     *
     * @param id the id of the comentariosServicios to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comentariosServicios, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comentarios-servicios/{id}")
    public ResponseEntity<ComentariosServicios> getComentariosServicios(@PathVariable Long id) {
        log.debug("REST request to get ComentariosServicios : {}", id);
        Optional<ComentariosServicios> comentariosServicios = comentariosServiciosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comentariosServicios);
    }

    /**
     * {@code DELETE  /comentarios-servicios/:id} : delete the "id" comentariosServicios.
     *
     * @param id the id of the comentariosServicios to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comentarios-servicios/{id}")
    public ResponseEntity<Void> deleteComentariosServicios(@PathVariable Long id) {
        log.debug("REST request to delete ComentariosServicios : {}", id);
        comentariosServiciosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
