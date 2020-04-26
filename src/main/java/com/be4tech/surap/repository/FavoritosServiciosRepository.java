package com.be4tech.surap.repository;

import com.be4tech.surap.domain.FavoritosServicios;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the FavoritosServicios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavoritosServiciosRepository extends JpaRepository<FavoritosServicios, Long>, JpaSpecificationExecutor<FavoritosServicios> {

    @Query("select favoritosServicios from FavoritosServicios favoritosServicios where favoritosServicios.idUser.login = ?#{principal.username}")
    List<FavoritosServicios> findByIdUserIsCurrentUser();
}
