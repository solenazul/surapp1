package com.be4tech.surap.repository;

import com.be4tech.surap.domain.EncuestaSintomas;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the EncuestaSintomas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EncuestaSintomasRepository extends JpaRepository<EncuestaSintomas, Long>, JpaSpecificationExecutor<EncuestaSintomas> {

    @Query("select encuestaSintomas from EncuestaSintomas encuestaSintomas where encuestaSintomas.user.login = ?#{principal.username}")
    List<EncuestaSintomas> findByUserIsCurrentUser();
}
