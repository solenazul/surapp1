package com.be4tech.surap.repository;

import com.be4tech.surap.domain.ComentarioBlog;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ComentarioBlog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComentarioBlogRepository extends JpaRepository<ComentarioBlog, Long>, JpaSpecificationExecutor<ComentarioBlog> {
}
