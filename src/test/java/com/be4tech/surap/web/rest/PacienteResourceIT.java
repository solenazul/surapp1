package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.Paciente;
import com.be4tech.surap.domain.IPS;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.repository.PacienteRepository;
import com.be4tech.surap.service.PacienteService;
import com.be4tech.surap.service.dto.PacienteCriteria;
import com.be4tech.surap.service.PacienteQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PacienteResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class PacienteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_IDENTIFICACION = 1;
    private static final Integer UPDATED_IDENTIFICACION = 2;
    private static final Integer SMALLER_IDENTIFICACION = 1 - 1;

    private static final Integer DEFAULT_EDAD = 1;
    private static final Integer UPDATED_EDAD = 2;
    private static final Integer SMALLER_EDAD = 1 - 1;

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_NACIMIENTO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CONDICION = "AAAAAAAAAA";
    private static final String UPDATED_CONDICION = "BBBBBBBBBB";

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private PacienteQueryService pacienteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPacienteMockMvc;

    private Paciente paciente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paciente createEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .nombre(DEFAULT_NOMBRE)
            .identificacion(DEFAULT_IDENTIFICACION)
            .edad(DEFAULT_EDAD)
            .sexo(DEFAULT_SEXO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .condicion(DEFAULT_CONDICION);
        return paciente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paciente createUpdatedEntity(EntityManager em) {
        Paciente paciente = new Paciente()
            .nombre(UPDATED_NOMBRE)
            .identificacion(UPDATED_IDENTIFICACION)
            .edad(UPDATED_EDAD)
            .sexo(UPDATED_SEXO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .condicion(UPDATED_CONDICION);
        return paciente;
    }

    @BeforeEach
    public void initTest() {
        paciente = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaciente() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente
        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isCreated());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate + 1);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPaciente.getIdentificacion()).isEqualTo(DEFAULT_IDENTIFICACION);
        assertThat(testPaciente.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testPaciente.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testPaciente.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testPaciente.getCondicion()).isEqualTo(DEFAULT_CONDICION);
    }

    @Test
    @Transactional
    public void createPacienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacienteRepository.findAll().size();

        // Create the Paciente with an existing ID
        paciente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacienteMockMvc.perform(post("/api/pacientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPacientes() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList
        restPacienteMockMvc.perform(get("/api/pacientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].condicion").value(hasItem(DEFAULT_CONDICION.toString())));
    }
    
    @Test
    @Transactional
    public void getPaciente() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", paciente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paciente.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.identificacion").value(DEFAULT_IDENTIFICACION))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.condicion").value(DEFAULT_CONDICION.toString()));
    }


    @Test
    @Transactional
    public void getPacientesByIdFiltering() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        Long id = paciente.getId();

        defaultPacienteShouldBeFound("id.equals=" + id);
        defaultPacienteShouldNotBeFound("id.notEquals=" + id);

        defaultPacienteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPacienteShouldNotBeFound("id.greaterThan=" + id);

        defaultPacienteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPacienteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPacientesByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nombre equals to DEFAULT_NOMBRE
        defaultPacienteShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the pacienteList where nombre equals to UPDATED_NOMBRE
        defaultPacienteShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPacientesByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nombre not equals to DEFAULT_NOMBRE
        defaultPacienteShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the pacienteList where nombre not equals to UPDATED_NOMBRE
        defaultPacienteShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPacientesByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultPacienteShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the pacienteList where nombre equals to UPDATED_NOMBRE
        defaultPacienteShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPacientesByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nombre is not null
        defaultPacienteShouldBeFound("nombre.specified=true");

        // Get all the pacienteList where nombre is null
        defaultPacienteShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllPacientesByNombreContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nombre contains DEFAULT_NOMBRE
        defaultPacienteShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the pacienteList where nombre contains UPDATED_NOMBRE
        defaultPacienteShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllPacientesByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where nombre does not contain DEFAULT_NOMBRE
        defaultPacienteShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the pacienteList where nombre does not contain UPDATED_NOMBRE
        defaultPacienteShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllPacientesByIdentificacionIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where identificacion equals to DEFAULT_IDENTIFICACION
        defaultPacienteShouldBeFound("identificacion.equals=" + DEFAULT_IDENTIFICACION);

        // Get all the pacienteList where identificacion equals to UPDATED_IDENTIFICACION
        defaultPacienteShouldNotBeFound("identificacion.equals=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllPacientesByIdentificacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where identificacion not equals to DEFAULT_IDENTIFICACION
        defaultPacienteShouldNotBeFound("identificacion.notEquals=" + DEFAULT_IDENTIFICACION);

        // Get all the pacienteList where identificacion not equals to UPDATED_IDENTIFICACION
        defaultPacienteShouldBeFound("identificacion.notEquals=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllPacientesByIdentificacionIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where identificacion in DEFAULT_IDENTIFICACION or UPDATED_IDENTIFICACION
        defaultPacienteShouldBeFound("identificacion.in=" + DEFAULT_IDENTIFICACION + "," + UPDATED_IDENTIFICACION);

        // Get all the pacienteList where identificacion equals to UPDATED_IDENTIFICACION
        defaultPacienteShouldNotBeFound("identificacion.in=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllPacientesByIdentificacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where identificacion is not null
        defaultPacienteShouldBeFound("identificacion.specified=true");

        // Get all the pacienteList where identificacion is null
        defaultPacienteShouldNotBeFound("identificacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByIdentificacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where identificacion is greater than or equal to DEFAULT_IDENTIFICACION
        defaultPacienteShouldBeFound("identificacion.greaterThanOrEqual=" + DEFAULT_IDENTIFICACION);

        // Get all the pacienteList where identificacion is greater than or equal to UPDATED_IDENTIFICACION
        defaultPacienteShouldNotBeFound("identificacion.greaterThanOrEqual=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllPacientesByIdentificacionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where identificacion is less than or equal to DEFAULT_IDENTIFICACION
        defaultPacienteShouldBeFound("identificacion.lessThanOrEqual=" + DEFAULT_IDENTIFICACION);

        // Get all the pacienteList where identificacion is less than or equal to SMALLER_IDENTIFICACION
        defaultPacienteShouldNotBeFound("identificacion.lessThanOrEqual=" + SMALLER_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllPacientesByIdentificacionIsLessThanSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where identificacion is less than DEFAULT_IDENTIFICACION
        defaultPacienteShouldNotBeFound("identificacion.lessThan=" + DEFAULT_IDENTIFICACION);

        // Get all the pacienteList where identificacion is less than UPDATED_IDENTIFICACION
        defaultPacienteShouldBeFound("identificacion.lessThan=" + UPDATED_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllPacientesByIdentificacionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where identificacion is greater than DEFAULT_IDENTIFICACION
        defaultPacienteShouldNotBeFound("identificacion.greaterThan=" + DEFAULT_IDENTIFICACION);

        // Get all the pacienteList where identificacion is greater than SMALLER_IDENTIFICACION
        defaultPacienteShouldBeFound("identificacion.greaterThan=" + SMALLER_IDENTIFICACION);
    }


    @Test
    @Transactional
    public void getAllPacientesByEdadIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where edad equals to DEFAULT_EDAD
        defaultPacienteShouldBeFound("edad.equals=" + DEFAULT_EDAD);

        // Get all the pacienteList where edad equals to UPDATED_EDAD
        defaultPacienteShouldNotBeFound("edad.equals=" + UPDATED_EDAD);
    }

    @Test
    @Transactional
    public void getAllPacientesByEdadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where edad not equals to DEFAULT_EDAD
        defaultPacienteShouldNotBeFound("edad.notEquals=" + DEFAULT_EDAD);

        // Get all the pacienteList where edad not equals to UPDATED_EDAD
        defaultPacienteShouldBeFound("edad.notEquals=" + UPDATED_EDAD);
    }

    @Test
    @Transactional
    public void getAllPacientesByEdadIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where edad in DEFAULT_EDAD or UPDATED_EDAD
        defaultPacienteShouldBeFound("edad.in=" + DEFAULT_EDAD + "," + UPDATED_EDAD);

        // Get all the pacienteList where edad equals to UPDATED_EDAD
        defaultPacienteShouldNotBeFound("edad.in=" + UPDATED_EDAD);
    }

    @Test
    @Transactional
    public void getAllPacientesByEdadIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where edad is not null
        defaultPacienteShouldBeFound("edad.specified=true");

        // Get all the pacienteList where edad is null
        defaultPacienteShouldNotBeFound("edad.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByEdadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where edad is greater than or equal to DEFAULT_EDAD
        defaultPacienteShouldBeFound("edad.greaterThanOrEqual=" + DEFAULT_EDAD);

        // Get all the pacienteList where edad is greater than or equal to UPDATED_EDAD
        defaultPacienteShouldNotBeFound("edad.greaterThanOrEqual=" + UPDATED_EDAD);
    }

    @Test
    @Transactional
    public void getAllPacientesByEdadIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where edad is less than or equal to DEFAULT_EDAD
        defaultPacienteShouldBeFound("edad.lessThanOrEqual=" + DEFAULT_EDAD);

        // Get all the pacienteList where edad is less than or equal to SMALLER_EDAD
        defaultPacienteShouldNotBeFound("edad.lessThanOrEqual=" + SMALLER_EDAD);
    }

    @Test
    @Transactional
    public void getAllPacientesByEdadIsLessThanSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where edad is less than DEFAULT_EDAD
        defaultPacienteShouldNotBeFound("edad.lessThan=" + DEFAULT_EDAD);

        // Get all the pacienteList where edad is less than UPDATED_EDAD
        defaultPacienteShouldBeFound("edad.lessThan=" + UPDATED_EDAD);
    }

    @Test
    @Transactional
    public void getAllPacientesByEdadIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where edad is greater than DEFAULT_EDAD
        defaultPacienteShouldNotBeFound("edad.greaterThan=" + DEFAULT_EDAD);

        // Get all the pacienteList where edad is greater than SMALLER_EDAD
        defaultPacienteShouldBeFound("edad.greaterThan=" + SMALLER_EDAD);
    }


    @Test
    @Transactional
    public void getAllPacientesBySexoIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where sexo equals to DEFAULT_SEXO
        defaultPacienteShouldBeFound("sexo.equals=" + DEFAULT_SEXO);

        // Get all the pacienteList where sexo equals to UPDATED_SEXO
        defaultPacienteShouldNotBeFound("sexo.equals=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void getAllPacientesBySexoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where sexo not equals to DEFAULT_SEXO
        defaultPacienteShouldNotBeFound("sexo.notEquals=" + DEFAULT_SEXO);

        // Get all the pacienteList where sexo not equals to UPDATED_SEXO
        defaultPacienteShouldBeFound("sexo.notEquals=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void getAllPacientesBySexoIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where sexo in DEFAULT_SEXO or UPDATED_SEXO
        defaultPacienteShouldBeFound("sexo.in=" + DEFAULT_SEXO + "," + UPDATED_SEXO);

        // Get all the pacienteList where sexo equals to UPDATED_SEXO
        defaultPacienteShouldNotBeFound("sexo.in=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void getAllPacientesBySexoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where sexo is not null
        defaultPacienteShouldBeFound("sexo.specified=true");

        // Get all the pacienteList where sexo is null
        defaultPacienteShouldNotBeFound("sexo.specified=false");
    }
                @Test
    @Transactional
    public void getAllPacientesBySexoContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where sexo contains DEFAULT_SEXO
        defaultPacienteShouldBeFound("sexo.contains=" + DEFAULT_SEXO);

        // Get all the pacienteList where sexo contains UPDATED_SEXO
        defaultPacienteShouldNotBeFound("sexo.contains=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void getAllPacientesBySexoNotContainsSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where sexo does not contain DEFAULT_SEXO
        defaultPacienteShouldNotBeFound("sexo.doesNotContain=" + DEFAULT_SEXO);

        // Get all the pacienteList where sexo does not contain UPDATED_SEXO
        defaultPacienteShouldBeFound("sexo.doesNotContain=" + UPDATED_SEXO);
    }


    @Test
    @Transactional
    public void getAllPacientesByFechaNacimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where fechaNacimiento equals to DEFAULT_FECHA_NACIMIENTO
        defaultPacienteShouldBeFound("fechaNacimiento.equals=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the pacienteList where fechaNacimiento equals to UPDATED_FECHA_NACIMIENTO
        defaultPacienteShouldNotBeFound("fechaNacimiento.equals=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllPacientesByFechaNacimientoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where fechaNacimiento not equals to DEFAULT_FECHA_NACIMIENTO
        defaultPacienteShouldNotBeFound("fechaNacimiento.notEquals=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the pacienteList where fechaNacimiento not equals to UPDATED_FECHA_NACIMIENTO
        defaultPacienteShouldBeFound("fechaNacimiento.notEquals=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllPacientesByFechaNacimientoIsInShouldWork() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where fechaNacimiento in DEFAULT_FECHA_NACIMIENTO or UPDATED_FECHA_NACIMIENTO
        defaultPacienteShouldBeFound("fechaNacimiento.in=" + DEFAULT_FECHA_NACIMIENTO + "," + UPDATED_FECHA_NACIMIENTO);

        // Get all the pacienteList where fechaNacimiento equals to UPDATED_FECHA_NACIMIENTO
        defaultPacienteShouldNotBeFound("fechaNacimiento.in=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllPacientesByFechaNacimientoIsNullOrNotNull() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where fechaNacimiento is not null
        defaultPacienteShouldBeFound("fechaNacimiento.specified=true");

        // Get all the pacienteList where fechaNacimiento is null
        defaultPacienteShouldNotBeFound("fechaNacimiento.specified=false");
    }

    @Test
    @Transactional
    public void getAllPacientesByFechaNacimientoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where fechaNacimiento is greater than or equal to DEFAULT_FECHA_NACIMIENTO
        defaultPacienteShouldBeFound("fechaNacimiento.greaterThanOrEqual=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the pacienteList where fechaNacimiento is greater than or equal to UPDATED_FECHA_NACIMIENTO
        defaultPacienteShouldNotBeFound("fechaNacimiento.greaterThanOrEqual=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllPacientesByFechaNacimientoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where fechaNacimiento is less than or equal to DEFAULT_FECHA_NACIMIENTO
        defaultPacienteShouldBeFound("fechaNacimiento.lessThanOrEqual=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the pacienteList where fechaNacimiento is less than or equal to SMALLER_FECHA_NACIMIENTO
        defaultPacienteShouldNotBeFound("fechaNacimiento.lessThanOrEqual=" + SMALLER_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllPacientesByFechaNacimientoIsLessThanSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where fechaNacimiento is less than DEFAULT_FECHA_NACIMIENTO
        defaultPacienteShouldNotBeFound("fechaNacimiento.lessThan=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the pacienteList where fechaNacimiento is less than UPDATED_FECHA_NACIMIENTO
        defaultPacienteShouldBeFound("fechaNacimiento.lessThan=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllPacientesByFechaNacimientoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);

        // Get all the pacienteList where fechaNacimiento is greater than DEFAULT_FECHA_NACIMIENTO
        defaultPacienteShouldNotBeFound("fechaNacimiento.greaterThan=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the pacienteList where fechaNacimiento is greater than SMALLER_FECHA_NACIMIENTO
        defaultPacienteShouldBeFound("fechaNacimiento.greaterThan=" + SMALLER_FECHA_NACIMIENTO);
    }


    @Test
    @Transactional
    public void getAllPacientesByIpsIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);
        IPS ips = IPSResourceIT.createEntity(em);
        em.persist(ips);
        em.flush();
        paciente.setIps(ips);
        pacienteRepository.saveAndFlush(paciente);
        Long ipsId = ips.getId();

        // Get all the pacienteList where ips equals to ipsId
        defaultPacienteShouldBeFound("ipsId.equals=" + ipsId);

        // Get all the pacienteList where ips equals to ipsId + 1
        defaultPacienteShouldNotBeFound("ipsId.equals=" + (ipsId + 1));
    }


    @Test
    @Transactional
    public void getAllPacientesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        pacienteRepository.saveAndFlush(paciente);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        paciente.setUser(user);
        pacienteRepository.saveAndFlush(paciente);
        Long userId = user.getId();

        // Get all the pacienteList where user equals to userId
        defaultPacienteShouldBeFound("userId.equals=" + userId);

        // Get all the pacienteList where user equals to userId + 1
        defaultPacienteShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPacienteShouldBeFound(String filter) throws Exception {
        restPacienteMockMvc.perform(get("/api/pacientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paciente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].identificacion").value(hasItem(DEFAULT_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].condicion").value(hasItem(DEFAULT_CONDICION.toString())));

        // Check, that the count call also returns 1
        restPacienteMockMvc.perform(get("/api/pacientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPacienteShouldNotBeFound(String filter) throws Exception {
        restPacienteMockMvc.perform(get("/api/pacientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPacienteMockMvc.perform(get("/api/pacientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPaciente() throws Exception {
        // Get the paciente
        restPacienteMockMvc.perform(get("/api/pacientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaciente() throws Exception {
        // Initialize the database
        pacienteService.save(paciente);

        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Update the paciente
        Paciente updatedPaciente = pacienteRepository.findById(paciente.getId()).get();
        // Disconnect from session so that the updates on updatedPaciente are not directly saved in db
        em.detach(updatedPaciente);
        updatedPaciente
            .nombre(UPDATED_NOMBRE)
            .identificacion(UPDATED_IDENTIFICACION)
            .edad(UPDATED_EDAD)
            .sexo(UPDATED_SEXO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .condicion(UPDATED_CONDICION);

        restPacienteMockMvc.perform(put("/api/pacientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaciente)))
            .andExpect(status().isOk());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
        Paciente testPaciente = pacienteList.get(pacienteList.size() - 1);
        assertThat(testPaciente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPaciente.getIdentificacion()).isEqualTo(UPDATED_IDENTIFICACION);
        assertThat(testPaciente.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testPaciente.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPaciente.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testPaciente.getCondicion()).isEqualTo(UPDATED_CONDICION);
    }

    @Test
    @Transactional
    public void updateNonExistingPaciente() throws Exception {
        int databaseSizeBeforeUpdate = pacienteRepository.findAll().size();

        // Create the Paciente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPacienteMockMvc.perform(put("/api/pacientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paciente)))
            .andExpect(status().isBadRequest());

        // Validate the Paciente in the database
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaciente() throws Exception {
        // Initialize the database
        pacienteService.save(paciente);

        int databaseSizeBeforeDelete = pacienteRepository.findAll().size();

        // Delete the paciente
        restPacienteMockMvc.perform(delete("/api/pacientes/{id}", paciente.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paciente> pacienteList = pacienteRepository.findAll();
        assertThat(pacienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
