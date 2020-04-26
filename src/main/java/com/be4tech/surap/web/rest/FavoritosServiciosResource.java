package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.FavoritosServicios;
import com.be4tech.surap.service.FavoritosServiciosService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.FavoritosServiciosCriteria;
import com.be4tech.surap.service.FavoritosServiciosQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.FavoritosServicios}.
 */
@RestController
@RequestMapping("/api")
public class FavoritosServiciosResource {

    private final Logger log = LoggerFactory.getLogger(FavoritosServiciosResource.class);

    private static final String ENTITY_NAME = "favoritosServicios";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FavoritosServiciosService favoritosServiciosService;

    private final FavoritosServiciosQueryService favoritosServiciosQueryService;

    public FavoritosServiciosResource(FavoritosServiciosService favoritosServiciosService, FavoritosServiciosQueryService favoritosServiciosQueryService) {
        this.favoritosServiciosService = favoritosServiciosService;
        this.favoritosServiciosQueryService = favoritosServiciosQueryService;
    }

    /**
     * {@code POST  /favoritos-servicios} : Create a new favoritosServicios.
     *
     * @param favoritosServicios the favoritosServicios to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new favoritosServicios, or with status {@code 400 (Bad Request)} if the favoritosServicios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/favoritos-servicios")
    public ResponseEntity<FavoritosServicios> createFavoritosServicios(@RequestBody FavoritosServicios favoritosServicios) throws URISyntaxException {
        log.debug("REST request to save FavoritosServicios : {}", favoritosServicios);
        if (favoritosServicios.getId() != null) {
            throw new BadRequestAlertException("A new favoritosServicios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FavoritosServicios result = favoritosServiciosService.save(favoritosServicios);
        return ResponseEntity.created(new URI("/api/favoritos-servicios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /favoritos-servicios} : Updates an existing favoritosServicios.
     *
     * @param favoritosServicios the favoritosServicios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated favoritosServicios,
     * or with status {@code 400 (Bad Request)} if the favoritosServicios is not valid,
     * or with status {@code 500 (Internal Server Error)} if the favoritosServicios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/favoritos-servicios")
    public ResponseEntity<FavoritosServicios> updateFavoritosServicios(@RequestBody FavoritosServicios favoritosServicios) throws URISyntaxException {
        log.debug("REST request to update FavoritosServicios : {}", favoritosServicios);
        if (favoritosServicios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FavoritosServicios result = favoritosServiciosService.save(favoritosServicios);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, favoritosServicios.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /favoritos-servicios} : get all the favoritosServicios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of favoritosServicios in body.
     */
    @GetMapping("/favoritos-servicios")
    public ResponseEntity<List<FavoritosServicios>> getAllFavoritosServicios(FavoritosServiciosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FavoritosServicios by criteria: {}", criteria);
        Page<FavoritosServicios> page = favoritosServiciosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /favoritos-servicios/count} : count all the favoritosServicios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/favoritos-servicios/count")
    public ResponseEntity<Long> countFavoritosServicios(FavoritosServiciosCriteria criteria) {
        log.debug("REST request to count FavoritosServicios by criteria: {}", criteria);
        return ResponseEntity.ok().body(favoritosServiciosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /favoritos-servicios/:id} : get the "id" favoritosServicios.
     *
     * @param id the id of the favoritosServicios to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the favoritosServicios, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/favoritos-servicios/{id}")
    public ResponseEntity<FavoritosServicios> getFavoritosServicios(@PathVariable Long id) {
        log.debug("REST request to get FavoritosServicios : {}", id);
        Optional<FavoritosServicios> favoritosServicios = favoritosServiciosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(favoritosServicios);
    }

    /**
     * {@code DELETE  /favoritos-servicios/:id} : delete the "id" favoritosServicios.
     *
     * @param id the id of the favoritosServicios to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/favoritos-servicios/{id}")
    public ResponseEntity<Void> deleteFavoritosServicios(@PathVariable Long id) {
        log.debug("REST request to delete FavoritosServicios : {}", id);
        favoritosServiciosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
