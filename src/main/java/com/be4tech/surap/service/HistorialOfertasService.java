package com.be4tech.surap.service;

import com.be4tech.surap.domain.HistorialOfertas;
import com.be4tech.surap.repository.HistorialOfertasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link HistorialOfertas}.
 */
@Service
@Transactional
public class HistorialOfertasService {

    private final Logger log = LoggerFactory.getLogger(HistorialOfertasService.class);

    private final HistorialOfertasRepository historialOfertasRepository;

    public HistorialOfertasService(HistorialOfertasRepository historialOfertasRepository) {
        this.historialOfertasRepository = historialOfertasRepository;
    }

    /**
     * Save a historialOfertas.
     *
     * @param historialOfertas the entity to save.
     * @return the persisted entity.
     */
    public HistorialOfertas save(HistorialOfertas historialOfertas) {
        log.debug("Request to save HistorialOfertas : {}", historialOfertas);
        return historialOfertasRepository.save(historialOfertas);
    }

    /**
     * Get all the historialOfertas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HistorialOfertas> findAll(Pageable pageable) {
        log.debug("Request to get all HistorialOfertas");
        return historialOfertasRepository.findAll(pageable);
    }

    /**
     * Get one historialOfertas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HistorialOfertas> findOne(Long id) {
        log.debug("Request to get HistorialOfertas : {}", id);
        return historialOfertasRepository.findById(id);
    }

    /**
     * Delete the historialOfertas by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HistorialOfertas : {}", id);
        historialOfertasRepository.deleteById(id);
    }
}
