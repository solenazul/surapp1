package com.be4tech.surap.service;

import com.be4tech.surap.domain.Categorias;
import com.be4tech.surap.repository.CategoriasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Categorias}.
 */
@Service
@Transactional
public class CategoriasService {

    private final Logger log = LoggerFactory.getLogger(CategoriasService.class);

    private final CategoriasRepository categoriasRepository;

    public CategoriasService(CategoriasRepository categoriasRepository) {
        this.categoriasRepository = categoriasRepository;
    }

    /**
     * Save a categorias.
     *
     * @param categorias the entity to save.
     * @return the persisted entity.
     */
    public Categorias save(Categorias categorias) {
        log.debug("Request to save Categorias : {}", categorias);
        return categoriasRepository.save(categorias);
    }

    /**
     * Get all the categorias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Categorias> findAll(Pageable pageable) {
        log.debug("Request to get all Categorias");
        return categoriasRepository.findAll(pageable);
    }

    /**
     * Get one categorias by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Categorias> findOne(Long id) {
        log.debug("Request to get Categorias : {}", id);
        return categoriasRepository.findById(id);
    }

    /**
     * Delete the categorias by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Categorias : {}", id);
        categoriasRepository.deleteById(id);
    }
}
