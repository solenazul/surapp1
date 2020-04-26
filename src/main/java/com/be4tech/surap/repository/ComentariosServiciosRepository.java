package com.be4tech.surap.repository;

import com.be4tech.surap.domain.ComentariosServicios;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ComentariosServicios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComentariosServiciosRepository extends JpaRepository<ComentariosServicios, Long>, JpaSpecificationExecutor<ComentariosServicios> {

    @Query("select comentariosServicios from ComentariosServicios comentariosServicios where comentariosServicios.idUser.login = ?#{principal.username}")
    List<ComentariosServicios> findByIdUserIsCurrentUser();
}
