package com.be4tech.surap.service;

import com.be4tech.surap.domain.ContenidoTablero;
import com.be4tech.surap.repository.ContenidoTableroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ContenidoTablero}.
 */
@Service
@Transactional
public class ContenidoTableroService {

    private final Logger log = LoggerFactory.getLogger(ContenidoTableroService.class);

    private final ContenidoTableroRepository contenidoTableroRepository;

    public ContenidoTableroService(ContenidoTableroRepository contenidoTableroRepository) {
        this.contenidoTableroRepository = contenidoTableroRepository;
    }

    /**
     * Save a contenidoTablero.
     *
     * @param contenidoTablero the entity to save.
     * @return the persisted entity.
     */
    public ContenidoTablero save(ContenidoTablero contenidoTablero) {
        log.debug("Request to save ContenidoTablero : {}", contenidoTablero);
        return contenidoTableroRepository.save(contenidoTablero);
    }

    /**
     * Get all the contenidoTableros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContenidoTablero> findAll(Pageable pageable) {
        log.debug("Request to get all ContenidoTableros");
        return contenidoTableroRepository.findAll(pageable);
    }

    /**
     * Get one contenidoTablero by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContenidoTablero> findOne(Long id) {
        log.debug("Request to get ContenidoTablero : {}", id);
        return contenidoTableroRepository.findById(id);
    }

    /**
     * Delete the contenidoTablero by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContenidoTablero : {}", id);
        contenidoTableroRepository.deleteById(id);
    }
}
