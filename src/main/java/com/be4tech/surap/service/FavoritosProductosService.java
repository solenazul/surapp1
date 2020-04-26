package com.be4tech.surap.service;

import com.be4tech.surap.domain.FavoritosProductos;
import com.be4tech.surap.repository.FavoritosProductosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FavoritosProductos}.
 */
@Service
@Transactional
public class FavoritosProductosService {

    private final Logger log = LoggerFactory.getLogger(FavoritosProductosService.class);

    private final FavoritosProductosRepository favoritosProductosRepository;

    public FavoritosProductosService(FavoritosProductosRepository favoritosProductosRepository) {
        this.favoritosProductosRepository = favoritosProductosRepository;
    }

    /**
     * Save a favoritosProductos.
     *
     * @param favoritosProductos the entity to save.
     * @return the persisted entity.
     */
    public FavoritosProductos save(FavoritosProductos favoritosProductos) {
        log.debug("Request to save FavoritosProductos : {}", favoritosProductos);
        return favoritosProductosRepository.save(favoritosProductos);
    }

    /**
     * Get all the favoritosProductos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FavoritosProductos> findAll(Pageable pageable) {
        log.debug("Request to get all FavoritosProductos");
        return favoritosProductosRepository.findAll(pageable);
    }

    /**
     * Get one favoritosProductos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FavoritosProductos> findOne(Long id) {
        log.debug("Request to get FavoritosProductos : {}", id);
        return favoritosProductosRepository.findById(id);
    }

    /**
     * Delete the favoritosProductos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FavoritosProductos : {}", id);
        favoritosProductosRepository.deleteById(id);
    }
}
