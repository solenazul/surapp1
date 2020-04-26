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

import com.be4tech.surap.domain.FavoritosProductos;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.FavoritosProductosRepository;
import com.be4tech.surap.service.dto.FavoritosProductosCriteria;

/**
 * Service for executing complex queries for {@link FavoritosProductos} entities in the database.
 * The main input is a {@link FavoritosProductosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FavoritosProductos} or a {@link Page} of {@link FavoritosProductos} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FavoritosProductosQueryService extends QueryService<FavoritosProductos> {

    private final Logger log = LoggerFactory.getLogger(FavoritosProductosQueryService.class);

    private final FavoritosProductosRepository favoritosProductosRepository;

    public FavoritosProductosQueryService(FavoritosProductosRepository favoritosProductosRepository) {
        this.favoritosProductosRepository = favoritosProductosRepository;
    }

    /**
     * Return a {@link List} of {@link FavoritosProductos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FavoritosProductos> findByCriteria(FavoritosProductosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FavoritosProductos> specification = createSpecification(criteria);
        return favoritosProductosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FavoritosProductos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FavoritosProductos> findByCriteria(FavoritosProductosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FavoritosProductos> specification = createSpecification(criteria);
        return favoritosProductosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FavoritosProductosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FavoritosProductos> specification = createSpecification(criteria);
        return favoritosProductosRepository.count(specification);
    }

    /**
     * Function to convert {@link FavoritosProductosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FavoritosProductos> createSpecification(FavoritosProductosCriteria criteria) {
        Specification<FavoritosProductos> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FavoritosProductos_.id));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), FavoritosProductos_.fecha));
            }
            if (criteria.getIdUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdUserId(),
                    root -> root.join(FavoritosProductos_.idUser, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getProductoIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductoIdId(),
                    root -> root.join(FavoritosProductos_.productoId, JoinType.LEFT).get(Productos_.id)));
            }
        }
        return specification;
    }
}
