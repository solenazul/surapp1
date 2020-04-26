package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.Blog;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.repository.BlogRepository;
import com.be4tech.surap.service.BlogService;
import com.be4tech.surap.service.dto.BlogCriteria;
import com.be4tech.surap.service.BlogQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BlogResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class BlogResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_VIDEO = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CALIFICACION = 1;
    private static final Integer UPDATED_CALIFICACION = 2;
    private static final Integer SMALLER_CALIFICACION = 1 - 1;

    private static final Integer DEFAULT_LECTURAS = 1;
    private static final Integer UPDATED_LECTURAS = 2;
    private static final Integer SMALLER_LECTURAS = 1 - 1;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogQueryService blogQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBlogMockMvc;

    private Blog blog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Blog createEntity(EntityManager em) {
        Blog blog = new Blog()
            .titulo(DEFAULT_TITULO)
            .contenido(DEFAULT_CONTENIDO)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .video(DEFAULT_VIDEO)
            .calificacion(DEFAULT_CALIFICACION)
            .lecturas(DEFAULT_LECTURAS);
        return blog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Blog createUpdatedEntity(EntityManager em) {
        Blog blog = new Blog()
            .titulo(UPDATED_TITULO)
            .contenido(UPDATED_CONTENIDO)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .calificacion(UPDATED_CALIFICACION)
            .lecturas(UPDATED_LECTURAS);
        return blog;
    }

    @BeforeEach
    public void initTest() {
        blog = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlog() throws Exception {
        int databaseSizeBeforeCreate = blogRepository.findAll().size();

        // Create the Blog
        restBlogMockMvc.perform(post("/api/blogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(blog)))
            .andExpect(status().isCreated());

        // Validate the Blog in the database
        List<Blog> blogList = blogRepository.findAll();
        assertThat(blogList).hasSize(databaseSizeBeforeCreate + 1);
        Blog testBlog = blogList.get(blogList.size() - 1);
        assertThat(testBlog.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testBlog.getContenido()).isEqualTo(DEFAULT_CONTENIDO);
        assertThat(testBlog.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testBlog.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testBlog.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testBlog.getCalificacion()).isEqualTo(DEFAULT_CALIFICACION);
        assertThat(testBlog.getLecturas()).isEqualTo(DEFAULT_LECTURAS);
    }

    @Test
    @Transactional
    public void createBlogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blogRepository.findAll().size();

        // Create the Blog with an existing ID
        blog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlogMockMvc.perform(post("/api/blogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(blog)))
            .andExpect(status().isBadRequest());

        // Validate the Blog in the database
        List<Blog> blogList = blogRepository.findAll();
        assertThat(blogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBlogs() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList
        restBlogMockMvc.perform(get("/api/blogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blog.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].video").value(hasItem(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.[*].calificacion").value(hasItem(DEFAULT_CALIFICACION)))
            .andExpect(jsonPath("$.[*].lecturas").value(hasItem(DEFAULT_LECTURAS)));
    }
    
    @Test
    @Transactional
    public void getBlog() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get the blog
        restBlogMockMvc.perform(get("/api/blogs/{id}", blog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(blog.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO.toString()))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.video").value(DEFAULT_VIDEO))
            .andExpect(jsonPath("$.calificacion").value(DEFAULT_CALIFICACION))
            .andExpect(jsonPath("$.lecturas").value(DEFAULT_LECTURAS));
    }


    @Test
    @Transactional
    public void getBlogsByIdFiltering() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        Long id = blog.getId();

        defaultBlogShouldBeFound("id.equals=" + id);
        defaultBlogShouldNotBeFound("id.notEquals=" + id);

        defaultBlogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBlogShouldNotBeFound("id.greaterThan=" + id);

        defaultBlogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBlogShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBlogsByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where titulo equals to DEFAULT_TITULO
        defaultBlogShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the blogList where titulo equals to UPDATED_TITULO
        defaultBlogShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllBlogsByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where titulo not equals to DEFAULT_TITULO
        defaultBlogShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the blogList where titulo not equals to UPDATED_TITULO
        defaultBlogShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllBlogsByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultBlogShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the blogList where titulo equals to UPDATED_TITULO
        defaultBlogShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllBlogsByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where titulo is not null
        defaultBlogShouldBeFound("titulo.specified=true");

        // Get all the blogList where titulo is null
        defaultBlogShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllBlogsByTituloContainsSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where titulo contains DEFAULT_TITULO
        defaultBlogShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the blogList where titulo contains UPDATED_TITULO
        defaultBlogShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllBlogsByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where titulo does not contain DEFAULT_TITULO
        defaultBlogShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the blogList where titulo does not contain UPDATED_TITULO
        defaultBlogShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllBlogsByVideoIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where video equals to DEFAULT_VIDEO
        defaultBlogShouldBeFound("video.equals=" + DEFAULT_VIDEO);

        // Get all the blogList where video equals to UPDATED_VIDEO
        defaultBlogShouldNotBeFound("video.equals=" + UPDATED_VIDEO);
    }

    @Test
    @Transactional
    public void getAllBlogsByVideoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where video not equals to DEFAULT_VIDEO
        defaultBlogShouldNotBeFound("video.notEquals=" + DEFAULT_VIDEO);

        // Get all the blogList where video not equals to UPDATED_VIDEO
        defaultBlogShouldBeFound("video.notEquals=" + UPDATED_VIDEO);
    }

    @Test
    @Transactional
    public void getAllBlogsByVideoIsInShouldWork() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where video in DEFAULT_VIDEO or UPDATED_VIDEO
        defaultBlogShouldBeFound("video.in=" + DEFAULT_VIDEO + "," + UPDATED_VIDEO);

        // Get all the blogList where video equals to UPDATED_VIDEO
        defaultBlogShouldNotBeFound("video.in=" + UPDATED_VIDEO);
    }

    @Test
    @Transactional
    public void getAllBlogsByVideoIsNullOrNotNull() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where video is not null
        defaultBlogShouldBeFound("video.specified=true");

        // Get all the blogList where video is null
        defaultBlogShouldNotBeFound("video.specified=false");
    }
                @Test
    @Transactional
    public void getAllBlogsByVideoContainsSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where video contains DEFAULT_VIDEO
        defaultBlogShouldBeFound("video.contains=" + DEFAULT_VIDEO);

        // Get all the blogList where video contains UPDATED_VIDEO
        defaultBlogShouldNotBeFound("video.contains=" + UPDATED_VIDEO);
    }

    @Test
    @Transactional
    public void getAllBlogsByVideoNotContainsSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where video does not contain DEFAULT_VIDEO
        defaultBlogShouldNotBeFound("video.doesNotContain=" + DEFAULT_VIDEO);

        // Get all the blogList where video does not contain UPDATED_VIDEO
        defaultBlogShouldBeFound("video.doesNotContain=" + UPDATED_VIDEO);
    }


    @Test
    @Transactional
    public void getAllBlogsByCalificacionIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where calificacion equals to DEFAULT_CALIFICACION
        defaultBlogShouldBeFound("calificacion.equals=" + DEFAULT_CALIFICACION);

        // Get all the blogList where calificacion equals to UPDATED_CALIFICACION
        defaultBlogShouldNotBeFound("calificacion.equals=" + UPDATED_CALIFICACION);
    }

    @Test
    @Transactional
    public void getAllBlogsByCalificacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where calificacion not equals to DEFAULT_CALIFICACION
        defaultBlogShouldNotBeFound("calificacion.notEquals=" + DEFAULT_CALIFICACION);

        // Get all the blogList where calificacion not equals to UPDATED_CALIFICACION
        defaultBlogShouldBeFound("calificacion.notEquals=" + UPDATED_CALIFICACION);
    }

    @Test
    @Transactional
    public void getAllBlogsByCalificacionIsInShouldWork() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where calificacion in DEFAULT_CALIFICACION or UPDATED_CALIFICACION
        defaultBlogShouldBeFound("calificacion.in=" + DEFAULT_CALIFICACION + "," + UPDATED_CALIFICACION);

        // Get all the blogList where calificacion equals to UPDATED_CALIFICACION
        defaultBlogShouldNotBeFound("calificacion.in=" + UPDATED_CALIFICACION);
    }

    @Test
    @Transactional
    public void getAllBlogsByCalificacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where calificacion is not null
        defaultBlogShouldBeFound("calificacion.specified=true");

        // Get all the blogList where calificacion is null
        defaultBlogShouldNotBeFound("calificacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllBlogsByCalificacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where calificacion is greater than or equal to DEFAULT_CALIFICACION
        defaultBlogShouldBeFound("calificacion.greaterThanOrEqual=" + DEFAULT_CALIFICACION);

        // Get all the blogList where calificacion is greater than or equal to UPDATED_CALIFICACION
        defaultBlogShouldNotBeFound("calificacion.greaterThanOrEqual=" + UPDATED_CALIFICACION);
    }

    @Test
    @Transactional
    public void getAllBlogsByCalificacionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where calificacion is less than or equal to DEFAULT_CALIFICACION
        defaultBlogShouldBeFound("calificacion.lessThanOrEqual=" + DEFAULT_CALIFICACION);

        // Get all the blogList where calificacion is less than or equal to SMALLER_CALIFICACION
        defaultBlogShouldNotBeFound("calificacion.lessThanOrEqual=" + SMALLER_CALIFICACION);
    }

    @Test
    @Transactional
    public void getAllBlogsByCalificacionIsLessThanSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where calificacion is less than DEFAULT_CALIFICACION
        defaultBlogShouldNotBeFound("calificacion.lessThan=" + DEFAULT_CALIFICACION);

        // Get all the blogList where calificacion is less than UPDATED_CALIFICACION
        defaultBlogShouldBeFound("calificacion.lessThan=" + UPDATED_CALIFICACION);
    }

    @Test
    @Transactional
    public void getAllBlogsByCalificacionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where calificacion is greater than DEFAULT_CALIFICACION
        defaultBlogShouldNotBeFound("calificacion.greaterThan=" + DEFAULT_CALIFICACION);

        // Get all the blogList where calificacion is greater than SMALLER_CALIFICACION
        defaultBlogShouldBeFound("calificacion.greaterThan=" + SMALLER_CALIFICACION);
    }


    @Test
    @Transactional
    public void getAllBlogsByLecturasIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where lecturas equals to DEFAULT_LECTURAS
        defaultBlogShouldBeFound("lecturas.equals=" + DEFAULT_LECTURAS);

        // Get all the blogList where lecturas equals to UPDATED_LECTURAS
        defaultBlogShouldNotBeFound("lecturas.equals=" + UPDATED_LECTURAS);
    }

    @Test
    @Transactional
    public void getAllBlogsByLecturasIsNotEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where lecturas not equals to DEFAULT_LECTURAS
        defaultBlogShouldNotBeFound("lecturas.notEquals=" + DEFAULT_LECTURAS);

        // Get all the blogList where lecturas not equals to UPDATED_LECTURAS
        defaultBlogShouldBeFound("lecturas.notEquals=" + UPDATED_LECTURAS);
    }

    @Test
    @Transactional
    public void getAllBlogsByLecturasIsInShouldWork() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where lecturas in DEFAULT_LECTURAS or UPDATED_LECTURAS
        defaultBlogShouldBeFound("lecturas.in=" + DEFAULT_LECTURAS + "," + UPDATED_LECTURAS);

        // Get all the blogList where lecturas equals to UPDATED_LECTURAS
        defaultBlogShouldNotBeFound("lecturas.in=" + UPDATED_LECTURAS);
    }

    @Test
    @Transactional
    public void getAllBlogsByLecturasIsNullOrNotNull() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where lecturas is not null
        defaultBlogShouldBeFound("lecturas.specified=true");

        // Get all the blogList where lecturas is null
        defaultBlogShouldNotBeFound("lecturas.specified=false");
    }

    @Test
    @Transactional
    public void getAllBlogsByLecturasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where lecturas is greater than or equal to DEFAULT_LECTURAS
        defaultBlogShouldBeFound("lecturas.greaterThanOrEqual=" + DEFAULT_LECTURAS);

        // Get all the blogList where lecturas is greater than or equal to UPDATED_LECTURAS
        defaultBlogShouldNotBeFound("lecturas.greaterThanOrEqual=" + UPDATED_LECTURAS);
    }

    @Test
    @Transactional
    public void getAllBlogsByLecturasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where lecturas is less than or equal to DEFAULT_LECTURAS
        defaultBlogShouldBeFound("lecturas.lessThanOrEqual=" + DEFAULT_LECTURAS);

        // Get all the blogList where lecturas is less than or equal to SMALLER_LECTURAS
        defaultBlogShouldNotBeFound("lecturas.lessThanOrEqual=" + SMALLER_LECTURAS);
    }

    @Test
    @Transactional
    public void getAllBlogsByLecturasIsLessThanSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where lecturas is less than DEFAULT_LECTURAS
        defaultBlogShouldNotBeFound("lecturas.lessThan=" + DEFAULT_LECTURAS);

        // Get all the blogList where lecturas is less than UPDATED_LECTURAS
        defaultBlogShouldBeFound("lecturas.lessThan=" + UPDATED_LECTURAS);
    }

    @Test
    @Transactional
    public void getAllBlogsByLecturasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);

        // Get all the blogList where lecturas is greater than DEFAULT_LECTURAS
        defaultBlogShouldNotBeFound("lecturas.greaterThan=" + DEFAULT_LECTURAS);

        // Get all the blogList where lecturas is greater than SMALLER_LECTURAS
        defaultBlogShouldBeFound("lecturas.greaterThan=" + SMALLER_LECTURAS);
    }


    @Test
    @Transactional
    public void getAllBlogsByIdUserIsEqualToSomething() throws Exception {
        // Initialize the database
        blogRepository.saveAndFlush(blog);
        User idUser = UserResourceIT.createEntity(em);
        em.persist(idUser);
        em.flush();
        blog.setIdUser(idUser);
        blogRepository.saveAndFlush(blog);
        Long idUserId = idUser.getId();

        // Get all the blogList where idUser equals to idUserId
        defaultBlogShouldBeFound("idUserId.equals=" + idUserId);

        // Get all the blogList where idUser equals to idUserId + 1
        defaultBlogShouldNotBeFound("idUserId.equals=" + (idUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBlogShouldBeFound(String filter) throws Exception {
        restBlogMockMvc.perform(get("/api/blogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blog.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].video").value(hasItem(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.[*].calificacion").value(hasItem(DEFAULT_CALIFICACION)))
            .andExpect(jsonPath("$.[*].lecturas").value(hasItem(DEFAULT_LECTURAS)));

        // Check, that the count call also returns 1
        restBlogMockMvc.perform(get("/api/blogs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBlogShouldNotBeFound(String filter) throws Exception {
        restBlogMockMvc.perform(get("/api/blogs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBlogMockMvc.perform(get("/api/blogs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBlog() throws Exception {
        // Get the blog
        restBlogMockMvc.perform(get("/api/blogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlog() throws Exception {
        // Initialize the database
        blogService.save(blog);

        int databaseSizeBeforeUpdate = blogRepository.findAll().size();

        // Update the blog
        Blog updatedBlog = blogRepository.findById(blog.getId()).get();
        // Disconnect from session so that the updates on updatedBlog are not directly saved in db
        em.detach(updatedBlog);
        updatedBlog
            .titulo(UPDATED_TITULO)
            .contenido(UPDATED_CONTENIDO)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .video(UPDATED_VIDEO)
            .calificacion(UPDATED_CALIFICACION)
            .lecturas(UPDATED_LECTURAS);

        restBlogMockMvc.perform(put("/api/blogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBlog)))
            .andExpect(status().isOk());

        // Validate the Blog in the database
        List<Blog> blogList = blogRepository.findAll();
        assertThat(blogList).hasSize(databaseSizeBeforeUpdate);
        Blog testBlog = blogList.get(blogList.size() - 1);
        assertThat(testBlog.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testBlog.getContenido()).isEqualTo(UPDATED_CONTENIDO);
        assertThat(testBlog.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testBlog.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testBlog.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testBlog.getCalificacion()).isEqualTo(UPDATED_CALIFICACION);
        assertThat(testBlog.getLecturas()).isEqualTo(UPDATED_LECTURAS);
    }

    @Test
    @Transactional
    public void updateNonExistingBlog() throws Exception {
        int databaseSizeBeforeUpdate = blogRepository.findAll().size();

        // Create the Blog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogMockMvc.perform(put("/api/blogs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(blog)))
            .andExpect(status().isBadRequest());

        // Validate the Blog in the database
        List<Blog> blogList = blogRepository.findAll();
        assertThat(blogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBlog() throws Exception {
        // Initialize the database
        blogService.save(blog);

        int databaseSizeBeforeDelete = blogRepository.findAll().size();

        // Delete the blog
        restBlogMockMvc.perform(delete("/api/blogs/{id}", blog.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Blog> blogList = blogRepository.findAll();
        assertThat(blogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
