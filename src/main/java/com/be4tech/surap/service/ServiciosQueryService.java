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

import com.be4tech.surap.domain.Servicios;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.ServiciosRepository;
import com.be4tech.surap.service.dto.ServiciosCriteria;

/**
 * Service for executing complex queries for {@link Servicios} entities in the database.
 * The main input is a {@link ServiciosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Servicios} or a {@link Page} of {@link Servicios} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiciosQueryService extends QueryService<Servicios> {

    private final Logger log = LoggerFactory.getLogger(ServiciosQueryService.class);

    private final ServiciosRepository serviciosRepository;

    public ServiciosQueryService(ServiciosRepository serviciosRepository) {
        this.serviciosRepository = serviciosRepository;
    }

    /**
     * Return a {@link List} of {@link Servicios} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Servicios> findByCriteria(ServiciosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Servicios> specification = createSpecification(criteria);
        return serviciosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Servicios} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Servicios> findByCriteria(ServiciosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Servicios> specification = createSpecification(criteria);
        return serviciosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiciosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Servicios> specification = createSpecification(criteria);
        return serviciosRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiciosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Servicios> createSpecification(ServiciosCriteria criteria) {
        Specification<Servicios> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Servicios_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Servicios_.nombre));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Servicios_.descripcion));
            }
            if (criteria.getProveedor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProveedor(), Servicios_.proveedor));
            }
            if (criteria.getImpuesto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getImpuesto(), Servicios_.impuesto));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), Servicios_.valor));
            }
            if (criteria.getUnidad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnidad(), Servicios_.unidad));
            }
            if (criteria.getDispinibilidad() != null) {
                specification = specification.and(buildSpecification(criteria.getDispinibilidad(), Servicios_.dispinibilidad));
            }
            if (criteria.getDescuento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDescuento(), Servicios_.descuento));
            }
            if (criteria.getRemate() != null) {
                specification = specification.and(buildSpecification(criteria.getRemate(), Servicios_.remate));
            }
            if (criteria.getTags() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTags(), Servicios_.tags));
            }
            if (criteria.getPuntuacion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPuntuacion(), Servicios_.puntuacion));
            }
            if (criteria.getVistos() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVistos(), Servicios_.vistos));
            }
            if (criteria.getOferta() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOferta(), Servicios_.oferta));
            }
            if (criteria.getTiempoOferta() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTiempoOferta(), Servicios_.tiempoOferta));
            }
        }
        return specification;
    }
}
