package com.be4tech.surap.repository;

import com.be4tech.surap.domain.Fisiometria1;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Fisiometria1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Fisiometria1Repository extends JpaRepository<Fisiometria1, Long>, JpaSpecificationExecutor<Fisiometria1> {

    @Query("select fisiometria1 from Fisiometria1 fisiometria1 where fisiometria1.user.login = ?#{principal.username}")
    List<Fisiometria1> findByUserIsCurrentUser();
}
