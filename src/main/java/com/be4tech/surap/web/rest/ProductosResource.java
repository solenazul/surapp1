package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.Productos;
import com.be4tech.surap.service.ProductosService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.ProductosCriteria;
import com.be4tech.surap.service.ProductosQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.Productos}.
 */
@RestController
@RequestMapping("/api")
public class ProductosResource {

    private final Logger log = LoggerFactory.getLogger(ProductosResource.class);

    private static final String ENTITY_NAME = "productos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductosService productosService;

    private final ProductosQueryService productosQueryService;

    public ProductosResource(ProductosService productosService, ProductosQueryService productosQueryService) {
        this.productosService = productosService;
        this.productosQueryService = productosQueryService;
    }

    /**
     * {@code POST  /productos} : Create a new productos.
     *
     * @param productos the productos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productos, or with status {@code 400 (Bad Request)} if the productos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/productos")
    public ResponseEntity<Productos> createProductos(@RequestBody Productos productos) throws URISyntaxException {
        log.debug("REST request to save Productos : {}", productos);
        if (productos.getId() != null) {
            throw new BadRequestAlertException("A new productos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Productos result = productosService.save(productos);
        return ResponseEntity.created(new URI("/api/productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /productos} : Updates an existing productos.
     *
     * @param productos the productos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productos,
     * or with status {@code 400 (Bad Request)} if the productos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/productos")
    public ResponseEntity<Productos> updateProductos(@RequestBody Productos productos) throws URISyntaxException {
        log.debug("REST request to update Productos : {}", productos);
        if (productos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Productos result = productosService.save(productos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /productos} : get all the productos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productos in body.
     */
    @GetMapping("/productos")
    public ResponseEntity<List<Productos>> getAllProductos(ProductosCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Productos by criteria: {}", criteria);
        Page<Productos> page = productosQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /productos/count} : count all the productos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/productos/count")
    public ResponseEntity<Long> countProductos(ProductosCriteria criteria) {
        log.debug("REST request to count Productos by criteria: {}", criteria);
        return ResponseEntity.ok().body(productosQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /productos/:id} : get the "id" productos.
     *
     * @param id the id of the productos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/productos/{id}")
    public ResponseEntity<Productos> getProductos(@PathVariable Long id) {
        log.debug("REST request to get Productos : {}", id);
        Optional<Productos> productos = productosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productos);
    }

    /**
     * {@code DELETE  /productos/:id} : delete the "id" productos.
     *
     * @param id the id of the productos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> deleteProductos(@PathVariable Long id) {
        log.debug("REST request to delete Productos : {}", id);
        productosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
