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

import com.be4tech.surap.domain.ComentariosProducto;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.ComentariosProductoRepository;
import com.be4tech.surap.service.dto.ComentariosProductoCriteria;

/**
 * Service for executing complex queries for {@link ComentariosProducto} entities in the database.
 * The main input is a {@link ComentariosProductoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComentariosProducto} or a {@link Page} of {@link ComentariosProducto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComentariosProductoQueryService extends QueryService<ComentariosProducto> {

    private final Logger log = LoggerFactory.getLogger(ComentariosProductoQueryService.class);

    private final ComentariosProductoRepository comentariosProductoRepository;

    public ComentariosProductoQueryService(ComentariosProductoRepository comentariosProductoRepository) {
        this.comentariosProductoRepository = comentariosProductoRepository;
    }

    /**
     * Return a {@link List} of {@link ComentariosProducto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComentariosProducto> findByCriteria(ComentariosProductoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ComentariosProducto> specification = createSpecification(criteria);
        return comentariosProductoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ComentariosProducto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComentariosProducto> findByCriteria(ComentariosProductoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ComentariosProducto> specification = createSpecification(criteria);
        return comentariosProductoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComentariosProductoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ComentariosProducto> specification = createSpecification(criteria);
        return comentariosProductoRepository.count(specification);
    }

    /**
     * Function to convert {@link ComentariosProductoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ComentariosProducto> createSpecification(ComentariosProductoCriteria criteria) {
        Specification<ComentariosProducto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ComentariosProducto_.id));
            }
            if (criteria.getComentario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComentario(), ComentariosProducto_.comentario));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), ComentariosProducto_.fecha));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstado(), ComentariosProducto_.estado));
            }
            if (criteria.getIdUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdUserId(),
                    root -> root.join(ComentariosProducto_.idUser, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getProductoIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductoIdId(),
                    root -> root.join(ComentariosProducto_.productoId, JoinType.LEFT).get(Productos_.id)));
            }
        }
        return specification;
    }
}
