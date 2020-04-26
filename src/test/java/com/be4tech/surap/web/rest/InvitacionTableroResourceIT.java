package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.InvitacionTablero;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.domain.Tableros;
import com.be4tech.surap.repository.InvitacionTableroRepository;
import com.be4tech.surap.service.InvitacionTableroService;
import com.be4tech.surap.service.dto.InvitacionTableroCriteria;
import com.be4tech.surap.service.InvitacionTableroQueryService;

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
 * Integration tests for the {@link InvitacionTableroResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class InvitacionTableroResourceIT {

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InvitacionTableroRepository invitacionTableroRepository;

    @Autowired
    private InvitacionTableroService invitacionTableroService;

    @Autowired
    private InvitacionTableroQueryService invitacionTableroQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvitacionTableroMockMvc;

    private InvitacionTablero invitacionTablero;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvitacionTablero createEntity(EntityManager em) {
        InvitacionTablero invitacionTablero = new InvitacionTablero()
            .estado(DEFAULT_ESTADO)
            .fecha(DEFAULT_FECHA);
        return invitacionTablero;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvitacionTablero createUpdatedEntity(EntityManager em) {
        InvitacionTablero invitacionTablero = new InvitacionTablero()
            .estado(UPDATED_ESTADO)
            .fecha(UPDATED_FECHA);
        return invitacionTablero;
    }

    @BeforeEach
    public void initTest() {
        invitacionTablero = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvitacionTablero() throws Exception {
        int databaseSizeBeforeCreate = invitacionTableroRepository.findAll().size();

        // Create the InvitacionTablero
        restInvitacionTableroMockMvc.perform(post("/api/invitacion-tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitacionTablero)))
            .andExpect(status().isCreated());

        // Validate the InvitacionTablero in the database
        List<InvitacionTablero> invitacionTableroList = invitacionTableroRepository.findAll();
        assertThat(invitacionTableroList).hasSize(databaseSizeBeforeCreate + 1);
        InvitacionTablero testInvitacionTablero = invitacionTableroList.get(invitacionTableroList.size() - 1);
        assertThat(testInvitacionTablero.isEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testInvitacionTablero.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createInvitacionTableroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invitacionTableroRepository.findAll().size();

        // Create the InvitacionTablero with an existing ID
        invitacionTablero.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvitacionTableroMockMvc.perform(post("/api/invitacion-tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitacionTablero)))
            .andExpect(status().isBadRequest());

        // Validate the InvitacionTablero in the database
        List<InvitacionTablero> invitacionTableroList = invitacionTableroRepository.findAll();
        assertThat(invitacionTableroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInvitacionTableros() throws Exception {
        // Initialize the database
        invitacionTableroRepository.saveAndFlush(invitacionTablero);

        // Get all the invitacionTableroList
        restInvitacionTableroMockMvc.perform(get("/api/invitacion-tableros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invitacionTablero.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getInvitacionTablero() throws Exception {
        // Initialize the database
        invitacionTableroRepository.saveAndFlush(invitacionTablero);

        // Get the invitacionTablero
        restInvitacionTableroMockMvc.perform(get("/api/invitacion-tableros/{id}", invitacionTablero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invitacionTablero.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }


    @Test
    @Transactional
    public void getInvitacionTablerosByIdFiltering() throws Exception {
        // Initialize the database
        invitacionTableroRepository.saveAndFlush(invitacionTablero);

        Long id = invitacionTablero.getId();

        defaultInvitacionTableroShouldBeFound("id.equals=" + id);
        defaultInvitacionTableroShouldNotBeFound("id.notEquals=" + id);

        defaultInvitacionTableroShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInvitacionTableroShouldNotBeFound("id.greaterThan=" + id);

        defaultInvitacionTableroShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInvitacionTableroShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInvitacionTablerosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        invitacionTableroRepository.saveAndFlush(invitacionTablero);

        // Get all the invitacionTableroList where estado equals to DEFAULT_ESTADO
        defaultInvitacionTableroShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the invitacionTableroList where estado equals to UPDATED_ESTADO
        defaultInvitacionTableroShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllInvitacionTablerosByEstadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invitacionTableroRepository.saveAndFlush(invitacionTablero);

        // Get all the invitacionTableroList where estado not equals to DEFAULT_ESTADO
        defaultInvitacionTableroShouldNotBeFound("estado.notEquals=" + DEFAULT_ESTADO);

        // Get all the invitacionTableroList where estado not equals to UPDATED_ESTADO
        defaultInvitacionTableroShouldBeFound("estado.notEquals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllInvitacionTablerosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        invitacionTableroRepository.saveAndFlush(invitacionTablero);

        // Get all the invitacionTableroList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultInvitacionTableroShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the invitacionTableroList where estado equals to UPDATED_ESTADO
        defaultInvitacionTableroShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllInvitacionTablerosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        invitacionTableroRepository.saveAndFlush(invitacionTablero);

        // Get all the invitacionTableroList where estado is not null
        defaultInvitacionTableroShouldBeFound("estado.specified=true");

        // Get all the invitacionTableroList where estado is null
        defaultInvitacionTableroShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvitacionTablerosByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        invitacionTableroRepository.saveAndFlush(invitacionTablero);

        // Get all the invitacionTableroList where fecha equals to DEFAULT_FECHA
        defaultInvitacionTableroShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the invitacionTableroList where fecha equals to UPDATED_FECHA
        defaultInvitacionTableroShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllInvitacionTablerosByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invitacionTableroRepository.saveAndFlush(invitacionTablero);

        // Get all the invitacionTableroList where fecha not equals to DEFAULT_FECHA
        defaultInvitacionTableroShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the invitacionTableroList where fecha not equals to UPDATED_FECHA
        defaultInvitacionTableroShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllInvitacionTablerosByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        invitacionTableroRepository.saveAndFlush(invitacionTablero);

        // Get all the invitacionTableroList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultInvitacionTableroShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the invitacionTableroList where fecha equals to UPDATED_FECHA
        defaultInvitacionTableroShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllInvitacionTablerosByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        invitacionTableroRepository.saveAndFlush(invitacionTablero);

        // Get all the invitacionTableroList where fecha is not null
        defaultInvitacionTableroShouldBeFound("fecha.specified=true");

        // Get all the invitacionTableroList where fecha is null
        defaultInvitacionTableroShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvitacionTablerosByIdUserIsEqualToSomething() throws Exception {
        // Initialize the database
        invitacionTableroRepository.saveAndFlush(invitacionTablero);
        User idUser = UserResourceIT.createEntity(em);
        em.persist(idUser);
        em.flush();
        invitacionTablero.setIdUser(idUser);
        invitacionTableroRepository.saveAndFlush(invitacionTablero);
        Long idUserId = idUser.getId();

        // Get all the invitacionTableroList where idUser equals to idUserId
        defaultInvitacionTableroShouldBeFound("idUserId.equals=" + idUserId);

        // Get all the invitacionTableroList where idUser equals to idUserId + 1
        defaultInvitacionTableroShouldNotBeFound("idUserId.equals=" + (idUserId + 1));
    }


    @Test
    @Transactional
    public void getAllInvitacionTablerosByTableroIdIsEqualToSomething() throws Exception {
        // Initialize the database
        invitacionTableroRepository.saveAndFlush(invitacionTablero);
        Tableros tableroId = TablerosResourceIT.createEntity(em);
        em.persist(tableroId);
        em.flush();
        invitacionTablero.setTableroId(tableroId);
        invitacionTableroRepository.saveAndFlush(invitacionTablero);
        Long tableroIdId = tableroId.getId();

        // Get all the invitacionTableroList where tableroId equals to tableroIdId
        defaultInvitacionTableroShouldBeFound("tableroIdId.equals=" + tableroIdId);

        // Get all the invitacionTableroList where tableroId equals to tableroIdId + 1
        defaultInvitacionTableroShouldNotBeFound("tableroIdId.equals=" + (tableroIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvitacionTableroShouldBeFound(String filter) throws Exception {
        restInvitacionTableroMockMvc.perform(get("/api/invitacion-tableros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invitacionTablero.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

        // Check, that the count call also returns 1
        restInvitacionTableroMockMvc.perform(get("/api/invitacion-tableros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvitacionTableroShouldNotBeFound(String filter) throws Exception {
        restInvitacionTableroMockMvc.perform(get("/api/invitacion-tableros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvitacionTableroMockMvc.perform(get("/api/invitacion-tableros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingInvitacionTablero() throws Exception {
        // Get the invitacionTablero
        restInvitacionTableroMockMvc.perform(get("/api/invitacion-tableros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvitacionTablero() throws Exception {
        // Initialize the database
        invitacionTableroService.save(invitacionTablero);

        int databaseSizeBeforeUpdate = invitacionTableroRepository.findAll().size();

        // Update the invitacionTablero
        InvitacionTablero updatedInvitacionTablero = invitacionTableroRepository.findById(invitacionTablero.getId()).get();
        // Disconnect from session so that the updates on updatedInvitacionTablero are not directly saved in db
        em.detach(updatedInvitacionTablero);
        updatedInvitacionTablero
            .estado(UPDATED_ESTADO)
            .fecha(UPDATED_FECHA);

        restInvitacionTableroMockMvc.perform(put("/api/invitacion-tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvitacionTablero)))
            .andExpect(status().isOk());

        // Validate the InvitacionTablero in the database
        List<InvitacionTablero> invitacionTableroList = invitacionTableroRepository.findAll();
        assertThat(invitacionTableroList).hasSize(databaseSizeBeforeUpdate);
        InvitacionTablero testInvitacionTablero = invitacionTableroList.get(invitacionTableroList.size() - 1);
        assertThat(testInvitacionTablero.isEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testInvitacionTablero.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingInvitacionTablero() throws Exception {
        int databaseSizeBeforeUpdate = invitacionTableroRepository.findAll().size();

        // Create the InvitacionTablero

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvitacionTableroMockMvc.perform(put("/api/invitacion-tableros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitacionTablero)))
            .andExpect(status().isBadRequest());

        // Validate the InvitacionTablero in the database
        List<InvitacionTablero> invitacionTableroList = invitacionTableroRepository.findAll();
        assertThat(invitacionTableroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvitacionTablero() throws Exception {
        // Initialize the database
        invitacionTableroService.save(invitacionTablero);

        int databaseSizeBeforeDelete = invitacionTableroRepository.findAll().size();

        // Delete the invitacionTablero
        restInvitacionTableroMockMvc.perform(delete("/api/invitacion-tableros/{id}", invitacionTablero.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvitacionTablero> invitacionTableroList = invitacionTableroRepository.findAll();
        assertThat(invitacionTableroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
