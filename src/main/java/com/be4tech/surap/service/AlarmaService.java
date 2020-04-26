package com.be4tech.surap.service;

import com.be4tech.surap.domain.Alarma;
import com.be4tech.surap.repository.AlarmaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Alarma}.
 */
@Service
@Transactional
public class AlarmaService {

    private final Logger log = LoggerFactory.getLogger(AlarmaService.class);

    private final AlarmaRepository alarmaRepository;

    public AlarmaService(AlarmaRepository alarmaRepository) {
        this.alarmaRepository = alarmaRepository;
    }

    /**
     * Save a alarma.
     *
     * @param alarma the entity to save.
     * @return the persisted entity.
     */
    public Alarma save(Alarma alarma) {
        log.debug("Request to save Alarma : {}", alarma);
        return alarmaRepository.save(alarma);
    }

    /**
     * Get all the alarmas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Alarma> findAll(Pageable pageable) {
        log.debug("Request to get all Alarmas");
        return alarmaRepository.findAll(pageable);
    }

    /**
     * Get one alarma by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Alarma> findOne(Long id) {
        log.debug("Request to get Alarma : {}", id);
        return alarmaRepository.findById(id);
    }

    /**
     * Delete the alarma by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Alarma : {}", id);
        alarmaRepository.deleteById(id);
    }
}
