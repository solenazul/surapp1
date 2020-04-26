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

import com.be4tech.surap.domain.Pais;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.PaisRepository;
import com.be4tech.surap.service.dto.PaisCriteria;

/**
 * Service for executing complex queries for {@link Pais} entities in the database.
 * The main input is a {@link PaisCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Pais} or a {@link Page} of {@link Pais} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaisQueryService extends QueryService<Pais> {

    private final Logger log = LoggerFactory.getLogger(PaisQueryService.class);

    private final PaisRepository paisRepository;

    public PaisQueryService(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    /**
     * Return a {@link List} of {@link Pais} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Pais> findByCriteria(PaisCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Pais> specification = createSpecification(criteria);
        return paisRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Pais} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Pais> findByCriteria(PaisCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pais> specification = createSpecification(criteria);
        return paisRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaisCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Pais> specification = createSpecification(criteria);
        return paisRepository.count(specification);
    }

    /**
     * Function to convert {@link PaisCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Pais> createSpecification(PaisCriteria criteria) {
        Specification<Pais> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Pais_.id));
            }
            if (criteria.getNombrePais() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombrePais(), Pais_.nombrePais));
            }
        }
        return specification;
    }
}
