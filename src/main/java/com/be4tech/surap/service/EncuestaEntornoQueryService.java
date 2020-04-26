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

import com.be4tech.surap.domain.EncuestaEntorno;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.EncuestaEntornoRepository;
import com.be4tech.surap.service.dto.EncuestaEntornoCriteria;

/**
 * Service for executing complex queries for {@link EncuestaEntorno} entities in the database.
 * The main input is a {@link EncuestaEntornoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EncuestaEntorno} or a {@link Page} of {@link EncuestaEntorno} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EncuestaEntornoQueryService extends QueryService<EncuestaEntorno> {

    private final Logger log = LoggerFactory.getLogger(EncuestaEntornoQueryService.class);

    private final EncuestaEntornoRepository encuestaEntornoRepository;

    public EncuestaEntornoQueryService(EncuestaEntornoRepository encuestaEntornoRepository) {
        this.encuestaEntornoRepository = encuestaEntornoRepository;
    }

    /**
     * Return a {@link List} of {@link EncuestaEntorno} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EncuestaEntorno> findByCriteria(EncuestaEntornoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EncuestaEntorno> specification = createSpecification(criteria);
        return encuestaEntornoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EncuestaEntorno} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EncuestaEntorno> findByCriteria(EncuestaEntornoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EncuestaEntorno> specification = createSpecification(criteria);
        return encuestaEntornoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EncuestaEntornoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EncuestaEntorno> specification = createSpecification(criteria);
        return encuestaEntornoRepository.count(specification);
    }

    /**
     * Function to convert {@link EncuestaEntornoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EncuestaEntorno> createSpecification(EncuestaEntornoCriteria criteria) {
        Specification<EncuestaEntorno> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EncuestaEntorno_.id));
            }
            if (criteria.getContactoSintomas() != null) {
                specification = specification.and(buildSpecification(criteria.getContactoSintomas(), EncuestaEntorno_.contactoSintomas));
            }
            if (criteria.getViajeInternacional() != null) {
                specification = specification.and(buildSpecification(criteria.getViajeInternacional(), EncuestaEntorno_.viajeInternacional));
            }
            if (criteria.getViajeNacional() != null) {
                specification = specification.and(buildSpecification(criteria.getViajeNacional(), EncuestaEntorno_.viajeNacional));
            }
            if (criteria.getTrabajadorSalud() != null) {
                specification = specification.and(buildSpecification(criteria.getTrabajadorSalud(), EncuestaEntorno_.trabajadorSalud));
            }
            if (criteria.getNinguna() != null) {
                specification = specification.and(buildSpecification(criteria.getNinguna(), EncuestaEntorno_.ninguna));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), EncuestaEntorno_.fecha));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(EncuestaEntorno_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
