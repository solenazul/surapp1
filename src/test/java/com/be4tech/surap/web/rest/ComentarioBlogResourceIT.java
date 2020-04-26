package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.ComentarioBlog;
import com.be4tech.surap.domain.Blog;
import com.be4tech.surap.repository.ComentarioBlogRepository;
import com.be4tech.surap.service.ComentarioBlogService;
import com.be4tech.surap.service.dto.ComentarioBlogCriteria;
import com.be4tech.surap.service.ComentarioBlogQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ComentarioBlogResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class ComentarioBlogResourceIT {

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    @Autowired
    private ComentarioBlogRepository comentarioBlogRepository;

    @Autowired
    private ComentarioBlogService comentarioBlogService;

    @Autowired
    private ComentarioBlogQueryService comentarioBlogQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComentarioBlogMockMvc;

    private ComentarioBlog comentarioBlog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComentarioBlog createEntity(EntityManager em) {
        ComentarioBlog comentarioBlog = new ComentarioBlog()
            .comentario(DEFAULT_COMENTARIO)
            .fecha(DEFAULT_FECHA)
            .estado(DEFAULT_ESTADO);
        return comentarioBlog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComentarioBlog createUpdatedEntity(EntityManager em) {
        ComentarioBlog comentarioBlog = new ComentarioBlog()
            .comentario(UPDATED_COMENTARIO)
            .fecha(UPDATED_FECHA)
            .estado(UPDATED_ESTADO);
        return comentarioBlog;
    }

    @BeforeEach
    public void initTest() {
        comentarioBlog = createEntity(em);
    }

    @Test
    @Transactional
    public void createComentarioBlog() throws Exception {
        int databaseSizeBeforeCreate = comentarioBlogRepository.findAll().size();

        // Create the ComentarioBlog
        restComentarioBlogMockMvc.perform(post("/api/comentario-blogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentarioBlog)))
            .andExpect(status().isCreated());

        // Validate the ComentarioBlog in the database
        List<ComentarioBlog> comentarioBlogList = comentarioBlogRepository.findAll();
        assertThat(comentarioBlogList).hasSize(databaseSizeBeforeCreate + 1);
        ComentarioBlog testComentarioBlog = comentarioBlogList.get(comentarioBlogList.size() - 1);
        assertThat(testComentarioBlog.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testComentarioBlog.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testComentarioBlog.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createComentarioBlogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comentarioBlogRepository.findAll().size();

        // Create the ComentarioBlog with an existing ID
        comentarioBlog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComentarioBlogMockMvc.perform(post("/api/comentario-blogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentarioBlog)))
            .andExpect(status().isBadRequest());

        // Validate the ComentarioBlog in the database
        List<ComentarioBlog> comentarioBlogList = comentarioBlogRepository.findAll();
        assertThat(comentarioBlogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllComentarioBlogs() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList
        restComentarioBlogMockMvc.perform(get("/api/comentario-blogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentarioBlog.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));
    }
    
    @Test
    @Transactional
    public void getComentarioBlog() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get the comentarioBlog
        restComentarioBlogMockMvc.perform(get("/api/comentario-blogs/{id}", comentarioBlog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comentarioBlog.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO));
    }


    @Test
    @Transactional
    public void getComentarioBlogsByIdFiltering() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        Long id = comentarioBlog.getId();

        defaultComentarioBlogShouldBeFound("id.equals=" + id);
        defaultComentarioBlogShouldNotBeFound("id.notEquals=" + id);

        defaultComentarioBlogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultComentarioBlogShouldNotBeFound("id.greaterThan=" + id);

        defaultComentarioBlogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultComentarioBlogShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllComentarioBlogsByComentarioIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where comentario equals to DEFAULT_COMENTARIO
        defaultComentarioBlogShouldBeFound("comentario.equals=" + DEFAULT_COMENTARIO);

        // Get all the comentarioBlogList where comentario equals to UPDATED_COMENTARIO
        defaultComentarioBlogShouldNotBeFound("comentario.equals=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByComentarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where comentario not equals to DEFAULT_COMENTARIO
        defaultComentarioBlogShouldNotBeFound("comentario.notEquals=" + DEFAULT_COMENTARIO);

        // Get all the comentarioBlogList where comentario not equals to UPDATED_COMENTARIO
        defaultComentarioBlogShouldBeFound("comentario.notEquals=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByComentarioIsInShouldWork() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where comentario in DEFAULT_COMENTARIO or UPDATED_COMENTARIO
        defaultComentarioBlogShouldBeFound("comentario.in=" + DEFAULT_COMENTARIO + "," + UPDATED_COMENTARIO);

        // Get all the comentarioBlogList where comentario equals to UPDATED_COMENTARIO
        defaultComentarioBlogShouldNotBeFound("comentario.in=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByComentarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where comentario is not null
        defaultComentarioBlogShouldBeFound("comentario.specified=true");

        // Get all the comentarioBlogList where comentario is null
        defaultComentarioBlogShouldNotBeFound("comentario.specified=false");
    }
                @Test
    @Transactional
    public void getAllComentarioBlogsByComentarioContainsSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where comentario contains DEFAULT_COMENTARIO
        defaultComentarioBlogShouldBeFound("comentario.contains=" + DEFAULT_COMENTARIO);

        // Get all the comentarioBlogList where comentario contains UPDATED_COMENTARIO
        defaultComentarioBlogShouldNotBeFound("comentario.contains=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByComentarioNotContainsSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where comentario does not contain DEFAULT_COMENTARIO
        defaultComentarioBlogShouldNotBeFound("comentario.doesNotContain=" + DEFAULT_COMENTARIO);

        // Get all the comentarioBlogList where comentario does not contain UPDATED_COMENTARIO
        defaultComentarioBlogShouldBeFound("comentario.doesNotContain=" + UPDATED_COMENTARIO);
    }


    @Test
    @Transactional
    public void getAllComentarioBlogsByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where fecha equals to DEFAULT_FECHA
        defaultComentarioBlogShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the comentarioBlogList where fecha equals to UPDATED_FECHA
        defaultComentarioBlogShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where fecha not equals to DEFAULT_FECHA
        defaultComentarioBlogShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the comentarioBlogList where fecha not equals to UPDATED_FECHA
        defaultComentarioBlogShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultComentarioBlogShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the comentarioBlogList where fecha equals to UPDATED_FECHA
        defaultComentarioBlogShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where fecha is not null
        defaultComentarioBlogShouldBeFound("fecha.specified=true");

        // Get all the comentarioBlogList where fecha is null
        defaultComentarioBlogShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where estado equals to DEFAULT_ESTADO
        defaultComentarioBlogShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the comentarioBlogList where estado equals to UPDATED_ESTADO
        defaultComentarioBlogShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByEstadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where estado not equals to DEFAULT_ESTADO
        defaultComentarioBlogShouldNotBeFound("estado.notEquals=" + DEFAULT_ESTADO);

        // Get all the comentarioBlogList where estado not equals to UPDATED_ESTADO
        defaultComentarioBlogShouldBeFound("estado.notEquals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultComentarioBlogShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the comentarioBlogList where estado equals to UPDATED_ESTADO
        defaultComentarioBlogShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where estado is not null
        defaultComentarioBlogShouldBeFound("estado.specified=true");

        // Get all the comentarioBlogList where estado is null
        defaultComentarioBlogShouldNotBeFound("estado.specified=false");
    }
                @Test
    @Transactional
    public void getAllComentarioBlogsByEstadoContainsSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where estado contains DEFAULT_ESTADO
        defaultComentarioBlogShouldBeFound("estado.contains=" + DEFAULT_ESTADO);

        // Get all the comentarioBlogList where estado contains UPDATED_ESTADO
        defaultComentarioBlogShouldNotBeFound("estado.contains=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllComentarioBlogsByEstadoNotContainsSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);

        // Get all the comentarioBlogList where estado does not contain DEFAULT_ESTADO
        defaultComentarioBlogShouldNotBeFound("estado.doesNotContain=" + DEFAULT_ESTADO);

        // Get all the comentarioBlogList where estado does not contain UPDATED_ESTADO
        defaultComentarioBlogShouldBeFound("estado.doesNotContain=" + UPDATED_ESTADO);
    }


    @Test
    @Transactional
    public void getAllComentarioBlogsByBlogIdIsEqualToSomething() throws Exception {
        // Initialize the database
        comentarioBlogRepository.saveAndFlush(comentarioBlog);
        Blog blogId = BlogResourceIT.createEntity(em);
        em.persist(blogId);
        em.flush();
        comentarioBlog.setBlogId(blogId);
        comentarioBlogRepository.saveAndFlush(comentarioBlog);
        Long blogIdId = blogId.getId();

        // Get all the comentarioBlogList where blogId equals to blogIdId
        defaultComentarioBlogShouldBeFound("blogIdId.equals=" + blogIdId);

        // Get all the comentarioBlogList where blogId equals to blogIdId + 1
        defaultComentarioBlogShouldNotBeFound("blogIdId.equals=" + (blogIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComentarioBlogShouldBeFound(String filter) throws Exception {
        restComentarioBlogMockMvc.perform(get("/api/comentario-blogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentarioBlog.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));

        // Check, that the count call also returns 1
        restComentarioBlogMockMvc.perform(get("/api/comentario-blogs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComentarioBlogShouldNotBeFound(String filter) throws Exception {
        restComentarioBlogMockMvc.perform(get("/api/comentario-blogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComentarioBlogMockMvc.perform(get("/api/comentario-blogs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingComentarioBlog() throws Exception {
        // Get the comentarioBlog
        restComentarioBlogMockMvc.perform(get("/api/comentario-blogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentarioBlog() throws Exception {
        // Initialize the database
        comentarioBlogService.save(comentarioBlog);

        int databaseSizeBeforeUpdate = comentarioBlogRepository.findAll().size();

        // Update the comentarioBlog
        ComentarioBlog updatedComentarioBlog = comentarioBlogRepository.findById(comentarioBlog.getId()).get();
        // Disconnect from session so that the updates on updatedComentarioBlog are not directly saved in db
        em.detach(updatedComentarioBlog);
        updatedComentarioBlog
            .comentario(UPDATED_COMENTARIO)
            .fecha(UPDATED_FECHA)
            .estado(UPDATED_ESTADO);

        restComentarioBlogMockMvc.perform(put("/api/comentario-blogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedComentarioBlog)))
            .andExpect(status().isOk());

        // Validate the ComentarioBlog in the database
        List<ComentarioBlog> comentarioBlogList = comentarioBlogRepository.findAll();
        assertThat(comentarioBlogList).hasSize(databaseSizeBeforeUpdate);
        ComentarioBlog testComentarioBlog = comentarioBlogList.get(comentarioBlogList.size() - 1);
        assertThat(testComentarioBlog.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testComentarioBlog.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testComentarioBlog.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingComentarioBlog() throws Exception {
        int databaseSizeBeforeUpdate = comentarioBlogRepository.findAll().size();

        // Create the ComentarioBlog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComentarioBlogMockMvc.perform(put("/api/comentario-blogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentarioBlog)))
            .andExpect(status().isBadRequest());

        // Validate the ComentarioBlog in the database
        List<ComentarioBlog> comentarioBlogList = comentarioBlogRepository.findAll();
        assertThat(comentarioBlogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComentarioBlog() throws Exception {
        // Initialize the database
        comentarioBlogService.save(comentarioBlog);

        int databaseSizeBeforeDelete = comentarioBlogRepository.findAll().size();

        // Delete the comentarioBlog
        restComentarioBlogMockMvc.perform(delete("/api/comentario-blogs/{id}", comentarioBlog.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComentarioBlog> comentarioBlogList = comentarioBlogRepository.findAll();
        assertThat(comentarioBlogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
