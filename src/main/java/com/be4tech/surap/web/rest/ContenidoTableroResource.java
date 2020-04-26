package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.ContenidoTablero;
import com.be4tech.surap.service.ContenidoTableroService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.ContenidoTableroCriteria;
import com.be4tech.surap.service.ContenidoTableroQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.ContenidoTablero}.
 */
@RestController
@RequestMapping("/api")
public class ContenidoTableroResource {

    private final Logger log = LoggerFactory.getLogger(ContenidoTableroResource.class);

    private static final String ENTITY_NAME = "contenidoTablero";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContenidoTableroService contenidoTableroService;

    private final ContenidoTableroQueryService contenidoTableroQueryService;

    public ContenidoTableroResource(ContenidoTableroService contenidoTableroService, ContenidoTableroQueryService contenidoTableroQueryService) {
        this.contenidoTableroService = contenidoTableroService;
        this.contenidoTableroQueryService = contenidoTableroQueryService;
    }

    /**
     * {@code POST  /contenido-tableros} : Create a new contenidoTablero.
     *
     * @param contenidoTablero the contenidoTablero to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contenidoTablero, or with status {@code 400 (Bad Request)} if the contenidoTablero has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contenido-tableros")
    public ResponseEntity<ContenidoTablero> createContenidoTablero(@RequestBody ContenidoTablero contenidoTablero) throws URISyntaxException {
        log.debug("REST request to save ContenidoTablero : {}", contenidoTablero);
        if (contenidoTablero.getId() != null) {
            throw new BadRequestAlertException("A new contenidoTablero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContenidoTablero result = contenidoTableroService.save(contenidoTablero);
        return ResponseEntity.created(new URI("/api/contenido-tableros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contenido-tableros} : Updates an existing contenidoTablero.
     *
     * @param contenidoTablero the contenidoTablero to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contenidoTablero,
     * or with status {@code 400 (Bad Request)} if the contenidoTablero is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contenidoTablero couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contenido-tableros")
    public ResponseEntity<ContenidoTablero> updateContenidoTablero(@RequestBody ContenidoTablero contenidoTablero) throws URISyntaxException {
        log.debug("REST request to update ContenidoTablero : {}", contenidoTablero);
        if (contenidoTablero.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContenidoTablero result = contenidoTableroService.save(contenidoTablero);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contenidoTablero.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contenido-tableros} : get all the contenidoTableros.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contenidoTableros in body.
     */
    @GetMapping("/contenido-tableros")
    public ResponseEntity<List<ContenidoTablero>> getAllContenidoTableros(ContenidoTableroCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ContenidoTableros by criteria: {}", criteria);
        Page<ContenidoTablero> page = contenidoTableroQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contenido-tableros/count} : count all the contenidoTableros.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/contenido-tableros/count")
    public ResponseEntity<Long> countContenidoTableros(ContenidoTableroCriteria criteria) {
        log.debug("REST request to count ContenidoTableros by criteria: {}", criteria);
        return ResponseEntity.ok().body(contenidoTableroQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contenido-tableros/:id} : get the "id" contenidoTablero.
     *
     * @param id the id of the contenidoTablero to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contenidoTablero, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contenido-tableros/{id}")
    public ResponseEntity<ContenidoTablero> getContenidoTablero(@PathVariable Long id) {
        log.debug("REST request to get ContenidoTablero : {}", id);
        Optional<ContenidoTablero> contenidoTablero = contenidoTableroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contenidoTablero);
    }

    /**
     * {@code DELETE  /contenido-tableros/:id} : delete the "id" contenidoTablero.
     *
     * @param id the id of the contenidoTablero to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contenido-tableros/{id}")
    public ResponseEntity<Void> deleteContenidoTablero(@PathVariable Long id) {
        log.debug("REST request to delete ContenidoTablero : {}", id);
        contenidoTableroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
