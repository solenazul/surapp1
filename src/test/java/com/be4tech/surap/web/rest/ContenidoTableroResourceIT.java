package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.ContenidoTablero;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.domain.Tableros;
import com.be4tech.surap.domain.Productos;
import com.be4tech.surap.repository.ContenidoTableroRepository;
import com.be4tech.surap.service.ContenidoTableroService;
import com.be4tech.surap.service.dto.ContenidoTableroCriteria;
import com.be4tech.surap.service.ContenidoTableroQueryService;

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
 * Integration tests for the {@link ContenidoTableroResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class ContenidoTableroResourceIT {

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ContenidoTableroRepository contenidoTableroRepository;

    @Autowired
    private ContenidoTableroService contenidoTableroService;

    @Autowired
    private ContenidoTableroQueryService contenidoTableroQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContenidoTableroMockMvc;

    private ContenidoTablero contenidoTablero;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContenidoTablero createEntity(EntityManager em) {
        ContenidoTablero contenidoTablero = new ContenidoTablero()
            .comentario(DEFAULT_COMENTARIO)
            .fecha(DEFAULT_FECHA);
        return contenidoTablero;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContenidoTablero createUpdatedEntity(EntityManager em) {
        ContenidoTablero contenidoTablero = new ContenidoTablero()
            .comentario(UPDATED_COMENTARIO)
            .fecha(UPDATED_FECHA);
        return contenidoTablero;
    }

    @BeforeEach
    public void initTest() {
        contenidoTablero = createEntity(em);
    }

    @Test
    @Transactional
    public void createContenidoTablero() throws Exception {
        int databaseSizeBeforeCreate = contenidoTableroRepository.findAll().size();

        // Create the ContenidoTablero
        restContenidoTableroMockMvc.perform(post("/api/contenido-tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenidoTablero)))
            .andExpect(status().isCreated());

        // Validate the ContenidoTablero in the database
        List<ContenidoTablero> contenidoTableroList = contenidoTableroRepository.findAll();
        assertThat(contenidoTableroList).hasSize(databaseSizeBeforeCreate + 1);
        ContenidoTablero testContenidoTablero = contenidoTableroList.get(contenidoTableroList.size() - 1);
        assertThat(testContenidoTablero.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testContenidoTablero.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createContenidoTableroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contenidoTableroRepository.findAll().size();

        // Create the ContenidoTablero with an existing ID
        contenidoTablero.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContenidoTableroMockMvc.perform(post("/api/contenido-tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenidoTablero)))
            .andExpect(status().isBadRequest());

        // Validate the ContenidoTablero in the database
        List<ContenidoTablero> contenidoTableroList = contenidoTableroRepository.findAll();
        assertThat(contenidoTableroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContenidoTableros() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);

        // Get all the contenidoTableroList
        restContenidoTableroMockMvc.perform(get("/api/contenido-tableros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contenidoTablero.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getContenidoTablero() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);

        // Get the contenidoTablero
        restContenidoTableroMockMvc.perform(get("/api/contenido-tableros/{id}", contenidoTablero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contenidoTablero.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }


    @Test
    @Transactional
    public void getContenidoTablerosByIdFiltering() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);

        Long id = contenidoTablero.getId();

        defaultContenidoTableroShouldBeFound("id.equals=" + id);
        defaultContenidoTableroShouldNotBeFound("id.notEquals=" + id);

        defaultContenidoTableroShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContenidoTableroShouldNotBeFound("id.greaterThan=" + id);

        defaultContenidoTableroShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContenidoTableroShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllContenidoTablerosByComentarioIsEqualToSomething() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);

        // Get all the contenidoTableroList where comentario equals to DEFAULT_COMENTARIO
        defaultContenidoTableroShouldBeFound("comentario.equals=" + DEFAULT_COMENTARIO);

        // Get all the contenidoTableroList where comentario equals to UPDATED_COMENTARIO
        defaultContenidoTableroShouldNotBeFound("comentario.equals=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllContenidoTablerosByComentarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);

        // Get all the contenidoTableroList where comentario not equals to DEFAULT_COMENTARIO
        defaultContenidoTableroShouldNotBeFound("comentario.notEquals=" + DEFAULT_COMENTARIO);

        // Get all the contenidoTableroList where comentario not equals to UPDATED_COMENTARIO
        defaultContenidoTableroShouldBeFound("comentario.notEquals=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllContenidoTablerosByComentarioIsInShouldWork() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);

        // Get all the contenidoTableroList where comentario in DEFAULT_COMENTARIO or UPDATED_COMENTARIO
        defaultContenidoTableroShouldBeFound("comentario.in=" + DEFAULT_COMENTARIO + "," + UPDATED_COMENTARIO);

        // Get all the contenidoTableroList where comentario equals to UPDATED_COMENTARIO
        defaultContenidoTableroShouldNotBeFound("comentario.in=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllContenidoTablerosByComentarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);

        // Get all the contenidoTableroList where comentario is not null
        defaultContenidoTableroShouldBeFound("comentario.specified=true");

        // Get all the contenidoTableroList where comentario is null
        defaultContenidoTableroShouldNotBeFound("comentario.specified=false");
    }
                @Test
    @Transactional
    public void getAllContenidoTablerosByComentarioContainsSomething() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);

        // Get all the contenidoTableroList where comentario contains DEFAULT_COMENTARIO
        defaultContenidoTableroShouldBeFound("comentario.contains=" + DEFAULT_COMENTARIO);

        // Get all the contenidoTableroList where comentario contains UPDATED_COMENTARIO
        defaultContenidoTableroShouldNotBeFound("comentario.contains=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllContenidoTablerosByComentarioNotContainsSomething() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);

        // Get all the contenidoTableroList where comentario does not contain DEFAULT_COMENTARIO
        defaultContenidoTableroShouldNotBeFound("comentario.doesNotContain=" + DEFAULT_COMENTARIO);

        // Get all the contenidoTableroList where comentario does not contain UPDATED_COMENTARIO
        defaultContenidoTableroShouldBeFound("comentario.doesNotContain=" + UPDATED_COMENTARIO);
    }


    @Test
    @Transactional
    public void getAllContenidoTablerosByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);

        // Get all the contenidoTableroList where fecha equals to DEFAULT_FECHA
        defaultContenidoTableroShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the contenidoTableroList where fecha equals to UPDATED_FECHA
        defaultContenidoTableroShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllContenidoTablerosByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);

        // Get all the contenidoTableroList where fecha not equals to DEFAULT_FECHA
        defaultContenidoTableroShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the contenidoTableroList where fecha not equals to UPDATED_FECHA
        defaultContenidoTableroShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllContenidoTablerosByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);

        // Get all the contenidoTableroList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultContenidoTableroShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the contenidoTableroList where fecha equals to UPDATED_FECHA
        defaultContenidoTableroShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllContenidoTablerosByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);

        // Get all the contenidoTableroList where fecha is not null
        defaultContenidoTableroShouldBeFound("fecha.specified=true");

        // Get all the contenidoTableroList where fecha is null
        defaultContenidoTableroShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllContenidoTablerosByIdUserIsEqualToSomething() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);
        User idUser = UserResourceIT.createEntity(em);
        em.persist(idUser);
        em.flush();
        contenidoTablero.setIdUser(idUser);
        contenidoTableroRepository.saveAndFlush(contenidoTablero);
        Long idUserId = idUser.getId();

        // Get all the contenidoTableroList where idUser equals to idUserId
        defaultContenidoTableroShouldBeFound("idUserId.equals=" + idUserId);

        // Get all the contenidoTableroList where idUser equals to idUserId + 1
        defaultContenidoTableroShouldNotBeFound("idUserId.equals=" + (idUserId + 1));
    }


    @Test
    @Transactional
    public void getAllContenidoTablerosByTableroIdIsEqualToSomething() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);
        Tableros tableroId = TablerosResourceIT.createEntity(em);
        em.persist(tableroId);
        em.flush();
        contenidoTablero.setTableroId(tableroId);
        contenidoTableroRepository.saveAndFlush(contenidoTablero);
        Long tableroIdId = tableroId.getId();

        // Get all the contenidoTableroList where tableroId equals to tableroIdId
        defaultContenidoTableroShouldBeFound("tableroIdId.equals=" + tableroIdId);

        // Get all the contenidoTableroList where tableroId equals to tableroIdId + 1
        defaultContenidoTableroShouldNotBeFound("tableroIdId.equals=" + (tableroIdId + 1));
    }


    @Test
    @Transactional
    public void getAllContenidoTablerosByProductoIdIsEqualToSomething() throws Exception {
        // Initialize the database
        contenidoTableroRepository.saveAndFlush(contenidoTablero);
        Productos productoId = ProductosResourceIT.createEntity(em);
        em.persist(productoId);
        em.flush();
        contenidoTablero.setProductoId(productoId);
        contenidoTableroRepository.saveAndFlush(contenidoTablero);
        Long productoIdId = productoId.getId();

        // Get all the contenidoTableroList where productoId equals to productoIdId
        defaultContenidoTableroShouldBeFound("productoIdId.equals=" + productoIdId);

        // Get all the contenidoTableroList where productoId equals to productoIdId + 1
        defaultContenidoTableroShouldNotBeFound("productoIdId.equals=" + (productoIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContenidoTableroShouldBeFound(String filter) throws Exception {
        restContenidoTableroMockMvc.perform(get("/api/contenido-tableros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contenidoTablero.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

        // Check, that the count call also returns 1
        restContenidoTableroMockMvc.perform(get("/api/contenido-tableros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContenidoTableroShouldNotBeFound(String filter) throws Exception {
        restContenidoTableroMockMvc.perform(get("/api/contenido-tableros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContenidoTableroMockMvc.perform(get("/api/contenido-tableros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingContenidoTablero() throws Exception {
        // Get the contenidoTablero
        restContenidoTableroMockMvc.perform(get("/api/contenido-tableros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContenidoTablero() throws Exception {
        // Initialize the database
        contenidoTableroService.save(contenidoTablero);

        int databaseSizeBeforeUpdate = contenidoTableroRepository.findAll().size();

        // Update the contenidoTablero
        ContenidoTablero updatedContenidoTablero = contenidoTableroRepository.findById(contenidoTablero.getId()).get();
        // Disconnect from session so that the updates on updatedContenidoTablero are not directly saved in db
        em.detach(updatedContenidoTablero);
        updatedContenidoTablero
            .comentario(UPDATED_COMENTARIO)
            .fecha(UPDATED_FECHA);

        restContenidoTableroMockMvc.perform(put("/api/contenido-tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContenidoTablero)))
            .andExpect(status().isOk());

        // Validate the ContenidoTablero in the database
        List<ContenidoTablero> contenidoTableroList = contenidoTableroRepository.findAll();
        assertThat(contenidoTableroList).hasSize(databaseSizeBeforeUpdate);
        ContenidoTablero testContenidoTablero = contenidoTableroList.get(contenidoTableroList.size() - 1);
        assertThat(testContenidoTablero.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testContenidoTablero.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingContenidoTablero() throws Exception {
        int databaseSizeBeforeUpdate = contenidoTableroRepository.findAll().size();

        // Create the ContenidoTablero

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContenidoTableroMockMvc.perform(put("/api/contenido-tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenidoTablero)))
            .andExpect(status().isBadRequest());

        // Validate the ContenidoTablero in the database
        List<ContenidoTablero> contenidoTableroList = contenidoTableroRepository.findAll();
        assertThat(contenidoTableroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContenidoTablero() throws Exception {
        // Initialize the database
        contenidoTableroService.save(contenidoTablero);

        int databaseSizeBeforeDelete = contenidoTableroRepository.findAll().size();

        // Delete the contenidoTablero
        restContenidoTableroMockMvc.perform(delete("/api/contenido-tableros/{id}", contenidoTablero.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContenidoTablero> contenidoTableroList = contenidoTableroRepository.findAll();
        assertThat(contenidoTableroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
