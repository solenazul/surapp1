package com.be4tech.surap.repository;

import com.be4tech.surap.domain.Modelo3D;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Modelo3D entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Modelo3DRepository extends JpaRepository<Modelo3D, Long>, JpaSpecificationExecutor<Modelo3D> {

    @Query("select modelo3D from Modelo3D modelo3D where modelo3D.idUser.login = ?#{principal.username}")
    List<Modelo3D> findByIdUserIsCurrentUser();
}
