package com.be4tech.surap.service;

import com.be4tech.surap.domain.IPS;
import com.be4tech.surap.repository.IPSRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IPS}.
 */
@Service
@Transactional
public class IPSService {

    private final Logger log = LoggerFactory.getLogger(IPSService.class);

    private final IPSRepository iPSRepository;

    public IPSService(IPSRepository iPSRepository) {
        this.iPSRepository = iPSRepository;
    }

    /**
     * Save a iPS.
     *
     * @param iPS the entity to save.
     * @return the persisted entity.
     */
    public IPS save(IPS iPS) {
        log.debug("Request to save IPS : {}", iPS);
        return iPSRepository.save(iPS);
    }

    /**
     * Get all the iPS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IPS> findAll(Pageable pageable) {
        log.debug("Request to get all IPS");
        return iPSRepository.findAll(pageable);
    }

    /**
     * Get one iPS by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IPS> findOne(Long id) {
        log.debug("Request to get IPS : {}", id);
        return iPSRepository.findById(id);
    }

    /**
     * Delete the iPS by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IPS : {}", id);
        iPSRepository.deleteById(id);
    }
}
