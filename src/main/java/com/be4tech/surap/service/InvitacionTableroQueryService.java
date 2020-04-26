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

import com.be4tech.surap.domain.InvitacionTablero;
import com.be4tech.surap.domain.*; // for static metamodels
import com.be4tech.surap.repository.InvitacionTableroRepository;
import com.be4tech.surap.service.dto.InvitacionTableroCriteria;

/**
 * Service for executing complex queries for {@link InvitacionTablero} entities in the database.
 * The main input is a {@link InvitacionTableroCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InvitacionTablero} or a {@link Page} of {@link InvitacionTablero} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvitacionTableroQueryService extends QueryService<InvitacionTablero> {

    private final Logger log = LoggerFactory.getLogger(InvitacionTableroQueryService.class);

    private final InvitacionTableroRepository invitacionTableroRepository;

    public InvitacionTableroQueryService(InvitacionTableroRepository invitacionTableroRepository) {
        this.invitacionTableroRepository = invitacionTableroRepository;
    }

    /**
     * Return a {@link List} of {@link InvitacionTablero} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InvitacionTablero> findByCriteria(InvitacionTableroCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InvitacionTablero> specification = createSpecification(criteria);
        return invitacionTableroRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link InvitacionTablero} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InvitacionTablero> findByCriteria(InvitacionTableroCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InvitacionTablero> specification = createSpecification(criteria);
        return invitacionTableroRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvitacionTableroCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InvitacionTablero> specification = createSpecification(criteria);
        return invitacionTableroRepository.count(specification);
    }

    /**
     * Function to convert {@link InvitacionTableroCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InvitacionTablero> createSpecification(InvitacionTableroCriteria criteria) {
        Specification<InvitacionTablero> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InvitacionTablero_.id));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildSpecification(criteria.getEstado(), InvitacionTablero_.estado));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), InvitacionTablero_.fecha));
            }
            if (criteria.getIdUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdUserId(),
                    root -> root.join(InvitacionTablero_.idUser, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getTableroIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getTableroIdId(),
                    root -> root.join(InvitacionTablero_.tableroId, JoinType.LEFT).get(Tableros_.id)));
            }
        }
        return specification;
    }
}
