package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.FavoritosProductos;
import com.be4tech.surap.service.FavoritosProductosService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.FavoritosProductosCriteria;
import com.be4tech.surap.service.FavoritosProductosQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.FavoritosProductos}.
 */
@RestController
@RequestMapping("/api")
public class FavoritosProductosResource {

    private final Logger log = LoggerFactory.getLogger(FavoritosProductosResource.class);

    private static final String ENTITY_NAME = "favoritosProductos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FavoritosProductosService favoritosProductosService;

    private final FavoritosProductosQueryService favoritosProductosQueryService;

    public FavoritosProductosResource(FavoritosProductosService favoritosProductosService, FavoritosProductosQueryService favoritosProductosQueryService) {
        this.favoritosProductosService = favoritosProductosService;
        this.favoritosProductosQueryService = favoritosProductosQueryService;
    }

    /**
     * {@code POST  /favoritos-productos} : Create a new favoritosProductos.
     *
     * @param favoritosProductos the favoritosProductos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new favoritosProductos, or with status {@code 400 (Bad Request)} if the favoritosProductos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/favoritos-productos")
    public ResponseEntity<FavoritosProductos> createFavoritosProductos(@RequestBody FavoritosProductos favoritosProductos) throws URISyntaxException {
        log.debug("REST request to save FavoritosProductos : {}", favoritosProductos);
        if (favoritosProductos.getId() != null) {
            throw new BadRequestAlertException("A new favoritosProductos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FavoritosProductos result = favoritosProductosService.save(favoritosProductos);
        return ResponseEntity.created(new URI("/api/favoritos-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /favoritos-productos} : Updates an existing favoritosProductos.
     *
     * @param favoritosProductos the favoritosProductos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated favoritosProductos,
     * or with status {@code 400 (Bad Request)} if the favoritosProductos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the favoritosProductos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/favoritos-productos")
    public ResponseEntity<FavoritosProductos> updateFavoritosProductos(@RequestBody FavoritosProductos favoritosProductos) throws URISyntaxException {
        log.debug("REST request to update FavoritosProductos : {}", favoritosProductos);
        if (favoritosProductos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FavoritosProductos result = favoritosProductosService.save(favoritosProductos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, favoritosProductos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /favoritos-productos} : get all the favoritosProductos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of favoritosProductos in body.
     */
    @GetMapping("/favoritos-productos")
    public ResponseEntity<List<FavoritosProductos>> getAllFavoritosProductos(FavoritosProductosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FavoritosProductos by criteria: {}", criteria);
        Page<FavoritosProductos> page = favoritosProductosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /favoritos-productos/count} : count all the favoritosProductos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/favoritos-productos/count")
    public ResponseEntity<Long> countFavoritosProductos(FavoritosProductosCriteria criteria) {
        log.debug("REST request to count FavoritosProductos by criteria: {}", criteria);
        return ResponseEntity.ok().body(favoritosProductosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /favoritos-productos/:id} : get the "id" favoritosProductos.
     *
     * @param id the id of the favoritosProductos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the favoritosProductos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/favoritos-productos/{id}")
    public ResponseEntity<FavoritosProductos> getFavoritosProductos(@PathVariable Long id) {
        log.debug("REST request to get FavoritosProductos : {}", id);
        Optional<FavoritosProductos> favoritosProductos = favoritosProductosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(favoritosProductos);
    }

    /**
     * {@code DELETE  /favoritos-productos/:id} : delete the "id" favoritosProductos.
     *
     * @param id the id of the favoritosProductos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/favoritos-productos/{id}")
    public ResponseEntity<Void> deleteFavoritosProductos(@PathVariable Long id) {
        log.debug("REST request to delete FavoritosProductos : {}", id);
        favoritosProductosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
