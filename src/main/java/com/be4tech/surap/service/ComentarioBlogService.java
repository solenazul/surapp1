package com.be4tech.surap.service;

import com.be4tech.surap.domain.ComentarioBlog;
import com.be4tech.surap.repository.ComentarioBlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ComentarioBlog}.
 */
@Service
@Transactional
public class ComentarioBlogService {

    private final Logger log = LoggerFactory.getLogger(ComentarioBlogService.class);

    private final ComentarioBlogRepository comentarioBlogRepository;

    public ComentarioBlogService(ComentarioBlogRepository comentarioBlogRepository) {
        this.comentarioBlogRepository = comentarioBlogRepository;
    }

    /**
     * Save a comentarioBlog.
     *
     * @param comentarioBlog the entity to save.
     * @return the persisted entity.
     */
    public ComentarioBlog save(ComentarioBlog comentarioBlog) {
        log.debug("Request to save ComentarioBlog : {}", comentarioBlog);
        return comentarioBlogRepository.save(comentarioBlog);
    }

    /**
     * Get all the comentarioBlogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComentarioBlog> findAll(Pageable pageable) {
        log.debug("Request to get all ComentarioBlogs");
        return comentarioBlogRepository.findAll(pageable);
    }

    /**
     * Get one comentarioBlog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComentarioBlog> findOne(Long id) {
        log.debug("Request to get ComentarioBlog : {}", id);
        return comentarioBlogRepository.findById(id);
    }

    /**
     * Delete the comentarioBlog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ComentarioBlog : {}", id);
        comentarioBlogRepository.deleteById(id);
    }
}
