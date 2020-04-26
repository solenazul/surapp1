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

import com.be4tech.surap.domain.CategoriaProducto;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.CategoriaProductoRepository;
import com.be4tech.surap.service.dto.CategoriaProductoCriteria;

/**
 * Service for executing complex queries for {@link CategoriaProducto} entities in the database.
 * The main input is a {@link CategoriaProductoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoriaProducto} or a {@link Page} of {@link CategoriaProducto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoriaProductoQueryService extends QueryService<CategoriaProducto> {

    private final Logger log = LoggerFactory.getLogger(CategoriaProductoQueryService.class);

    private final CategoriaProductoRepository categoriaProductoRepository;

    public CategoriaProductoQueryService(CategoriaProductoRepository categoriaProductoRepository) {
        this.categoriaProductoRepository = categoriaProductoRepository;
    }

    /**
     * Return a {@link List} of {@link CategoriaProducto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoriaProducto> findByCriteria(CategoriaProductoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategoriaProducto> specification = createSpecification(criteria);
        return categoriaProductoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CategoriaProducto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriaProducto> findByCriteria(CategoriaProductoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategoriaProducto> specification = createSpecification(criteria);
        return categoriaProductoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoriaProductoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategoriaProducto> specification = createSpecification(criteria);
        return categoriaProductoRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoriaProductoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CategoriaProducto> createSpecification(CategoriaProductoCriteria criteria) {
        Specification<CategoriaProducto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CategoriaProducto_.id));
            }
            if (criteria.getCategoria() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoria(), CategoriaProducto_.categoria));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), CategoriaProducto_.fecha));
            }
            if (criteria.getCatagoriaIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getCatagoriaIdId(),
                    root -> root.join(CategoriaProducto_.catagoriaId, JoinType.LEFT).get(Categorias_.id)));
            }
            if (criteria.getProductoIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductoIdId(),
                    root -> root.join(CategoriaProducto_.productoId, JoinType.LEFT).get(Productos_.id)));
            }
        }
        return specification;
    }
}
