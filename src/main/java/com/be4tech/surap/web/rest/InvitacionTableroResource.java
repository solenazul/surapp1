package com.be4tech.surap.web.rest;

import com.be4tech.surap.domain.InvitacionTablero;
import com.be4tech.surap.service.InvitacionTableroService;
import com.be4tech.surap.web.rest.errors.BadRequestAlertException;
import com.be4tech.surap.service.dto.InvitacionTableroCriteria;
import com.be4tech.surap.service.InvitacionTableroQueryService;

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
 * REST controller for managing {@link com.be4tech.surap.domain.InvitacionTablero}.
 */
@RestController
@RequestMapping("/api")
public class InvitacionTableroResource {

    private final Logger log = LoggerFactory.getLogger(InvitacionTableroResource.class);

    private static final String ENTITY_NAME = "invitacionTablero";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvitacionTableroService invitacionTableroService;

    private final InvitacionTableroQueryService invitacionTableroQueryService;

    public InvitacionTableroResource(InvitacionTableroService invitacionTableroService, InvitacionTableroQueryService invitacionTableroQueryService) {
        this.invitacionTableroService = invitacionTableroService;
        this.invitacionTableroQueryService = invitacionTableroQueryService;
    }

    /**
     * {@code POST  /invitacion-tableros} : Create a new invitacionTablero.
     *
     * @param invitacionTablero the invitacionTablero to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invitacionTablero, or with status {@code 400 (Bad Request)} if the invitacionTablero has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invitacion-tableros")
    public ResponseEntity<InvitacionTablero> createInvitacionTablero(@RequestBody InvitacionTablero invitacionTablero) throws URISyntaxException {
        log.debug("REST request to save InvitacionTablero : {}", invitacionTablero);
        if (invitacionTablero.getId() != null) {
            throw new BadRequestAlertException("A new invitacionTablero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvitacionTablero result = invitacionTableroService.save(invitacionTablero);
        return ResponseEntity.created(new URI("/api/invitacion-tableros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invitacion-tableros} : Updates an existing invitacionTablero.
     *
     * @param invitacionTablero the invitacionTablero to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invitacionTablero,
     * or with status {@code 400 (Bad Request)} if the invitacionTablero is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invitacionTablero couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invitacion-tableros")
    public ResponseEntity<InvitacionTablero> updateInvitacionTablero(@RequestBody InvitacionTablero invitacionTablero) throws URISyntaxException {
        log.debug("REST request to update InvitacionTablero : {}", invitacionTablero);
        if (invitacionTablero.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvitacionTablero result = invitacionTableroService.save(invitacionTablero);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invitacionTablero.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invitacion-tableros} : get all the invitacionTableros.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invitacionTableros in body.
     */
    @GetMapping("/invitacion-tableros")
    public ResponseEntity<List<InvitacionTablero>> getAllInvitacionTableros(InvitacionTableroCriteria criteria, Pageable pageable) {
        log.debug("REST request to get InvitacionTableros by criteria: {}", criteria);
        Page<InvitacionTablero> page = invitacionTableroQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invitacion-tableros/count} : count all the invitacionTableros.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/invitacion-tableros/count")
    public ResponseEntity<Long> countInvitacionTableros(InvitacionTableroCriteria criteria) {
        log.debug("REST request to count InvitacionTableros by criteria: {}", criteria);
        return ResponseEntity.ok().body(invitacionTableroQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /invitacion-tableros/:id} : get the "id" invitacionTablero.
     *
     * @param id the id of the invitacionTablero to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invitacionTablero, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invitacion-tableros/{id}")
    public ResponseEntity<InvitacionTablero> getInvitacionTablero(@PathVariable Long id) {
        log.debug("REST request to get InvitacionTablero : {}", id);
        Optional<InvitacionTablero> invitacionTablero = invitacionTableroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invitacionTablero);
    }

    /**
     * {@code DELETE  /invitacion-tableros/:id} : delete the "id" invitacionTablero.
     *
     * @param id the id of the invitacionTablero to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invitacion-tableros/{id}")
    public ResponseEntity<Void> deleteInvitacionTablero(@PathVariable Long id) {
        log.debug("REST request to delete InvitacionTablero : {}", id);
        invitacionTableroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
