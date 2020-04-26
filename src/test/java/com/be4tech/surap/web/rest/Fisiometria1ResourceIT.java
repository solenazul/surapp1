package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.Fisiometria1;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.repository.Fisiometria1Repository;
import com.be4tech.surap.service.Fisiometria1Service;
import com.be4tech.surap.service.dto.Fisiometria1Criteria;
import com.be4tech.surap.service.Fisiometria1QueryService;

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
 * Integration tests for the {@link Fisiometria1Resource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class Fisiometria1ResourceIT {

    private static final Integer DEFAULT_RITMO_CARDIACO = 1;
    private static final Integer UPDATED_RITMO_CARDIACO = 2;
    private static final Integer SMALLER_RITMO_CARDIACO = 1 - 1;

    private static final Integer DEFAULT_RITMO_RESPIRATORIO = 1;
    private static final Integer UPDATED_RITMO_RESPIRATORIO = 2;
    private static final Integer SMALLER_RITMO_RESPIRATORIO = 1 - 1;

    private static final Integer DEFAULT_OXIMETRIA = 1;
    private static final Integer UPDATED_OXIMETRIA = 2;
    private static final Integer SMALLER_OXIMETRIA = 1 - 1;

    private static final Integer DEFAULT_PRESION_ARTERIAL_SISTOLICA = 1;
    private static final Integer UPDATED_PRESION_ARTERIAL_SISTOLICA = 2;
    private static final Integer SMALLER_PRESION_ARTERIAL_SISTOLICA = 1 - 1;

    private static final Integer DEFAULT_PRESION_ARTERIAL_DIASTOLICA = 1;
    private static final Integer UPDATED_PRESION_ARTERIAL_DIASTOLICA = 2;
    private static final Integer SMALLER_PRESION_ARTERIAL_DIASTOLICA = 1 - 1;

    private static final Float DEFAULT_TEMPERATURA = 1F;
    private static final Float UPDATED_TEMPERATURA = 2F;
    private static final Float SMALLER_TEMPERATURA = 1F - 1F;

    private static final Instant DEFAULT_TIME_INSTANT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_INSTANT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private Fisiometria1Repository fisiometria1Repository;

    @Autowired
    private Fisiometria1Service fisiometria1Service;

    @Autowired
    private Fisiometria1QueryService fisiometria1QueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFisiometria1MockMvc;

    private Fisiometria1 fisiometria1;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fisiometria1 createEntity(EntityManager em) {
        Fisiometria1 fisiometria1 = new Fisiometria1()
            .ritmoCardiaco(DEFAULT_RITMO_CARDIACO)
            .ritmoRespiratorio(DEFAULT_RITMO_RESPIRATORIO)
            .oximetria(DEFAULT_OXIMETRIA)
            .presionArterialSistolica(DEFAULT_PRESION_ARTERIAL_SISTOLICA)
            .presionArterialDiastolica(DEFAULT_PRESION_ARTERIAL_DIASTOLICA)
            .temperatura(DEFAULT_TEMPERATURA)
            .timeInstant(DEFAULT_TIME_INSTANT);
        return fisiometria1;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fisiometria1 createUpdatedEntity(EntityManager em) {
        Fisiometria1 fisiometria1 = new Fisiometria1()
            .ritmoCardiaco(UPDATED_RITMO_CARDIACO)
            .ritmoRespiratorio(UPDATED_RITMO_RESPIRATORIO)
            .oximetria(UPDATED_OXIMETRIA)
            .presionArterialSistolica(UPDATED_PRESION_ARTERIAL_SISTOLICA)
            .presionArterialDiastolica(UPDATED_PRESION_ARTERIAL_DIASTOLICA)
            .temperatura(UPDATED_TEMPERATURA)
            .timeInstant(UPDATED_TIME_INSTANT);
        return fisiometria1;
    }

    @BeforeEach
    public void initTest() {
        fisiometria1 = createEntity(em);
    }

    @Test
    @Transactional
    public void createFisiometria1() throws Exception {
        int databaseSizeBeforeCreate = fisiometria1Repository.findAll().size();

        // Create the Fisiometria1
        restFisiometria1MockMvc.perform(post("/api/fisiometria-1-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fisiometria1)))
            .andExpect(status().isCreated());

        // Validate the Fisiometria1 in the database
        List<Fisiometria1> fisiometria1List = fisiometria1Repository.findAll();
        assertThat(fisiometria1List).hasSize(databaseSizeBeforeCreate + 1);
        Fisiometria1 testFisiometria1 = fisiometria1List.get(fisiometria1List.size() - 1);
        assertThat(testFisiometria1.getRitmoCardiaco()).isEqualTo(DEFAULT_RITMO_CARDIACO);
        assertThat(testFisiometria1.getRitmoRespiratorio()).isEqualTo(DEFAULT_RITMO_RESPIRATORIO);
        assertThat(testFisiometria1.getOximetria()).isEqualTo(DEFAULT_OXIMETRIA);
        assertThat(testFisiometria1.getPresionArterialSistolica()).isEqualTo(DEFAULT_PRESION_ARTERIAL_SISTOLICA);
        assertThat(testFisiometria1.getPresionArterialDiastolica()).isEqualTo(DEFAULT_PRESION_ARTERIAL_DIASTOLICA);
        assertThat(testFisiometria1.getTemperatura()).isEqualTo(DEFAULT_TEMPERATURA);
        assertThat(testFisiometria1.getTimeInstant()).isEqualTo(DEFAULT_TIME_INSTANT);
    }

    @Test
    @Transactional
    public void createFisiometria1WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fisiometria1Repository.findAll().size();

        // Create the Fisiometria1 with an existing ID
        fisiometria1.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFisiometria1MockMvc.perform(post("/api/fisiometria-1-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fisiometria1)))
            .andExpect(status().isBadRequest());

        // Validate the Fisiometria1 in the database
        List<Fisiometria1> fisiometria1List = fisiometria1Repository.findAll();
        assertThat(fisiometria1List).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFisiometria1S() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List
        restFisiometria1MockMvc.perform(get("/api/fisiometria-1-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fisiometria1.getId().intValue())))
            .andExpect(jsonPath("$.[*].ritmoCardiaco").value(hasItem(DEFAULT_RITMO_CARDIACO)))
            .andExpect(jsonPath("$.[*].ritmoRespiratorio").value(hasItem(DEFAULT_RITMO_RESPIRATORIO)))
            .andExpect(jsonPath("$.[*].oximetria").value(hasItem(DEFAULT_OXIMETRIA)))
            .andExpect(jsonPath("$.[*].presionArterialSistolica").value(hasItem(DEFAULT_PRESION_ARTERIAL_SISTOLICA)))
            .andExpect(jsonPath("$.[*].presionArterialDiastolica").value(hasItem(DEFAULT_PRESION_ARTERIAL_DIASTOLICA)))
            .andExpect(jsonPath("$.[*].temperatura").value(hasItem(DEFAULT_TEMPERATURA.doubleValue())))
            .andExpect(jsonPath("$.[*].timeInstant").value(hasItem(DEFAULT_TIME_INSTANT.toString())));
    }
    
    @Test
    @Transactional
    public void getFisiometria1() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get the fisiometria1
        restFisiometria1MockMvc.perform(get("/api/fisiometria-1-s/{id}", fisiometria1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fisiometria1.getId().intValue()))
            .andExpect(jsonPath("$.ritmoCardiaco").value(DEFAULT_RITMO_CARDIACO))
            .andExpect(jsonPath("$.ritmoRespiratorio").value(DEFAULT_RITMO_RESPIRATORIO))
            .andExpect(jsonPath("$.oximetria").value(DEFAULT_OXIMETRIA))
            .andExpect(jsonPath("$.presionArterialSistolica").value(DEFAULT_PRESION_ARTERIAL_SISTOLICA))
            .andExpect(jsonPath("$.presionArterialDiastolica").value(DEFAULT_PRESION_ARTERIAL_DIASTOLICA))
            .andExpect(jsonPath("$.temperatura").value(DEFAULT_TEMPERATURA.doubleValue()))
            .andExpect(jsonPath("$.timeInstant").value(DEFAULT_TIME_INSTANT.toString()));
    }


    @Test
    @Transactional
    public void getFisiometria1SByIdFiltering() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        Long id = fisiometria1.getId();

        defaultFisiometria1ShouldBeFound("id.equals=" + id);
        defaultFisiometria1ShouldNotBeFound("id.notEquals=" + id);

        defaultFisiometria1ShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFisiometria1ShouldNotBeFound("id.greaterThan=" + id);

        defaultFisiometria1ShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFisiometria1ShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoCardiacoIsEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoCardiaco equals to DEFAULT_RITMO_CARDIACO
        defaultFisiometria1ShouldBeFound("ritmoCardiaco.equals=" + DEFAULT_RITMO_CARDIACO);

        // Get all the fisiometria1List where ritmoCardiaco equals to UPDATED_RITMO_CARDIACO
        defaultFisiometria1ShouldNotBeFound("ritmoCardiaco.equals=" + UPDATED_RITMO_CARDIACO);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoCardiacoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoCardiaco not equals to DEFAULT_RITMO_CARDIACO
        defaultFisiometria1ShouldNotBeFound("ritmoCardiaco.notEquals=" + DEFAULT_RITMO_CARDIACO);

        // Get all the fisiometria1List where ritmoCardiaco not equals to UPDATED_RITMO_CARDIACO
        defaultFisiometria1ShouldBeFound("ritmoCardiaco.notEquals=" + UPDATED_RITMO_CARDIACO);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoCardiacoIsInShouldWork() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoCardiaco in DEFAULT_RITMO_CARDIACO or UPDATED_RITMO_CARDIACO
        defaultFisiometria1ShouldBeFound("ritmoCardiaco.in=" + DEFAULT_RITMO_CARDIACO + "," + UPDATED_RITMO_CARDIACO);

        // Get all the fisiometria1List where ritmoCardiaco equals to UPDATED_RITMO_CARDIACO
        defaultFisiometria1ShouldNotBeFound("ritmoCardiaco.in=" + UPDATED_RITMO_CARDIACO);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoCardiacoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoCardiaco is not null
        defaultFisiometria1ShouldBeFound("ritmoCardiaco.specified=true");

        // Get all the fisiometria1List where ritmoCardiaco is null
        defaultFisiometria1ShouldNotBeFound("ritmoCardiaco.specified=false");
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoCardiacoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoCardiaco is greater than or equal to DEFAULT_RITMO_CARDIACO
        defaultFisiometria1ShouldBeFound("ritmoCardiaco.greaterThanOrEqual=" + DEFAULT_RITMO_CARDIACO);

        // Get all the fisiometria1List where ritmoCardiaco is greater than or equal to UPDATED_RITMO_CARDIACO
        defaultFisiometria1ShouldNotBeFound("ritmoCardiaco.greaterThanOrEqual=" + UPDATED_RITMO_CARDIACO);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoCardiacoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoCardiaco is less than or equal to DEFAULT_RITMO_CARDIACO
        defaultFisiometria1ShouldBeFound("ritmoCardiaco.lessThanOrEqual=" + DEFAULT_RITMO_CARDIACO);

        // Get all the fisiometria1List where ritmoCardiaco is less than or equal to SMALLER_RITMO_CARDIACO
        defaultFisiometria1ShouldNotBeFound("ritmoCardiaco.lessThanOrEqual=" + SMALLER_RITMO_CARDIACO);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoCardiacoIsLessThanSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoCardiaco is less than DEFAULT_RITMO_CARDIACO
        defaultFisiometria1ShouldNotBeFound("ritmoCardiaco.lessThan=" + DEFAULT_RITMO_CARDIACO);

        // Get all the fisiometria1List where ritmoCardiaco is less than UPDATED_RITMO_CARDIACO
        defaultFisiometria1ShouldBeFound("ritmoCardiaco.lessThan=" + UPDATED_RITMO_CARDIACO);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoCardiacoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoCardiaco is greater than DEFAULT_RITMO_CARDIACO
        defaultFisiometria1ShouldNotBeFound("ritmoCardiaco.greaterThan=" + DEFAULT_RITMO_CARDIACO);

        // Get all the fisiometria1List where ritmoCardiaco is greater than SMALLER_RITMO_CARDIACO
        defaultFisiometria1ShouldBeFound("ritmoCardiaco.greaterThan=" + SMALLER_RITMO_CARDIACO);
    }


    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoRespiratorioIsEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoRespiratorio equals to DEFAULT_RITMO_RESPIRATORIO
        defaultFisiometria1ShouldBeFound("ritmoRespiratorio.equals=" + DEFAULT_RITMO_RESPIRATORIO);

        // Get all the fisiometria1List where ritmoRespiratorio equals to UPDATED_RITMO_RESPIRATORIO
        defaultFisiometria1ShouldNotBeFound("ritmoRespiratorio.equals=" + UPDATED_RITMO_RESPIRATORIO);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoRespiratorioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoRespiratorio not equals to DEFAULT_RITMO_RESPIRATORIO
        defaultFisiometria1ShouldNotBeFound("ritmoRespiratorio.notEquals=" + DEFAULT_RITMO_RESPIRATORIO);

        // Get all the fisiometria1List where ritmoRespiratorio not equals to UPDATED_RITMO_RESPIRATORIO
        defaultFisiometria1ShouldBeFound("ritmoRespiratorio.notEquals=" + UPDATED_RITMO_RESPIRATORIO);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoRespiratorioIsInShouldWork() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoRespiratorio in DEFAULT_RITMO_RESPIRATORIO or UPDATED_RITMO_RESPIRATORIO
        defaultFisiometria1ShouldBeFound("ritmoRespiratorio.in=" + DEFAULT_RITMO_RESPIRATORIO + "," + UPDATED_RITMO_RESPIRATORIO);

        // Get all the fisiometria1List where ritmoRespiratorio equals to UPDATED_RITMO_RESPIRATORIO
        defaultFisiometria1ShouldNotBeFound("ritmoRespiratorio.in=" + UPDATED_RITMO_RESPIRATORIO);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoRespiratorioIsNullOrNotNull() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoRespiratorio is not null
        defaultFisiometria1ShouldBeFound("ritmoRespiratorio.specified=true");

        // Get all the fisiometria1List where ritmoRespiratorio is null
        defaultFisiometria1ShouldNotBeFound("ritmoRespiratorio.specified=false");
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoRespiratorioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoRespiratorio is greater than or equal to DEFAULT_RITMO_RESPIRATORIO
        defaultFisiometria1ShouldBeFound("ritmoRespiratorio.greaterThanOrEqual=" + DEFAULT_RITMO_RESPIRATORIO);

        // Get all the fisiometria1List where ritmoRespiratorio is greater than or equal to UPDATED_RITMO_RESPIRATORIO
        defaultFisiometria1ShouldNotBeFound("ritmoRespiratorio.greaterThanOrEqual=" + UPDATED_RITMO_RESPIRATORIO);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoRespiratorioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoRespiratorio is less than or equal to DEFAULT_RITMO_RESPIRATORIO
        defaultFisiometria1ShouldBeFound("ritmoRespiratorio.lessThanOrEqual=" + DEFAULT_RITMO_RESPIRATORIO);

        // Get all the fisiometria1List where ritmoRespiratorio is less than or equal to SMALLER_RITMO_RESPIRATORIO
        defaultFisiometria1ShouldNotBeFound("ritmoRespiratorio.lessThanOrEqual=" + SMALLER_RITMO_RESPIRATORIO);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoRespiratorioIsLessThanSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoRespiratorio is less than DEFAULT_RITMO_RESPIRATORIO
        defaultFisiometria1ShouldNotBeFound("ritmoRespiratorio.lessThan=" + DEFAULT_RITMO_RESPIRATORIO);

        // Get all the fisiometria1List where ritmoRespiratorio is less than UPDATED_RITMO_RESPIRATORIO
        defaultFisiometria1ShouldBeFound("ritmoRespiratorio.lessThan=" + UPDATED_RITMO_RESPIRATORIO);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByRitmoRespiratorioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where ritmoRespiratorio is greater than DEFAULT_RITMO_RESPIRATORIO
        defaultFisiometria1ShouldNotBeFound("ritmoRespiratorio.greaterThan=" + DEFAULT_RITMO_RESPIRATORIO);

        // Get all the fisiometria1List where ritmoRespiratorio is greater than SMALLER_RITMO_RESPIRATORIO
        defaultFisiometria1ShouldBeFound("ritmoRespiratorio.greaterThan=" + SMALLER_RITMO_RESPIRATORIO);
    }


    @Test
    @Transactional
    public void getAllFisiometria1SByOximetriaIsEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where oximetria equals to DEFAULT_OXIMETRIA
        defaultFisiometria1ShouldBeFound("oximetria.equals=" + DEFAULT_OXIMETRIA);

        // Get all the fisiometria1List where oximetria equals to UPDATED_OXIMETRIA
        defaultFisiometria1ShouldNotBeFound("oximetria.equals=" + UPDATED_OXIMETRIA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByOximetriaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where oximetria not equals to DEFAULT_OXIMETRIA
        defaultFisiometria1ShouldNotBeFound("oximetria.notEquals=" + DEFAULT_OXIMETRIA);

        // Get all the fisiometria1List where oximetria not equals to UPDATED_OXIMETRIA
        defaultFisiometria1ShouldBeFound("oximetria.notEquals=" + UPDATED_OXIMETRIA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByOximetriaIsInShouldWork() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where oximetria in DEFAULT_OXIMETRIA or UPDATED_OXIMETRIA
        defaultFisiometria1ShouldBeFound("oximetria.in=" + DEFAULT_OXIMETRIA + "," + UPDATED_OXIMETRIA);

        // Get all the fisiometria1List where oximetria equals to UPDATED_OXIMETRIA
        defaultFisiometria1ShouldNotBeFound("oximetria.in=" + UPDATED_OXIMETRIA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByOximetriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where oximetria is not null
        defaultFisiometria1ShouldBeFound("oximetria.specified=true");

        // Get all the fisiometria1List where oximetria is null
        defaultFisiometria1ShouldNotBeFound("oximetria.specified=false");
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByOximetriaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where oximetria is greater than or equal to DEFAULT_OXIMETRIA
        defaultFisiometria1ShouldBeFound("oximetria.greaterThanOrEqual=" + DEFAULT_OXIMETRIA);

        // Get all the fisiometria1List where oximetria is greater than or equal to UPDATED_OXIMETRIA
        defaultFisiometria1ShouldNotBeFound("oximetria.greaterThanOrEqual=" + UPDATED_OXIMETRIA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByOximetriaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where oximetria is less than or equal to DEFAULT_OXIMETRIA
        defaultFisiometria1ShouldBeFound("oximetria.lessThanOrEqual=" + DEFAULT_OXIMETRIA);

        // Get all the fisiometria1List where oximetria is less than or equal to SMALLER_OXIMETRIA
        defaultFisiometria1ShouldNotBeFound("oximetria.lessThanOrEqual=" + SMALLER_OXIMETRIA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByOximetriaIsLessThanSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where oximetria is less than DEFAULT_OXIMETRIA
        defaultFisiometria1ShouldNotBeFound("oximetria.lessThan=" + DEFAULT_OXIMETRIA);

        // Get all the fisiometria1List where oximetria is less than UPDATED_OXIMETRIA
        defaultFisiometria1ShouldBeFound("oximetria.lessThan=" + UPDATED_OXIMETRIA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByOximetriaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where oximetria is greater than DEFAULT_OXIMETRIA
        defaultFisiometria1ShouldNotBeFound("oximetria.greaterThan=" + DEFAULT_OXIMETRIA);

        // Get all the fisiometria1List where oximetria is greater than SMALLER_OXIMETRIA
        defaultFisiometria1ShouldBeFound("oximetria.greaterThan=" + SMALLER_OXIMETRIA);
    }


    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialSistolicaIsEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialSistolica equals to DEFAULT_PRESION_ARTERIAL_SISTOLICA
        defaultFisiometria1ShouldBeFound("presionArterialSistolica.equals=" + DEFAULT_PRESION_ARTERIAL_SISTOLICA);

        // Get all the fisiometria1List where presionArterialSistolica equals to UPDATED_PRESION_ARTERIAL_SISTOLICA
        defaultFisiometria1ShouldNotBeFound("presionArterialSistolica.equals=" + UPDATED_PRESION_ARTERIAL_SISTOLICA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialSistolicaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialSistolica not equals to DEFAULT_PRESION_ARTERIAL_SISTOLICA
        defaultFisiometria1ShouldNotBeFound("presionArterialSistolica.notEquals=" + DEFAULT_PRESION_ARTERIAL_SISTOLICA);

        // Get all the fisiometria1List where presionArterialSistolica not equals to UPDATED_PRESION_ARTERIAL_SISTOLICA
        defaultFisiometria1ShouldBeFound("presionArterialSistolica.notEquals=" + UPDATED_PRESION_ARTERIAL_SISTOLICA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialSistolicaIsInShouldWork() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialSistolica in DEFAULT_PRESION_ARTERIAL_SISTOLICA or UPDATED_PRESION_ARTERIAL_SISTOLICA
        defaultFisiometria1ShouldBeFound("presionArterialSistolica.in=" + DEFAULT_PRESION_ARTERIAL_SISTOLICA + "," + UPDATED_PRESION_ARTERIAL_SISTOLICA);

        // Get all the fisiometria1List where presionArterialSistolica equals to UPDATED_PRESION_ARTERIAL_SISTOLICA
        defaultFisiometria1ShouldNotBeFound("presionArterialSistolica.in=" + UPDATED_PRESION_ARTERIAL_SISTOLICA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialSistolicaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialSistolica is not null
        defaultFisiometria1ShouldBeFound("presionArterialSistolica.specified=true");

        // Get all the fisiometria1List where presionArterialSistolica is null
        defaultFisiometria1ShouldNotBeFound("presionArterialSistolica.specified=false");
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialSistolicaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialSistolica is greater than or equal to DEFAULT_PRESION_ARTERIAL_SISTOLICA
        defaultFisiometria1ShouldBeFound("presionArterialSistolica.greaterThanOrEqual=" + DEFAULT_PRESION_ARTERIAL_SISTOLICA);

        // Get all the fisiometria1List where presionArterialSistolica is greater than or equal to UPDATED_PRESION_ARTERIAL_SISTOLICA
        defaultFisiometria1ShouldNotBeFound("presionArterialSistolica.greaterThanOrEqual=" + UPDATED_PRESION_ARTERIAL_SISTOLICA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialSistolicaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialSistolica is less than or equal to DEFAULT_PRESION_ARTERIAL_SISTOLICA
        defaultFisiometria1ShouldBeFound("presionArterialSistolica.lessThanOrEqual=" + DEFAULT_PRESION_ARTERIAL_SISTOLICA);

        // Get all the fisiometria1List where presionArterialSistolica is less than or equal to SMALLER_PRESION_ARTERIAL_SISTOLICA
        defaultFisiometria1ShouldNotBeFound("presionArterialSistolica.lessThanOrEqual=" + SMALLER_PRESION_ARTERIAL_SISTOLICA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialSistolicaIsLessThanSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialSistolica is less than DEFAULT_PRESION_ARTERIAL_SISTOLICA
        defaultFisiometria1ShouldNotBeFound("presionArterialSistolica.lessThan=" + DEFAULT_PRESION_ARTERIAL_SISTOLICA);

        // Get all the fisiometria1List where presionArterialSistolica is less than UPDATED_PRESION_ARTERIAL_SISTOLICA
        defaultFisiometria1ShouldBeFound("presionArterialSistolica.lessThan=" + UPDATED_PRESION_ARTERIAL_SISTOLICA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialSistolicaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialSistolica is greater than DEFAULT_PRESION_ARTERIAL_SISTOLICA
        defaultFisiometria1ShouldNotBeFound("presionArterialSistolica.greaterThan=" + DEFAULT_PRESION_ARTERIAL_SISTOLICA);

        // Get all the fisiometria1List where presionArterialSistolica is greater than SMALLER_PRESION_ARTERIAL_SISTOLICA
        defaultFisiometria1ShouldBeFound("presionArterialSistolica.greaterThan=" + SMALLER_PRESION_ARTERIAL_SISTOLICA);
    }


    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialDiastolicaIsEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialDiastolica equals to DEFAULT_PRESION_ARTERIAL_DIASTOLICA
        defaultFisiometria1ShouldBeFound("presionArterialDiastolica.equals=" + DEFAULT_PRESION_ARTERIAL_DIASTOLICA);

        // Get all the fisiometria1List where presionArterialDiastolica equals to UPDATED_PRESION_ARTERIAL_DIASTOLICA
        defaultFisiometria1ShouldNotBeFound("presionArterialDiastolica.equals=" + UPDATED_PRESION_ARTERIAL_DIASTOLICA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialDiastolicaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialDiastolica not equals to DEFAULT_PRESION_ARTERIAL_DIASTOLICA
        defaultFisiometria1ShouldNotBeFound("presionArterialDiastolica.notEquals=" + DEFAULT_PRESION_ARTERIAL_DIASTOLICA);

        // Get all the fisiometria1List where presionArterialDiastolica not equals to UPDATED_PRESION_ARTERIAL_DIASTOLICA
        defaultFisiometria1ShouldBeFound("presionArterialDiastolica.notEquals=" + UPDATED_PRESION_ARTERIAL_DIASTOLICA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialDiastolicaIsInShouldWork() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialDiastolica in DEFAULT_PRESION_ARTERIAL_DIASTOLICA or UPDATED_PRESION_ARTERIAL_DIASTOLICA
        defaultFisiometria1ShouldBeFound("presionArterialDiastolica.in=" + DEFAULT_PRESION_ARTERIAL_DIASTOLICA + "," + UPDATED_PRESION_ARTERIAL_DIASTOLICA);

        // Get all the fisiometria1List where presionArterialDiastolica equals to UPDATED_PRESION_ARTERIAL_DIASTOLICA
        defaultFisiometria1ShouldNotBeFound("presionArterialDiastolica.in=" + UPDATED_PRESION_ARTERIAL_DIASTOLICA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialDiastolicaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialDiastolica is not null
        defaultFisiometria1ShouldBeFound("presionArterialDiastolica.specified=true");

        // Get all the fisiometria1List where presionArterialDiastolica is null
        defaultFisiometria1ShouldNotBeFound("presionArterialDiastolica.specified=false");
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialDiastolicaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialDiastolica is greater than or equal to DEFAULT_PRESION_ARTERIAL_DIASTOLICA
        defaultFisiometria1ShouldBeFound("presionArterialDiastolica.greaterThanOrEqual=" + DEFAULT_PRESION_ARTERIAL_DIASTOLICA);

        // Get all the fisiometria1List where presionArterialDiastolica is greater than or equal to UPDATED_PRESION_ARTERIAL_DIASTOLICA
        defaultFisiometria1ShouldNotBeFound("presionArterialDiastolica.greaterThanOrEqual=" + UPDATED_PRESION_ARTERIAL_DIASTOLICA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialDiastolicaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialDiastolica is less than or equal to DEFAULT_PRESION_ARTERIAL_DIASTOLICA
        defaultFisiometria1ShouldBeFound("presionArterialDiastolica.lessThanOrEqual=" + DEFAULT_PRESION_ARTERIAL_DIASTOLICA);

        // Get all the fisiometria1List where presionArterialDiastolica is less than or equal to SMALLER_PRESION_ARTERIAL_DIASTOLICA
        defaultFisiometria1ShouldNotBeFound("presionArterialDiastolica.lessThanOrEqual=" + SMALLER_PRESION_ARTERIAL_DIASTOLICA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialDiastolicaIsLessThanSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialDiastolica is less than DEFAULT_PRESION_ARTERIAL_DIASTOLICA
        defaultFisiometria1ShouldNotBeFound("presionArterialDiastolica.lessThan=" + DEFAULT_PRESION_ARTERIAL_DIASTOLICA);

        // Get all the fisiometria1List where presionArterialDiastolica is less than UPDATED_PRESION_ARTERIAL_DIASTOLICA
        defaultFisiometria1ShouldBeFound("presionArterialDiastolica.lessThan=" + UPDATED_PRESION_ARTERIAL_DIASTOLICA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByPresionArterialDiastolicaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where presionArterialDiastolica is greater than DEFAULT_PRESION_ARTERIAL_DIASTOLICA
        defaultFisiometria1ShouldNotBeFound("presionArterialDiastolica.greaterThan=" + DEFAULT_PRESION_ARTERIAL_DIASTOLICA);

        // Get all the fisiometria1List where presionArterialDiastolica is greater than SMALLER_PRESION_ARTERIAL_DIASTOLICA
        defaultFisiometria1ShouldBeFound("presionArterialDiastolica.greaterThan=" + SMALLER_PRESION_ARTERIAL_DIASTOLICA);
    }


    @Test
    @Transactional
    public void getAllFisiometria1SByTemperaturaIsEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where temperatura equals to DEFAULT_TEMPERATURA
        defaultFisiometria1ShouldBeFound("temperatura.equals=" + DEFAULT_TEMPERATURA);

        // Get all the fisiometria1List where temperatura equals to UPDATED_TEMPERATURA
        defaultFisiometria1ShouldNotBeFound("temperatura.equals=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByTemperaturaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where temperatura not equals to DEFAULT_TEMPERATURA
        defaultFisiometria1ShouldNotBeFound("temperatura.notEquals=" + DEFAULT_TEMPERATURA);

        // Get all the fisiometria1List where temperatura not equals to UPDATED_TEMPERATURA
        defaultFisiometria1ShouldBeFound("temperatura.notEquals=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByTemperaturaIsInShouldWork() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where temperatura in DEFAULT_TEMPERATURA or UPDATED_TEMPERATURA
        defaultFisiometria1ShouldBeFound("temperatura.in=" + DEFAULT_TEMPERATURA + "," + UPDATED_TEMPERATURA);

        // Get all the fisiometria1List where temperatura equals to UPDATED_TEMPERATURA
        defaultFisiometria1ShouldNotBeFound("temperatura.in=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByTemperaturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where temperatura is not null
        defaultFisiometria1ShouldBeFound("temperatura.specified=true");

        // Get all the fisiometria1List where temperatura is null
        defaultFisiometria1ShouldNotBeFound("temperatura.specified=false");
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByTemperaturaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where temperatura is greater than or equal to DEFAULT_TEMPERATURA
        defaultFisiometria1ShouldBeFound("temperatura.greaterThanOrEqual=" + DEFAULT_TEMPERATURA);

        // Get all the fisiometria1List where temperatura is greater than or equal to UPDATED_TEMPERATURA
        defaultFisiometria1ShouldNotBeFound("temperatura.greaterThanOrEqual=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByTemperaturaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where temperatura is less than or equal to DEFAULT_TEMPERATURA
        defaultFisiometria1ShouldBeFound("temperatura.lessThanOrEqual=" + DEFAULT_TEMPERATURA);

        // Get all the fisiometria1List where temperatura is less than or equal to SMALLER_TEMPERATURA
        defaultFisiometria1ShouldNotBeFound("temperatura.lessThanOrEqual=" + SMALLER_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByTemperaturaIsLessThanSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where temperatura is less than DEFAULT_TEMPERATURA
        defaultFisiometria1ShouldNotBeFound("temperatura.lessThan=" + DEFAULT_TEMPERATURA);

        // Get all the fisiometria1List where temperatura is less than UPDATED_TEMPERATURA
        defaultFisiometria1ShouldBeFound("temperatura.lessThan=" + UPDATED_TEMPERATURA);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByTemperaturaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where temperatura is greater than DEFAULT_TEMPERATURA
        defaultFisiometria1ShouldNotBeFound("temperatura.greaterThan=" + DEFAULT_TEMPERATURA);

        // Get all the fisiometria1List where temperatura is greater than SMALLER_TEMPERATURA
        defaultFisiometria1ShouldBeFound("temperatura.greaterThan=" + SMALLER_TEMPERATURA);
    }


    @Test
    @Transactional
    public void getAllFisiometria1SByTimeInstantIsEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where timeInstant equals to DEFAULT_TIME_INSTANT
        defaultFisiometria1ShouldBeFound("timeInstant.equals=" + DEFAULT_TIME_INSTANT);

        // Get all the fisiometria1List where timeInstant equals to UPDATED_TIME_INSTANT
        defaultFisiometria1ShouldNotBeFound("timeInstant.equals=" + UPDATED_TIME_INSTANT);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByTimeInstantIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where timeInstant not equals to DEFAULT_TIME_INSTANT
        defaultFisiometria1ShouldNotBeFound("timeInstant.notEquals=" + DEFAULT_TIME_INSTANT);

        // Get all the fisiometria1List where timeInstant not equals to UPDATED_TIME_INSTANT
        defaultFisiometria1ShouldBeFound("timeInstant.notEquals=" + UPDATED_TIME_INSTANT);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByTimeInstantIsInShouldWork() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where timeInstant in DEFAULT_TIME_INSTANT or UPDATED_TIME_INSTANT
        defaultFisiometria1ShouldBeFound("timeInstant.in=" + DEFAULT_TIME_INSTANT + "," + UPDATED_TIME_INSTANT);

        // Get all the fisiometria1List where timeInstant equals to UPDATED_TIME_INSTANT
        defaultFisiometria1ShouldNotBeFound("timeInstant.in=" + UPDATED_TIME_INSTANT);
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByTimeInstantIsNullOrNotNull() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);

        // Get all the fisiometria1List where timeInstant is not null
        defaultFisiometria1ShouldBeFound("timeInstant.specified=true");

        // Get all the fisiometria1List where timeInstant is null
        defaultFisiometria1ShouldNotBeFound("timeInstant.specified=false");
    }

    @Test
    @Transactional
    public void getAllFisiometria1SByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        fisiometria1Repository.saveAndFlush(fisiometria1);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        fisiometria1.setUser(user);
        fisiometria1Repository.saveAndFlush(fisiometria1);
        Long userId = user.getId();

        // Get all the fisiometria1List where user equals to userId
        defaultFisiometria1ShouldBeFound("userId.equals=" + userId);

        // Get all the fisiometria1List where user equals to userId + 1
        defaultFisiometria1ShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFisiometria1ShouldBeFound(String filter) throws Exception {
        restFisiometria1MockMvc.perform(get("/api/fisiometria-1-s?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fisiometria1.getId().intValue())))
            .andExpect(jsonPath("$.[*].ritmoCardiaco").value(hasItem(DEFAULT_RITMO_CARDIACO)))
            .andExpect(jsonPath("$.[*].ritmoRespiratorio").value(hasItem(DEFAULT_RITMO_RESPIRATORIO)))
            .andExpect(jsonPath("$.[*].oximetria").value(hasItem(DEFAULT_OXIMETRIA)))
            .andExpect(jsonPath("$.[*].presionArterialSistolica").value(hasItem(DEFAULT_PRESION_ARTERIAL_SISTOLICA)))
            .andExpect(jsonPath("$.[*].presionArterialDiastolica").value(hasItem(DEFAULT_PRESION_ARTERIAL_DIASTOLICA)))
            .andExpect(jsonPath("$.[*].temperatura").value(hasItem(DEFAULT_TEMPERATURA.doubleValue())))
            .andExpect(jsonPath("$.[*].timeInstant").value(hasItem(DEFAULT_TIME_INSTANT.toString())));

        // Check, that the count call also returns 1
        restFisiometria1MockMvc.perform(get("/api/fisiometria-1-s/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFisiometria1ShouldNotBeFound(String filter) throws Exception {
        restFisiometria1MockMvc.perform(get("/api/fisiometria-1-s?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFisiometria1MockMvc.perform(get("/api/fisiometria-1-s/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFisiometria1() throws Exception {
        // Get the fisiometria1
        restFisiometria1MockMvc.perform(get("/api/fisiometria-1-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFisiometria1() throws Exception {
        // Initialize the database
        fisiometria1Service.save(fisiometria1);

        int databaseSizeBeforeUpdate = fisiometria1Repository.findAll().size();

        // Update the fisiometria1
        Fisiometria1 updatedFisiometria1 = fisiometria1Repository.findById(fisiometria1.getId()).get();
        // Disconnect from session so that the updates on updatedFisiometria1 are not directly saved in db
        em.detach(updatedFisiometria1);
        updatedFisiometria1
            .ritmoCardiaco(UPDATED_RITMO_CARDIACO)
            .ritmoRespiratorio(UPDATED_RITMO_RESPIRATORIO)
            .oximetria(UPDATED_OXIMETRIA)
            .presionArterialSistolica(UPDATED_PRESION_ARTERIAL_SISTOLICA)
            .presionArterialDiastolica(UPDATED_PRESION_ARTERIAL_DIASTOLICA)
            .temperatura(UPDATED_TEMPERATURA)
            .timeInstant(UPDATED_TIME_INSTANT);

        restFisiometria1MockMvc.perform(put("/api/fisiometria-1-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFisiometria1)))
            .andExpect(status().isOk());

        // Validate the Fisiometria1 in the database
        List<Fisiometria1> fisiometria1List = fisiometria1Repository.findAll();
        assertThat(fisiometria1List).hasSize(databaseSizeBeforeUpdate);
        Fisiometria1 testFisiometria1 = fisiometria1List.get(fisiometria1List.size() - 1);
        assertThat(testFisiometria1.getRitmoCardiaco()).isEqualTo(UPDATED_RITMO_CARDIACO);
        assertThat(testFisiometria1.getRitmoRespiratorio()).isEqualTo(UPDATED_RITMO_RESPIRATORIO);
        assertThat(testFisiometria1.getOximetria()).isEqualTo(UPDATED_OXIMETRIA);
        assertThat(testFisiometria1.getPresionArterialSistolica()).isEqualTo(UPDATED_PRESION_ARTERIAL_SISTOLICA);
        assertThat(testFisiometria1.getPresionArterialDiastolica()).isEqualTo(UPDATED_PRESION_ARTERIAL_DIASTOLICA);
        assertThat(testFisiometria1.getTemperatura()).isEqualTo(UPDATED_TEMPERATURA);
        assertThat(testFisiometria1.getTimeInstant()).isEqualTo(UPDATED_TIME_INSTANT);
    }

    @Test
    @Transactional
    public void updateNonExistingFisiometria1() throws Exception {
        int databaseSizeBeforeUpdate = fisiometria1Repository.findAll().size();

        // Create the Fisiometria1

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFisiometria1MockMvc.perform(put("/api/fisiometria-1-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fisiometria1)))
            .andExpect(status().isBadRequest());

        // Validate the Fisiometria1 in the database
        List<Fisiometria1> fisiometria1List = fisiometria1Repository.findAll();
        assertThat(fisiometria1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFisiometria1() throws Exception {
        // Initialize the database
        fisiometria1Service.save(fisiometria1);

        int databaseSizeBeforeDelete = fisiometria1Repository.findAll().size();

        // Delete the fisiometria1
        restFisiometria1MockMvc.perform(delete("/api/fisiometria-1-s/{id}", fisiometria1.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fisiometria1> fisiometria1List = fisiometria1Repository.findAll();
        assertThat(fisiometria1List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
