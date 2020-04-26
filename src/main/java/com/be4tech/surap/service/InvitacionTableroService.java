package com.be4tech.surap.service;

import com.be4tech.surap.domain.InvitacionTablero;
import com.be4tech.surap.repository.InvitacionTableroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link InvitacionTablero}.
 */
@Service
@Transactional
public class InvitacionTableroService {

    private final Logger log = LoggerFactory.getLogger(InvitacionTableroService.class);

    private final InvitacionTableroRepository invitacionTableroRepository;

    public InvitacionTableroService(InvitacionTableroRepository invitacionTableroRepository) {
        this.invitacionTableroRepository = invitacionTableroRepository;
    }

    /**
     * Save a invitacionTablero.
     *
     * @param invitacionTablero the entity to save.
     * @return the persisted entity.
     */
    public InvitacionTablero save(InvitacionTablero invitacionTablero) {
        log.debug("Request to save InvitacionTablero : {}", invitacionTablero);
        return invitacionTableroRepository.save(invitacionTablero);
    }

    /**
     * Get all the invitacionTableros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InvitacionTablero> findAll(Pageable pageable) {
        log.debug("Request to get all InvitacionTableros");
        return invitacionTableroRepository.findAll(pageable);
    }

    /**
     * Get one invitacionTablero by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InvitacionTablero> findOne(Long id) {
        log.debug("Request to get InvitacionTablero : {}", id);
        return invitacionTableroRepository.findById(id);
    }

    /**
     * Delete the invitacionTablero by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InvitacionTablero : {}", id);
        invitacionTableroRepository.deleteById(id);
    }
}
