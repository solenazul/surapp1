package com.be4tech.surap.repository;

import com.be4tech.surap.domain.ComentariosProducto;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ComentariosProducto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComentariosProductoRepository extends JpaRepository<ComentariosProducto, Long>, JpaSpecificationExecutor<ComentariosProducto> {

    @Query("select comentariosProducto from ComentariosProducto comentariosProducto where comentariosProducto.idUser.login = ?#{principal.username}")
    List<ComentariosProducto> findByIdUserIsCurrentUser();
}
