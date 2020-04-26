package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.Alarma;
import com.be4tech.surap.service.AlarmaService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.AlarmaCriteria;
import com.be4tech.surap.service.AlarmaQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.Alarma}.
 */
@RestController
@RequestMapping("/api")
public class AlarmaResource {

    private final Logger log = LoggerFactory.getLogger(AlarmaResource.class);

    private static final String ENTITY_NAME = "alarma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlarmaService alarmaService;

    private final AlarmaQueryService alarmaQueryService;

    public AlarmaResource(AlarmaService alarmaService, AlarmaQueryService alarmaQueryService) {
        this.alarmaService = alarmaService;
        this.alarmaQueryService = alarmaQueryService;
    }

    /**
     * {@code POST  /alarmas} : Create a new alarma.
     *
     * @param alarma the alarma to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alarma, or with status {@code 400 (Bad Request)} if the alarma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alarmas")
    public ResponseEntity<Alarma> createAlarma(@RequestBody Alarma alarma) throws URISyntaxException {
        log.debug("REST request to save Alarma : {}", alarma);
        if (alarma.getId() != null) {
            throw new BadRequestAlertException("A new alarma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Alarma result = alarmaService.save(alarma);
        return ResponseEntity.created(new URI("/api/alarmas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alarmas} : Updates an existing alarma.
     *
     * @param alarma the alarma to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alarma,
     * or with status {@code 400 (Bad Request)} if the alarma is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alarma couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alarmas")
    public ResponseEntity<Alarma> updateAlarma(@RequestBody Alarma alarma) throws URISyntaxException {
        log.debug("REST request to update Alarma : {}", alarma);
        if (alarma.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Alarma result = alarmaService.save(alarma);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alarma.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alarmas} : get all the alarmas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alarmas in body.
     */
    @GetMapping("/alarmas")
    public ResponseEntity<List<Alarma>> getAllAlarmas(AlarmaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Alarmas by criteria: {}", criteria);
        Page<Alarma> page = alarmaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alarmas/count} : count all the alarmas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/alarmas/count")
    public ResponseEntity<Long> countAlarmas(AlarmaCriteria criteria) {
        log.debug("REST request to count Alarmas by criteria: {}", criteria);
        return ResponseEntity.ok().body(alarmaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /alarmas/:id} : get the "id" alarma.
     *
     * @param id the id of the alarma to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alarma, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alarmas/{id}")
    public ResponseEntity<Alarma> getAlarma(@PathVariable Long id) {
        log.debug("REST request to get Alarma : {}", id);
        Optional<Alarma> alarma = alarmaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alarma);
    }

    /**
     * {@code DELETE  /alarmas/:id} : delete the "id" alarma.
     *
     * @param id the id of the alarma to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alarmas/{id}")
    public ResponseEntity<Void> deleteAlarma(@PathVariable Long id) {
        log.debug("REST request to delete Alarma : {}", id);
        alarmaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
