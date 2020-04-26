package com.be4tech.surap.service;

import com.be4tech.surap.domain.Productos;
import com.be4tech.surap.repository.ProductosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Productos}.
 */
@Service
@Transactional
public class ProductosService {

    private final Logger log = LoggerFactory.getLogger(ProductosService.class);

    private final ProductosRepository productosRepository;

    public ProductosService(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    /**
     * Save a productos.
     *
     * @param productos the entity to save.
     * @return the persisted entity.
     */
    public Productos save(Productos productos) {
        log.debug("Request to save Productos : {}", productos);
        return productosRepository.save(productos);
    }

    /**
     * Get all the productos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Productos> findAll(Pageable pageable) {
        log.debug("Request to get all Productos");
        return productosRepository.findAll(pageable);
    }

    /**
     * Get one productos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Productos> findOne(Long id) {
        log.debug("Request to get Productos : {}", id);
        return productosRepository.findById(id);
    }

    /**
     * Delete the productos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Productos : {}", id);
        productosRepository.deleteById(id);
    }
}
