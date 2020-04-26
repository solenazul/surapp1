package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.ComentarioBlog;
import com.be4tech.surap.service.ComentarioBlogService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.ComentarioBlogCriteria;
import com.be4tech.surap.service.ComentarioBlogQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.ComentarioBlog}.
 */
@RestController
@RequestMapping("/api")
public class ComentarioBlogResource {

    private final Logger log = LoggerFactory.getLogger(ComentarioBlogResource.class);

    private static final String ENTITY_NAME = "comentarioBlog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComentarioBlogService comentarioBlogService;

    private final ComentarioBlogQueryService comentarioBlogQueryService;

    public ComentarioBlogResource(ComentarioBlogService comentarioBlogService, ComentarioBlogQueryService comentarioBlogQueryService) {
        this.comentarioBlogService = comentarioBlogService;
        this.comentarioBlogQueryService = comentarioBlogQueryService;
    }

    /**
     * {@code POST  /comentario-blogs} : Create a new comentarioBlog.
     *
     * @param comentarioBlog the comentarioBlog to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comentarioBlog, or with status {@code 400 (Bad Request)} if the comentarioBlog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comentario-blogs")
    public ResponseEntity<ComentarioBlog> createComentarioBlog(@RequestBody ComentarioBlog comentarioBlog) throws URISyntaxException {
        log.debug("REST request to save ComentarioBlog : {}", comentarioBlog);
        if (comentarioBlog.getId() != null) {
            throw new BadRequestAlertException("A new comentarioBlog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComentarioBlog result = comentarioBlogService.save(comentarioBlog);
        return ResponseEntity.created(new URI("/api/comentario-blogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comentario-blogs} : Updates an existing comentarioBlog.
     *
     * @param comentarioBlog the comentarioBlog to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comentarioBlog,
     * or with status {@code 400 (Bad Request)} if the comentarioBlog is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comentarioBlog couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comentario-blogs")
    public ResponseEntity<ComentarioBlog> updateComentarioBlog(@RequestBody ComentarioBlog comentarioBlog) throws URISyntaxException {
        log.debug("REST request to update ComentarioBlog : {}", comentarioBlog);
        if (comentarioBlog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ComentarioBlog result = comentarioBlogService.save(comentarioBlog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comentarioBlog.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comentario-blogs} : get all the comentarioBlogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comentarioBlogs in body.
     */
    @GetMapping("/comentario-blogs")
    public ResponseEntity<List<ComentarioBlog>> getAllComentarioBlogs(ComentarioBlogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ComentarioBlogs by criteria: {}", criteria);
        Page<ComentarioBlog> page = comentarioBlogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comentario-blogs/count} : count all the comentarioBlogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/comentario-blogs/count")
    public ResponseEntity<Long> countComentarioBlogs(ComentarioBlogCriteria criteria) {
        log.debug("REST request to count ComentarioBlogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(comentarioBlogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /comentario-blogs/:id} : get the "id" comentarioBlog.
     *
     * @param id the id of the comentarioBlog to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comentarioBlog, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comentario-blogs/{id}")
    public ResponseEntity<ComentarioBlog> getComentarioBlog(@PathVariable Long id) {
        log.debug("REST request to get ComentarioBlog : {}", id);
        Optional<ComentarioBlog> comentarioBlog = comentarioBlogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comentarioBlog);
    }

    /**
     * {@code DELETE  /comentario-blogs/:id} : delete the "id" comentarioBlog.
     *
     * @param id the id of the comentarioBlog to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comentario-blogs/{id}")
    public ResponseEntity<Void> deleteComentarioBlog(@PathVariable Long id) {
        log.debug("REST request to delete ComentarioBlog : {}", id);
        comentarioBlogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
