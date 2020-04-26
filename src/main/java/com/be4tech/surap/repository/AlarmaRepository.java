package com.be4tech.surap.repository;

import com.be4tech.surap.domain.Alarma;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Alarma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlarmaRepository extends JpaRepository<Alarma, Long>, JpaSpecificationExecutor<Alarma> {

    @Query("select alarma from Alarma alarma where alarma.user.login = ?#{principal.username}")
    List<Alarma> findByUserIsCurrentUser();
}
