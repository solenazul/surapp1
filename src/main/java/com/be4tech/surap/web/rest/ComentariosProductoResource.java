package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.ComentariosProducto;
import com.be4tech.surap.service.ComentariosProductoService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.ComentariosProductoCriteria;
import com.be4tech.surap.service.ComentariosProductoQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.ComentariosProducto}.
 */
@RestController
@RequestMapping("/api")
public class ComentariosProductoResource {

    private final Logger log = LoggerFactory.getLogger(ComentariosProductoResource.class);

    private static final String ENTITY_NAME = "comentariosProducto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComentariosProductoService comentariosProductoService;

    private final ComentariosProductoQueryService comentariosProductoQueryService;

    public ComentariosProductoResource(ComentariosProductoService comentariosProductoService, ComentariosProductoQueryService comentariosProductoQueryService) {
        this.comentariosProductoService = comentariosProductoService;
        this.comentariosProductoQueryService = comentariosProductoQueryService;
    }

    /**
     * {@code POST  /comentarios-productos} : Create a new comentariosProducto.
     *
     * @param comentariosProducto the comentariosProducto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comentariosProducto, or with status {@code 400 (Bad Request)} if the comentariosProducto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comentarios-productos")
    public ResponseEntity<ComentariosProducto> createComentariosProducto(@RequestBody ComentariosProducto comentariosProducto) throws URISyntaxException {
        log.debug("REST request to save ComentariosProducto : {}", comentariosProducto);
        if (comentariosProducto.getId() != null) {
            throw new BadRequestAlertException("A new comentariosProducto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComentariosProducto result = comentariosProductoService.save(comentariosProducto);
        return ResponseEntity.created(new URI("/api/comentarios-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comentarios-productos} : Updates an existing comentariosProducto.
     *
     * @param comentariosProducto the comentariosProducto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comentariosProducto,
     * or with status {@code 400 (Bad Request)} if the comentariosProducto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comentariosProducto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comentarios-productos")
    public ResponseEntity<ComentariosProducto> updateComentariosProducto(@RequestBody ComentariosProducto comentariosProducto) throws URISyntaxException {
        log.debug("REST request to update ComentariosProducto : {}", comentariosProducto);
        if (comentariosProducto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComentariosProducto result = comentariosProductoService.save(comentariosProducto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comentariosProducto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comentarios-productos} : get all the comentariosProductos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comentariosProductos in body.
     */
    @GetMapping("/comentarios-productos")
    public ResponseEntity<List<ComentariosProducto>> getAllComentariosProductos(ComentariosProductoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ComentariosProductos by criteria: {}", criteria);
        Page<ComentariosProducto> page = comentariosProductoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comentarios-productos/count} : count all the comentariosProductos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/comentarios-productos/count")
    public ResponseEntity<Long> countComentariosProductos(ComentariosProductoCriteria criteria) {
        log.debug("REST request to count ComentariosProductos by criteria: {}", criteria);
        return ResponseEntity.ok().body(comentariosProductoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /comentarios-productos/:id} : get the "id" comentariosProducto.
     *
     * @param id the id of the comentariosProducto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comentariosProducto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comentarios-productos/{id}")
    public ResponseEntity<ComentariosProducto> getComentariosProducto(@PathVariable Long id) {
        log.debug("REST request to get ComentariosProducto : {}", id);
        Optional<ComentariosProducto> comentariosProducto = comentariosProductoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comentariosProducto);
    }

    /**
     * {@code DELETE  /comentarios-productos/:id} : delete the "id" comentariosProducto.
     *
     * @param id the id of the comentariosProducto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comentarios-productos/{id}")
    public ResponseEntity<Void> deleteComentariosProducto(@PathVariable Long id) {
        log.debug("REST request to delete ComentariosProducto : {}", id);
        comentariosProductoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
