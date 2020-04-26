package com.be4tech.surap.service;

import com.be4tech.surap.domain.FavoritosServicios;
import com.be4tech.surap.repository.FavoritosServiciosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FavoritosServicios}.
 */
@Service
@Transactional
public class FavoritosServiciosService {

    private final Logger log = LoggerFactory.getLogger(FavoritosServiciosService.class);

    private final FavoritosServiciosRepository favoritosServiciosRepository;

    public FavoritosServiciosService(FavoritosServiciosRepository favoritosServiciosRepository) {
        this.favoritosServiciosRepository = favoritosServiciosRepository;
    }

    /**
     * Save a favoritosServicios.
     *
     * @param favoritosServicios the entity to save.
     * @return the persisted entity.
     */
    public FavoritosServicios save(FavoritosServicios favoritosServicios) {
        log.debug("Request to save FavoritosServicios : {}", favoritosServicios);
        return favoritosServiciosRepository.save(favoritosServicios);
    }

    /**
     * Get all the favoritosServicios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FavoritosServicios> findAll(Pageable pageable) {
        log.debug("Request to get all FavoritosServicios");
        return favoritosServiciosRepository.findAll(pageable);
    }

    /**
     * Get one favoritosServicios by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FavoritosServicios> findOne(Long id) {
        log.debug("Request to get FavoritosServicios : {}", id);
        return favoritosServiciosRepository.findById(id);
    }

    /**
     * Delete the favoritosServicios by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FavoritosServicios : {}", id);
        favoritosServiciosRepository.deleteById(id);
    }
}
