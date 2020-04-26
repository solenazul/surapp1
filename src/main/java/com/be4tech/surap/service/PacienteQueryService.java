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

import com.be4tech.surap.domain.Paciente;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.PacienteRepository;
import com.be4tech.surap.service.dto.PacienteCriteria;

/**
 * Service for executing complex queries for {@link Paciente} entities in the database.
 * The main input is a {@link PacienteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Paciente} or a {@link Page} of {@link Paciente} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PacienteQueryService extends QueryService<Paciente> {

    private final Logger log = LoggerFactory.getLogger(PacienteQueryService.class);

    private final PacienteRepository pacienteRepository;

    public PacienteQueryService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Return a {@link List} of {@link Paciente} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Paciente> findByCriteria(PacienteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Paciente> specification = createSpecification(criteria);
        return pacienteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Paciente} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Paciente> findByCriteria(PacienteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Paciente> specification = createSpecification(criteria);
        return pacienteRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PacienteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Paciente> specification = createSpecification(criteria);
        return pacienteRepository.count(specification);
    }

    /**
     * Function to convert {@link PacienteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Paciente> createSpecification(PacienteCriteria criteria) {
        Specification<Paciente> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Paciente_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Paciente_.nombre));
            }
            if (criteria.getIdentificacion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdentificacion(), Paciente_.identificacion));
            }
            if (criteria.getEdad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEdad(), Paciente_.edad));
            }
            if (criteria.getSexo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSexo(), Paciente_.sexo));
            }
            if (criteria.getFechaNacimiento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFechaNacimiento(), Paciente_.fechaNacimiento));
            }
            if (criteria.getIpsId() != null) {
                specification = specification.and(buildSpecification(criteria.getIpsId(),
                    root -> root.join(Paciente_.ips, JoinType.LEFT).get(IPS_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Paciente_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
