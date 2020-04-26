package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.CategoriaTablero;
import com.be4tech.surap.service.CategoriaTableroService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.CategoriaTableroCriteria;
import com.be4tech.surap.service.CategoriaTableroQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.CategoriaTablero}.
 */
@RestController
@RequestMapping("/api")
public class CategoriaTableroResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaTableroResource.class);

    private static final String ENTITY_NAME = "categoriaTablero";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaTableroService categoriaTableroService;

    private final CategoriaTableroQueryService categoriaTableroQueryService;

    public CategoriaTableroResource(CategoriaTableroService categoriaTableroService, CategoriaTableroQueryService categoriaTableroQueryService) {
        this.categoriaTableroService = categoriaTableroService;
        this.categoriaTableroQueryService = categoriaTableroQueryService;
    }

    /**
     * {@code POST  /categoria-tableros} : Create a new categoriaTablero.
     *
     * @param categoriaTablero the categoriaTablero to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriaTablero, or with status {@code 400 (Bad Request)} if the categoriaTablero has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categoria-tableros")
    public ResponseEntity<CategoriaTablero> createCategoriaTablero(@RequestBody CategoriaTablero categoriaTablero) throws URISyntaxException {
        log.debug("REST request to save CategoriaTablero : {}", categoriaTablero);
        if (categoriaTablero.getId() != null) {
            throw new BadRequestAlertException("A new categoriaTablero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaTablero result = categoriaTableroService.save(categoriaTablero);
        return ResponseEntity.created(new URI("/api/categoria-tableros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categoria-tableros} : Updates an existing categoriaTablero.
     *
     * @param categoriaTablero the categoriaTablero to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaTablero,
     * or with status {@code 400 (Bad Request)} if the categoriaTablero is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriaTablero couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categoria-tableros")
    public ResponseEntity<CategoriaTablero> updateCategoriaTablero(@RequestBody CategoriaTablero categoriaTablero) throws URISyntaxException {
        log.debug("REST request to update CategoriaTablero : {}", categoriaTablero);
        if (categoriaTablero.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoriaTablero result = categoriaTableroService.save(categoriaTablero);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaTablero.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categoria-tableros} : get all the categoriaTableros.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriaTableros in body.
     */
    @GetMapping("/categoria-tableros")
    public ResponseEntity<List<CategoriaTablero>> getAllCategoriaTableros(CategoriaTableroCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CategoriaTableros by criteria: {}", criteria);
        Page<CategoriaTablero> page = categoriaTableroQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categoria-tableros/count} : count all the categoriaTableros.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/categoria-tableros/count")
    public ResponseEntity<Long> countCategoriaTableros(CategoriaTableroCriteria criteria) {
        log.debug("REST request to count CategoriaTableros by criteria: {}", criteria);
        return ResponseEntity.ok().body(categoriaTableroQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /categoria-tableros/:id} : get the "id" categoriaTablero.
     *
     * @param id the id of the categoriaTablero to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaTablero, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categoria-tableros/{id}")
    public ResponseEntity<CategoriaTablero> getCategoriaTablero(@PathVariable Long id) {
        log.debug("REST request to get CategoriaTablero : {}", id);
        Optional<CategoriaTablero> categoriaTablero = categoriaTableroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaTablero);
    }

    /**
     * {@code DELETE  /categoria-tableros/:id} : delete the "id" categoriaTablero.
     *
     * @param id the id of the categoriaTablero to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categoria-tableros/{id}")
    public ResponseEntity<Void> deleteCategoriaTablero(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaTablero : {}", id);
        categoriaTableroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
