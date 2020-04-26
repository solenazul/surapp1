package com.be4tech.surap.repository;

import com.be4tech.surap.domain.Servicios;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Servicios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiciosRepository extends JpaRepository<Servicios, Long>, JpaSpecificationExecutor<Servicios> {
}
