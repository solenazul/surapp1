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

import com.be4tech.surap.domain.Productos;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.ProductosRepository;
import com.be4tech.surap.service.dto.ProductosCriteria;

/**
 * Service for executing complex queries for {@link Productos} entities in the database.
 * The main input is a {@link ProductosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Productos} or a {@link Page} of {@link Productos} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductosQueryService extends QueryService<Productos> {

    private final Logger log = LoggerFactory.getLogger(ProductosQueryService.class);

    private final ProductosRepository productosRepository;

    public ProductosQueryService(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    /**
     * Return a {@link List} of {@link Productos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Productos> findByCriteria(ProductosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Productos> specification = createSpecification(criteria);
        return productosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Productos} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Productos> findByCriteria(ProductosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Productos> specification = createSpecification(criteria);
        return productosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Productos> specification = createSpecification(criteria);
        return productosRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Productos> createSpecification(ProductosCriteria criteria) {
        Specification<Productos> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Productos_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Productos_.nombre));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Productos_.descripcion));
            }
            if (criteria.getInventario() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInventario(), Productos_.inventario));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTipo(), Productos_.tipo));
            }
            if (criteria.getImpuesto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getImpuesto(), Productos_.impuesto));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), Productos_.valor));
            }
            if (criteria.getUnidad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnidad(), Productos_.unidad));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstado(), Productos_.estado));
            }
            if (criteria.getTiempoEntrega() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTiempoEntrega(), Productos_.tiempoEntrega));
            }
            if (criteria.getDispinibilidad() != null) {
                specification = specification.and(buildSpecification(criteria.getDispinibilidad(), Productos_.dispinibilidad));
            }
            if (criteria.getNuevo() != null) {
                specification = specification.and(buildSpecification(criteria.getNuevo(), Productos_.nuevo));
            }
            if (criteria.getDescuento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDescuento(), Productos_.descuento));
            }
            if (criteria.getRemate() != null) {
                specification = specification.and(buildSpecification(criteria.getRemate(), Productos_.remate));
            }
            if (criteria.getTags() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTags(), Productos_.tags));
            }
            if (criteria.getPuntuacion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPuntuacion(), Productos_.puntuacion));
            }
            if (criteria.getVistos() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVistos(), Productos_.vistos));
            }
            if (criteria.getOferta() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOferta(), Productos_.oferta));
            }
            if (criteria.getTiempoOferta() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTiempoOferta(), Productos_.tiempoOferta));
            }
        }
        return specification;
    }
}
