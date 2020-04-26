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

import com.be4tech.surap.domain.HistorialOfertas;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.HistorialOfertasRepository;
import com.be4tech.surap.service.dto.HistorialOfertasCriteria;

/**
 * Service for executing complex queries for {@link HistorialOfertas} entities in the database.
 * The main input is a {@link HistorialOfertasCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HistorialOfertas} or a {@link Page} of {@link HistorialOfertas} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HistorialOfertasQueryService extends QueryService<HistorialOfertas> {

    private final Logger log = LoggerFactory.getLogger(HistorialOfertasQueryService.class);

    private final HistorialOfertasRepository historialOfertasRepository;

    public HistorialOfertasQueryService(HistorialOfertasRepository historialOfertasRepository) {
        this.historialOfertasRepository = historialOfertasRepository;
    }

    /**
     * Return a {@link List} of {@link HistorialOfertas} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HistorialOfertas> findByCriteria(HistorialOfertasCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HistorialOfertas> specification = createSpecification(criteria);
        return historialOfertasRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link HistorialOfertas} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HistorialOfertas> findByCriteria(HistorialOfertasCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HistorialOfertas> specification = createSpecification(criteria);
        return historialOfertasRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HistorialOfertasCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HistorialOfertas> specification = createSpecification(criteria);
        return historialOfertasRepository.count(specification);
    }

    /**
     * Function to convert {@link HistorialOfertasCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<HistorialOfertas> createSpecification(HistorialOfertasCriteria criteria) {
        Specification<HistorialOfertas> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), HistorialOfertas_.id));
            }
            if (criteria.getValorProducto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorProducto(), HistorialOfertas_.valorProducto));
            }
            if (criteria.getValorOferta() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorOferta(), HistorialOfertas_.valorOferta));
            }
            if (criteria.getFechaInicial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaInicial(), HistorialOfertas_.fechaInicial));
            }
            if (criteria.getFechaFinal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaFinal(), HistorialOfertas_.fechaFinal));
            }
            if (criteria.getProductoIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductoIdId(),
                    root -> root.join(HistorialOfertas_.productoId, JoinType.LEFT).get(Productos_.id)));
            }
        }
        return specification;
    }
}
