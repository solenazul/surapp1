package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.EncuestaEntorno;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.repository.EncuestaEntornoRepository;
import com.be4tech.surap.service.EncuestaEntornoService;
import com.be4tech.surap.service.dto.EncuestaEntornoCriteria;
import com.be4tech.surap.service.EncuestaEntornoQueryService;

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
 * Integration tests for the {@link EncuestaEntornoResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class EncuestaEntornoResourceIT {

    private static final Boolean DEFAULT_CONTACTO_SINTOMAS = false;
    private static final Boolean UPDATED_CONTACTO_SINTOMAS = true;

    private static final Boolean DEFAULT_VIAJE_INTERNACIONAL = false;
    private static final Boolean UPDATED_VIAJE_INTERNACIONAL = true;

    private static final Boolean DEFAULT_VIAJE_NACIONAL = false;
    private static final Boolean UPDATED_VIAJE_NACIONAL = true;

    private static final Boolean DEFAULT_TRABAJADOR_SALUD = false;
    private static final Boolean UPDATED_TRABAJADOR_SALUD = true;

    private static final Boolean DEFAULT_NINGUNA = false;
    private static final Boolean UPDATED_NINGUNA = true;

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EncuestaEntornoRepository encuestaEntornoRepository;

    @Autowired
    private EncuestaEntornoService encuestaEntornoService;

    @Autowired
    private EncuestaEntornoQueryService encuestaEntornoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEncuestaEntornoMockMvc;

    private EncuestaEntorno encuestaEntorno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EncuestaEntorno createEntity(EntityManager em) {
        EncuestaEntorno encuestaEntorno = new EncuestaEntorno()
            .contactoSintomas(DEFAULT_CONTACTO_SINTOMAS)
            .viajeInternacional(DEFAULT_VIAJE_INTERNACIONAL)
            .viajeNacional(DEFAULT_VIAJE_NACIONAL)
            .trabajadorSalud(DEFAULT_TRABAJADOR_SALUD)
            .ninguna(DEFAULT_NINGUNA)
            .fecha(DEFAULT_FECHA);
        return encuestaEntorno;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EncuestaEntorno createUpdatedEntity(EntityManager em) {
        EncuestaEntorno encuestaEntorno = new EncuestaEntorno()
            .contactoSintomas(UPDATED_CONTACTO_SINTOMAS)
            .viajeInternacional(UPDATED_VIAJE_INTERNACIONAL)
            .viajeNacional(UPDATED_VIAJE_NACIONAL)
            .trabajadorSalud(UPDATED_TRABAJADOR_SALUD)
            .ninguna(UPDATED_NINGUNA)
            .fecha(UPDATED_FECHA);
        return encuestaEntorno;
    }

    @BeforeEach
    public void initTest() {
        encuestaEntorno = createEntity(em);
    }

    @Test
    @Transactional
    public void createEncuestaEntorno() throws Exception {
        int databaseSizeBeforeCreate = encuestaEntornoRepository.findAll().size();

        // Create the EncuestaEntorno
        restEncuestaEntornoMockMvc.perform(post("/api/encuesta-entornos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(encuestaEntorno)))
            .andExpect(status().isCreated());

        // Validate the EncuestaEntorno in the database
        List<EncuestaEntorno> encuestaEntornoList = encuestaEntornoRepository.findAll();
        assertThat(encuestaEntornoList).hasSize(databaseSizeBeforeCreate + 1);
        EncuestaEntorno testEncuestaEntorno = encuestaEntornoList.get(encuestaEntornoList.size() - 1);
        assertThat(testEncuestaEntorno.isContactoSintomas()).isEqualTo(DEFAULT_CONTACTO_SINTOMAS);
        assertThat(testEncuestaEntorno.isViajeInternacional()).isEqualTo(DEFAULT_VIAJE_INTERNACIONAL);
        assertThat(testEncuestaEntorno.isViajeNacional()).isEqualTo(DEFAULT_VIAJE_NACIONAL);
        assertThat(testEncuestaEntorno.isTrabajadorSalud()).isEqualTo(DEFAULT_TRABAJADOR_SALUD);
        assertThat(testEncuestaEntorno.isNinguna()).isEqualTo(DEFAULT_NINGUNA);
        assertThat(testEncuestaEntorno.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createEncuestaEntornoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = encuestaEntornoRepository.findAll().size();

        // Create the EncuestaEntorno with an existing ID
        encuestaEntorno.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncuestaEntornoMockMvc.perform(post("/api/encuesta-entornos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(encuestaEntorno)))
            .andExpect(status().isBadRequest());

        // Validate the EncuestaEntorno in the database
        List<EncuestaEntorno> encuestaEntornoList = encuestaEntornoRepository.findAll();
        assertThat(encuestaEntornoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEncuestaEntornos() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList
        restEncuestaEntornoMockMvc.perform(get("/api/encuesta-entornos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encuestaEntorno.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactoSintomas").value(hasItem(DEFAULT_CONTACTO_SINTOMAS.booleanValue())))
            .andExpect(jsonPath("$.[*].viajeInternacional").value(hasItem(DEFAULT_VIAJE_INTERNACIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].viajeNacional").value(hasItem(DEFAULT_VIAJE_NACIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].trabajadorSalud").value(hasItem(DEFAULT_TRABAJADOR_SALUD.booleanValue())))
            .andExpect(jsonPath("$.[*].ninguna").value(hasItem(DEFAULT_NINGUNA.booleanValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getEncuestaEntorno() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get the encuestaEntorno
        restEncuestaEntornoMockMvc.perform(get("/api/encuesta-entornos/{id}", encuestaEntorno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(encuestaEntorno.getId().intValue()))
            .andExpect(jsonPath("$.contactoSintomas").value(DEFAULT_CONTACTO_SINTOMAS.booleanValue()))
            .andExpect(jsonPath("$.viajeInternacional").value(DEFAULT_VIAJE_INTERNACIONAL.booleanValue()))
            .andExpect(jsonPath("$.viajeNacional").value(DEFAULT_VIAJE_NACIONAL.booleanValue()))
            .andExpect(jsonPath("$.trabajadorSalud").value(DEFAULT_TRABAJADOR_SALUD.booleanValue()))
            .andExpect(jsonPath("$.ninguna").value(DEFAULT_NINGUNA.booleanValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }


    @Test
    @Transactional
    public void getEncuestaEntornosByIdFiltering() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        Long id = encuestaEntorno.getId();

        defaultEncuestaEntornoShouldBeFound("id.equals=" + id);
        defaultEncuestaEntornoShouldNotBeFound("id.notEquals=" + id);

        defaultEncuestaEntornoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEncuestaEntornoShouldNotBeFound("id.greaterThan=" + id);

        defaultEncuestaEntornoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEncuestaEntornoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEncuestaEntornosByContactoSintomasIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where contactoSintomas equals to DEFAULT_CONTACTO_SINTOMAS
        defaultEncuestaEntornoShouldBeFound("contactoSintomas.equals=" + DEFAULT_CONTACTO_SINTOMAS);

        // Get all the encuestaEntornoList where contactoSintomas equals to UPDATED_CONTACTO_SINTOMAS
        defaultEncuestaEntornoShouldNotBeFound("contactoSintomas.equals=" + UPDATED_CONTACTO_SINTOMAS);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByContactoSintomasIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where contactoSintomas not equals to DEFAULT_CONTACTO_SINTOMAS
        defaultEncuestaEntornoShouldNotBeFound("contactoSintomas.notEquals=" + DEFAULT_CONTACTO_SINTOMAS);

        // Get all the encuestaEntornoList where contactoSintomas not equals to UPDATED_CONTACTO_SINTOMAS
        defaultEncuestaEntornoShouldBeFound("contactoSintomas.notEquals=" + UPDATED_CONTACTO_SINTOMAS);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByContactoSintomasIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where contactoSintomas in DEFAULT_CONTACTO_SINTOMAS or UPDATED_CONTACTO_SINTOMAS
        defaultEncuestaEntornoShouldBeFound("contactoSintomas.in=" + DEFAULT_CONTACTO_SINTOMAS + "," + UPDATED_CONTACTO_SINTOMAS);

        // Get all the encuestaEntornoList where contactoSintomas equals to UPDATED_CONTACTO_SINTOMAS
        defaultEncuestaEntornoShouldNotBeFound("contactoSintomas.in=" + UPDATED_CONTACTO_SINTOMAS);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByContactoSintomasIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where contactoSintomas is not null
        defaultEncuestaEntornoShouldBeFound("contactoSintomas.specified=true");

        // Get all the encuestaEntornoList where contactoSintomas is null
        defaultEncuestaEntornoShouldNotBeFound("contactoSintomas.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByViajeInternacionalIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where viajeInternacional equals to DEFAULT_VIAJE_INTERNACIONAL
        defaultEncuestaEntornoShouldBeFound("viajeInternacional.equals=" + DEFAULT_VIAJE_INTERNACIONAL);

        // Get all the encuestaEntornoList where viajeInternacional equals to UPDATED_VIAJE_INTERNACIONAL
        defaultEncuestaEntornoShouldNotBeFound("viajeInternacional.equals=" + UPDATED_VIAJE_INTERNACIONAL);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByViajeInternacionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where viajeInternacional not equals to DEFAULT_VIAJE_INTERNACIONAL
        defaultEncuestaEntornoShouldNotBeFound("viajeInternacional.notEquals=" + DEFAULT_VIAJE_INTERNACIONAL);

        // Get all the encuestaEntornoList where viajeInternacional not equals to UPDATED_VIAJE_INTERNACIONAL
        defaultEncuestaEntornoShouldBeFound("viajeInternacional.notEquals=" + UPDATED_VIAJE_INTERNACIONAL);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByViajeInternacionalIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where viajeInternacional in DEFAULT_VIAJE_INTERNACIONAL or UPDATED_VIAJE_INTERNACIONAL
        defaultEncuestaEntornoShouldBeFound("viajeInternacional.in=" + DEFAULT_VIAJE_INTERNACIONAL + "," + UPDATED_VIAJE_INTERNACIONAL);

        // Get all the encuestaEntornoList where viajeInternacional equals to UPDATED_VIAJE_INTERNACIONAL
        defaultEncuestaEntornoShouldNotBeFound("viajeInternacional.in=" + UPDATED_VIAJE_INTERNACIONAL);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByViajeInternacionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where viajeInternacional is not null
        defaultEncuestaEntornoShouldBeFound("viajeInternacional.specified=true");

        // Get all the encuestaEntornoList where viajeInternacional is null
        defaultEncuestaEntornoShouldNotBeFound("viajeInternacional.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByViajeNacionalIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where viajeNacional equals to DEFAULT_VIAJE_NACIONAL
        defaultEncuestaEntornoShouldBeFound("viajeNacional.equals=" + DEFAULT_VIAJE_NACIONAL);

        // Get all the encuestaEntornoList where viajeNacional equals to UPDATED_VIAJE_NACIONAL
        defaultEncuestaEntornoShouldNotBeFound("viajeNacional.equals=" + UPDATED_VIAJE_NACIONAL);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByViajeNacionalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where viajeNacional not equals to DEFAULT_VIAJE_NACIONAL
        defaultEncuestaEntornoShouldNotBeFound("viajeNacional.notEquals=" + DEFAULT_VIAJE_NACIONAL);

        // Get all the encuestaEntornoList where viajeNacional not equals to UPDATED_VIAJE_NACIONAL
        defaultEncuestaEntornoShouldBeFound("viajeNacional.notEquals=" + UPDATED_VIAJE_NACIONAL);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByViajeNacionalIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where viajeNacional in DEFAULT_VIAJE_NACIONAL or UPDATED_VIAJE_NACIONAL
        defaultEncuestaEntornoShouldBeFound("viajeNacional.in=" + DEFAULT_VIAJE_NACIONAL + "," + UPDATED_VIAJE_NACIONAL);

        // Get all the encuestaEntornoList where viajeNacional equals to UPDATED_VIAJE_NACIONAL
        defaultEncuestaEntornoShouldNotBeFound("viajeNacional.in=" + UPDATED_VIAJE_NACIONAL);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByViajeNacionalIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where viajeNacional is not null
        defaultEncuestaEntornoShouldBeFound("viajeNacional.specified=true");

        // Get all the encuestaEntornoList where viajeNacional is null
        defaultEncuestaEntornoShouldNotBeFound("viajeNacional.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByTrabajadorSaludIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where trabajadorSalud equals to DEFAULT_TRABAJADOR_SALUD
        defaultEncuestaEntornoShouldBeFound("trabajadorSalud.equals=" + DEFAULT_TRABAJADOR_SALUD);

        // Get all the encuestaEntornoList where trabajadorSalud equals to UPDATED_TRABAJADOR_SALUD
        defaultEncuestaEntornoShouldNotBeFound("trabajadorSalud.equals=" + UPDATED_TRABAJADOR_SALUD);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByTrabajadorSaludIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where trabajadorSalud not equals to DEFAULT_TRABAJADOR_SALUD
        defaultEncuestaEntornoShouldNotBeFound("trabajadorSalud.notEquals=" + DEFAULT_TRABAJADOR_SALUD);

        // Get all the encuestaEntornoList where trabajadorSalud not equals to UPDATED_TRABAJADOR_SALUD
        defaultEncuestaEntornoShouldBeFound("trabajadorSalud.notEquals=" + UPDATED_TRABAJADOR_SALUD);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByTrabajadorSaludIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where trabajadorSalud in DEFAULT_TRABAJADOR_SALUD or UPDATED_TRABAJADOR_SALUD
        defaultEncuestaEntornoShouldBeFound("trabajadorSalud.in=" + DEFAULT_TRABAJADOR_SALUD + "," + UPDATED_TRABAJADOR_SALUD);

        // Get all the encuestaEntornoList where trabajadorSalud equals to UPDATED_TRABAJADOR_SALUD
        defaultEncuestaEntornoShouldNotBeFound("trabajadorSalud.in=" + UPDATED_TRABAJADOR_SALUD);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByTrabajadorSaludIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where trabajadorSalud is not null
        defaultEncuestaEntornoShouldBeFound("trabajadorSalud.specified=true");

        // Get all the encuestaEntornoList where trabajadorSalud is null
        defaultEncuestaEntornoShouldNotBeFound("trabajadorSalud.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByNingunaIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where ninguna equals to DEFAULT_NINGUNA
        defaultEncuestaEntornoShouldBeFound("ninguna.equals=" + DEFAULT_NINGUNA);

        // Get all the encuestaEntornoList where ninguna equals to UPDATED_NINGUNA
        defaultEncuestaEntornoShouldNotBeFound("ninguna.equals=" + UPDATED_NINGUNA);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByNingunaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where ninguna not equals to DEFAULT_NINGUNA
        defaultEncuestaEntornoShouldNotBeFound("ninguna.notEquals=" + DEFAULT_NINGUNA);

        // Get all the encuestaEntornoList where ninguna not equals to UPDATED_NINGUNA
        defaultEncuestaEntornoShouldBeFound("ninguna.notEquals=" + UPDATED_NINGUNA);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByNingunaIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where ninguna in DEFAULT_NINGUNA or UPDATED_NINGUNA
        defaultEncuestaEntornoShouldBeFound("ninguna.in=" + DEFAULT_NINGUNA + "," + UPDATED_NINGUNA);

        // Get all the encuestaEntornoList where ninguna equals to UPDATED_NINGUNA
        defaultEncuestaEntornoShouldNotBeFound("ninguna.in=" + UPDATED_NINGUNA);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByNingunaIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where ninguna is not null
        defaultEncuestaEntornoShouldBeFound("ninguna.specified=true");

        // Get all the encuestaEntornoList where ninguna is null
        defaultEncuestaEntornoShouldNotBeFound("ninguna.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where fecha equals to DEFAULT_FECHA
        defaultEncuestaEntornoShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the encuestaEntornoList where fecha equals to UPDATED_FECHA
        defaultEncuestaEntornoShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where fecha not equals to DEFAULT_FECHA
        defaultEncuestaEntornoShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the encuestaEntornoList where fecha not equals to UPDATED_FECHA
        defaultEncuestaEntornoShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultEncuestaEntornoShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the encuestaEntornoList where fecha equals to UPDATED_FECHA
        defaultEncuestaEntornoShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);

        // Get all the encuestaEntornoList where fecha is not null
        defaultEncuestaEntornoShouldBeFound("fecha.specified=true");

        // Get all the encuestaEntornoList where fecha is null
        defaultEncuestaEntornoShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaEntornosByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        encuestaEntorno.setUser(user);
        encuestaEntornoRepository.saveAndFlush(encuestaEntorno);
        Long userId = user.getId();

        // Get all the encuestaEntornoList where user equals to userId
        defaultEncuestaEntornoShouldBeFound("userId.equals=" + userId);

        // Get all the encuestaEntornoList where user equals to userId + 1
        defaultEncuestaEntornoShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEncuestaEntornoShouldBeFound(String filter) throws Exception {
        restEncuestaEntornoMockMvc.perform(get("/api/encuesta-entornos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encuestaEntorno.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactoSintomas").value(hasItem(DEFAULT_CONTACTO_SINTOMAS.booleanValue())))
            .andExpect(jsonPath("$.[*].viajeInternacional").value(hasItem(DEFAULT_VIAJE_INTERNACIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].viajeNacional").value(hasItem(DEFAULT_VIAJE_NACIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].trabajadorSalud").value(hasItem(DEFAULT_TRABAJADOR_SALUD.booleanValue())))
            .andExpect(jsonPath("$.[*].ninguna").value(hasItem(DEFAULT_NINGUNA.booleanValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

        // Check, that the count call also returns 1
        restEncuestaEntornoMockMvc.perform(get("/api/encuesta-entornos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEncuestaEntornoShouldNotBeFound(String filter) throws Exception {
        restEncuestaEntornoMockMvc.perform(get("/api/encuesta-entornos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEncuestaEntornoMockMvc.perform(get("/api/encuesta-entornos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEncuestaEntorno() throws Exception {
        // Get the encuestaEntorno
        restEncuestaEntornoMockMvc.perform(get("/api/encuesta-entornos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEncuestaEntorno() throws Exception {
        // Initialize the database
        encuestaEntornoService.save(encuestaEntorno);

        int databaseSizeBeforeUpdate = encuestaEntornoRepository.findAll().size();

        // Update the encuestaEntorno
        EncuestaEntorno updatedEncuestaEntorno = encuestaEntornoRepository.findById(encuestaEntorno.getId()).get();
        // Disconnect from session so that the updates on updatedEncuestaEntorno are not directly saved in db
        em.detach(updatedEncuestaEntorno);
        updatedEncuestaEntorno
            .contactoSintomas(UPDATED_CONTACTO_SINTOMAS)
            .viajeInternacional(UPDATED_VIAJE_INTERNACIONAL)
            .viajeNacional(UPDATED_VIAJE_NACIONAL)
            .trabajadorSalud(UPDATED_TRABAJADOR_SALUD)
            .ninguna(UPDATED_NINGUNA)
            .fecha(UPDATED_FECHA);

        restEncuestaEntornoMockMvc.perform(put("/api/encuesta-entornos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEncuestaEntorno)))
            .andExpect(status().isOk());

        // Validate the EncuestaEntorno in the database
        List<EncuestaEntorno> encuestaEntornoList = encuestaEntornoRepository.findAll();
        assertThat(encuestaEntornoList).hasSize(databaseSizeBeforeUpdate);
        EncuestaEntorno testEncuestaEntorno = encuestaEntornoList.get(encuestaEntornoList.size() - 1);
        assertThat(testEncuestaEntorno.isContactoSintomas()).isEqualTo(UPDATED_CONTACTO_SINTOMAS);
        assertThat(testEncuestaEntorno.isViajeInternacional()).isEqualTo(UPDATED_VIAJE_INTERNACIONAL);
        assertThat(testEncuestaEntorno.isViajeNacional()).isEqualTo(UPDATED_VIAJE_NACIONAL);
        assertThat(testEncuestaEntorno.isTrabajadorSalud()).isEqualTo(UPDATED_TRABAJADOR_SALUD);
        assertThat(testEncuestaEntorno.isNinguna()).isEqualTo(UPDATED_NINGUNA);
        assertThat(testEncuestaEntorno.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingEncuestaEntorno() throws Exception {
        int databaseSizeBeforeUpdate = encuestaEntornoRepository.findAll().size();

        // Create the EncuestaEntorno

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncuestaEntornoMockMvc.perform(put("/api/encuesta-entornos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(encuestaEntorno)))
            .andExpect(status().isBadRequest());

        // Validate the EncuestaEntorno in the database
        List<EncuestaEntorno> encuestaEntornoList = encuestaEntornoRepository.findAll();
        assertThat(encuestaEntornoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEncuestaEntorno() throws Exception {
        // Initialize the database
        encuestaEntornoService.save(encuestaEntorno);

        int databaseSizeBeforeDelete = encuestaEntornoRepository.findAll().size();

        // Delete the encuestaEntorno
        restEncuestaEntornoMockMvc.perform(delete("/api/encuesta-entornos/{id}", encuestaEntorno.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EncuestaEntorno> encuestaEntornoList = encuestaEntornoRepository.findAll();
        assertThat(encuestaEntornoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
