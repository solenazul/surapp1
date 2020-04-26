package com.be4tech.surap.service;

import com.be4tech.surap.domain.Fisiometria1;
import com.be4tech.surap.repository.Fisiometria1Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Fisiometria1}.
 */
@Service
@Transactional
public class Fisiometria1Service {

    private final Logger log = LoggerFactory.getLogger(Fisiometria1Service.class);

    private final Fisiometria1Repository fisiometria1Repository;

    public Fisiometria1Service(Fisiometria1Repository fisiometria1Repository) {
        this.fisiometria1Repository = fisiometria1Repository;
    }

    /**
     * Save a fisiometria1.
     *
     * @param fisiometria1 the entity to save.
     * @return the persisted entity.
     */
    public Fisiometria1 save(Fisiometria1 fisiometria1) {
        log.debug("Request to save Fisiometria1 : {}", fisiometria1);
        return fisiometria1Repository.save(fisiometria1);
    }

    /**
     * Get all the fisiometria1S.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Fisiometria1> findAll(Pageable pageable) {
        log.debug("Request to get all Fisiometria1S");
        return fisiometria1Repository.findAll(pageable);
    }

    /**
     * Get one fisiometria1 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Fisiometria1> findOne(Long id) {
        log.debug("Request to get Fisiometria1 : {}", id);
        return fisiometria1Repository.findById(id);
    }

    /**
     * Delete the fisiometria1 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Fisiometria1 : {}", id);
        fisiometria1Repository.deleteById(id);
    }
}
