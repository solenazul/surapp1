package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.FavoritosServicios;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.domain.Servicios;
import com.be4tech.surap.repository.FavoritosServiciosRepository;
import com.be4tech.surap.service.FavoritosServiciosService;
import com.be4tech.surap.service.dto.FavoritosServiciosCriteria;
import com.be4tech.surap.service.FavoritosServiciosQueryService;

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
 * Integration tests for the {@link FavoritosServiciosResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class FavoritosServiciosResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA = LocalDate.ofEpochDay(-1L);

    @Autowired
    private FavoritosServiciosRepository favoritosServiciosRepository;

    @Autowired
    private FavoritosServiciosService favoritosServiciosService;

    @Autowired
    private FavoritosServiciosQueryService favoritosServiciosQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFavoritosServiciosMockMvc;

    private FavoritosServicios favoritosServicios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavoritosServicios createEntity(EntityManager em) {
        FavoritosServicios favoritosServicios = new FavoritosServicios()
            .fecha(DEFAULT_FECHA);
        return favoritosServicios;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FavoritosServicios createUpdatedEntity(EntityManager em) {
        FavoritosServicios favoritosServicios = new FavoritosServicios()
            .fecha(UPDATED_FECHA);
        return favoritosServicios;
    }

    @BeforeEach
    public void initTest() {
        favoritosServicios = createEntity(em);
    }

    @Test
    @Transactional
    public void createFavoritosServicios() throws Exception {
        int databaseSizeBeforeCreate = favoritosServiciosRepository.findAll().size();

        // Create the FavoritosServicios
        restFavoritosServiciosMockMvc.perform(post("/api/favoritos-servicios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(favoritosServicios)))
            .andExpect(status().isCreated());

        // Validate the FavoritosServicios in the database
        List<FavoritosServicios> favoritosServiciosList = favoritosServiciosRepository.findAll();
        assertThat(favoritosServiciosList).hasSize(databaseSizeBeforeCreate + 1);
        FavoritosServicios testFavoritosServicios = favoritosServiciosList.get(favoritosServiciosList.size() - 1);
        assertThat(testFavoritosServicios.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createFavoritosServiciosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = favoritosServiciosRepository.findAll().size();

        // Create the FavoritosServicios with an existing ID
        favoritosServicios.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFavoritosServiciosMockMvc.perform(post("/api/favoritos-servicios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(favoritosServicios)))
            .andExpect(status().isBadRequest());

        // Validate the FavoritosServicios in the database
        List<FavoritosServicios> favoritosServiciosList = favoritosServiciosRepository.findAll();
        assertThat(favoritosServiciosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFavoritosServicios() throws Exception {
        // Initialize the database
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);

        // Get all the favoritosServiciosList
        restFavoritosServiciosMockMvc.perform(get("/api/favoritos-servicios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favoritosServicios.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getFavoritosServicios() throws Exception {
        // Initialize the database
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);

        // Get the favoritosServicios
        restFavoritosServiciosMockMvc.perform(get("/api/favoritos-servicios/{id}", favoritosServicios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(favoritosServicios.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }


    @Test
    @Transactional
    public void getFavoritosServiciosByIdFiltering() throws Exception {
        // Initialize the database
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);

        Long id = favoritosServicios.getId();

        defaultFavoritosServiciosShouldBeFound("id.equals=" + id);
        defaultFavoritosServiciosShouldNotBeFound("id.notEquals=" + id);

        defaultFavoritosServiciosShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFavoritosServiciosShouldNotBeFound("id.greaterThan=" + id);

        defaultFavoritosServiciosShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFavoritosServiciosShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFavoritosServiciosByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);

        // Get all the favoritosServiciosList where fecha equals to DEFAULT_FECHA
        defaultFavoritosServiciosShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the favoritosServiciosList where fecha equals to UPDATED_FECHA
        defaultFavoritosServiciosShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFavoritosServiciosByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);

        // Get all the favoritosServiciosList where fecha not equals to DEFAULT_FECHA
        defaultFavoritosServiciosShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the favoritosServiciosList where fecha not equals to UPDATED_FECHA
        defaultFavoritosServiciosShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFavoritosServiciosByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);

        // Get all the favoritosServiciosList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultFavoritosServiciosShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the favoritosServiciosList where fecha equals to UPDATED_FECHA
        defaultFavoritosServiciosShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFavoritosServiciosByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);

        // Get all the favoritosServiciosList where fecha is not null
        defaultFavoritosServiciosShouldBeFound("fecha.specified=true");

        // Get all the favoritosServiciosList where fecha is null
        defaultFavoritosServiciosShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllFavoritosServiciosByFechaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);

        // Get all the favoritosServiciosList where fecha is greater than or equal to DEFAULT_FECHA
        defaultFavoritosServiciosShouldBeFound("fecha.greaterThanOrEqual=" + DEFAULT_FECHA);

        // Get all the favoritosServiciosList where fecha is greater than or equal to UPDATED_FECHA
        defaultFavoritosServiciosShouldNotBeFound("fecha.greaterThanOrEqual=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFavoritosServiciosByFechaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);

        // Get all the favoritosServiciosList where fecha is less than or equal to DEFAULT_FECHA
        defaultFavoritosServiciosShouldBeFound("fecha.lessThanOrEqual=" + DEFAULT_FECHA);

        // Get all the favoritosServiciosList where fecha is less than or equal to SMALLER_FECHA
        defaultFavoritosServiciosShouldNotBeFound("fecha.lessThanOrEqual=" + SMALLER_FECHA);
    }

    @Test
    @Transactional
    public void getAllFavoritosServiciosByFechaIsLessThanSomething() throws Exception {
        // Initialize the database
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);

        // Get all the favoritosServiciosList where fecha is less than DEFAULT_FECHA
        defaultFavoritosServiciosShouldNotBeFound("fecha.lessThan=" + DEFAULT_FECHA);

        // Get all the favoritosServiciosList where fecha is less than UPDATED_FECHA
        defaultFavoritosServiciosShouldBeFound("fecha.lessThan=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllFavoritosServiciosByFechaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);

        // Get all the favoritosServiciosList where fecha is greater than DEFAULT_FECHA
        defaultFavoritosServiciosShouldNotBeFound("fecha.greaterThan=" + DEFAULT_FECHA);

        // Get all the favoritosServiciosList where fecha is greater than SMALLER_FECHA
        defaultFavoritosServiciosShouldBeFound("fecha.greaterThan=" + SMALLER_FECHA);
    }


    @Test
    @Transactional
    public void getAllFavoritosServiciosByIdUserIsEqualToSomething() throws Exception {
        // Initialize the database
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);
        User idUser = UserResourceIT.createEntity(em);
        em.persist(idUser);
        em.flush();
        favoritosServicios.setIdUser(idUser);
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);
        Long idUserId = idUser.getId();

        // Get all the favoritosServiciosList where idUser equals to idUserId
        defaultFavoritosServiciosShouldBeFound("idUserId.equals=" + idUserId);

        // Get all the favoritosServiciosList where idUser equals to idUserId + 1
        defaultFavoritosServiciosShouldNotBeFound("idUserId.equals=" + (idUserId + 1));
    }


    @Test
    @Transactional
    public void getAllFavoritosServiciosByProductoIdIsEqualToSomething() throws Exception {
        // Initialize the database
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);
        Servicios productoId = ServiciosResourceIT.createEntity(em);
        em.persist(productoId);
        em.flush();
        favoritosServicios.setProductoId(productoId);
        favoritosServiciosRepository.saveAndFlush(favoritosServicios);
        Long productoIdId = productoId.getId();

        // Get all the favoritosServiciosList where productoId equals to productoIdId
        defaultFavoritosServiciosShouldBeFound("productoIdId.equals=" + productoIdId);

        // Get all the favoritosServiciosList where productoId equals to productoIdId + 1
        defaultFavoritosServiciosShouldNotBeFound("productoIdId.equals=" + (productoIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFavoritosServiciosShouldBeFound(String filter) throws Exception {
        restFavoritosServiciosMockMvc.perform(get("/api/favoritos-servicios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favoritosServicios.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

        // Check, that the count call also returns 1
        restFavoritosServiciosMockMvc.perform(get("/api/favoritos-servicios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFavoritosServiciosShouldNotBeFound(String filter) throws Exception {
        restFavoritosServiciosMockMvc.perform(get("/api/favoritos-servicios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFavoritosServiciosMockMvc.perform(get("/api/favoritos-servicios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFavoritosServicios() throws Exception {
        // Get the favoritosServicios
        restFavoritosServiciosMockMvc.perform(get("/api/favoritos-servicios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFavoritosServicios() throws Exception {
        // Initialize the database
        favoritosServiciosService.save(favoritosServicios);

        int databaseSizeBeforeUpdate = favoritosServiciosRepository.findAll().size();

        // Update the favoritosServicios
        FavoritosServicios updatedFavoritosServicios = favoritosServiciosRepository.findById(favoritosServicios.getId()).get();
        // Disconnect from session so that the updates on updatedFavoritosServicios are not directly saved in db
        em.detach(updatedFavoritosServicios);
        updatedFavoritosServicios
            .fecha(UPDATED_FECHA);

        restFavoritosServiciosMockMvc.perform(put("/api/favoritos-servicios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFavoritosServicios)))
            .andExpect(status().isOk());

        // Validate the FavoritosServicios in the database
        List<FavoritosServicios> favoritosServiciosList = favoritosServiciosRepository.findAll();
        assertThat(favoritosServiciosList).hasSize(databaseSizeBeforeUpdate);
        FavoritosServicios testFavoritosServicios = favoritosServiciosList.get(favoritosServiciosList.size() - 1);
        assertThat(testFavoritosServicios.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingFavoritosServicios() throws Exception {
        int databaseSizeBeforeUpdate = favoritosServiciosRepository.findAll().size();

        // Create the FavoritosServicios

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFavoritosServiciosMockMvc.perform(put("/api/favoritos-servicios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(favoritosServicios)))
            .andExpect(status().isBadRequest());

        // Validate the FavoritosServicios in the database
        List<FavoritosServicios> favoritosServiciosList = favoritosServiciosRepository.findAll();
        assertThat(favoritosServiciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFavoritosServicios() throws Exception {
        // Initialize the database
        favoritosServiciosService.save(favoritosServicios);

        int databaseSizeBeforeDelete = favoritosServiciosRepository.findAll().size();

        // Delete the favoritosServicios
        restFavoritosServiciosMockMvc.perform(delete("/api/favoritos-servicios/{id}", favoritosServicios.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FavoritosServicios> favoritosServiciosList = favoritosServiciosRepository.findAll();
        assertThat(favoritosServiciosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
