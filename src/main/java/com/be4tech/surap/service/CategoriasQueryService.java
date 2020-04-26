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

import com.be4tech.surap.domain.Categorias;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.CategoriasRepository;
import com.be4tech.surap.service.dto.CategoriasCriteria;

/**
 * Service for executing complex queries for {@link Categorias} entities in the database.
 * The main input is a {@link CategoriasCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Categorias} or a {@link Page} of {@link Categorias} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoriasQueryService extends QueryService<Categorias> {

    private final Logger log = LoggerFactory.getLogger(CategoriasQueryService.class);

    private final CategoriasRepository categoriasRepository;

    public CategoriasQueryService(CategoriasRepository categoriasRepository) {
        this.categoriasRepository = categoriasRepository;
    }

    /**
     * Return a {@link List} of {@link Categorias} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Categorias> findByCriteria(CategoriasCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Categorias> specification = createSpecification(criteria);
        return categoriasRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Categorias} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Categorias> findByCriteria(CategoriasCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Categorias> specification = createSpecification(criteria);
        return categoriasRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoriasCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Categorias> specification = createSpecification(criteria);
        return categoriasRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoriasCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Categorias> createSpecification(CategoriasCriteria criteria) {
        Specification<Categorias> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Categorias_.id));
            }
            if (criteria.getCategoria() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoria(), Categorias_.categoria));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), Categorias_.fecha));
            }
        }
        return specification;
    }
}
