package com.be4tech.surap.repository;

import com.be4tech.surap.domain.Tableros;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Tableros entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TablerosRepository extends JpaRepository<Tableros, Long>, JpaSpecificationExecutor<Tableros> {

    @Query("select tableros from Tableros tableros where tableros.idUser.login = ?#{principal.username}")
    List<Tableros> findByIdUserIsCurrentUser();
}
