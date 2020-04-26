package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.CategoriaProducto;
import com.be4tech.surap.service.CategoriaProductoService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.CategoriaProductoCriteria;
import com.be4tech.surap.service.CategoriaProductoQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.CategoriaProducto}.
 */
@RestController
@RequestMapping("/api")
public class CategoriaProductoResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaProductoResource.class);

    private static final String ENTITY_NAME = "categoriaProducto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaProductoService categoriaProductoService;

    private final CategoriaProductoQueryService categoriaProductoQueryService;

    public CategoriaProductoResource(CategoriaProductoService categoriaProductoService, CategoriaProductoQueryService categoriaProductoQueryService) {
        this.categoriaProductoService = categoriaProductoService;
        this.categoriaProductoQueryService = categoriaProductoQueryService;
    }

    /**
     * {@code POST  /categoria-productos} : Create a new categoriaProducto.
     *
     * @param categoriaProducto the categoriaProducto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriaProducto, or with status {@code 400 (Bad Request)} if the categoriaProducto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categoria-productos")
    public ResponseEntity<CategoriaProducto> createCategoriaProducto(@RequestBody CategoriaProducto categoriaProducto) throws URISyntaxException {
        log.debug("REST request to save CategoriaProducto : {}", categoriaProducto);
        if (categoriaProducto.getId() != null) {
            throw new BadRequestAlertException("A new categoriaProducto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaProducto result = categoriaProductoService.save(categoriaProducto);
        return ResponseEntity.created(new URI("/api/categoria-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categoria-productos} : Updates an existing categoriaProducto.
     *
     * @param categoriaProducto the categoriaProducto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaProducto,
     * or with status {@code 400 (Bad Request)} if the categoriaProducto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriaProducto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categoria-productos")
    public ResponseEntity<CategoriaProducto> updateCategoriaProducto(@RequestBody CategoriaProducto categoriaProducto) throws URISyntaxException {
        log.debug("REST request to update CategoriaProducto : {}", categoriaProducto);
        if (categoriaProducto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoriaProducto result = categoriaProductoService.save(categoriaProducto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaProducto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categoria-productos} : get all the categoriaProductos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriaProductos in body.
     */
    @GetMapping("/categoria-productos")
    public ResponseEntity<List<CategoriaProducto>> getAllCategoriaProductos(CategoriaProductoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CategoriaProductos by criteria: {}", criteria);
        Page<CategoriaProducto> page = categoriaProductoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categoria-productos/count} : count all the categoriaProductos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/categoria-productos/count")
    public ResponseEntity<Long> countCategoriaProductos(CategoriaProductoCriteria criteria) {
        log.debug("REST request to count CategoriaProductos by criteria: {}", criteria);
        return ResponseEntity.ok().body(categoriaProductoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categoria-productos/:id} : get the "id" categoriaProducto.
     *
     * @param id the id of the categoriaProducto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaProducto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categoria-productos/{id}")
    public ResponseEntity<CategoriaProducto> getCategoriaProducto(@PathVariable Long id) {
        log.debug("REST request to get CategoriaProducto : {}", id);
        Optional<CategoriaProducto> categoriaProducto = categoriaProductoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaProducto);
    }

    /**
     * {@code DELETE  /categoria-productos/:id} : delete the "id" categoriaProducto.
     *
     * @param id the id of the categoriaProducto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categoria-productos/{id}")
    public ResponseEntity<Void> deleteCategoriaProducto(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaProducto : {}", id);
        categoriaProductoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
