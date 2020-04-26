package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.Alarma;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.repository.AlarmaRepository;
import com.be4tech.surap.service.AlarmaService;
import com.be4tech.surap.service.dto.AlarmaCriteria;
import com.be4tech.surap.service.AlarmaQueryService;

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
 * Integration tests for the {@link AlarmaResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class AlarmaResourceIT {

    private static final Instant DEFAULT_TIME_INSTANT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_INSTANT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_PROCEDIMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDIMIENTO = "BBBBBBBBBB";

    @Autowired
    private AlarmaRepository alarmaRepository;

    @Autowired
    private AlarmaService alarmaService;

    @Autowired
    private AlarmaQueryService alarmaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlarmaMockMvc;

    private Alarma alarma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alarma createEntity(EntityManager em) {
        Alarma alarma = new Alarma()
            .timeInstant(DEFAULT_TIME_INSTANT)
            .descripcion(DEFAULT_DESCRIPCION)
            .procedimiento(DEFAULT_PROCEDIMIENTO);
        return alarma;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alarma createUpdatedEntity(EntityManager em) {
        Alarma alarma = new Alarma()
            .timeInstant(UPDATED_TIME_INSTANT)
            .descripcion(UPDATED_DESCRIPCION)
            .procedimiento(UPDATED_PROCEDIMIENTO);
        return alarma;
    }

    @BeforeEach
    public void initTest() {
        alarma = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlarma() throws Exception {
        int databaseSizeBeforeCreate = alarmaRepository.findAll().size();

        // Create the Alarma
        restAlarmaMockMvc.perform(post("/api/alarmas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarma)))
            .andExpect(status().isCreated());

        // Validate the Alarma in the database
        List<Alarma> alarmaList = alarmaRepository.findAll();
        assertThat(alarmaList).hasSize(databaseSizeBeforeCreate + 1);
        Alarma testAlarma = alarmaList.get(alarmaList.size() - 1);
        assertThat(testAlarma.getTimeInstant()).isEqualTo(DEFAULT_TIME_INSTANT);
        assertThat(testAlarma.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testAlarma.getProcedimiento()).isEqualTo(DEFAULT_PROCEDIMIENTO);
    }

    @Test
    @Transactional
    public void createAlarmaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alarmaRepository.findAll().size();

        // Create the Alarma with an existing ID
        alarma.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlarmaMockMvc.perform(post("/api/alarmas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarma)))
            .andExpect(status().isBadRequest());

        // Validate the Alarma in the database
        List<Alarma> alarmaList = alarmaRepository.findAll();
        assertThat(alarmaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAlarmas() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList
        restAlarmaMockMvc.perform(get("/api/alarmas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarma.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeInstant").value(hasItem(DEFAULT_TIME_INSTANT.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].procedimiento").value(hasItem(DEFAULT_PROCEDIMIENTO)));
    }
    
    @Test
    @Transactional
    public void getAlarma() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get the alarma
        restAlarmaMockMvc.perform(get("/api/alarmas/{id}", alarma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alarma.getId().intValue()))
            .andExpect(jsonPath("$.timeInstant").value(DEFAULT_TIME_INSTANT.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.procedimiento").value(DEFAULT_PROCEDIMIENTO));
    }


    @Test
    @Transactional
    public void getAlarmasByIdFiltering() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        Long id = alarma.getId();

        defaultAlarmaShouldBeFound("id.equals=" + id);
        defaultAlarmaShouldNotBeFound("id.notEquals=" + id);

        defaultAlarmaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlarmaShouldNotBeFound("id.greaterThan=" + id);

        defaultAlarmaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlarmaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlarmasByTimeInstantIsEqualToSomething() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where timeInstant equals to DEFAULT_TIME_INSTANT
        defaultAlarmaShouldBeFound("timeInstant.equals=" + DEFAULT_TIME_INSTANT);

        // Get all the alarmaList where timeInstant equals to UPDATED_TIME_INSTANT
        defaultAlarmaShouldNotBeFound("timeInstant.equals=" + UPDATED_TIME_INSTANT);
    }

    @Test
    @Transactional
    public void getAllAlarmasByTimeInstantIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where timeInstant not equals to DEFAULT_TIME_INSTANT
        defaultAlarmaShouldNotBeFound("timeInstant.notEquals=" + DEFAULT_TIME_INSTANT);

        // Get all the alarmaList where timeInstant not equals to UPDATED_TIME_INSTANT
        defaultAlarmaShouldBeFound("timeInstant.notEquals=" + UPDATED_TIME_INSTANT);
    }

    @Test
    @Transactional
    public void getAllAlarmasByTimeInstantIsInShouldWork() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where timeInstant in DEFAULT_TIME_INSTANT or UPDATED_TIME_INSTANT
        defaultAlarmaShouldBeFound("timeInstant.in=" + DEFAULT_TIME_INSTANT + "," + UPDATED_TIME_INSTANT);

        // Get all the alarmaList where timeInstant equals to UPDATED_TIME_INSTANT
        defaultAlarmaShouldNotBeFound("timeInstant.in=" + UPDATED_TIME_INSTANT);
    }

    @Test
    @Transactional
    public void getAllAlarmasByTimeInstantIsNullOrNotNull() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where timeInstant is not null
        defaultAlarmaShouldBeFound("timeInstant.specified=true");

        // Get all the alarmaList where timeInstant is null
        defaultAlarmaShouldNotBeFound("timeInstant.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlarmasByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where descripcion equals to DEFAULT_DESCRIPCION
        defaultAlarmaShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the alarmaList where descripcion equals to UPDATED_DESCRIPCION
        defaultAlarmaShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllAlarmasByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultAlarmaShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the alarmaList where descripcion not equals to UPDATED_DESCRIPCION
        defaultAlarmaShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllAlarmasByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultAlarmaShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the alarmaList where descripcion equals to UPDATED_DESCRIPCION
        defaultAlarmaShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllAlarmasByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where descripcion is not null
        defaultAlarmaShouldBeFound("descripcion.specified=true");

        // Get all the alarmaList where descripcion is null
        defaultAlarmaShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlarmasByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where descripcion contains DEFAULT_DESCRIPCION
        defaultAlarmaShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the alarmaList where descripcion contains UPDATED_DESCRIPCION
        defaultAlarmaShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllAlarmasByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultAlarmaShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the alarmaList where descripcion does not contain UPDATED_DESCRIPCION
        defaultAlarmaShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }


    @Test
    @Transactional
    public void getAllAlarmasByProcedimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where procedimiento equals to DEFAULT_PROCEDIMIENTO
        defaultAlarmaShouldBeFound("procedimiento.equals=" + DEFAULT_PROCEDIMIENTO);

        // Get all the alarmaList where procedimiento equals to UPDATED_PROCEDIMIENTO
        defaultAlarmaShouldNotBeFound("procedimiento.equals=" + UPDATED_PROCEDIMIENTO);
    }

    @Test
    @Transactional
    public void getAllAlarmasByProcedimientoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where procedimiento not equals to DEFAULT_PROCEDIMIENTO
        defaultAlarmaShouldNotBeFound("procedimiento.notEquals=" + DEFAULT_PROCEDIMIENTO);

        // Get all the alarmaList where procedimiento not equals to UPDATED_PROCEDIMIENTO
        defaultAlarmaShouldBeFound("procedimiento.notEquals=" + UPDATED_PROCEDIMIENTO);
    }

    @Test
    @Transactional
    public void getAllAlarmasByProcedimientoIsInShouldWork() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where procedimiento in DEFAULT_PROCEDIMIENTO or UPDATED_PROCEDIMIENTO
        defaultAlarmaShouldBeFound("procedimiento.in=" + DEFAULT_PROCEDIMIENTO + "," + UPDATED_PROCEDIMIENTO);

        // Get all the alarmaList where procedimiento equals to UPDATED_PROCEDIMIENTO
        defaultAlarmaShouldNotBeFound("procedimiento.in=" + UPDATED_PROCEDIMIENTO);
    }

    @Test
    @Transactional
    public void getAllAlarmasByProcedimientoIsNullOrNotNull() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where procedimiento is not null
        defaultAlarmaShouldBeFound("procedimiento.specified=true");

        // Get all the alarmaList where procedimiento is null
        defaultAlarmaShouldNotBeFound("procedimiento.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlarmasByProcedimientoContainsSomething() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where procedimiento contains DEFAULT_PROCEDIMIENTO
        defaultAlarmaShouldBeFound("procedimiento.contains=" + DEFAULT_PROCEDIMIENTO);

        // Get all the alarmaList where procedimiento contains UPDATED_PROCEDIMIENTO
        defaultAlarmaShouldNotBeFound("procedimiento.contains=" + UPDATED_PROCEDIMIENTO);
    }

    @Test
    @Transactional
    public void getAllAlarmasByProcedimientoNotContainsSomething() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);

        // Get all the alarmaList where procedimiento does not contain DEFAULT_PROCEDIMIENTO
        defaultAlarmaShouldNotBeFound("procedimiento.doesNotContain=" + DEFAULT_PROCEDIMIENTO);

        // Get all the alarmaList where procedimiento does not contain UPDATED_PROCEDIMIENTO
        defaultAlarmaShouldBeFound("procedimiento.doesNotContain=" + UPDATED_PROCEDIMIENTO);
    }


    @Test
    @Transactional
    public void getAllAlarmasByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        alarmaRepository.saveAndFlush(alarma);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        alarma.setUser(user);
        alarmaRepository.saveAndFlush(alarma);
        Long userId = user.getId();

        // Get all the alarmaList where user equals to userId
        defaultAlarmaShouldBeFound("userId.equals=" + userId);

        // Get all the alarmaList where user equals to userId + 1
        defaultAlarmaShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlarmaShouldBeFound(String filter) throws Exception {
        restAlarmaMockMvc.perform(get("/api/alarmas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarma.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeInstant").value(hasItem(DEFAULT_TIME_INSTANT.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].procedimiento").value(hasItem(DEFAULT_PROCEDIMIENTO)));

        // Check, that the count call also returns 1
        restAlarmaMockMvc.perform(get("/api/alarmas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlarmaShouldNotBeFound(String filter) throws Exception {
        restAlarmaMockMvc.perform(get("/api/alarmas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlarmaMockMvc.perform(get("/api/alarmas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAlarma() throws Exception {
        // Get the alarma
        restAlarmaMockMvc.perform(get("/api/alarmas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlarma() throws Exception {
        // Initialize the database
        alarmaService.save(alarma);

        int databaseSizeBeforeUpdate = alarmaRepository.findAll().size();

        // Update the alarma
        Alarma updatedAlarma = alarmaRepository.findById(alarma.getId()).get();
        // Disconnect from session so that the updates on updatedAlarma are not directly saved in db
        em.detach(updatedAlarma);
        updatedAlarma
            .timeInstant(UPDATED_TIME_INSTANT)
            .descripcion(UPDATED_DESCRIPCION)
            .procedimiento(UPDATED_PROCEDIMIENTO);

        restAlarmaMockMvc.perform(put("/api/alarmas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlarma)))
            .andExpect(status().isOk());

        // Validate the Alarma in the database
        List<Alarma> alarmaList = alarmaRepository.findAll();
        assertThat(alarmaList).hasSize(databaseSizeBeforeUpdate);
        Alarma testAlarma = alarmaList.get(alarmaList.size() - 1);
        assertThat(testAlarma.getTimeInstant()).isEqualTo(UPDATED_TIME_INSTANT);
        assertThat(testAlarma.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testAlarma.getProcedimiento()).isEqualTo(UPDATED_PROCEDIMIENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingAlarma() throws Exception {
        int databaseSizeBeforeUpdate = alarmaRepository.findAll().size();

        // Create the Alarma

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlarmaMockMvc.perform(put("/api/alarmas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarma)))
            .andExpect(status().isBadRequest());

        // Validate the Alarma in the database
        List<Alarma> alarmaList = alarmaRepository.findAll();
        assertThat(alarmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlarma() throws Exception {
        // Initialize the database
        alarmaService.save(alarma);

        int databaseSizeBeforeDelete = alarmaRepository.findAll().size();

        // Delete the alarma
        restAlarmaMockMvc.perform(delete("/api/alarmas/{id}", alarma.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alarma> alarmaList = alarmaRepository.findAll();
        assertThat(alarmaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
