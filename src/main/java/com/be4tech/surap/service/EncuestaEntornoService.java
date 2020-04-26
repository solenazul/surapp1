package com.be4tech.surap.service;

import com.be4tech.surap.domain.EncuestaEntorno;
import com.be4tech.surap.repository.EncuestaEntornoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EncuestaEntorno}.
 */
@Service
@Transactional
public class EncuestaEntornoService {

    private final Logger log = LoggerFactory.getLogger(EncuestaEntornoService.class);

    private final EncuestaEntornoRepository encuestaEntornoRepository;

    public EncuestaEntornoService(EncuestaEntornoRepository encuestaEntornoRepository) {
        this.encuestaEntornoRepository = encuestaEntornoRepository;
    }

    /**
     * Save a encuestaEntorno.
     *
     * @param encuestaEntorno the entity to save.
     * @return the persisted entity.
     */
    public EncuestaEntorno save(EncuestaEntorno encuestaEntorno) {
        log.debug("Request to save EncuestaEntorno : {}", encuestaEntorno);
        return encuestaEntornoRepository.save(encuestaEntorno);
    }

    /**
     * Get all the encuestaEntornos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EncuestaEntorno> findAll(Pageable pageable) {
        log.debug("Request to get all EncuestaEntornos");
        return encuestaEntornoRepository.findAll(pageable);
    }

    /**
     * Get one encuestaEntorno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EncuestaEntorno> findOne(Long id) {
        log.debug("Request to get EncuestaEntorno : {}", id);
        return encuestaEntornoRepository.findById(id);
    }

    /**
     * Delete the encuestaEntorno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EncuestaEntorno : {}", id);
        encuestaEntornoRepository.deleteById(id);
    }
}
