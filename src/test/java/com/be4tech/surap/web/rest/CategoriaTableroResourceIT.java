package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.CategoriaTablero;
import com.be4tech.surap.domain.Productos;
import com.be4tech.surap.repository.CategoriaTableroRepository;
import com.be4tech.surap.service.CategoriaTableroService;
import com.be4tech.surap.service.dto.CategoriaTableroCriteria;
import com.be4tech.surap.service.CategoriaTableroQueryService;

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
 * Integration tests for the {@link CategoriaTableroResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class CategoriaTableroResourceIT {

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CategoriaTableroRepository categoriaTableroRepository;

    @Autowired
    private CategoriaTableroService categoriaTableroService;

    @Autowired
    private CategoriaTableroQueryService categoriaTableroQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriaTableroMockMvc;

    private CategoriaTablero categoriaTablero;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaTablero createEntity(EntityManager em) {
        CategoriaTablero categoriaTablero = new CategoriaTablero()
            .categoria(DEFAULT_CATEGORIA)
            .fecha(DEFAULT_FECHA);
        return categoriaTablero;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaTablero createUpdatedEntity(EntityManager em) {
        CategoriaTablero categoriaTablero = new CategoriaTablero()
            .categoria(UPDATED_CATEGORIA)
            .fecha(UPDATED_FECHA);
        return categoriaTablero;
    }

    @BeforeEach
    public void initTest() {
        categoriaTablero = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoriaTablero() throws Exception {
        int databaseSizeBeforeCreate = categoriaTableroRepository.findAll().size();

        // Create the CategoriaTablero
        restCategoriaTableroMockMvc.perform(post("/api/categoria-tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriaTablero)))
            .andExpect(status().isCreated());

        // Validate the CategoriaTablero in the database
        List<CategoriaTablero> categoriaTableroList = categoriaTableroRepository.findAll();
        assertThat(categoriaTableroList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaTablero testCategoriaTablero = categoriaTableroList.get(categoriaTableroList.size() - 1);
        assertThat(testCategoriaTablero.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testCategoriaTablero.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createCategoriaTableroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriaTableroRepository.findAll().size();

        // Create the CategoriaTablero with an existing ID
        categoriaTablero.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaTableroMockMvc.perform(post("/api/categoria-tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriaTablero)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaTablero in the database
        List<CategoriaTablero> categoriaTableroList = categoriaTableroRepository.findAll();
        assertThat(categoriaTableroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCategoriaTableros() throws Exception {
        // Initialize the database
        categoriaTableroRepository.saveAndFlush(categoriaTablero);

        // Get all the categoriaTableroList
        restCategoriaTableroMockMvc.perform(get("/api/categoria-tableros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaTablero.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getCategoriaTablero() throws Exception {
        // Initialize the database
        categoriaTableroRepository.saveAndFlush(categoriaTablero);

        // Get the categoriaTablero
        restCategoriaTableroMockMvc.perform(get("/api/categoria-tableros/{id}", categoriaTablero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaTablero.getId().intValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }


    @Test
    @Transactional
    public void getCategoriaTablerosByIdFiltering() throws Exception {
        // Initialize the database
        categoriaTableroRepository.saveAndFlush(categoriaTablero);

        Long id = categoriaTablero.getId();

        defaultCategoriaTableroShouldBeFound("id.equals=" + id);
        defaultCategoriaTableroShouldNotBeFound("id.notEquals=" + id);

        defaultCategoriaTableroShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoriaTableroShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoriaTableroShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoriaTableroShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCategoriaTablerosByCategoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaTableroRepository.saveAndFlush(categoriaTablero);

        // Get all the categoriaTableroList where categoria equals to DEFAULT_CATEGORIA
        defaultCategoriaTableroShouldBeFound("categoria.equals=" + DEFAULT_CATEGORIA);

        // Get all the categoriaTableroList where categoria equals to UPDATED_CATEGORIA
        defaultCategoriaTableroShouldNotBeFound("categoria.equals=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriaTablerosByCategoriaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriaTableroRepository.saveAndFlush(categoriaTablero);

        // Get all the categoriaTableroList where categoria not equals to DEFAULT_CATEGORIA
        defaultCategoriaTableroShouldNotBeFound("categoria.notEquals=" + DEFAULT_CATEGORIA);

        // Get all the categoriaTableroList where categoria not equals to UPDATED_CATEGORIA
        defaultCategoriaTableroShouldBeFound("categoria.notEquals=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriaTablerosByCategoriaIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaTableroRepository.saveAndFlush(categoriaTablero);

        // Get all the categoriaTableroList where categoria in DEFAULT_CATEGORIA or UPDATED_CATEGORIA
        defaultCategoriaTableroShouldBeFound("categoria.in=" + DEFAULT_CATEGORIA + "," + UPDATED_CATEGORIA);

        // Get all the categoriaTableroList where categoria equals to UPDATED_CATEGORIA
        defaultCategoriaTableroShouldNotBeFound("categoria.in=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriaTablerosByCategoriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaTableroRepository.saveAndFlush(categoriaTablero);

        // Get all the categoriaTableroList where categoria is not null
        defaultCategoriaTableroShouldBeFound("categoria.specified=true");

        // Get all the categoriaTableroList where categoria is null
        defaultCategoriaTableroShouldNotBeFound("categoria.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriaTablerosByCategoriaContainsSomething() throws Exception {
        // Initialize the database
        categoriaTableroRepository.saveAndFlush(categoriaTablero);

        // Get all the categoriaTableroList where categoria contains DEFAULT_CATEGORIA
        defaultCategoriaTableroShouldBeFound("categoria.contains=" + DEFAULT_CATEGORIA);

        // Get all the categoriaTableroList where categoria contains UPDATED_CATEGORIA
        defaultCategoriaTableroShouldNotBeFound("categoria.contains=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriaTablerosByCategoriaNotContainsSomething() throws Exception {
        // Initialize the database
        categoriaTableroRepository.saveAndFlush(categoriaTablero);

        // Get all the categoriaTableroList where categoria does not contain DEFAULT_CATEGORIA
        defaultCategoriaTableroShouldNotBeFound("categoria.doesNotContain=" + DEFAULT_CATEGORIA);

        // Get all the categoriaTableroList where categoria does not contain UPDATED_CATEGORIA
        defaultCategoriaTableroShouldBeFound("categoria.doesNotContain=" + UPDATED_CATEGORIA);
    }


    @Test
    @Transactional
    public void getAllCategoriaTablerosByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaTableroRepository.saveAndFlush(categoriaTablero);

        // Get all the categoriaTableroList where fecha equals to DEFAULT_FECHA
        defaultCategoriaTableroShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the categoriaTableroList where fecha equals to UPDATED_FECHA
        defaultCategoriaTableroShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllCategoriaTablerosByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriaTableroRepository.saveAndFlush(categoriaTablero);

        // Get all the categoriaTableroList where fecha not equals to DEFAULT_FECHA
        defaultCategoriaTableroShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the categoriaTableroList where fecha not equals to UPDATED_FECHA
        defaultCategoriaTableroShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllCategoriaTablerosByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaTableroRepository.saveAndFlush(categoriaTablero);

        // Get all the categoriaTableroList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultCategoriaTableroShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the categoriaTableroList where fecha equals to UPDATED_FECHA
        defaultCategoriaTableroShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllCategoriaTablerosByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaTableroRepository.saveAndFlush(categoriaTablero);

        // Get all the categoriaTableroList where fecha is not null
        defaultCategoriaTableroShouldBeFound("fecha.specified=true");

        // Get all the categoriaTableroList where fecha is null
        defaultCategoriaTableroShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriaTablerosByProductoIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaTableroRepository.saveAndFlush(categoriaTablero);
        Productos productoId = ProductosResourceIT.createEntity(em);
        em.persist(productoId);
        em.flush();
        categoriaTablero.setProductoId(productoId);
        categoriaTableroRepository.saveAndFlush(categoriaTablero);
        Long productoIdId = productoId.getId();

        // Get all the categoriaTableroList where productoId equals to productoIdId
        defaultCategoriaTableroShouldBeFound("productoIdId.equals=" + productoIdId);

        // Get all the categoriaTableroList where productoId equals to productoIdId + 1
        defaultCategoriaTableroShouldNotBeFound("productoIdId.equals=" + (productoIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoriaTableroShouldBeFound(String filter) throws Exception {
        restCategoriaTableroMockMvc.perform(get("/api/categoria-tableros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaTablero.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

        // Check, that the count call also returns 1
        restCategoriaTableroMockMvc.perform(get("/api/categoria-tableros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoriaTableroShouldNotBeFound(String filter) throws Exception {
        restCategoriaTableroMockMvc.perform(get("/api/categoria-tableros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoriaTableroMockMvc.perform(get("/api/categoria-tableros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCategoriaTablero() throws Exception {
        // Get the categoriaTablero
        restCategoriaTableroMockMvc.perform(get("/api/categoria-tableros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoriaTablero() throws Exception {
        // Initialize the database
        categoriaTableroService.save(categoriaTablero);

        int databaseSizeBeforeUpdate = categoriaTableroRepository.findAll().size();

        // Update the categoriaTablero
        CategoriaTablero updatedCategoriaTablero = categoriaTableroRepository.findById(categoriaTablero.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaTablero are not directly saved in db
        em.detach(updatedCategoriaTablero);
        updatedCategoriaTablero
            .categoria(UPDATED_CATEGORIA)
            .fecha(UPDATED_FECHA);

        restCategoriaTableroMockMvc.perform(put("/api/categoria-tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategoriaTablero)))
            .andExpect(status().isOk());

        // Validate the CategoriaTablero in the database
        List<CategoriaTablero> categoriaTableroList = categoriaTableroRepository.findAll();
        assertThat(categoriaTableroList).hasSize(databaseSizeBeforeUpdate);
        CategoriaTablero testCategoriaTablero = categoriaTableroList.get(categoriaTableroList.size() - 1);
        assertThat(testCategoriaTablero.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testCategoriaTablero.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoriaTablero() throws Exception {
        int databaseSizeBeforeUpdate = categoriaTableroRepository.findAll().size();

        // Create the CategoriaTablero

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaTableroMockMvc.perform(put("/api/categoria-tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriaTablero)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaTablero in the database
        List<CategoriaTablero> categoriaTableroList = categoriaTableroRepository.findAll();
        assertThat(categoriaTableroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoriaTablero() throws Exception {
        // Initialize the database
        categoriaTableroService.save(categoriaTablero);

        int databaseSizeBeforeDelete = categoriaTableroRepository.findAll().size();

        // Delete the categoriaTablero
        restCategoriaTableroMockMvc.perform(delete("/api/categoria-tableros/{id}", categoriaTablero.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoriaTablero> categoriaTableroList = categoriaTableroRepository.findAll();
        assertThat(categoriaTableroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
