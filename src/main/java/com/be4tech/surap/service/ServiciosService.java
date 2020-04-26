package com.be4tech.surap.service;

import com.be4tech.surap.domain.Servicios;
import com.be4tech.surap.repository.ServiciosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Servicios}.
 */
@Service
@Transactional
public class ServiciosService {

    private final Logger log = LoggerFactory.getLogger(ServiciosService.class);

    private final ServiciosRepository serviciosRepository;

    public ServiciosService(ServiciosRepository serviciosRepository) {
        this.serviciosRepository = serviciosRepository;
    }

    /**
     * Save a servicios.
     *
     * @param servicios the entity to save.
     * @return the persisted entity.
     */
    public Servicios save(Servicios servicios) {
        log.debug("Request to save Servicios : {}", servicios);
        return serviciosRepository.save(servicios);
    }

    /**
     * Get all the servicios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Servicios> findAll(Pageable pageable) {
        log.debug("Request to get all Servicios");
        return serviciosRepository.findAll(pageable);
    }

    /**
     * Get one servicios by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Servicios> findOne(Long id) {
        log.debug("Request to get Servicios : {}", id);
        return serviciosRepository.findById(id);
    }

    /**
     * Delete the servicios by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Servicios : {}", id);
        serviciosRepository.deleteById(id);
    }
}
