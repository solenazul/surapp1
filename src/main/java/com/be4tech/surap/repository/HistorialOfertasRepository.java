package com.be4tech.surap.repository;

import com.be4tech.surap.domain.HistorialOfertas;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HistorialOfertas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistorialOfertasRepository extends JpaRepository<HistorialOfertas, Long>, JpaSpecificationExecutor<HistorialOfertas> {
}
