package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.IPS;
import com.be4tech.surap.service.IPSService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.IPSCriteria;
import com.be4tech.surap.service.IPSQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.IPS}.
 */
@RestController
@RequestMapping("/api")
public class IPSResource {

    private final Logger log = LoggerFactory.getLogger(IPSResource.class);

    private static final String ENTITY_NAME = "iPS";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IPSService iPSService;

    private final IPSQueryService iPSQueryService;

    public IPSResource(IPSService iPSService, IPSQueryService iPSQueryService) {
        this.iPSService = iPSService;
        this.iPSQueryService = iPSQueryService;
    }

    /**
     * {@code POST  /ips} : Create a new iPS.
     *
     * @param iPS the iPS to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new iPS, or with status {@code 400 (Bad Request)} if the iPS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ips")
    public ResponseEntity<IPS> createIPS(@RequestBody IPS iPS) throws URISyntaxException {
        log.debug("REST request to save IPS : {}", iPS);
        if (iPS.getId() != null) {
            throw new BadRequestAlertException("A new iPS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IPS result = iPSService.save(iPS);
        return ResponseEntity.created(new URI("/api/ips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ips} : Updates an existing iPS.
     *
     * @param iPS the iPS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated iPS,
     * or with status {@code 400 (Bad Request)} if the iPS is not valid,
     * or with status {@code 500 (Internal Server Error)} if the iPS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ips")
    public ResponseEntity<IPS> updateIPS(@RequestBody IPS iPS) throws URISyntaxException {
        log.debug("REST request to update IPS : {}", iPS);
        if (iPS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IPS result = iPSService.save(iPS);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, iPS.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ips} : get all the iPS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of iPS in body.
     */
    @GetMapping("/ips")
    public ResponseEntity<List<IPS>> getAllIPS(IPSCriteria criteria, Pageable pageable) {
        log.debug("REST request to get IPS by criteria: {}", criteria);
        Page<IPS> page = iPSQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ips/count} : count all the iPS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ips/count")
    public ResponseEntity<Long> countIPS(IPSCriteria criteria) {
        log.debug("REST request to count IPS by criteria: {}", criteria);
        return ResponseEntity.ok().body(iPSQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ips/:id} : get the "id" iPS.
     *
     * @param id the id of the iPS to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the iPS, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ips/{id}")
    public ResponseEntity<IPS> getIPS(@PathVariable Long id) {
        log.debug("REST request to get IPS : {}", id);
        Optional<IPS> iPS = iPSService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iPS);
    }

    /**
     * {@code DELETE  /ips/:id} : delete the "id" iPS.
     *
     * @param id the id of the iPS to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ips/{id}")
    public ResponseEntity<Void> deleteIPS(@PathVariable Long id) {
        log.debug("REST request to delete IPS : {}", id);
        iPSService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
