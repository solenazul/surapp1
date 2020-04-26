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

import com.be4tech.surap.domain.CategoriaTablero;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.CategoriaTableroRepository;
import com.be4tech.surap.service.dto.CategoriaTableroCriteria;

/**
 * Service for executing complex queries for {@link CategoriaTablero} entities in the database.
 * The main input is a {@link CategoriaTableroCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoriaTablero} or a {@link Page} of {@link CategoriaTablero} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoriaTableroQueryService extends QueryService<CategoriaTablero> {

    private final Logger log = LoggerFactory.getLogger(CategoriaTableroQueryService.class);

    private final CategoriaTableroRepository categoriaTableroRepository;

    public CategoriaTableroQueryService(CategoriaTableroRepository categoriaTableroRepository) {
        this.categoriaTableroRepository = categoriaTableroRepository;
    }

    /**
     * Return a {@link List} of {@link CategoriaTablero} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoriaTablero> findByCriteria(CategoriaTableroCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategoriaTablero> specification = createSpecification(criteria);
        return categoriaTableroRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CategoriaTablero} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaTablero> findByCriteria(CategoriaTableroCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategoriaTablero> specification = createSpecification(criteria);
        return categoriaTableroRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoriaTableroCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategoriaTablero> specification = createSpecification(criteria);
        return categoriaTableroRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoriaTableroCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CategoriaTablero> createSpecification(CategoriaTableroCriteria criteria) {
        Specification<CategoriaTablero> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CategoriaTablero_.id));
            }
            if (criteria.getCategoria() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoria(), CategoriaTablero_.categoria));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), CategoriaTablero_.fecha));
            }
            if (criteria.getProductoIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductoIdId(),
                    root -> root.join(CategoriaTablero_.productoId, JoinType.LEFT).get(Productos_.id)));
            }
        }
        return specification;
    }
}
