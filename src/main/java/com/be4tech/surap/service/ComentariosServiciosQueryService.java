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

import com.be4tech.surap.domain.ComentariosServicios;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.ComentariosServiciosRepository;
import com.be4tech.surap.service.dto.ComentariosServiciosCriteria;

/**
 * Service for executing complex queries for {@link ComentariosServicios} entities in the database.
 * The main input is a {@link ComentariosServiciosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComentariosServicios} or a {@link Page} of {@link ComentariosServicios} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComentariosServiciosQueryService extends QueryService<ComentariosServicios> {

    private final Logger log = LoggerFactory.getLogger(ComentariosServiciosQueryService.class);

    private final ComentariosServiciosRepository comentariosServiciosRepository;

    public ComentariosServiciosQueryService(ComentariosServiciosRepository comentariosServiciosRepository) {
        this.comentariosServiciosRepository = comentariosServiciosRepository;
    }

    /**
     * Return a {@link List} of {@link ComentariosServicios} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComentariosServicios> findByCriteria(ComentariosServiciosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ComentariosServicios> specification = createSpecification(criteria);
        return comentariosServiciosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ComentariosServicios} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComentariosServicios> findByCriteria(ComentariosServiciosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ComentariosServicios> specification = createSpecification(criteria);
        return comentariosServiciosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComentariosServiciosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ComentariosServicios> specification = createSpecification(criteria);
        return comentariosServiciosRepository.count(specification);
    }

    /**
     * Function to convert {@link ComentariosServiciosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ComentariosServicios> createSpecification(ComentariosServiciosCriteria criteria) {
        Specification<ComentariosServicios> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ComentariosServicios_.id));
            }
            if (criteria.getComentario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComentario(), ComentariosServicios_.comentario));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), ComentariosServicios_.fecha));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstado(), ComentariosServicios_.estado));
            }
            if (criteria.getIdUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdUserId(),
                    root -> root.join(ComentariosServicios_.idUser, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getProductoIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductoIdId(),
                    root -> root.join(ComentariosServicios_.productoId, JoinType.LEFT).get(Productos_.id)));
            }
        }
        return specification;
    }
}
