package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.Tableros;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.domain.CategoriaTablero;
import com.be4tech.surap.repository.TablerosRepository;
import com.be4tech.surap.service.TablerosService;
import com.be4tech.surap.service.dto.TablerosCriteria;
import com.be4tech.surap.service.TablerosQueryService;

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
 * Integration tests for the {@link TablerosResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class TablerosResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_PRIVADO = false;
    private static final Boolean UPDATED_PRIVADO = true;

    private static final Integer DEFAULT_CALIFICACION = 1;
    private static final Integer UPDATED_CALIFICACION = 2;
    private static final Integer SMALLER_CALIFICACION = 1 - 1;

    @Autowired
    private TablerosRepository tablerosRepository;

    @Autowired
    private TablerosService tablerosService;

    @Autowired
    private TablerosQueryService tablerosQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTablerosMockMvc;

    private Tableros tableros;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tableros createEntity(EntityManager em) {
        Tableros tableros = new Tableros()
            .nombre(DEFAULT_NOMBRE)
            .fecha(DEFAULT_FECHA)
            .privado(DEFAULT_PRIVADO)
            .calificacion(DEFAULT_CALIFICACION);
        return tableros;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tableros createUpdatedEntity(EntityManager em) {
        Tableros tableros = new Tableros()
            .nombre(UPDATED_NOMBRE)
            .fecha(UPDATED_FECHA)
            .privado(UPDATED_PRIVADO)
            .calificacion(UPDATED_CALIFICACION);
        return tableros;
    }

    @BeforeEach
    public void initTest() {
        tableros = createEntity(em);
    }

    @Test
    @Transactional
    public void createTableros() throws Exception {
        int databaseSizeBeforeCreate = tablerosRepository.findAll().size();

        // Create the Tableros
        restTablerosMockMvc.perform(post("/api/tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tableros)))
            .andExpect(status().isCreated());

        // Validate the Tableros in the database
        List<Tableros> tablerosList = tablerosRepository.findAll();
        assertThat(tablerosList).hasSize(databaseSizeBeforeCreate + 1);
        Tableros testTableros = tablerosList.get(tablerosList.size() - 1);
        assertThat(testTableros.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTableros.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testTableros.isPrivado()).isEqualTo(DEFAULT_PRIVADO);
        assertThat(testTableros.getCalificacion()).isEqualTo(DEFAULT_CALIFICACION);
    }

    @Test
    @Transactional
    public void createTablerosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tablerosRepository.findAll().size();

        // Create the Tableros with an existing ID
        tableros.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTablerosMockMvc.perform(post("/api/tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tableros)))
            .andExpect(status().isBadRequest());

        // Validate the Tableros in the database
        List<Tableros> tablerosList = tablerosRepository.findAll();
        assertThat(tablerosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTableros() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList
        restTablerosMockMvc.perform(get("/api/tableros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tableros.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].privado").value(hasItem(DEFAULT_PRIVADO.booleanValue())))
            .andExpect(jsonPath("$.[*].calificacion").value(hasItem(DEFAULT_CALIFICACION)));
    }
    
    @Test
    @Transactional
    public void getTableros() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get the tableros
        restTablerosMockMvc.perform(get("/api/tableros/{id}", tableros.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tableros.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.privado").value(DEFAULT_PRIVADO.booleanValue()))
            .andExpect(jsonPath("$.calificacion").value(DEFAULT_CALIFICACION));
    }


    @Test
    @Transactional
    public void getTablerosByIdFiltering() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        Long id = tableros.getId();

        defaultTablerosShouldBeFound("id.equals=" + id);
        defaultTablerosShouldNotBeFound("id.notEquals=" + id);

        defaultTablerosShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTablerosShouldNotBeFound("id.greaterThan=" + id);

        defaultTablerosShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTablerosShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTablerosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where nombre equals to DEFAULT_NOMBRE
        defaultTablerosShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the tablerosList where nombre equals to UPDATED_NOMBRE
        defaultTablerosShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTablerosByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where nombre not equals to DEFAULT_NOMBRE
        defaultTablerosShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the tablerosList where nombre not equals to UPDATED_NOMBRE
        defaultTablerosShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTablerosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultTablerosShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the tablerosList where nombre equals to UPDATED_NOMBRE
        defaultTablerosShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTablerosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where nombre is not null
        defaultTablerosShouldBeFound("nombre.specified=true");

        // Get all the tablerosList where nombre is null
        defaultTablerosShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllTablerosByNombreContainsSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where nombre contains DEFAULT_NOMBRE
        defaultTablerosShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the tablerosList where nombre contains UPDATED_NOMBRE
        defaultTablerosShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTablerosByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where nombre does not contain DEFAULT_NOMBRE
        defaultTablerosShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the tablerosList where nombre does not contain UPDATED_NOMBRE
        defaultTablerosShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllTablerosByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where fecha equals to DEFAULT_FECHA
        defaultTablerosShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the tablerosList where fecha equals to UPDATED_FECHA
        defaultTablerosShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllTablerosByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where fecha not equals to DEFAULT_FECHA
        defaultTablerosShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the tablerosList where fecha not equals to UPDATED_FECHA
        defaultTablerosShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllTablerosByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultTablerosShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the tablerosList where fecha equals to UPDATED_FECHA
        defaultTablerosShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllTablerosByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where fecha is not null
        defaultTablerosShouldBeFound("fecha.specified=true");

        // Get all the tablerosList where fecha is null
        defaultTablerosShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllTablerosByPrivadoIsEqualToSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where privado equals to DEFAULT_PRIVADO
        defaultTablerosShouldBeFound("privado.equals=" + DEFAULT_PRIVADO);

        // Get all the tablerosList where privado equals to UPDATED_PRIVADO
        defaultTablerosShouldNotBeFound("privado.equals=" + UPDATED_PRIVADO);
    }

    @Test
    @Transactional
    public void getAllTablerosByPrivadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where privado not equals to DEFAULT_PRIVADO
        defaultTablerosShouldNotBeFound("privado.notEquals=" + DEFAULT_PRIVADO);

        // Get all the tablerosList where privado not equals to UPDATED_PRIVADO
        defaultTablerosShouldBeFound("privado.notEquals=" + UPDATED_PRIVADO);
    }

    @Test
    @Transactional
    public void getAllTablerosByPrivadoIsInShouldWork() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where privado in DEFAULT_PRIVADO or UPDATED_PRIVADO
        defaultTablerosShouldBeFound("privado.in=" + DEFAULT_PRIVADO + "," + UPDATED_PRIVADO);

        // Get all the tablerosList where privado equals to UPDATED_PRIVADO
        defaultTablerosShouldNotBeFound("privado.in=" + UPDATED_PRIVADO);
    }

    @Test
    @Transactional
    public void getAllTablerosByPrivadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where privado is not null
        defaultTablerosShouldBeFound("privado.specified=true");

        // Get all the tablerosList where privado is null
        defaultTablerosShouldNotBeFound("privado.specified=false");
    }

    @Test
    @Transactional
    public void getAllTablerosByCalificacionIsEqualToSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where calificacion equals to DEFAULT_CALIFICACION
        defaultTablerosShouldBeFound("calificacion.equals=" + DEFAULT_CALIFICACION);

        // Get all the tablerosList where calificacion equals to UPDATED_CALIFICACION
        defaultTablerosShouldNotBeFound("calificacion.equals=" + UPDATED_CALIFICACION);
    }

    @Test
    @Transactional
    public void getAllTablerosByCalificacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where calificacion not equals to DEFAULT_CALIFICACION
        defaultTablerosShouldNotBeFound("calificacion.notEquals=" + DEFAULT_CALIFICACION);

        // Get all the tablerosList where calificacion not equals to UPDATED_CALIFICACION
        defaultTablerosShouldBeFound("calificacion.notEquals=" + UPDATED_CALIFICACION);
    }

    @Test
    @Transactional
    public void getAllTablerosByCalificacionIsInShouldWork() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where calificacion in DEFAULT_CALIFICACION or UPDATED_CALIFICACION
        defaultTablerosShouldBeFound("calificacion.in=" + DEFAULT_CALIFICACION + "," + UPDATED_CALIFICACION);

        // Get all the tablerosList where calificacion equals to UPDATED_CALIFICACION
        defaultTablerosShouldNotBeFound("calificacion.in=" + UPDATED_CALIFICACION);
    }

    @Test
    @Transactional
    public void getAllTablerosByCalificacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where calificacion is not null
        defaultTablerosShouldBeFound("calificacion.specified=true");

        // Get all the tablerosList where calificacion is null
        defaultTablerosShouldNotBeFound("calificacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllTablerosByCalificacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where calificacion is greater than or equal to DEFAULT_CALIFICACION
        defaultTablerosShouldBeFound("calificacion.greaterThanOrEqual=" + DEFAULT_CALIFICACION);

        // Get all the tablerosList where calificacion is greater than or equal to UPDATED_CALIFICACION
        defaultTablerosShouldNotBeFound("calificacion.greaterThanOrEqual=" + UPDATED_CALIFICACION);
    }

    @Test
    @Transactional
    public void getAllTablerosByCalificacionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where calificacion is less than or equal to DEFAULT_CALIFICACION
        defaultTablerosShouldBeFound("calificacion.lessThanOrEqual=" + DEFAULT_CALIFICACION);

        // Get all the tablerosList where calificacion is less than or equal to SMALLER_CALIFICACION
        defaultTablerosShouldNotBeFound("calificacion.lessThanOrEqual=" + SMALLER_CALIFICACION);
    }

    @Test
    @Transactional
    public void getAllTablerosByCalificacionIsLessThanSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where calificacion is less than DEFAULT_CALIFICACION
        defaultTablerosShouldNotBeFound("calificacion.lessThan=" + DEFAULT_CALIFICACION);

        // Get all the tablerosList where calificacion is less than UPDATED_CALIFICACION
        defaultTablerosShouldBeFound("calificacion.lessThan=" + UPDATED_CALIFICACION);
    }

    @Test
    @Transactional
    public void getAllTablerosByCalificacionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);

        // Get all the tablerosList where calificacion is greater than DEFAULT_CALIFICACION
        defaultTablerosShouldNotBeFound("calificacion.greaterThan=" + DEFAULT_CALIFICACION);

        // Get all the tablerosList where calificacion is greater than SMALLER_CALIFICACION
        defaultTablerosShouldBeFound("calificacion.greaterThan=" + SMALLER_CALIFICACION);
    }


    @Test
    @Transactional
    public void getAllTablerosByIdUserIsEqualToSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);
        User idUser = UserResourceIT.createEntity(em);
        em.persist(idUser);
        em.flush();
        tableros.setIdUser(idUser);
        tablerosRepository.saveAndFlush(tableros);
        Long idUserId = idUser.getId();

        // Get all the tablerosList where idUser equals to idUserId
        defaultTablerosShouldBeFound("idUserId.equals=" + idUserId);

        // Get all the tablerosList where idUser equals to idUserId + 1
        defaultTablerosShouldNotBeFound("idUserId.equals=" + (idUserId + 1));
    }


    @Test
    @Transactional
    public void getAllTablerosByCategoriaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        tablerosRepository.saveAndFlush(tableros);
        CategoriaTablero categoriaId = CategoriaTableroResourceIT.createEntity(em);
        em.persist(categoriaId);
        em.flush();
        tableros.setCategoriaId(categoriaId);
        tablerosRepository.saveAndFlush(tableros);
        Long categoriaIdId = categoriaId.getId();

        // Get all the tablerosList where categoriaId equals to categoriaIdId
        defaultTablerosShouldBeFound("categoriaIdId.equals=" + categoriaIdId);

        // Get all the tablerosList where categoriaId equals to categoriaIdId + 1
        defaultTablerosShouldNotBeFound("categoriaIdId.equals=" + (categoriaIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTablerosShouldBeFound(String filter) throws Exception {
        restTablerosMockMvc.perform(get("/api/tableros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tableros.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].privado").value(hasItem(DEFAULT_PRIVADO.booleanValue())))
            .andExpect(jsonPath("$.[*].calificacion").value(hasItem(DEFAULT_CALIFICACION)));

        // Check, that the count call also returns 1
        restTablerosMockMvc.perform(get("/api/tableros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTablerosShouldNotBeFound(String filter) throws Exception {
        restTablerosMockMvc.perform(get("/api/tableros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTablerosMockMvc.perform(get("/api/tableros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTableros() throws Exception {
        // Get the tableros
        restTablerosMockMvc.perform(get("/api/tableros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTableros() throws Exception {
        // Initialize the database
        tablerosService.save(tableros);

        int databaseSizeBeforeUpdate = tablerosRepository.findAll().size();

        // Update the tableros
        Tableros updatedTableros = tablerosRepository.findById(tableros.getId()).get();
        // Disconnect from session so that the updates on updatedTableros are not directly saved in db
        em.detach(updatedTableros);
        updatedTableros
            .nombre(UPDATED_NOMBRE)
            .fecha(UPDATED_FECHA)
            .privado(UPDATED_PRIVADO)
            .calificacion(UPDATED_CALIFICACION);

        restTablerosMockMvc.perform(put("/api/tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTableros)))
            .andExpect(status().isOk());

        // Validate the Tableros in the database
        List<Tableros> tablerosList = tablerosRepository.findAll();
        assertThat(tablerosList).hasSize(databaseSizeBeforeUpdate);
        Tableros testTableros = tablerosList.get(tablerosList.size() - 1);
        assertThat(testTableros.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTableros.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTableros.isPrivado()).isEqualTo(UPDATED_PRIVADO);
        assertThat(testTableros.getCalificacion()).isEqualTo(UPDATED_CALIFICACION);
    }

    @Test
    @Transactional
    public void updateNonExistingTableros() throws Exception {
        int databaseSizeBeforeUpdate = tablerosRepository.findAll().size();

        // Create the Tableros

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTablerosMockMvc.perform(put("/api/tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tableros)))
            .andExpect(status().isBadRequest());

        // Validate the Tableros in the database
        List<Tableros> tablerosList = tablerosRepository.findAll();
        assertThat(tablerosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTableros() throws Exception {
        // Initialize the database
        tablerosService.save(tableros);

        int databaseSizeBeforeDelete = tablerosRepository.findAll().size();

        // Delete the tableros
        restTablerosMockMvc.perform(delete("/api/tableros/{id}", tableros.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tableros> tablerosList = tablerosRepository.findAll();
        assertThat(tablerosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
