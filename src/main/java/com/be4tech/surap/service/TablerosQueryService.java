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

import com.be4tech.surap.domain.Tableros;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.TablerosRepository;
import com.be4tech.surap.service.dto.TablerosCriteria;

/**
 * Service for executing complex queries for {@link Tableros} entities in the database.
 * The main input is a {@link TablerosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Tableros} or a {@link Page} of {@link Tableros} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TablerosQueryService extends QueryService<Tableros> {

    private final Logger log = LoggerFactory.getLogger(TablerosQueryService.class);

    private final TablerosRepository tablerosRepository;

    public TablerosQueryService(TablerosRepository tablerosRepository) {
        this.tablerosRepository = tablerosRepository;
    }

    /**
     * Return a {@link List} of {@link Tableros} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Tableros> findByCriteria(TablerosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tableros> specification = createSpecification(criteria);
        return tablerosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Tableros} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Tableros> findByCriteria(TablerosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tableros> specification = createSpecification(criteria);
        return tablerosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TablerosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tableros> specification = createSpecification(criteria);
        return tablerosRepository.count(specification);
    }

    /**
     * Function to convert {@link TablerosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tableros> createSpecification(TablerosCriteria criteria) {
        Specification<Tableros> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tableros_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Tableros_.nombre));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), Tableros_.fecha));
            }
            if (criteria.getPrivado() != null) {
                specification = specification.and(buildSpecification(criteria.getPrivado(), Tableros_.privado));
            }
            if (criteria.getCalificacion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCalificacion(), Tableros_.calificacion));
            }
            if (criteria.getIdUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdUserId(),
                    root -> root.join(Tableros_.idUser, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getCategoriaIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoriaIdId(),
                    root -> root.join(Tableros_.categoriaId, JoinType.LEFT).get(CategoriaTablero_.id)));
            }
        }
        return specification;
    }
}
