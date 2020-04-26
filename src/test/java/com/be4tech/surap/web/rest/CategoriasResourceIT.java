package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.Categorias;
import com.be4tech.surap.repository.CategoriasRepository;
import com.be4tech.surap.service.CategoriasService;
import com.be4tech.surap.service.dto.CategoriasCriteria;
import com.be4tech.surap.service.CategoriasQueryService;

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
 * Integration tests for the {@link CategoriasResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class CategoriasResourceIT {

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CategoriasRepository categoriasRepository;

    @Autowired
    private CategoriasService categoriasService;

    @Autowired
    private CategoriasQueryService categoriasQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriasMockMvc;

    private Categorias categorias;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorias createEntity(EntityManager em) {
        Categorias categorias = new Categorias()
            .categoria(DEFAULT_CATEGORIA)
            .fecha(DEFAULT_FECHA);
        return categorias;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorias createUpdatedEntity(EntityManager em) {
        Categorias categorias = new Categorias()
            .categoria(UPDATED_CATEGORIA)
            .fecha(UPDATED_FECHA);
        return categorias;
    }

    @BeforeEach
    public void initTest() {
        categorias = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategorias() throws Exception {
        int databaseSizeBeforeCreate = categoriasRepository.findAll().size();

        // Create the Categorias
        restCategoriasMockMvc.perform(post("/api/categorias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorias)))
            .andExpect(status().isCreated());

        // Validate the Categorias in the database
        List<Categorias> categoriasList = categoriasRepository.findAll();
        assertThat(categoriasList).hasSize(databaseSizeBeforeCreate + 1);
        Categorias testCategorias = categoriasList.get(categoriasList.size() - 1);
        assertThat(testCategorias.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testCategorias.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createCategoriasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriasRepository.findAll().size();

        // Create the Categorias with an existing ID
        categorias.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriasMockMvc.perform(post("/api/categorias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorias)))
            .andExpect(status().isBadRequest());

        // Validate the Categorias in the database
        List<Categorias> categoriasList = categoriasRepository.findAll();
        assertThat(categoriasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCategorias() throws Exception {
        // Initialize the database
        categoriasRepository.saveAndFlush(categorias);

        // Get all the categoriasList
        restCategoriasMockMvc.perform(get("/api/categorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorias.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getCategorias() throws Exception {
        // Initialize the database
        categoriasRepository.saveAndFlush(categorias);

        // Get the categorias
        restCategoriasMockMvc.perform(get("/api/categorias/{id}", categorias.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorias.getId().intValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }


    @Test
    @Transactional
    public void getCategoriasByIdFiltering() throws Exception {
        // Initialize the database
        categoriasRepository.saveAndFlush(categorias);

        Long id = categorias.getId();

        defaultCategoriasShouldBeFound("id.equals=" + id);
        defaultCategoriasShouldNotBeFound("id.notEquals=" + id);

        defaultCategoriasShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoriasShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoriasShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoriasShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCategoriasByCategoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriasRepository.saveAndFlush(categorias);

        // Get all the categoriasList where categoria equals to DEFAULT_CATEGORIA
        defaultCategoriasShouldBeFound("categoria.equals=" + DEFAULT_CATEGORIA);

        // Get all the categoriasList where categoria equals to UPDATED_CATEGORIA
        defaultCategoriasShouldNotBeFound("categoria.equals=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriasByCategoriaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriasRepository.saveAndFlush(categorias);

        // Get all the categoriasList where categoria not equals to DEFAULT_CATEGORIA
        defaultCategoriasShouldNotBeFound("categoria.notEquals=" + DEFAULT_CATEGORIA);

        // Get all the categoriasList where categoria not equals to UPDATED_CATEGORIA
        defaultCategoriasShouldBeFound("categoria.notEquals=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriasByCategoriaIsInShouldWork() throws Exception {
        // Initialize the database
        categoriasRepository.saveAndFlush(categorias);

        // Get all the categoriasList where categoria in DEFAULT_CATEGORIA or UPDATED_CATEGORIA
        defaultCategoriasShouldBeFound("categoria.in=" + DEFAULT_CATEGORIA + "," + UPDATED_CATEGORIA);

        // Get all the categoriasList where categoria equals to UPDATED_CATEGORIA
        defaultCategoriasShouldNotBeFound("categoria.in=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriasByCategoriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriasRepository.saveAndFlush(categorias);

        // Get all the categoriasList where categoria is not null
        defaultCategoriasShouldBeFound("categoria.specified=true");

        // Get all the categoriasList where categoria is null
        defaultCategoriasShouldNotBeFound("categoria.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriasByCategoriaContainsSomething() throws Exception {
        // Initialize the database
        categoriasRepository.saveAndFlush(categorias);

        // Get all the categoriasList where categoria contains DEFAULT_CATEGORIA
        defaultCategoriasShouldBeFound("categoria.contains=" + DEFAULT_CATEGORIA);

        // Get all the categoriasList where categoria contains UPDATED_CATEGORIA
        defaultCategoriasShouldNotBeFound("categoria.contains=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriasByCategoriaNotContainsSomething() throws Exception {
        // Initialize the database
        categoriasRepository.saveAndFlush(categorias);

        // Get all the categoriasList where categoria does not contain DEFAULT_CATEGORIA
        defaultCategoriasShouldNotBeFound("categoria.doesNotContain=" + DEFAULT_CATEGORIA);

        // Get all the categoriasList where categoria does not contain UPDATED_CATEGORIA
        defaultCategoriasShouldBeFound("categoria.doesNotContain=" + UPDATED_CATEGORIA);
    }


    @Test
    @Transactional
    public void getAllCategoriasByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriasRepository.saveAndFlush(categorias);

        // Get all the categoriasList where fecha equals to DEFAULT_FECHA
        defaultCategoriasShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the categoriasList where fecha equals to UPDATED_FECHA
        defaultCategoriasShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllCategoriasByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriasRepository.saveAndFlush(categorias);

        // Get all the categoriasList where fecha not equals to DEFAULT_FECHA
        defaultCategoriasShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the categoriasList where fecha not equals to UPDATED_FECHA
        defaultCategoriasShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllCategoriasByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        categoriasRepository.saveAndFlush(categorias);

        // Get all the categoriasList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultCategoriasShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the categoriasList where fecha equals to UPDATED_FECHA
        defaultCategoriasShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllCategoriasByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriasRepository.saveAndFlush(categorias);

        // Get all the categoriasList where fecha is not null
        defaultCategoriasShouldBeFound("fecha.specified=true");

        // Get all the categoriasList where fecha is null
        defaultCategoriasShouldNotBeFound("fecha.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoriasShouldBeFound(String filter) throws Exception {
        restCategoriasMockMvc.perform(get("/api/categorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorias.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

        // Check, that the count call also returns 1
        restCategoriasMockMvc.perform(get("/api/categorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoriasShouldNotBeFound(String filter) throws Exception {
        restCategoriasMockMvc.perform(get("/api/categorias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoriasMockMvc.perform(get("/api/categorias/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCategorias() throws Exception {
        // Get the categorias
        restCategoriasMockMvc.perform(get("/api/categorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategorias() throws Exception {
        // Initialize the database
        categoriasService.save(categorias);

        int databaseSizeBeforeUpdate = categoriasRepository.findAll().size();

        // Update the categorias
        Categorias updatedCategorias = categoriasRepository.findById(categorias.getId()).get();
        // Disconnect from session so that the updates on updatedCategorias are not directly saved in db
        em.detach(updatedCategorias);
        updatedCategorias
            .categoria(UPDATED_CATEGORIA)
            .fecha(UPDATED_FECHA);

        restCategoriasMockMvc.perform(put("/api/categorias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategorias)))
            .andExpect(status().isOk());

        // Validate the Categorias in the database
        List<Categorias> categoriasList = categoriasRepository.findAll();
        assertThat(categoriasList).hasSize(databaseSizeBeforeUpdate);
        Categorias testCategorias = categoriasList.get(categoriasList.size() - 1);
        assertThat(testCategorias.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testCategorias.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingCategorias() throws Exception {
        int databaseSizeBeforeUpdate = categoriasRepository.findAll().size();

        // Create the Categorias

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriasMockMvc.perform(put("/api/categorias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorias)))
            .andExpect(status().isBadRequest());

        // Validate the Categorias in the database
        List<Categorias> categoriasList = categoriasRepository.findAll();
        assertThat(categoriasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategorias() throws Exception {
        // Initialize the database
        categoriasService.save(categorias);

        int databaseSizeBeforeDelete = categoriasRepository.findAll().size();

        // Delete the categorias
        restCategoriasMockMvc.perform(delete("/api/categorias/{id}", categorias.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Categorias> categoriasList = categoriasRepository.findAll();
        assertThat(categoriasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
