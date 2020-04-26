package com.be4tech.surap.repository;

import com.be4tech.surap.domain.IPS;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IPS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IPSRepository extends JpaRepository<IPS, Long>, JpaSpecificationExecutor<IPS> {
}
