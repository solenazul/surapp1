package com.be4tech.surap.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.be4tech.surap.domain.ComentarioBlog;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.ComentarioBlogRepository;
import com.be4tech.surap.service.dto.ComentarioBlogCriteria;

/**
 * Service for executing complex queries for {@link ComentarioBlog} entities in the database.
 * The main input is a {@link ComentarioBlogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComentarioBlog} or a {@link Page} of {@link ComentarioBlog} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComentarioBlogQueryService extends QueryService<ComentarioBlog> {

    private final Logger log = LoggerFactory.getLogger(ComentarioBlogQueryService.class);

    private final ComentarioBlogRepository comentarioBlogRepository;

    public ComentarioBlogQueryService(ComentarioBlogRepository comentarioBlogRepository) {
        this.comentarioBlogRepository = comentarioBlogRepository;
    }

    /**
     * Return a {@link List} of {@link ComentarioBlog} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComentarioBlog> findByCriteria(ComentarioBlogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ComentarioBlog> specification = createSpecification(criteria);
        return comentarioBlogRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ComentarioBlog} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComentarioBlog> findByCriteria(ComentarioBlogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ComentarioBlog> specification = createSpecification(criteria);
        return comentarioBlogRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComentarioBlogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ComentarioBlog> specification = createSpecification(criteria);
        return comentarioBlogRepository.count(specification);
    }

    /**
     * Function to convert {@link ComentarioBlogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ComentarioBlog> createSpecification(ComentarioBlogCriteria criteria) {
        Specification<ComentarioBlog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ComentarioBlog_.id));
            }
            if (criteria.getComentario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComentario(), ComentarioBlog_.comentario));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), ComentarioBlog_.fecha));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstado(), ComentarioBlog_.estado));
            }
            if (criteria.getBlogIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getBlogIdId(),
                    root -> root.join(ComentarioBlog_.blogId, JoinType.LEFT).get(Blog_.id)));
            }
        }
        return specification;
    }
}
