package com.be4tech.surap.repository;

import com.be4tech.surap.domain.EncuestaEntorno;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the EncuestaEntorno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EncuestaEntornoRepository extends JpaRepository<EncuestaEntorno, Long>, JpaSpecificationExecutor<EncuestaEntorno> {

    @Query("select encuestaEntorno from EncuestaEntorno encuestaEntorno where encuestaEntorno.user.login = ?#{principal.username}")
    List<EncuestaEntorno> findByUserIsCurrentUser();
}
