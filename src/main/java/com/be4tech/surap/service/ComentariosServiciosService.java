package com.be4tech.surap.service;

import com.be4tech.surap.domain.ComentariosServicios;
import com.be4tech.surap.repository.ComentariosServiciosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ComentariosServicios}.
 */
@Service
@Transactional
public class ComentariosServiciosService {

    private final Logger log = LoggerFactory.getLogger(ComentariosServiciosService.class);

    private final ComentariosServiciosRepository comentariosServiciosRepository;

    public ComentariosServiciosService(ComentariosServiciosRepository comentariosServiciosRepository) {
        this.comentariosServiciosRepository = comentariosServiciosRepository;
    }

    /**
     * Save a comentariosServicios.
     *
     * @param comentariosServicios the entity to save.
     * @return the persisted entity.
     */
    public ComentariosServicios save(ComentariosServicios comentariosServicios) {
        log.debug("Request to save ComentariosServicios : {}", comentariosServicios);
        return comentariosServiciosRepository.save(comentariosServicios);
    }

    /**
     * Get all the comentariosServicios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComentariosServicios> findAll(Pageable pageable) {
        log.debug("Request to get all ComentariosServicios");
        return comentariosServiciosRepository.findAll(pageable);
    }

    /**
     * Get one comentariosServicios by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComentariosServicios> findOne(Long id) {
        log.debug("Request to get ComentariosServicios : {}", id);
        return comentariosServiciosRepository.findById(id);
    }

    /**
     * Delete the comentariosServicios by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ComentariosServicios : {}", id);
        comentariosServiciosRepository.deleteById(id);
    }
}
