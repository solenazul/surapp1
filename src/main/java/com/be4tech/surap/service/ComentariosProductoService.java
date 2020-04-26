package com.be4tech.surap.service;

import com.be4tech.surap.domain.ComentariosProducto;
import com.be4tech.surap.repository.ComentariosProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ComentariosProducto}.
 */
@Service
@Transactional
public class ComentariosProductoService {

    private final Logger log = LoggerFactory.getLogger(ComentariosProductoService.class);

    private final ComentariosProductoRepository comentariosProductoRepository;

    public ComentariosProductoService(ComentariosProductoRepository comentariosProductoRepository) {
        this.comentariosProductoRepository = comentariosProductoRepository;
    }

    /**
     * Save a comentariosProducto.
     *
     * @param comentariosProducto the entity to save.
     * @return the persisted entity.
     */
    public ComentariosProducto save(ComentariosProducto comentariosProducto) {
        log.debug("Request to save ComentariosProducto : {}", comentariosProducto);
        return comentariosProductoRepository.save(comentariosProducto);
    }

    /**
     * Get all the comentariosProductos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComentariosProducto> findAll(Pageable pageable) {
        log.debug("Request to get all ComentariosProductos");
        return comentariosProductoRepository.findAll(pageable);
    }

    /**
     * Get one comentariosProducto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComentariosProducto> findOne(Long id) {
        log.debug("Request to get ComentariosProducto : {}", id);
        return comentariosProductoRepository.findById(id);
    }

    /**
     * Delete the comentariosProducto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ComentariosProducto : {}", id);
        comentariosProductoRepository.deleteById(id);
    }
}
