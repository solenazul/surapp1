package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.CategoriaProducto;
import com.be4tech.surap.domain.Categorias;
import com.be4tech.surap.domain.Productos;
import com.be4tech.surap.repository.CategoriaProductoRepository;
import com.be4tech.surap.service.CategoriaProductoService;
import com.be4tech.surap.service.dto.CategoriaProductoCriteria;
import com.be4tech.surap.service.CategoriaProductoQueryService;

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
 * Integration tests for the {@link CategoriaProductoResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class CategoriaProductoResourceIT {

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    @Autowired
    private CategoriaProductoService categoriaProductoService;

    @Autowired
    private CategoriaProductoQueryService categoriaProductoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriaProductoMockMvc;

    private CategoriaProducto categoriaProducto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaProducto createEntity(EntityManager em) {
        CategoriaProducto categoriaProducto = new CategoriaProducto()
            .categoria(DEFAULT_CATEGORIA)
            .fecha(DEFAULT_FECHA);
        return categoriaProducto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategoriaProducto createUpdatedEntity(EntityManager em) {
        CategoriaProducto categoriaProducto = new CategoriaProducto()
            .categoria(UPDATED_CATEGORIA)
            .fecha(UPDATED_FECHA);
        return categoriaProducto;
    }

    @BeforeEach
    public void initTest() {
        categoriaProducto = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoriaProducto() throws Exception {
        int databaseSizeBeforeCreate = categoriaProductoRepository.findAll().size();

        // Create the CategoriaProducto
        restCategoriaProductoMockMvc.perform(post("/api/categoria-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriaProducto)))
            .andExpect(status().isCreated());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaProducto testCategoriaProducto = categoriaProductoList.get(categoriaProductoList.size() - 1);
        assertThat(testCategoriaProducto.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testCategoriaProducto.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createCategoriaProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriaProductoRepository.findAll().size();

        // Create the CategoriaProducto with an existing ID
        categoriaProducto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaProductoMockMvc.perform(post("/api/categoria-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriaProducto)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCategoriaProductos() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get all the categoriaProductoList
        restCategoriaProductoMockMvc.perform(get("/api/categoria-productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getCategoriaProducto() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get the categoriaProducto
        restCategoriaProductoMockMvc.perform(get("/api/categoria-productos/{id}", categoriaProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaProducto.getId().intValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }


    @Test
    @Transactional
    public void getCategoriaProductosByIdFiltering() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        Long id = categoriaProducto.getId();

        defaultCategoriaProductoShouldBeFound("id.equals=" + id);
        defaultCategoriaProductoShouldNotBeFound("id.notEquals=" + id);

        defaultCategoriaProductoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoriaProductoShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoriaProductoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoriaProductoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCategoriaProductosByCategoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get all the categoriaProductoList where categoria equals to DEFAULT_CATEGORIA
        defaultCategoriaProductoShouldBeFound("categoria.equals=" + DEFAULT_CATEGORIA);

        // Get all the categoriaProductoList where categoria equals to UPDATED_CATEGORIA
        defaultCategoriaProductoShouldNotBeFound("categoria.equals=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriaProductosByCategoriaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get all the categoriaProductoList where categoria not equals to DEFAULT_CATEGORIA
        defaultCategoriaProductoShouldNotBeFound("categoria.notEquals=" + DEFAULT_CATEGORIA);

        // Get all the categoriaProductoList where categoria not equals to UPDATED_CATEGORIA
        defaultCategoriaProductoShouldBeFound("categoria.notEquals=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriaProductosByCategoriaIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get all the categoriaProductoList where categoria in DEFAULT_CATEGORIA or UPDATED_CATEGORIA
        defaultCategoriaProductoShouldBeFound("categoria.in=" + DEFAULT_CATEGORIA + "," + UPDATED_CATEGORIA);

        // Get all the categoriaProductoList where categoria equals to UPDATED_CATEGORIA
        defaultCategoriaProductoShouldNotBeFound("categoria.in=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriaProductosByCategoriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get all the categoriaProductoList where categoria is not null
        defaultCategoriaProductoShouldBeFound("categoria.specified=true");

        // Get all the categoriaProductoList where categoria is null
        defaultCategoriaProductoShouldNotBeFound("categoria.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriaProductosByCategoriaContainsSomething() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get all the categoriaProductoList where categoria contains DEFAULT_CATEGORIA
        defaultCategoriaProductoShouldBeFound("categoria.contains=" + DEFAULT_CATEGORIA);

        // Get all the categoriaProductoList where categoria contains UPDATED_CATEGORIA
        defaultCategoriaProductoShouldNotBeFound("categoria.contains=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void getAllCategoriaProductosByCategoriaNotContainsSomething() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get all the categoriaProductoList where categoria does not contain DEFAULT_CATEGORIA
        defaultCategoriaProductoShouldNotBeFound("categoria.doesNotContain=" + DEFAULT_CATEGORIA);

        // Get all the categoriaProductoList where categoria does not contain UPDATED_CATEGORIA
        defaultCategoriaProductoShouldBeFound("categoria.doesNotContain=" + UPDATED_CATEGORIA);
    }


    @Test
    @Transactional
    public void getAllCategoriaProductosByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get all the categoriaProductoList where fecha equals to DEFAULT_FECHA
        defaultCategoriaProductoShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the categoriaProductoList where fecha equals to UPDATED_FECHA
        defaultCategoriaProductoShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllCategoriaProductosByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get all the categoriaProductoList where fecha not equals to DEFAULT_FECHA
        defaultCategoriaProductoShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the categoriaProductoList where fecha not equals to UPDATED_FECHA
        defaultCategoriaProductoShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllCategoriaProductosByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get all the categoriaProductoList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultCategoriaProductoShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the categoriaProductoList where fecha equals to UPDATED_FECHA
        defaultCategoriaProductoShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllCategoriaProductosByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);

        // Get all the categoriaProductoList where fecha is not null
        defaultCategoriaProductoShouldBeFound("fecha.specified=true");

        // Get all the categoriaProductoList where fecha is null
        defaultCategoriaProductoShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriaProductosByCatagoriaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);
        Categorias catagoriaId = CategoriasResourceIT.createEntity(em);
        em.persist(catagoriaId);
        em.flush();
        categoriaProducto.setCatagoriaId(catagoriaId);
        categoriaProductoRepository.saveAndFlush(categoriaProducto);
        Long catagoriaIdId = catagoriaId.getId();

        // Get all the categoriaProductoList where catagoriaId equals to catagoriaIdId
        defaultCategoriaProductoShouldBeFound("catagoriaIdId.equals=" + catagoriaIdId);

        // Get all the categoriaProductoList where catagoriaId equals to catagoriaIdId + 1
        defaultCategoriaProductoShouldNotBeFound("catagoriaIdId.equals=" + (catagoriaIdId + 1));
    }


    @Test
    @Transactional
    public void getAllCategoriaProductosByProductoIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoriaProductoRepository.saveAndFlush(categoriaProducto);
        Productos productoId = ProductosResourceIT.createEntity(em);
        em.persist(productoId);
        em.flush();
        categoriaProducto.setProductoId(productoId);
        categoriaProductoRepository.saveAndFlush(categoriaProducto);
        Long productoIdId = productoId.getId();

        // Get all the categoriaProductoList where productoId equals to productoIdId
        defaultCategoriaProductoShouldBeFound("productoIdId.equals=" + productoIdId);

        // Get all the categoriaProductoList where productoId equals to productoIdId + 1
        defaultCategoriaProductoShouldNotBeFound("productoIdId.equals=" + (productoIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoriaProductoShouldBeFound(String filter) throws Exception {
        restCategoriaProductoMockMvc.perform(get("/api/categoria-productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

        // Check, that the count call also returns 1
        restCategoriaProductoMockMvc.perform(get("/api/categoria-productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoriaProductoShouldNotBeFound(String filter) throws Exception {
        restCategoriaProductoMockMvc.perform(get("/api/categoria-productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoriaProductoMockMvc.perform(get("/api/categoria-productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCategoriaProducto() throws Exception {
        // Get the categoriaProducto
        restCategoriaProductoMockMvc.perform(get("/api/categoria-productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoriaProducto() throws Exception {
        // Initialize the database
        categoriaProductoService.save(categoriaProducto);

        int databaseSizeBeforeUpdate = categoriaProductoRepository.findAll().size();

        // Update the categoriaProducto
        CategoriaProducto updatedCategoriaProducto = categoriaProductoRepository.findById(categoriaProducto.getId()).get();
        // Disconnect from session so that the updates on updatedCategoriaProducto are not directly saved in db
        em.detach(updatedCategoriaProducto);
        updatedCategoriaProducto
            .categoria(UPDATED_CATEGORIA)
            .fecha(UPDATED_FECHA);

        restCategoriaProductoMockMvc.perform(put("/api/categoria-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategoriaProducto)))
            .andExpect(status().isOk());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaProducto testCategoriaProducto = categoriaProductoList.get(categoriaProductoList.size() - 1);
        assertThat(testCategoriaProducto.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testCategoriaProducto.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoriaProducto() throws Exception {
        int databaseSizeBeforeUpdate = categoriaProductoRepository.findAll().size();

        // Create the CategoriaProducto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriaProductoMockMvc.perform(put("/api/categoria-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categoriaProducto)))
            .andExpect(status().isBadRequest());

        // Validate the CategoriaProducto in the database
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategoriaProducto() throws Exception {
        // Initialize the database
        categoriaProductoService.save(categoriaProducto);

        int databaseSizeBeforeDelete = categoriaProductoRepository.findAll().size();

        // Delete the categoriaProducto
        restCategoriaProductoMockMvc.perform(delete("/api/categoria-productos/{id}", categoriaProducto.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategoriaProducto> categoriaProductoList = categoriaProductoRepository.findAll();
        assertThat(categoriaProductoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
