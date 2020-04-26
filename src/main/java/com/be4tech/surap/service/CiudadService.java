package com.be4tech.surap.service;

import com.be4tech.surap.domain.Ciudad;
import com.be4tech.surap.repository.CiudadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Ciudad}.
 */
@Service
@Transactional
public class CiudadService {

    private final Logger log = LoggerFactory.getLogger(CiudadService.class);

    private final CiudadRepository ciudadRepository;

    public CiudadService(CiudadRepository ciudadRepository) {
        this.ciudadRepository = ciudadRepository;
    }

    /**
     * Save a ciudad.
     *
     * @param ciudad the entity to save.
     * @return the persisted entity.
     */
    public Ciudad save(Ciudad ciudad) {
        log.debug("Request to save Ciudad : {}", ciudad);
        return ciudadRepository.save(ciudad);
    }

    /**
     * Get all the ciudads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Ciudad> findAll(Pageable pageable) {
        log.debug("Request to get all Ciudads");
        return ciudadRepository.findAll(pageable);
    }

    /**
     * Get one ciudad by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Ciudad> findOne(Long id) {
        log.debug("Request to get Ciudad : {}", id);
        return ciudadRepository.findById(id);
    }

    /**
     * Delete the ciudad by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ciudad : {}", id);
        ciudadRepository.deleteById(id);
    }
}
