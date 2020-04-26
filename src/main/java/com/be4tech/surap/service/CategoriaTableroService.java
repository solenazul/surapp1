package com.be4tech.surap.service;

import com.be4tech.surap.domain.CategoriaTablero;
import com.be4tech.surap.repository.CategoriaTableroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoriaTablero}.
 */
@Service
@Transactional
public class CategoriaTableroService {

    private final Logger log = LoggerFactory.getLogger(CategoriaTableroService.class);

    private final CategoriaTableroRepository categoriaTableroRepository;

    public CategoriaTableroService(CategoriaTableroRepository categoriaTableroRepository) {
        this.categoriaTableroRepository = categoriaTableroRepository;
    }

    /**
     * Save a categoriaTablero.
     *
     * @param categoriaTablero the entity to save.
     * @return the persisted entity.
     */
    public CategoriaTablero save(CategoriaTablero categoriaTablero) {
        log.debug("Request to save CategoriaTablero : {}", categoriaTablero);
        return categoriaTableroRepository.save(categoriaTablero);
    }

    /**
     * Get all the categoriaTableros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaTablero> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriaTableros");
        return categoriaTableroRepository.findAll(pageable);
    }

    /**
     * Get one categoriaTablero by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoriaTablero> findOne(Long id) {
        log.debug("Request to get CategoriaTablero : {}", id);
        return categoriaTableroRepository.findById(id);
    }

    /**
     * Delete the categoriaTablero by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CategoriaTablero : {}", id);
        categoriaTableroRepository.deleteById(id);
    }
}
