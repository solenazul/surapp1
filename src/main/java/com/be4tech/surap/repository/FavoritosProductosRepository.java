package com.be4tech.surap.repository;

import com.be4tech.surap.domain.FavoritosProductos;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the FavoritosProductos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavoritosProductosRepository extends JpaRepository<FavoritosProductos, Long>, JpaSpecificationExecutor<FavoritosProductos> {

    @Query("select favoritosProductos from FavoritosProductos favoritosProductos where favoritosProductos.idUser.login = ?#{principal.username}")
    List<FavoritosProductos> findByIdUserIsCurrentUser();
}
