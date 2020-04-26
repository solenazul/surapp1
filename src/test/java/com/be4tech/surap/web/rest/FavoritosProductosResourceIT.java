package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.FavoritosProductos;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.domain.Productos;
import com.be4tech.surap.repository.FavoritosProductosRepository;
import com.be4tech.surap.service.FavoritosProductosService;
import com.be4tech.surap.service.dto.FavoritosProductosCriteria;
import com.be4tech.surap.service.FavoritosProductosQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FavoritosProductosResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class FavoritosProductosResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA = LocalDate.ofEpochDay(-1L);

    @Autowired
    private FavoritosProductosRepository favoritosProductosRepository;

    @Autowired
    private FavoritosProductosService favoritosProductosService;

    @Autowired
    private FavoritosProductosQueryService favoritosProductosQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFavoritosProductosMockMvc;

    private FavoritosProductos favoritosProductos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavoritosProductos createEntity(EntityManager em) {
        FavoritosProductos favoritosProductos = new FavoritosProductos()
            .fecha(DEFAULT_FECHA);
        return favoritosProductos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavoritosProductos createUpdatedEntity(EntityManager em) {
        FavoritosProductos favoritosProductos = new FavoritosProductos()
            .fecha(UPDATED_FECHA);
        return favoritosProductos;
    }

    @BeforeEach
    public void initTest() {
        favoritosProductos = createEntity(em);
    }

    @Test
    @Transactional
    public void createFavoritosProductos() throws Exception {
        int databaseSizeBeforeCreate = favoritosProductosRepository.findAll().size();

        // Create the FavoritosProductos
        restFavoritosProductosMockMvc.perform(post("/api/favoritos-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(favoritosProductos)))
            .andExpect(status().isCreated());

        // Validate the FavoritosProductos in the database
        List<FavoritosProductos> favoritosProductosList = favoritosProductosRepository.findAll();
        assertThat(favoritosProductosList).hasSize(databaseSizeBeforeCreate + 1);
        FavoritosProductos testFavoritosProductos = favoritosProductosList.get(favoritosProductosList.size() - 1);
        assertThat(testFavoritosProductos.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createFavoritosProductosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = favoritosProductosRepository.findAll().size();

        // Create the FavoritosProductos with an existing ID
        favoritosProductos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFavoritosProductosMockMvc.perform(post("/api/favoritos-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(favoritosProductos)))
            .andExpect(status().isBadRequest());

        // Validate the FavoritosProductos in the database
        List<FavoritosProductos> favoritosProductosList = favoritosProductosRepository.findAll();
        assertThat(favoritosProductosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFavoritosProductos() throws Exception {
        // Initialize the database
        favoritosProductosRepository.saveAndFlush(favoritosProductos);

        // Get all the favoritosProductosList
        restFavoritosProductosMockMvc.perform(get("/api/favoritos-productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favoritosProductos.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getFavoritosProductos() throws Exception {
        // Initialize the database
        favoritosProductosRepository.saveAndFlush(favoritosProductos);

        // Get the favoritosProductos
        restFavoritosProductosMockMvc.perform(get("/api/favoritos-productos/{id}", favoritosProductos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(favoritosProductos.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }


    @Test
    @Transactional
    public void getFavoritosProductosByIdFiltering() throws Exception {
        // Initialize the database
        favoritosProductosRepository.saveAndFlush(favoritosProductos);

        Long id = favoritosProductos.getId();

        defaultFavoritosProductosShouldBeFound("id.equals=" + id);
        defaultFavoritosProductosShouldNotBeFound("id.notEquals=" + id);

        defaultFavoritosProductosShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFavoritosProductosShouldNotBeFound("id.greaterThan=" + id);

        defaultFavoritosProductosShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFavoritosProductosShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFavoritosProductosByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        favoritosProductosRepository.saveAndFlush(favoritosProductos);

        // Get all the favoritosProductosList where fecha equals to DEFAULT_FECHA
        defaultFavoritosProductosShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the favoritosProductosList where fecha equals to UPDATED_FECHA
        defaultFavoritosProductosShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFavoritosProductosByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        favoritosProductosRepository.saveAndFlush(favoritosProductos);

        // Get all the favoritosProductosList where fecha not equals to DEFAULT_FECHA
        defaultFavoritosProductosShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the favoritosProductosList where fecha not equals to UPDATED_FECHA
        defaultFavoritosProductosShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFavoritosProductosByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        favoritosProductosRepository.saveAndFlush(favoritosProductos);

        // Get all the favoritosProductosList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultFavoritosProductosShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the favoritosProductosList where fecha equals to UPDATED_FECHA
        defaultFavoritosProductosShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFavoritosProductosByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        favoritosProductosRepository.saveAndFlush(favoritosProductos);

        // Get all the favoritosProductosList where fecha is not null
        defaultFavoritosProductosShouldBeFound("fecha.specified=true");

        // Get all the favoritosProductosList where fecha is null
        defaultFavoritosProductosShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllFavoritosProductosByFechaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        favoritosProductosRepository.saveAndFlush(favoritosProductos);

        // Get all the favoritosProductosList where fecha is greater than or equal to DEFAULT_FECHA
        defaultFavoritosProductosShouldBeFound("fecha.greaterThanOrEqual=" + DEFAULT_FECHA);

        // Get all the favoritosProductosList where fecha is greater than or equal to UPDATED_FECHA
        defaultFavoritosProductosShouldNotBeFound("fecha.greaterThanOrEqual=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFavoritosProductosByFechaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        favoritosProductosRepository.saveAndFlush(favoritosProductos);

        // Get all the favoritosProductosList where fecha is less than or equal to DEFAULT_FECHA
        defaultFavoritosProductosShouldBeFound("fecha.lessThanOrEqual=" + DEFAULT_FECHA);

        // Get all the favoritosProductosList where fecha is less than or equal to SMALLER_FECHA
        defaultFavoritosProductosShouldNotBeFound("fecha.lessThanOrEqual=" + SMALLER_FECHA);
    }

    @Test
    @Transactional
    public void getAllFavoritosProductosByFechaIsLessThanSomething() throws Exception {
        // Initialize the database
        favoritosProductosRepository.saveAndFlush(favoritosProductos);

        // Get all the favoritosProductosList where fecha is less than DEFAULT_FECHA
        defaultFavoritosProductosShouldNotBeFound("fecha.lessThan=" + DEFAULT_FECHA);

        // Get all the favoritosProductosList where fecha is less than UPDATED_FECHA
        defaultFavoritosProductosShouldBeFound("fecha.lessThan=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFavoritosProductosByFechaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        favoritosProductosRepository.saveAndFlush(favoritosProductos);

        // Get all the favoritosProductosList where fecha is greater than DEFAULT_FECHA
        defaultFavoritosProductosShouldNotBeFound("fecha.greaterThan=" + DEFAULT_FECHA);

        // Get all the favoritosProductosList where fecha is greater than SMALLER_FECHA
        defaultFavoritosProductosShouldBeFound("fecha.greaterThan=" + SMALLER_FECHA);
    }


    @Test
    @Transactional
    public void getAllFavoritosProductosByIdUserIsEqualToSomething() throws Exception {
        // Initialize the database
        favoritosProductosRepository.saveAndFlush(favoritosProductos);
        User idUser = UserResourceIT.createEntity(em);
        em.persist(idUser);
        em.flush();
        favoritosProductos.setIdUser(idUser);
        favoritosProductosRepository.saveAndFlush(favoritosProductos);
        Long idUserId = idUser.getId();

        // Get all the favoritosProductosList where idUser equals to idUserId
        defaultFavoritosProductosShouldBeFound("idUserId.equals=" + idUserId);

        // Get all the favoritosProductosList where idUser equals to idUserId + 1
        defaultFavoritosProductosShouldNotBeFound("idUserId.equals=" + (idUserId + 1));
    }


    @Test
    @Transactional
    public void getAllFavoritosProductosByProductoIdIsEqualToSomething() throws Exception {
        // Initialize the database
        favoritosProductosRepository.saveAndFlush(favoritosProductos);
        Productos productoId = ProductosResourceIT.createEntity(em);
        em.persist(productoId);
        em.flush();
        favoritosProductos.setProductoId(productoId);
        favoritosProductosRepository.saveAndFlush(favoritosProductos);
        Long productoIdId = productoId.getId();

        // Get all the favoritosProductosList where productoId equals to productoIdId
        defaultFavoritosProductosShouldBeFound("productoIdId.equals=" + productoIdId);

        // Get all the favoritosProductosList where productoId equals to productoIdId + 1
        defaultFavoritosProductosShouldNotBeFound("productoIdId.equals=" + (productoIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFavoritosProductosShouldBeFound(String filter) throws Exception {
        restFavoritosProductosMockMvc.perform(get("/api/favoritos-productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favoritosProductos.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

        // Check, that the count call also returns 1
        restFavoritosProductosMockMvc.perform(get("/api/favoritos-productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFavoritosProductosShouldNotBeFound(String filter) throws Exception {
        restFavoritosProductosMockMvc.perform(get("/api/favoritos-productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFavoritosProductosMockMvc.perform(get("/api/favoritos-productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFavoritosProductos() throws Exception {
        // Get the favoritosProductos
        restFavoritosProductosMockMvc.perform(get("/api/favoritos-productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFavoritosProductos() throws Exception {
        // Initialize the database
        favoritosProductosService.save(favoritosProductos);

        int databaseSizeBeforeUpdate = favoritosProductosRepository.findAll().size();

        // Update the favoritosProductos
        FavoritosProductos updatedFavoritosProductos = favoritosProductosRepository.findById(favoritosProductos.getId()).get();
        // Disconnect from session so that the updates on updatedFavoritosProductos are not directly saved in db
        em.detach(updatedFavoritosProductos);
        updatedFavoritosProductos
            .fecha(UPDATED_FECHA);

        restFavoritosProductosMockMvc.perform(put("/api/favoritos-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFavoritosProductos)))
            .andExpect(status().isOk());

        // Validate the FavoritosProductos in the database
        List<FavoritosProductos> favoritosProductosList = favoritosProductosRepository.findAll();
        assertThat(favoritosProductosList).hasSize(databaseSizeBeforeUpdate);
        FavoritosProductos testFavoritosProductos = favoritosProductosList.get(favoritosProductosList.size() - 1);
        assertThat(testFavoritosProductos.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingFavoritosProductos() throws Exception {
        int databaseSizeBeforeUpdate = favoritosProductosRepository.findAll().size();

        // Create the FavoritosProductos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFavoritosProductosMockMvc.perform(put("/api/favoritos-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(favoritosProductos)))
            .andExpect(status().isBadRequest());

        // Validate the FavoritosProductos in the database
        List<FavoritosProductos> favoritosProductosList = favoritosProductosRepository.findAll();
        assertThat(favoritosProductosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFavoritosProductos() throws Exception {
        // Initialize the database
        favoritosProductosService.save(favoritosProductos);

        int databaseSizeBeforeDelete = favoritosProductosRepository.findAll().size();

        // Delete the favoritosProductos
        restFavoritosProductosMockMvc.perform(delete("/api/favoritos-productos/{id}", favoritosProductos.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FavoritosProductos> favoritosProductosList = favoritosProductosRepository.findAll();
        assertThat(favoritosProductosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
