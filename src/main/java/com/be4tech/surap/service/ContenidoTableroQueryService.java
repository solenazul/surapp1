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

import com.be4tech.surap.domain.ContenidoTablero;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.ContenidoTableroRepository;
import com.be4tech.surap.service.dto.ContenidoTableroCriteria;

/**
 * Service for executing complex queries for {@link ContenidoTablero} entities in the database.
 * The main input is a {@link ContenidoTableroCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContenidoTablero} or a {@link Page} of {@link ContenidoTablero} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContenidoTableroQueryService extends QueryService<ContenidoTablero> {

    private final Logger log = LoggerFactory.getLogger(ContenidoTableroQueryService.class);

    private final ContenidoTableroRepository contenidoTableroRepository;

    public ContenidoTableroQueryService(ContenidoTableroRepository contenidoTableroRepository) {
        this.contenidoTableroRepository = contenidoTableroRepository;
    }

    /**
     * Return a {@link List} of {@link ContenidoTablero} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContenidoTablero> findByCriteria(ContenidoTableroCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContenidoTablero> specification = createSpecification(criteria);
        return contenidoTableroRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ContenidoTablero} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContenidoTablero> findByCriteria(ContenidoTableroCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContenidoTablero> specification = createSpecification(criteria);
        return contenidoTableroRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContenidoTableroCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ContenidoTablero> specification = createSpecification(criteria);
        return contenidoTableroRepository.count(specification);
    }

    /**
     * Function to convert {@link ContenidoTableroCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ContenidoTablero> createSpecification(ContenidoTableroCriteria criteria) {
        Specification<ContenidoTablero> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ContenidoTablero_.id));
            }
            if (criteria.getComentario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComentario(), ContenidoTablero_.comentario));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), ContenidoTablero_.fecha));
            }
            if (criteria.getIdUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdUserId(),
                    root -> root.join(ContenidoTablero_.idUser, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getTableroIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getTableroIdId(),
                    root -> root.join(ContenidoTablero_.tableroId, JoinType.LEFT).get(Tableros_.id)));
            }
            if (criteria.getProductoIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductoIdId(),
                    root -> root.join(ContenidoTablero_.productoId, JoinType.LEFT).get(Productos_.id)));
            }
        }
        return specification;
    }
}
