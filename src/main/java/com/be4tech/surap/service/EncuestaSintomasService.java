package com.be4tech.surap.service;

import com.be4tech.surap.domain.EncuestaSintomas;
import com.be4tech.surap.repository.EncuestaSintomasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EncuestaSintomas}.
 */
@Service
@Transactional
public class EncuestaSintomasService {

    private final Logger log = LoggerFactory.getLogger(EncuestaSintomasService.class);

    private final EncuestaSintomasRepository encuestaSintomasRepository;

    public EncuestaSintomasService(EncuestaSintomasRepository encuestaSintomasRepository) {
        this.encuestaSintomasRepository = encuestaSintomasRepository;
    }

    /**
     * Save a encuestaSintomas.
     *
     * @param encuestaSintomas the entity to save.
     * @return the persisted entity.
     */
    public EncuestaSintomas save(EncuestaSintomas encuestaSintomas) {
        log.debug("Request to save EncuestaSintomas : {}", encuestaSintomas);
        return encuestaSintomasRepository.save(encuestaSintomas);
    }

    /**
     * Get all the encuestaSintomas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EncuestaSintomas> findAll(Pageable pageable) {
        log.debug("Request to get all EncuestaSintomas");
        return encuestaSintomasRepository.findAll(pageable);
    }

    /**
     * Get one encuestaSintomas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EncuestaSintomas> findOne(Long id) {
        log.debug("Request to get EncuestaSintomas : {}", id);
        return encuestaSintomasRepository.findById(id);
    }

    /**
     * Delete the encuestaSintomas by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EncuestaSintomas : {}", id);
        encuestaSintomasRepository.deleteById(id);
    }
}
