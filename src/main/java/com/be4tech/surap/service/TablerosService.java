package com.be4tech.surap.service;

import com.be4tech.surap.domain.Tableros;
import com.be4tech.surap.repository.TablerosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Tableros}.
 */
@Service
@Transactional
public class TablerosService {

    private final Logger log = LoggerFactory.getLogger(TablerosService.class);

    private final TablerosRepository tablerosRepository;

    public TablerosService(TablerosRepository tablerosRepository) {
        this.tablerosRepository = tablerosRepository;
    }

    /**
     * Save a tableros.
     *
     * @param tableros the entity to save.
     * @return the persisted entity.
     */
    public Tableros save(Tableros tableros) {
        log.debug("Request to save Tableros : {}", tableros);
        return tablerosRepository.save(tableros);
    }

    /**
     * Get all the tableros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Tableros> findAll(Pageable pageable) {
        log.debug("Request to get all Tableros");
        return tablerosRepository.findAll(pageable);
    }

    /**
     * Get one tableros by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Tableros> findOne(Long id) {
        log.debug("Request to get Tableros : {}", id);
        return tablerosRepository.findById(id);
    }

    /**
     * Delete the tableros by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tableros : {}", id);
        tablerosRepository.deleteById(id);
    }
}
