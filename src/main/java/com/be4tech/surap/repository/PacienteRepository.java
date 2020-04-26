package com.be4tech.surap.repository;

import com.be4tech.surap.domain.Paciente;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Paciente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>, JpaSpecificationExecutor<Paciente> {

    @Query("select paciente from Paciente paciente where paciente.user.login = ?#{principal.username}")
    List<Paciente> findByUserIsCurrentUser();
}
