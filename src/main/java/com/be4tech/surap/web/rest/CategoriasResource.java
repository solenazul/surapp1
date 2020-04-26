package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.Categorias;
import com.be4tech.surap.service.CategoriasService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.CategoriasCriteria;
import com.be4tech.surap.service.CategoriasQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.Categorias}.
 */
@RestController
@RequestMapping("/api")
public class CategoriasResource {

    private final Logger log = LoggerFactory.getLogger(CategoriasResource.class);

    private static final String ENTITY_NAME = "categorias";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriasService categoriasService;

    private final CategoriasQueryService categoriasQueryService;

    public CategoriasResource(CategoriasService categoriasService, CategoriasQueryService categoriasQueryService) {
        this.categoriasService = categoriasService;
        this.categoriasQueryService = categoriasQueryService;
    }

    /**
     * {@code POST  /categorias} : Create a new categorias.
     *
     * @param categorias the categorias to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorias, or with status {@code 400 (Bad Request)} if the categorias has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorias")
    public ResponseEntity<Categorias> createCategorias(@RequestBody Categorias categorias) throws URISyntaxException {
        log.debug("REST request to save Categorias : {}", categorias);
        if (categorias.getId() != null) {
            throw new BadRequestAlertException("A new categorias cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Categorias result = categoriasService.save(categorias);
        return ResponseEntity.created(new URI("/api/categorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorias} : Updates an existing categorias.
     *
     * @param categorias the categorias to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorias,
     * or with status {@code 400 (Bad Request)} if the categorias is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorias couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorias")
    public ResponseEntity<Categorias> updateCategorias(@RequestBody Categorias categorias) throws URISyntaxException {
        log.debug("REST request to update Categorias : {}", categorias);
        if (categorias.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Categorias result = categoriasService.save(categorias);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorias.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categorias} : get all the categorias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorias in body.
     */
    @GetMapping("/categorias")
    public ResponseEntity<List<Categorias>> getAllCategorias(CategoriasCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Categorias by criteria: {}", criteria);
        Page<Categorias> page = categoriasQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categorias/count} : count all the categorias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/categorias/count")
    public ResponseEntity<Long> countCategorias(CategoriasCriteria criteria) {
        log.debug("REST request to count Categorias by criteria: {}", criteria);
        return ResponseEntity.ok().body(categoriasQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categorias/:id} : get the "id" categorias.
     *
     * @param id the id of the categorias to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorias, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categorias> getCategorias(@PathVariable Long id) {
        log.debug("REST request to get Categorias : {}", id);
        Optional<Categorias> categorias = categoriasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorias);
    }

    /**
     * {@code DELETE  /categorias/:id} : delete the "id" categorias.
     *
     * @param id the id of the categorias to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> deleteCategorias(@PathVariable Long id) {
        log.debug("REST request to delete Categorias : {}", id);
        categoriasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
