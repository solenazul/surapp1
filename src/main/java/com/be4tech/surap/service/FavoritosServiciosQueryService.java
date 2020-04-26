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

import com.be4tech.surap.domain.FavoritosServicios;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.FavoritosServiciosRepository;
import com.be4tech.surap.service.dto.FavoritosServiciosCriteria;

/**
 * Service for executing complex queries for {@link FavoritosServicios} entities in the database.
 * The main input is a {@link FavoritosServiciosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FavoritosServicios} or a {@link Page} of {@link FavoritosServicios} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FavoritosServiciosQueryService extends QueryService<FavoritosServicios> {

    private final Logger log = LoggerFactory.getLogger(FavoritosServiciosQueryService.class);

    private final FavoritosServiciosRepository favoritosServiciosRepository;

    public FavoritosServiciosQueryService(FavoritosServiciosRepository favoritosServiciosRepository) {
        this.favoritosServiciosRepository = favoritosServiciosRepository;
    }

    /**
     * Return a {@link List} of {@link FavoritosServicios} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FavoritosServicios> findByCriteria(FavoritosServiciosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FavoritosServicios> specification = createSpecification(criteria);
        return favoritosServiciosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FavoritosServicios} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FavoritosServicios> findByCriteria(FavoritosServiciosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FavoritosServicios> specification = createSpecification(criteria);
        return favoritosServiciosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FavoritosServiciosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FavoritosServicios> specification = createSpecification(criteria);
        return favoritosServiciosRepository.count(specification);
    }

    /**
     * Function to convert {@link FavoritosServiciosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FavoritosServicios> createSpecification(FavoritosServiciosCriteria criteria) {
        Specification<FavoritosServicios> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FavoritosServicios_.id));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), FavoritosServicios_.fecha));
            }
            if (criteria.getIdUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdUserId(),
                    root -> root.join(FavoritosServicios_.idUser, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getProductoIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductoIdId(),
                    root -> root.join(FavoritosServicios_.productoId, JoinType.LEFT).get(Servicios_.id)));
            }
        }
        return specification;
    }
}
