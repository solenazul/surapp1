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

import com.be4tech.surap.domain.Modelo3D;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.Modelo3DRepository;
import com.be4tech.surap.service.dto.Modelo3DCriteria;

/**
 * Service for executing complex queries for {@link Modelo3D} entities in the database.
 * The main input is a {@link Modelo3DCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Modelo3D} or a {@link Page} of {@link Modelo3D} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class Modelo3DQueryService extends QueryService<Modelo3D> {

    private final Logger log = LoggerFactory.getLogger(Modelo3DQueryService.class);

    private final Modelo3DRepository modelo3DRepository;

    public Modelo3DQueryService(Modelo3DRepository modelo3DRepository) {
        this.modelo3DRepository = modelo3DRepository;
    }

    /**
     * Return a {@link List} of {@link Modelo3D} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Modelo3D> findByCriteria(Modelo3DCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Modelo3D> specification = createSpecification(criteria);
        return modelo3DRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Modelo3D} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Modelo3D> findByCriteria(Modelo3DCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Modelo3D> specification = createSpecification(criteria);
        return modelo3DRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(Modelo3DCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Modelo3D> specification = createSpecification(criteria);
        return modelo3DRepository.count(specification);
    }

    /**
     * Function to convert {@link Modelo3DCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Modelo3D> createSpecification(Modelo3DCriteria criteria) {
        Specification<Modelo3D> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Modelo3D_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Modelo3D_.nombre));
            }
            if (criteria.getUrt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrt(), Modelo3D_.urt));
            }
            if (criteria.getColider() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColider(), Modelo3D_.colider));
            }
            if (criteria.getTextureA() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextureA(), Modelo3D_.textureA));
            }
            if (criteria.getTextureBC() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextureBC(), Modelo3D_.textureBC));
            }
            if (criteria.getTextureN() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextureN(), Modelo3D_.textureN));
            }
            if (criteria.getTextureR() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextureR(), Modelo3D_.textureR));
            }
            if (criteria.getShadow() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShadow(), Modelo3D_.shadow));
            }
            if (criteria.getAcutalizado() != null) {
                specification = specification.and(buildSpecification(criteria.getAcutalizado(), Modelo3D_.acutalizado));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), Modelo3D_.fecha));
            }
            if (criteria.getIdUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdUserId(),
                    root -> root.join(Modelo3D_.idUser, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
