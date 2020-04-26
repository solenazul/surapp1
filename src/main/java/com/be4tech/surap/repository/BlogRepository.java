package com.be4tech.surap.repository;

import com.be4tech.surap.domain.Blog;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Blog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("select blog from Blog blog where blog.idUser.login = ?#{principal.username}")
    List<Blog> findByIdUserIsCurrentUser();
}
