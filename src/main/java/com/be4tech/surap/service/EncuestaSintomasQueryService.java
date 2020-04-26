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

import com.be4tech.surap.domain.EncuestaSintomas;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.EncuestaSintomasRepository;
import com.be4tech.surap.service.dto.EncuestaSintomasCriteria;

/**
 * Service for executing complex queries for {@link EncuestaSintomas} entities in the database.
 * The main input is a {@link EncuestaSintomasCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EncuestaSintomas} or a {@link Page} of {@link EncuestaSintomas} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EncuestaSintomasQueryService extends QueryService<EncuestaSintomas> {

    private final Logger log = LoggerFactory.getLogger(EncuestaSintomasQueryService.class);

    private final EncuestaSintomasRepository encuestaSintomasRepository;

    public EncuestaSintomasQueryService(EncuestaSintomasRepository encuestaSintomasRepository) {
        this.encuestaSintomasRepository = encuestaSintomasRepository;
    }

    /**
     * Return a {@link List} of {@link EncuestaSintomas} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EncuestaSintomas> findByCriteria(EncuestaSintomasCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EncuestaSintomas> specification = createSpecification(criteria);
        return encuestaSintomasRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EncuestaSintomas} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EncuestaSintomas> findByCriteria(EncuestaSintomasCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EncuestaSintomas> specification = createSpecification(criteria);
        return encuestaSintomasRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EncuestaSintomasCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EncuestaSintomas> specification = createSpecification(criteria);
        return encuestaSintomasRepository.count(specification);
    }

    /**
     * Function to convert {@link EncuestaSintomasCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EncuestaSintomas> createSpecification(EncuestaSintomasCriteria criteria) {
        Specification<EncuestaSintomas> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EncuestaSintomas_.id));
            }
            if (criteria.getFiebre() != null) {
                specification = specification.and(buildSpecification(criteria.getFiebre(), EncuestaSintomas_.fiebre));
            }
            if (criteria.getDolorGarganta() != null) {
                specification = specification.and(buildSpecification(criteria.getDolorGarganta(), EncuestaSintomas_.dolorGarganta));
            }
            if (criteria.getCongestionNasal() != null) {
                specification = specification.and(buildSpecification(criteria.getCongestionNasal(), EncuestaSintomas_.congestionNasal));
            }
            if (criteria.getTos() != null) {
                specification = specification.and(buildSpecification(criteria.getTos(), EncuestaSintomas_.tos));
            }
            if (criteria.getDificultadRespirar() != null) {
                specification = specification.and(buildSpecification(criteria.getDificultadRespirar(), EncuestaSintomas_.dificultadRespirar));
            }
            if (criteria.getFatiga() != null) {
                specification = specification.and(buildSpecification(criteria.getFatiga(), EncuestaSintomas_.fatiga));
            }
            if (criteria.getEscalofrio() != null) {
                specification = specification.and(buildSpecification(criteria.getEscalofrio(), EncuestaSintomas_.escalofrio));
            }
            if (criteria.getDolorMuscular() != null) {
                specification = specification.and(buildSpecification(criteria.getDolorMuscular(), EncuestaSintomas_.dolorMuscular));
            }
            if (criteria.getNinguno() != null) {
                specification = specification.and(buildSpecification(criteria.getNinguno(), EncuestaSintomas_.ninguno));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), EncuestaSintomas_.fecha));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(EncuestaSintomas_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
