package com.be4tech.surap.repository;

import com.be4tech.surap.domain.InvitacionTablero;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the InvitacionTablero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvitacionTableroRepository extends JpaRepository<InvitacionTablero, Long>, JpaSpecificationExecutor<InvitacionTablero> {

    @Query("select invitacionTablero from InvitacionTablero invitacionTablero where invitacionTablero.idUser.login = ?#{principal.username}")
    List<InvitacionTablero> findByIdUserIsCurrentUser();
}
