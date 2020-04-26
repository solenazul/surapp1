package com.be4tech.surap.service;

import com.be4tech.surap.domain.CategoriaProducto;
import com.be4tech.surap.repository.CategoriaProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoriaProducto}.
 */
@Service
@Transactional
public class CategoriaProductoService {

    private final Logger log = LoggerFactory.getLogger(CategoriaProductoService.class);

    private final CategoriaProductoRepository categoriaProductoRepository;

    public CategoriaProductoService(CategoriaProductoRepository categoriaProductoRepository) {
        this.categoriaProductoRepository = categoriaProductoRepository;
    }

    /**
     * Save a categoriaProducto.
     *
     * @param categoriaProducto the entity to save.
     * @return the persisted entity.
     */
    public CategoriaProducto save(CategoriaProducto categoriaProducto) {
        log.debug("Request to save CategoriaProducto : {}", categoriaProducto);
        return categoriaProductoRepository.save(categoriaProducto);
    }

    /**
     * Get all the categoriaProductos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaProducto> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriaProductos");
        return categoriaProductoRepository.findAll(pageable);
    }

    /**
     * Get one categoriaProducto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoriaProducto> findOne(Long id) {
        log.debug("Request to get CategoriaProducto : {}", id);
        return categoriaProductoRepository.findById(id);
    }

    /**
     * Delete the categoriaProducto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CategoriaProducto : {}", id);
        categoriaProductoRepository.deleteById(id);
    }
}
