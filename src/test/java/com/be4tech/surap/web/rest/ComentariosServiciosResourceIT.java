package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.ComentariosServicios;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.domain.Productos;
import com.be4tech.surap.repository.ComentariosServiciosRepository;
import com.be4tech.surap.service.ComentariosServiciosService;
import com.be4tech.surap.service.dto.ComentariosServiciosCriteria;
import com.be4tech.surap.service.ComentariosServiciosQueryService;

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
 * Integration tests for the {@link ComentariosServiciosResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class ComentariosServiciosResourceIT {

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    @Autowired
    private ComentariosServiciosRepository comentariosServiciosRepository;

    @Autowired
    private ComentariosServiciosService comentariosServiciosService;

    @Autowired
    private ComentariosServiciosQueryService comentariosServiciosQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComentariosServiciosMockMvc;

    private ComentariosServicios comentariosServicios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComentariosServicios createEntity(EntityManager em) {
        ComentariosServicios comentariosServicios = new ComentariosServicios()
            .comentario(DEFAULT_COMENTARIO)
            .fecha(DEFAULT_FECHA)
            .estado(DEFAULT_ESTADO);
        return comentariosServicios;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComentariosServicios createUpdatedEntity(EntityManager em) {
        ComentariosServicios comentariosServicios = new ComentariosServicios()
            .comentario(UPDATED_COMENTARIO)
            .fecha(UPDATED_FECHA)
            .estado(UPDATED_ESTADO);
        return comentariosServicios;
    }

    @BeforeEach
    public void initTest() {
        comentariosServicios = createEntity(em);
    }

    @Test
    @Transactional
    public void createComentariosServicios() throws Exception {
        int databaseSizeBeforeCreate = comentariosServiciosRepository.findAll().size();

        // Create the ComentariosServicios
        restComentariosServiciosMockMvc.perform(post("/api/comentarios-servicios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentariosServicios)))
            .andExpect(status().isCreated());

        // Validate the ComentariosServicios in the database
        List<ComentariosServicios> comentariosServiciosList = comentariosServiciosRepository.findAll();
        assertThat(comentariosServiciosList).hasSize(databaseSizeBeforeCreate + 1);
        ComentariosServicios testComentariosServicios = comentariosServiciosList.get(comentariosServiciosList.size() - 1);
        assertThat(testComentariosServicios.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testComentariosServicios.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testComentariosServicios.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createComentariosServiciosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comentariosServiciosRepository.findAll().size();

        // Create the ComentariosServicios with an existing ID
        comentariosServicios.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComentariosServiciosMockMvc.perform(post("/api/comentarios-servicios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentariosServicios)))
            .andExpect(status().isBadRequest());

        // Validate the ComentariosServicios in the database
        List<ComentariosServicios> comentariosServiciosList = comentariosServiciosRepository.findAll();
        assertThat(comentariosServiciosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllComentariosServicios() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList
        restComentariosServiciosMockMvc.perform(get("/api/comentarios-servicios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentariosServicios.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));
    }
    
    @Test
    @Transactional
    public void getComentariosServicios() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get the comentariosServicios
        restComentariosServiciosMockMvc.perform(get("/api/comentarios-servicios/{id}", comentariosServicios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comentariosServicios.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO));
    }


    @Test
    @Transactional
    public void getComentariosServiciosByIdFiltering() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        Long id = comentariosServicios.getId();

        defaultComentariosServiciosShouldBeFound("id.equals=" + id);
        defaultComentariosServiciosShouldNotBeFound("id.notEquals=" + id);

        defaultComentariosServiciosShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultComentariosServiciosShouldNotBeFound("id.greaterThan=" + id);

        defaultComentariosServiciosShouldBeFound("id.lessThanOrEqual=" + id);
        defaultComentariosServiciosShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllComentariosServiciosByComentarioIsEqualToSomething() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where comentario equals to DEFAULT_COMENTARIO
        defaultComentariosServiciosShouldBeFound("comentario.equals=" + DEFAULT_COMENTARIO);

        // Get all the comentariosServiciosList where comentario equals to UPDATED_COMENTARIO
        defaultComentariosServiciosShouldNotBeFound("comentario.equals=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllComentariosServiciosByComentarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where comentario not equals to DEFAULT_COMENTARIO
        defaultComentariosServiciosShouldNotBeFound("comentario.notEquals=" + DEFAULT_COMENTARIO);

        // Get all the comentariosServiciosList where comentario not equals to UPDATED_COMENTARIO
        defaultComentariosServiciosShouldBeFound("comentario.notEquals=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllComentariosServiciosByComentarioIsInShouldWork() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where comentario in DEFAULT_COMENTARIO or UPDATED_COMENTARIO
        defaultComentariosServiciosShouldBeFound("comentario.in=" + DEFAULT_COMENTARIO + "," + UPDATED_COMENTARIO);

        // Get all the comentariosServiciosList where comentario equals to UPDATED_COMENTARIO
        defaultComentariosServiciosShouldNotBeFound("comentario.in=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllComentariosServiciosByComentarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where comentario is not null
        defaultComentariosServiciosShouldBeFound("comentario.specified=true");

        // Get all the comentariosServiciosList where comentario is null
        defaultComentariosServiciosShouldNotBeFound("comentario.specified=false");
    }
                @Test
    @Transactional
    public void getAllComentariosServiciosByComentarioContainsSomething() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where comentario contains DEFAULT_COMENTARIO
        defaultComentariosServiciosShouldBeFound("comentario.contains=" + DEFAULT_COMENTARIO);

        // Get all the comentariosServiciosList where comentario contains UPDATED_COMENTARIO
        defaultComentariosServiciosShouldNotBeFound("comentario.contains=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllComentariosServiciosByComentarioNotContainsSomething() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where comentario does not contain DEFAULT_COMENTARIO
        defaultComentariosServiciosShouldNotBeFound("comentario.doesNotContain=" + DEFAULT_COMENTARIO);

        // Get all the comentariosServiciosList where comentario does not contain UPDATED_COMENTARIO
        defaultComentariosServiciosShouldBeFound("comentario.doesNotContain=" + UPDATED_COMENTARIO);
    }


    @Test
    @Transactional
    public void getAllComentariosServiciosByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where fecha equals to DEFAULT_FECHA
        defaultComentariosServiciosShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the comentariosServiciosList where fecha equals to UPDATED_FECHA
        defaultComentariosServiciosShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentariosServiciosByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where fecha not equals to DEFAULT_FECHA
        defaultComentariosServiciosShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the comentariosServiciosList where fecha not equals to UPDATED_FECHA
        defaultComentariosServiciosShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentariosServiciosByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultComentariosServiciosShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the comentariosServiciosList where fecha equals to UPDATED_FECHA
        defaultComentariosServiciosShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentariosServiciosByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where fecha is not null
        defaultComentariosServiciosShouldBeFound("fecha.specified=true");

        // Get all the comentariosServiciosList where fecha is null
        defaultComentariosServiciosShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllComentariosServiciosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where estado equals to DEFAULT_ESTADO
        defaultComentariosServiciosShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the comentariosServiciosList where estado equals to UPDATED_ESTADO
        defaultComentariosServiciosShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllComentariosServiciosByEstadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where estado not equals to DEFAULT_ESTADO
        defaultComentariosServiciosShouldNotBeFound("estado.notEquals=" + DEFAULT_ESTADO);

        // Get all the comentariosServiciosList where estado not equals to UPDATED_ESTADO
        defaultComentariosServiciosShouldBeFound("estado.notEquals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllComentariosServiciosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultComentariosServiciosShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the comentariosServiciosList where estado equals to UPDATED_ESTADO
        defaultComentariosServiciosShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllComentariosServiciosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where estado is not null
        defaultComentariosServiciosShouldBeFound("estado.specified=true");

        // Get all the comentariosServiciosList where estado is null
        defaultComentariosServiciosShouldNotBeFound("estado.specified=false");
    }
                @Test
    @Transactional
    public void getAllComentariosServiciosByEstadoContainsSomething() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where estado contains DEFAULT_ESTADO
        defaultComentariosServiciosShouldBeFound("estado.contains=" + DEFAULT_ESTADO);

        // Get all the comentariosServiciosList where estado contains UPDATED_ESTADO
        defaultComentariosServiciosShouldNotBeFound("estado.contains=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllComentariosServiciosByEstadoNotContainsSomething() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);

        // Get all the comentariosServiciosList where estado does not contain DEFAULT_ESTADO
        defaultComentariosServiciosShouldNotBeFound("estado.doesNotContain=" + DEFAULT_ESTADO);

        // Get all the comentariosServiciosList where estado does not contain UPDATED_ESTADO
        defaultComentariosServiciosShouldBeFound("estado.doesNotContain=" + UPDATED_ESTADO);
    }


    @Test
    @Transactional
    public void getAllComentariosServiciosByIdUserIsEqualToSomething() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);
        User idUser = UserResourceIT.createEntity(em);
        em.persist(idUser);
        em.flush();
        comentariosServicios.setIdUser(idUser);
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);
        Long idUserId = idUser.getId();

        // Get all the comentariosServiciosList where idUser equals to idUserId
        defaultComentariosServiciosShouldBeFound("idUserId.equals=" + idUserId);

        // Get all the comentariosServiciosList where idUser equals to idUserId + 1
        defaultComentariosServiciosShouldNotBeFound("idUserId.equals=" + (idUserId + 1));
    }


    @Test
    @Transactional
    public void getAllComentariosServiciosByProductoIdIsEqualToSomething() throws Exception {
        // Initialize the database
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);
        Productos productoId = ProductosResourceIT.createEntity(em);
        em.persist(productoId);
        em.flush();
        comentariosServicios.setProductoId(productoId);
        comentariosServiciosRepository.saveAndFlush(comentariosServicios);
        Long productoIdId = productoId.getId();

        // Get all the comentariosServiciosList where productoId equals to productoIdId
        defaultComentariosServiciosShouldBeFound("productoIdId.equals=" + productoIdId);

        // Get all the comentariosServiciosList where productoId equals to productoIdId + 1
        defaultComentariosServiciosShouldNotBeFound("productoIdId.equals=" + (productoIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComentariosServiciosShouldBeFound(String filter) throws Exception {
        restComentariosServiciosMockMvc.perform(get("/api/comentarios-servicios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentariosServicios.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));

        // Check, that the count call also returns 1
        restComentariosServiciosMockMvc.perform(get("/api/comentarios-servicios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComentariosServiciosShouldNotBeFound(String filter) throws Exception {
        restComentariosServiciosMockMvc.perform(get("/api/comentarios-servicios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComentariosServiciosMockMvc.perform(get("/api/comentarios-servicios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingComentariosServicios() throws Exception {
        // Get the comentariosServicios
        restComentariosServiciosMockMvc.perform(get("/api/comentarios-servicios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentariosServicios() throws Exception {
        // Initialize the database
        comentariosServiciosService.save(comentariosServicios);

        int databaseSizeBeforeUpdate = comentariosServiciosRepository.findAll().size();

        // Update the comentariosServicios
        ComentariosServicios updatedComentariosServicios = comentariosServiciosRepository.findById(comentariosServicios.getId()).get();
        // Disconnect from session so that the updates on updatedComentariosServicios are not directly saved in db
        em.detach(updatedComentariosServicios);
        updatedComentariosServicios
            .comentario(UPDATED_COMENTARIO)
            .fecha(UPDATED_FECHA)
            .estado(UPDATED_ESTADO);

        restComentariosServiciosMockMvc.perform(put("/api/comentarios-servicios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedComentariosServicios)))
            .andExpect(status().isOk());

        // Validate the ComentariosServicios in the database
        List<ComentariosServicios> comentariosServiciosList = comentariosServiciosRepository.findAll();
        assertThat(comentariosServiciosList).hasSize(databaseSizeBeforeUpdate);
        ComentariosServicios testComentariosServicios = comentariosServiciosList.get(comentariosServiciosList.size() - 1);
        assertThat(testComentariosServicios.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testComentariosServicios.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testComentariosServicios.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingComentariosServicios() throws Exception {
        int databaseSizeBeforeUpdate = comentariosServiciosRepository.findAll().size();

        // Create the ComentariosServicios

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComentariosServiciosMockMvc.perform(put("/api/comentarios-servicios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentariosServicios)))
            .andExpect(status().isBadRequest());

        // Validate the ComentariosServicios in the database
        List<ComentariosServicios> comentariosServiciosList = comentariosServiciosRepository.findAll();
        assertThat(comentariosServiciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComentariosServicios() throws Exception {
        // Initialize the database
        comentariosServiciosService.save(comentariosServicios);

        int databaseSizeBeforeDelete = comentariosServiciosRepository.findAll().size();

        // Delete the comentariosServicios
        restComentariosServiciosMockMvc.perform(delete("/api/comentarios-servicios/{id}", comentariosServicios.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComentariosServicios> comentariosServiciosList = comentariosServiciosRepository.findAll();
        assertThat(comentariosServiciosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
