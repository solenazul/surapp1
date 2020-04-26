package com.be4tech.surap.repository;

import com.be4tech.surap.domain.Productos;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Productos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long>, JpaSpecificationExecutor<Productos> {
}
