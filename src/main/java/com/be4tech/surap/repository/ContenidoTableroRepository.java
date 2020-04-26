package com.be4tech.surap.repository;

import com.be4tech.surap.domain.ContenidoTablero;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ContenidoTablero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContenidoTableroRepository extends JpaRepository<ContenidoTablero, Long>, JpaSpecificationExecutor<ContenidoTablero> {

    @Query("select contenidoTablero from ContenidoTablero contenidoTablero where contenidoTablero.idUser.login = ?#{principal.username}")
    List<ContenidoTablero> findByIdUserIsCurrentUser();
}
