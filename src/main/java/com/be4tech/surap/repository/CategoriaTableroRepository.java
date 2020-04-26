package com.be4tech.surap.repository;

import com.be4tech.surap.domain.CategoriaTablero;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CategoriaTablero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaTableroRepository extends JpaRepository<CategoriaTablero, Long>, JpaSpecificationExecutor<CategoriaTablero> {
}
