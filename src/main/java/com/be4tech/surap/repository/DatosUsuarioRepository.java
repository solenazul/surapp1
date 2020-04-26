package com.be4tech.surap.repository;

import com.be4tech.surap.domain.DatosUsuario;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the DatosUsuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatosUsuarioRepository extends JpaRepository<DatosUsuario, Long>, JpaSpecificationExecutor<DatosUsuario> {

    @Query("select datosUsuario from DatosUsuario datosUsuario where datosUsuario.idUser.login = ?#{principal.username}")
    List<DatosUsuario> findByIdUserIsCurrentUser();
}
