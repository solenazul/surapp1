package com.be4tech.surap.repository;

import com.be4tech.surap.domain.Categorias;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Categorias entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Long>, JpaSpecificationExecutor<Categorias> {
}
