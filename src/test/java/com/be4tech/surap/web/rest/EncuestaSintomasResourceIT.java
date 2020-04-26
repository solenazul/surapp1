package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.EncuestaSintomas;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.repository.EncuestaSintomasRepository;
import com.be4tech.surap.service.EncuestaSintomasService;
import com.be4tech.surap.service.dto.EncuestaSintomasCriteria;
import com.be4tech.surap.service.EncuestaSintomasQueryService;

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
 * Integration tests for the {@link EncuestaSintomasResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class EncuestaSintomasResourceIT {

    private static final Boolean DEFAULT_FIEBRE = false;
    private static final Boolean UPDATED_FIEBRE = true;

    private static final Boolean DEFAULT_DOLOR_GARGANTA = false;
    private static final Boolean UPDATED_DOLOR_GARGANTA = true;

    private static final Boolean DEFAULT_CONGESTION_NASAL = false;
    private static final Boolean UPDATED_CONGESTION_NASAL = true;

    private static final Boolean DEFAULT_TOS = false;
    private static final Boolean UPDATED_TOS = true;

    private static final Boolean DEFAULT_DIFICULTAD_RESPIRAR = false;
    private static final Boolean UPDATED_DIFICULTAD_RESPIRAR = true;

    private static final Boolean DEFAULT_FATIGA = false;
    private static final Boolean UPDATED_FATIGA = true;

    private static final Boolean DEFAULT_ESCALOFRIO = false;
    private static final Boolean UPDATED_ESCALOFRIO = true;

    private static final Boolean DEFAULT_DOLOR_MUSCULAR = false;
    private static final Boolean UPDATED_DOLOR_MUSCULAR = true;

    private static final Boolean DEFAULT_NINGUNO = false;
    private static final Boolean UPDATED_NINGUNO = true;

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EncuestaSintomasRepository encuestaSintomasRepository;

    @Autowired
    private EncuestaSintomasService encuestaSintomasService;

    @Autowired
    private EncuestaSintomasQueryService encuestaSintomasQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEncuestaSintomasMockMvc;

    private EncuestaSintomas encuestaSintomas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EncuestaSintomas createEntity(EntityManager em) {
        EncuestaSintomas encuestaSintomas = new EncuestaSintomas()
            .fiebre(DEFAULT_FIEBRE)
            .dolorGarganta(DEFAULT_DOLOR_GARGANTA)
            .congestionNasal(DEFAULT_CONGESTION_NASAL)
            .tos(DEFAULT_TOS)
            .dificultadRespirar(DEFAULT_DIFICULTAD_RESPIRAR)
            .fatiga(DEFAULT_FATIGA)
            .escalofrio(DEFAULT_ESCALOFRIO)
            .dolorMuscular(DEFAULT_DOLOR_MUSCULAR)
            .ninguno(DEFAULT_NINGUNO)
            .fecha(DEFAULT_FECHA);
        return encuestaSintomas;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EncuestaSintomas createUpdatedEntity(EntityManager em) {
        EncuestaSintomas encuestaSintomas = new EncuestaSintomas()
            .fiebre(UPDATED_FIEBRE)
            .dolorGarganta(UPDATED_DOLOR_GARGANTA)
            .congestionNasal(UPDATED_CONGESTION_NASAL)
            .tos(UPDATED_TOS)
            .dificultadRespirar(UPDATED_DIFICULTAD_RESPIRAR)
            .fatiga(UPDATED_FATIGA)
            .escalofrio(UPDATED_ESCALOFRIO)
            .dolorMuscular(UPDATED_DOLOR_MUSCULAR)
            .ninguno(UPDATED_NINGUNO)
            .fecha(UPDATED_FECHA);
        return encuestaSintomas;
    }

    @BeforeEach
    public void initTest() {
        encuestaSintomas = createEntity(em);
    }

    @Test
    @Transactional
    public void createEncuestaSintomas() throws Exception {
        int databaseSizeBeforeCreate = encuestaSintomasRepository.findAll().size();

        // Create the EncuestaSintomas
        restEncuestaSintomasMockMvc.perform(post("/api/encuesta-sintomas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(encuestaSintomas)))
            .andExpect(status().isCreated());

        // Validate the EncuestaSintomas in the database
        List<EncuestaSintomas> encuestaSintomasList = encuestaSintomasRepository.findAll();
        assertThat(encuestaSintomasList).hasSize(databaseSizeBeforeCreate + 1);
        EncuestaSintomas testEncuestaSintomas = encuestaSintomasList.get(encuestaSintomasList.size() - 1);
        assertThat(testEncuestaSintomas.isFiebre()).isEqualTo(DEFAULT_FIEBRE);
        assertThat(testEncuestaSintomas.isDolorGarganta()).isEqualTo(DEFAULT_DOLOR_GARGANTA);
        assertThat(testEncuestaSintomas.isCongestionNasal()).isEqualTo(DEFAULT_CONGESTION_NASAL);
        assertThat(testEncuestaSintomas.isTos()).isEqualTo(DEFAULT_TOS);
        assertThat(testEncuestaSintomas.isDificultadRespirar()).isEqualTo(DEFAULT_DIFICULTAD_RESPIRAR);
        assertThat(testEncuestaSintomas.isFatiga()).isEqualTo(DEFAULT_FATIGA);
        assertThat(testEncuestaSintomas.isEscalofrio()).isEqualTo(DEFAULT_ESCALOFRIO);
        assertThat(testEncuestaSintomas.isDolorMuscular()).isEqualTo(DEFAULT_DOLOR_MUSCULAR);
        assertThat(testEncuestaSintomas.isNinguno()).isEqualTo(DEFAULT_NINGUNO);
        assertThat(testEncuestaSintomas.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createEncuestaSintomasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = encuestaSintomasRepository.findAll().size();

        // Create the EncuestaSintomas with an existing ID
        encuestaSintomas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncuestaSintomasMockMvc.perform(post("/api/encuesta-sintomas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(encuestaSintomas)))
            .andExpect(status().isBadRequest());

        // Validate the EncuestaSintomas in the database
        List<EncuestaSintomas> encuestaSintomasList = encuestaSintomasRepository.findAll();
        assertThat(encuestaSintomasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEncuestaSintomas() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList
        restEncuestaSintomasMockMvc.perform(get("/api/encuesta-sintomas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encuestaSintomas.getId().intValue())))
            .andExpect(jsonPath("$.[*].fiebre").value(hasItem(DEFAULT_FIEBRE.booleanValue())))
            .andExpect(jsonPath("$.[*].dolorGarganta").value(hasItem(DEFAULT_DOLOR_GARGANTA.booleanValue())))
            .andExpect(jsonPath("$.[*].congestionNasal").value(hasItem(DEFAULT_CONGESTION_NASAL.booleanValue())))
            .andExpect(jsonPath("$.[*].tos").value(hasItem(DEFAULT_TOS.booleanValue())))
            .andExpect(jsonPath("$.[*].dificultadRespirar").value(hasItem(DEFAULT_DIFICULTAD_RESPIRAR.booleanValue())))
            .andExpect(jsonPath("$.[*].fatiga").value(hasItem(DEFAULT_FATIGA.booleanValue())))
            .andExpect(jsonPath("$.[*].escalofrio").value(hasItem(DEFAULT_ESCALOFRIO.booleanValue())))
            .andExpect(jsonPath("$.[*].dolorMuscular").value(hasItem(DEFAULT_DOLOR_MUSCULAR.booleanValue())))
            .andExpect(jsonPath("$.[*].ninguno").value(hasItem(DEFAULT_NINGUNO.booleanValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getEncuestaSintomas() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get the encuestaSintomas
        restEncuestaSintomasMockMvc.perform(get("/api/encuesta-sintomas/{id}", encuestaSintomas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(encuestaSintomas.getId().intValue()))
            .andExpect(jsonPath("$.fiebre").value(DEFAULT_FIEBRE.booleanValue()))
            .andExpect(jsonPath("$.dolorGarganta").value(DEFAULT_DOLOR_GARGANTA.booleanValue()))
            .andExpect(jsonPath("$.congestionNasal").value(DEFAULT_CONGESTION_NASAL.booleanValue()))
            .andExpect(jsonPath("$.tos").value(DEFAULT_TOS.booleanValue()))
            .andExpect(jsonPath("$.dificultadRespirar").value(DEFAULT_DIFICULTAD_RESPIRAR.booleanValue()))
            .andExpect(jsonPath("$.fatiga").value(DEFAULT_FATIGA.booleanValue()))
            .andExpect(jsonPath("$.escalofrio").value(DEFAULT_ESCALOFRIO.booleanValue()))
            .andExpect(jsonPath("$.dolorMuscular").value(DEFAULT_DOLOR_MUSCULAR.booleanValue()))
            .andExpect(jsonPath("$.ninguno").value(DEFAULT_NINGUNO.booleanValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }


    @Test
    @Transactional
    public void getEncuestaSintomasByIdFiltering() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        Long id = encuestaSintomas.getId();

        defaultEncuestaSintomasShouldBeFound("id.equals=" + id);
        defaultEncuestaSintomasShouldNotBeFound("id.notEquals=" + id);

        defaultEncuestaSintomasShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEncuestaSintomasShouldNotBeFound("id.greaterThan=" + id);

        defaultEncuestaSintomasShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEncuestaSintomasShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEncuestaSintomasByFiebreIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where fiebre equals to DEFAULT_FIEBRE
        defaultEncuestaSintomasShouldBeFound("fiebre.equals=" + DEFAULT_FIEBRE);

        // Get all the encuestaSintomasList where fiebre equals to UPDATED_FIEBRE
        defaultEncuestaSintomasShouldNotBeFound("fiebre.equals=" + UPDATED_FIEBRE);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByFiebreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where fiebre not equals to DEFAULT_FIEBRE
        defaultEncuestaSintomasShouldNotBeFound("fiebre.notEquals=" + DEFAULT_FIEBRE);

        // Get all the encuestaSintomasList where fiebre not equals to UPDATED_FIEBRE
        defaultEncuestaSintomasShouldBeFound("fiebre.notEquals=" + UPDATED_FIEBRE);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByFiebreIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where fiebre in DEFAULT_FIEBRE or UPDATED_FIEBRE
        defaultEncuestaSintomasShouldBeFound("fiebre.in=" + DEFAULT_FIEBRE + "," + UPDATED_FIEBRE);

        // Get all the encuestaSintomasList where fiebre equals to UPDATED_FIEBRE
        defaultEncuestaSintomasShouldNotBeFound("fiebre.in=" + UPDATED_FIEBRE);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByFiebreIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where fiebre is not null
        defaultEncuestaSintomasShouldBeFound("fiebre.specified=true");

        // Get all the encuestaSintomasList where fiebre is null
        defaultEncuestaSintomasShouldNotBeFound("fiebre.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByDolorGargantaIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where dolorGarganta equals to DEFAULT_DOLOR_GARGANTA
        defaultEncuestaSintomasShouldBeFound("dolorGarganta.equals=" + DEFAULT_DOLOR_GARGANTA);

        // Get all the encuestaSintomasList where dolorGarganta equals to UPDATED_DOLOR_GARGANTA
        defaultEncuestaSintomasShouldNotBeFound("dolorGarganta.equals=" + UPDATED_DOLOR_GARGANTA);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByDolorGargantaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where dolorGarganta not equals to DEFAULT_DOLOR_GARGANTA
        defaultEncuestaSintomasShouldNotBeFound("dolorGarganta.notEquals=" + DEFAULT_DOLOR_GARGANTA);

        // Get all the encuestaSintomasList where dolorGarganta not equals to UPDATED_DOLOR_GARGANTA
        defaultEncuestaSintomasShouldBeFound("dolorGarganta.notEquals=" + UPDATED_DOLOR_GARGANTA);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByDolorGargantaIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where dolorGarganta in DEFAULT_DOLOR_GARGANTA or UPDATED_DOLOR_GARGANTA
        defaultEncuestaSintomasShouldBeFound("dolorGarganta.in=" + DEFAULT_DOLOR_GARGANTA + "," + UPDATED_DOLOR_GARGANTA);

        // Get all the encuestaSintomasList where dolorGarganta equals to UPDATED_DOLOR_GARGANTA
        defaultEncuestaSintomasShouldNotBeFound("dolorGarganta.in=" + UPDATED_DOLOR_GARGANTA);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByDolorGargantaIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where dolorGarganta is not null
        defaultEncuestaSintomasShouldBeFound("dolorGarganta.specified=true");

        // Get all the encuestaSintomasList where dolorGarganta is null
        defaultEncuestaSintomasShouldNotBeFound("dolorGarganta.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByCongestionNasalIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where congestionNasal equals to DEFAULT_CONGESTION_NASAL
        defaultEncuestaSintomasShouldBeFound("congestionNasal.equals=" + DEFAULT_CONGESTION_NASAL);

        // Get all the encuestaSintomasList where congestionNasal equals to UPDATED_CONGESTION_NASAL
        defaultEncuestaSintomasShouldNotBeFound("congestionNasal.equals=" + UPDATED_CONGESTION_NASAL);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByCongestionNasalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where congestionNasal not equals to DEFAULT_CONGESTION_NASAL
        defaultEncuestaSintomasShouldNotBeFound("congestionNasal.notEquals=" + DEFAULT_CONGESTION_NASAL);

        // Get all the encuestaSintomasList where congestionNasal not equals to UPDATED_CONGESTION_NASAL
        defaultEncuestaSintomasShouldBeFound("congestionNasal.notEquals=" + UPDATED_CONGESTION_NASAL);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByCongestionNasalIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where congestionNasal in DEFAULT_CONGESTION_NASAL or UPDATED_CONGESTION_NASAL
        defaultEncuestaSintomasShouldBeFound("congestionNasal.in=" + DEFAULT_CONGESTION_NASAL + "," + UPDATED_CONGESTION_NASAL);

        // Get all the encuestaSintomasList where congestionNasal equals to UPDATED_CONGESTION_NASAL
        defaultEncuestaSintomasShouldNotBeFound("congestionNasal.in=" + UPDATED_CONGESTION_NASAL);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByCongestionNasalIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where congestionNasal is not null
        defaultEncuestaSintomasShouldBeFound("congestionNasal.specified=true");

        // Get all the encuestaSintomasList where congestionNasal is null
        defaultEncuestaSintomasShouldNotBeFound("congestionNasal.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByTosIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where tos equals to DEFAULT_TOS
        defaultEncuestaSintomasShouldBeFound("tos.equals=" + DEFAULT_TOS);

        // Get all the encuestaSintomasList where tos equals to UPDATED_TOS
        defaultEncuestaSintomasShouldNotBeFound("tos.equals=" + UPDATED_TOS);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByTosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where tos not equals to DEFAULT_TOS
        defaultEncuestaSintomasShouldNotBeFound("tos.notEquals=" + DEFAULT_TOS);

        // Get all the encuestaSintomasList where tos not equals to UPDATED_TOS
        defaultEncuestaSintomasShouldBeFound("tos.notEquals=" + UPDATED_TOS);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByTosIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where tos in DEFAULT_TOS or UPDATED_TOS
        defaultEncuestaSintomasShouldBeFound("tos.in=" + DEFAULT_TOS + "," + UPDATED_TOS);

        // Get all the encuestaSintomasList where tos equals to UPDATED_TOS
        defaultEncuestaSintomasShouldNotBeFound("tos.in=" + UPDATED_TOS);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByTosIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where tos is not null
        defaultEncuestaSintomasShouldBeFound("tos.specified=true");

        // Get all the encuestaSintomasList where tos is null
        defaultEncuestaSintomasShouldNotBeFound("tos.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByDificultadRespirarIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where dificultadRespirar equals to DEFAULT_DIFICULTAD_RESPIRAR
        defaultEncuestaSintomasShouldBeFound("dificultadRespirar.equals=" + DEFAULT_DIFICULTAD_RESPIRAR);

        // Get all the encuestaSintomasList where dificultadRespirar equals to UPDATED_DIFICULTAD_RESPIRAR
        defaultEncuestaSintomasShouldNotBeFound("dificultadRespirar.equals=" + UPDATED_DIFICULTAD_RESPIRAR);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByDificultadRespirarIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where dificultadRespirar not equals to DEFAULT_DIFICULTAD_RESPIRAR
        defaultEncuestaSintomasShouldNotBeFound("dificultadRespirar.notEquals=" + DEFAULT_DIFICULTAD_RESPIRAR);

        // Get all the encuestaSintomasList where dificultadRespirar not equals to UPDATED_DIFICULTAD_RESPIRAR
        defaultEncuestaSintomasShouldBeFound("dificultadRespirar.notEquals=" + UPDATED_DIFICULTAD_RESPIRAR);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByDificultadRespirarIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where dificultadRespirar in DEFAULT_DIFICULTAD_RESPIRAR or UPDATED_DIFICULTAD_RESPIRAR
        defaultEncuestaSintomasShouldBeFound("dificultadRespirar.in=" + DEFAULT_DIFICULTAD_RESPIRAR + "," + UPDATED_DIFICULTAD_RESPIRAR);

        // Get all the encuestaSintomasList where dificultadRespirar equals to UPDATED_DIFICULTAD_RESPIRAR
        defaultEncuestaSintomasShouldNotBeFound("dificultadRespirar.in=" + UPDATED_DIFICULTAD_RESPIRAR);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByDificultadRespirarIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where dificultadRespirar is not null
        defaultEncuestaSintomasShouldBeFound("dificultadRespirar.specified=true");

        // Get all the encuestaSintomasList where dificultadRespirar is null
        defaultEncuestaSintomasShouldNotBeFound("dificultadRespirar.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByFatigaIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where fatiga equals to DEFAULT_FATIGA
        defaultEncuestaSintomasShouldBeFound("fatiga.equals=" + DEFAULT_FATIGA);

        // Get all the encuestaSintomasList where fatiga equals to UPDATED_FATIGA
        defaultEncuestaSintomasShouldNotBeFound("fatiga.equals=" + UPDATED_FATIGA);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByFatigaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where fatiga not equals to DEFAULT_FATIGA
        defaultEncuestaSintomasShouldNotBeFound("fatiga.notEquals=" + DEFAULT_FATIGA);

        // Get all the encuestaSintomasList where fatiga not equals to UPDATED_FATIGA
        defaultEncuestaSintomasShouldBeFound("fatiga.notEquals=" + UPDATED_FATIGA);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByFatigaIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where fatiga in DEFAULT_FATIGA or UPDATED_FATIGA
        defaultEncuestaSintomasShouldBeFound("fatiga.in=" + DEFAULT_FATIGA + "," + UPDATED_FATIGA);

        // Get all the encuestaSintomasList where fatiga equals to UPDATED_FATIGA
        defaultEncuestaSintomasShouldNotBeFound("fatiga.in=" + UPDATED_FATIGA);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByFatigaIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where fatiga is not null
        defaultEncuestaSintomasShouldBeFound("fatiga.specified=true");

        // Get all the encuestaSintomasList where fatiga is null
        defaultEncuestaSintomasShouldNotBeFound("fatiga.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByEscalofrioIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where escalofrio equals to DEFAULT_ESCALOFRIO
        defaultEncuestaSintomasShouldBeFound("escalofrio.equals=" + DEFAULT_ESCALOFRIO);

        // Get all the encuestaSintomasList where escalofrio equals to UPDATED_ESCALOFRIO
        defaultEncuestaSintomasShouldNotBeFound("escalofrio.equals=" + UPDATED_ESCALOFRIO);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByEscalofrioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where escalofrio not equals to DEFAULT_ESCALOFRIO
        defaultEncuestaSintomasShouldNotBeFound("escalofrio.notEquals=" + DEFAULT_ESCALOFRIO);

        // Get all the encuestaSintomasList where escalofrio not equals to UPDATED_ESCALOFRIO
        defaultEncuestaSintomasShouldBeFound("escalofrio.notEquals=" + UPDATED_ESCALOFRIO);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByEscalofrioIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where escalofrio in DEFAULT_ESCALOFRIO or UPDATED_ESCALOFRIO
        defaultEncuestaSintomasShouldBeFound("escalofrio.in=" + DEFAULT_ESCALOFRIO + "," + UPDATED_ESCALOFRIO);

        // Get all the encuestaSintomasList where escalofrio equals to UPDATED_ESCALOFRIO
        defaultEncuestaSintomasShouldNotBeFound("escalofrio.in=" + UPDATED_ESCALOFRIO);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByEscalofrioIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where escalofrio is not null
        defaultEncuestaSintomasShouldBeFound("escalofrio.specified=true");

        // Get all the encuestaSintomasList where escalofrio is null
        defaultEncuestaSintomasShouldNotBeFound("escalofrio.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByDolorMuscularIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where dolorMuscular equals to DEFAULT_DOLOR_MUSCULAR
        defaultEncuestaSintomasShouldBeFound("dolorMuscular.equals=" + DEFAULT_DOLOR_MUSCULAR);

        // Get all the encuestaSintomasList where dolorMuscular equals to UPDATED_DOLOR_MUSCULAR
        defaultEncuestaSintomasShouldNotBeFound("dolorMuscular.equals=" + UPDATED_DOLOR_MUSCULAR);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByDolorMuscularIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where dolorMuscular not equals to DEFAULT_DOLOR_MUSCULAR
        defaultEncuestaSintomasShouldNotBeFound("dolorMuscular.notEquals=" + DEFAULT_DOLOR_MUSCULAR);

        // Get all the encuestaSintomasList where dolorMuscular not equals to UPDATED_DOLOR_MUSCULAR
        defaultEncuestaSintomasShouldBeFound("dolorMuscular.notEquals=" + UPDATED_DOLOR_MUSCULAR);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByDolorMuscularIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where dolorMuscular in DEFAULT_DOLOR_MUSCULAR or UPDATED_DOLOR_MUSCULAR
        defaultEncuestaSintomasShouldBeFound("dolorMuscular.in=" + DEFAULT_DOLOR_MUSCULAR + "," + UPDATED_DOLOR_MUSCULAR);

        // Get all the encuestaSintomasList where dolorMuscular equals to UPDATED_DOLOR_MUSCULAR
        defaultEncuestaSintomasShouldNotBeFound("dolorMuscular.in=" + UPDATED_DOLOR_MUSCULAR);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByDolorMuscularIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where dolorMuscular is not null
        defaultEncuestaSintomasShouldBeFound("dolorMuscular.specified=true");

        // Get all the encuestaSintomasList where dolorMuscular is null
        defaultEncuestaSintomasShouldNotBeFound("dolorMuscular.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByNingunoIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where ninguno equals to DEFAULT_NINGUNO
        defaultEncuestaSintomasShouldBeFound("ninguno.equals=" + DEFAULT_NINGUNO);

        // Get all the encuestaSintomasList where ninguno equals to UPDATED_NINGUNO
        defaultEncuestaSintomasShouldNotBeFound("ninguno.equals=" + UPDATED_NINGUNO);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByNingunoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where ninguno not equals to DEFAULT_NINGUNO
        defaultEncuestaSintomasShouldNotBeFound("ninguno.notEquals=" + DEFAULT_NINGUNO);

        // Get all the encuestaSintomasList where ninguno not equals to UPDATED_NINGUNO
        defaultEncuestaSintomasShouldBeFound("ninguno.notEquals=" + UPDATED_NINGUNO);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByNingunoIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where ninguno in DEFAULT_NINGUNO or UPDATED_NINGUNO
        defaultEncuestaSintomasShouldBeFound("ninguno.in=" + DEFAULT_NINGUNO + "," + UPDATED_NINGUNO);

        // Get all the encuestaSintomasList where ninguno equals to UPDATED_NINGUNO
        defaultEncuestaSintomasShouldNotBeFound("ninguno.in=" + UPDATED_NINGUNO);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByNingunoIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where ninguno is not null
        defaultEncuestaSintomasShouldBeFound("ninguno.specified=true");

        // Get all the encuestaSintomasList where ninguno is null
        defaultEncuestaSintomasShouldNotBeFound("ninguno.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where fecha equals to DEFAULT_FECHA
        defaultEncuestaSintomasShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the encuestaSintomasList where fecha equals to UPDATED_FECHA
        defaultEncuestaSintomasShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where fecha not equals to DEFAULT_FECHA
        defaultEncuestaSintomasShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the encuestaSintomasList where fecha not equals to UPDATED_FECHA
        defaultEncuestaSintomasShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultEncuestaSintomasShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the encuestaSintomasList where fecha equals to UPDATED_FECHA
        defaultEncuestaSintomasShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);

        // Get all the encuestaSintomasList where fecha is not null
        defaultEncuestaSintomasShouldBeFound("fecha.specified=true");

        // Get all the encuestaSintomasList where fecha is null
        defaultEncuestaSintomasShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllEncuestaSintomasByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        encuestaSintomas.setUser(user);
        encuestaSintomasRepository.saveAndFlush(encuestaSintomas);
        Long userId = user.getId();

        // Get all the encuestaSintomasList where user equals to userId
        defaultEncuestaSintomasShouldBeFound("userId.equals=" + userId);

        // Get all the encuestaSintomasList where user equals to userId + 1
        defaultEncuestaSintomasShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEncuestaSintomasShouldBeFound(String filter) throws Exception {
        restEncuestaSintomasMockMvc.perform(get("/api/encuesta-sintomas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encuestaSintomas.getId().intValue())))
            .andExpect(jsonPath("$.[*].fiebre").value(hasItem(DEFAULT_FIEBRE.booleanValue())))
            .andExpect(jsonPath("$.[*].dolorGarganta").value(hasItem(DEFAULT_DOLOR_GARGANTA.booleanValue())))
            .andExpect(jsonPath("$.[*].congestionNasal").value(hasItem(DEFAULT_CONGESTION_NASAL.booleanValue())))
            .andExpect(jsonPath("$.[*].tos").value(hasItem(DEFAULT_TOS.booleanValue())))
            .andExpect(jsonPath("$.[*].dificultadRespirar").value(hasItem(DEFAULT_DIFICULTAD_RESPIRAR.booleanValue())))
            .andExpect(jsonPath("$.[*].fatiga").value(hasItem(DEFAULT_FATIGA.booleanValue())))
            .andExpect(jsonPath("$.[*].escalofrio").value(hasItem(DEFAULT_ESCALOFRIO.booleanValue())))
            .andExpect(jsonPath("$.[*].dolorMuscular").value(hasItem(DEFAULT_DOLOR_MUSCULAR.booleanValue())))
            .andExpect(jsonPath("$.[*].ninguno").value(hasItem(DEFAULT_NINGUNO.booleanValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

        // Check, that the count call also returns 1
        restEncuestaSintomasMockMvc.perform(get("/api/encuesta-sintomas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEncuestaSintomasShouldNotBeFound(String filter) throws Exception {
        restEncuestaSintomasMockMvc.perform(get("/api/encuesta-sintomas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEncuestaSintomasMockMvc.perform(get("/api/encuesta-sintomas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEncuestaSintomas() throws Exception {
        // Get the encuestaSintomas
        restEncuestaSintomasMockMvc.perform(get("/api/encuesta-sintomas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEncuestaSintomas() throws Exception {
        // Initialize the database
        encuestaSintomasService.save(encuestaSintomas);

        int databaseSizeBeforeUpdate = encuestaSintomasRepository.findAll().size();

        // Update the encuestaSintomas
        EncuestaSintomas updatedEncuestaSintomas = encuestaSintomasRepository.findById(encuestaSintomas.getId()).get();
        // Disconnect from session so that the updates on updatedEncuestaSintomas are not directly saved in db
        em.detach(updatedEncuestaSintomas);
        updatedEncuestaSintomas
            .fiebre(UPDATED_FIEBRE)
            .dolorGarganta(UPDATED_DOLOR_GARGANTA)
            .congestionNasal(UPDATED_CONGESTION_NASAL)
            .tos(UPDATED_TOS)
            .dificultadRespirar(UPDATED_DIFICULTAD_RESPIRAR)
            .fatiga(UPDATED_FATIGA)
            .escalofrio(UPDATED_ESCALOFRIO)
            .dolorMuscular(UPDATED_DOLOR_MUSCULAR)
            .ninguno(UPDATED_NINGUNO)
            .fecha(UPDATED_FECHA);

        restEncuestaSintomasMockMvc.perform(put("/api/encuesta-sintomas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEncuestaSintomas)))
            .andExpect(status().isOk());

        // Validate the EncuestaSintomas in the database
        List<EncuestaSintomas> encuestaSintomasList = encuestaSintomasRepository.findAll();
        assertThat(encuestaSintomasList).hasSize(databaseSizeBeforeUpdate);
        EncuestaSintomas testEncuestaSintomas = encuestaSintomasList.get(encuestaSintomasList.size() - 1);
        assertThat(testEncuestaSintomas.isFiebre()).isEqualTo(UPDATED_FIEBRE);
        assertThat(testEncuestaSintomas.isDolorGarganta()).isEqualTo(UPDATED_DOLOR_GARGANTA);
        assertThat(testEncuestaSintomas.isCongestionNasal()).isEqualTo(UPDATED_CONGESTION_NASAL);
        assertThat(testEncuestaSintomas.isTos()).isEqualTo(UPDATED_TOS);
        assertThat(testEncuestaSintomas.isDificultadRespirar()).isEqualTo(UPDATED_DIFICULTAD_RESPIRAR);
        assertThat(testEncuestaSintomas.isFatiga()).isEqualTo(UPDATED_FATIGA);
        assertThat(testEncuestaSintomas.isEscalofrio()).isEqualTo(UPDATED_ESCALOFRIO);
        assertThat(testEncuestaSintomas.isDolorMuscular()).isEqualTo(UPDATED_DOLOR_MUSCULAR);
        assertThat(testEncuestaSintomas.isNinguno()).isEqualTo(UPDATED_NINGUNO);
        assertThat(testEncuestaSintomas.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingEncuestaSintomas() throws Exception {
        int databaseSizeBeforeUpdate = encuestaSintomasRepository.findAll().size();

        // Create the EncuestaSintomas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncuestaSintomasMockMvc.perform(put("/api/encuesta-sintomas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(encuestaSintomas)))
            .andExpect(status().isBadRequest());

        // Validate the EncuestaSintomas in the database
        List<EncuestaSintomas> encuestaSintomasList = encuestaSintomasRepository.findAll();
        assertThat(encuestaSintomasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEncuestaSintomas() throws Exception {
        // Initialize the database
        encuestaSintomasService.save(encuestaSintomas);

        int databaseSizeBeforeDelete = encuestaSintomasRepository.findAll().size();

        // Delete the encuestaSintomas
        restEncuestaSintomasMockMvc.perform(delete("/api/encuesta-sintomas/{id}", encuestaSintomas.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EncuestaSintomas> encuestaSintomasList = encuestaSintomasRepository.findAll();
        assertThat(encuestaSintomasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
