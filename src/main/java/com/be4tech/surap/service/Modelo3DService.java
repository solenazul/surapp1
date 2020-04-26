package com.be4tech.surap.service;

import com.be4tech.surap.domain.Modelo3D;
import com.be4tech.surap.repository.Modelo3DRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Modelo3D}.
 */
@Service
@Transactional
public class Modelo3DService {

    private final Logger log = LoggerFactory.getLogger(Modelo3DService.class);

    private final Modelo3DRepository modelo3DRepository;

    public Modelo3DService(Modelo3DRepository modelo3DRepository) {
        this.modelo3DRepository = modelo3DRepository;
    }

    /**
     * Save a modelo3D.
     *
     * @param modelo3D the entity to save.
     * @return the persisted entity.
     */
    public Modelo3D save(Modelo3D modelo3D) {
        log.debug("Request to save Modelo3D : {}", modelo3D);
        return modelo3DRepository.save(modelo3D);
    }

    /**
     * Get all the modelo3DS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Modelo3D> findAll(Pageable pageable) {
        log.debug("Request to get all Modelo3DS");
        return modelo3DRepository.findAll(pageable);
    }

    /**
     * Get one modelo3D by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Modelo3D> findOne(Long id) {
        log.debug("Request to get Modelo3D : {}", id);
        return modelo3DRepository.findById(id);
    }

    /**
     * Delete the modelo3D by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Modelo3D : {}", id);
        modelo3DRepository.deleteById(id);
    }
}
