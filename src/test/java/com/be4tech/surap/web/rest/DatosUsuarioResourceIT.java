package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.DatosUsuario;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.domain.Pais;
import com.be4tech.surap.repository.DatosUsuarioRepository;
import com.be4tech.surap.service.DatosUsuarioService;
import com.be4tech.surap.service.dto.DatosUsuarioCriteria;
import com.be4tech.surap.service.DatosUsuarioQueryService;

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
 * Integration tests for the {@link DatosUsuarioResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class DatosUsuarioResourceIT {

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_NACIMIENTO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_GENERO = "AAAAAAAAAA";
    private static final String UPDATED_GENERO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEFONO = 1;
    private static final Integer UPDATED_TELEFONO = 2;
    private static final Integer SMALLER_TELEFONO = 1 - 1;

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_CIUDAD = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private DatosUsuarioRepository datosUsuarioRepository;

    @Autowired
    private DatosUsuarioService datosUsuarioService;

    @Autowired
    private DatosUsuarioQueryService datosUsuarioQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDatosUsuarioMockMvc;

    private DatosUsuario datosUsuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatosUsuario createEntity(EntityManager em) {
        DatosUsuario datosUsuario = new DatosUsuario()
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .genero(DEFAULT_GENERO)
            .telefono(DEFAULT_TELEFONO)
            .pais(DEFAULT_PAIS)
            .ciudad(DEFAULT_CIUDAD)
            .direccion(DEFAULT_DIRECCION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return datosUsuario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DatosUsuario createUpdatedEntity(EntityManager em) {
        DatosUsuario datosUsuario = new DatosUsuario()
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .genero(UPDATED_GENERO)
            .telefono(UPDATED_TELEFONO)
            .pais(UPDATED_PAIS)
            .ciudad(UPDATED_CIUDAD)
            .direccion(UPDATED_DIRECCION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return datosUsuario;
    }

    @BeforeEach
    public void initTest() {
        datosUsuario = createEntity(em);
    }

    @Test
    @Transactional
    public void createDatosUsuario() throws Exception {
        int databaseSizeBeforeCreate = datosUsuarioRepository.findAll().size();

        // Create the DatosUsuario
        restDatosUsuarioMockMvc.perform(post("/api/datos-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(datosUsuario)))
            .andExpect(status().isCreated());

        // Validate the DatosUsuario in the database
        List<DatosUsuario> datosUsuarioList = datosUsuarioRepository.findAll();
        assertThat(datosUsuarioList).hasSize(databaseSizeBeforeCreate + 1);
        DatosUsuario testDatosUsuario = datosUsuarioList.get(datosUsuarioList.size() - 1);
        assertThat(testDatosUsuario.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testDatosUsuario.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testDatosUsuario.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testDatosUsuario.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testDatosUsuario.getCiudad()).isEqualTo(DEFAULT_CIUDAD);
        assertThat(testDatosUsuario.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testDatosUsuario.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testDatosUsuario.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createDatosUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = datosUsuarioRepository.findAll().size();

        // Create the DatosUsuario with an existing ID
        datosUsuario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatosUsuarioMockMvc.perform(post("/api/datos-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(datosUsuario)))
            .andExpect(status().isBadRequest());

        // Validate the DatosUsuario in the database
        List<DatosUsuario> datosUsuarioList = datosUsuarioRepository.findAll();
        assertThat(datosUsuarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDatosUsuarios() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList
        restDatosUsuarioMockMvc.perform(get("/api/datos-usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datosUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getDatosUsuario() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get the datosUsuario
        restDatosUsuarioMockMvc.perform(get("/api/datos-usuarios/{id}", datosUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(datosUsuario.getId().intValue()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.ciudad").value(DEFAULT_CIUDAD))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }


    @Test
    @Transactional
    public void getDatosUsuariosByIdFiltering() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        Long id = datosUsuario.getId();

        defaultDatosUsuarioShouldBeFound("id.equals=" + id);
        defaultDatosUsuarioShouldNotBeFound("id.notEquals=" + id);

        defaultDatosUsuarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDatosUsuarioShouldNotBeFound("id.greaterThan=" + id);

        defaultDatosUsuarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDatosUsuarioShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaNacimientoIsEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaNacimiento equals to DEFAULT_FECHA_NACIMIENTO
        defaultDatosUsuarioShouldBeFound("fechaNacimiento.equals=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the datosUsuarioList where fechaNacimiento equals to UPDATED_FECHA_NACIMIENTO
        defaultDatosUsuarioShouldNotBeFound("fechaNacimiento.equals=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaNacimientoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaNacimiento not equals to DEFAULT_FECHA_NACIMIENTO
        defaultDatosUsuarioShouldNotBeFound("fechaNacimiento.notEquals=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the datosUsuarioList where fechaNacimiento not equals to UPDATED_FECHA_NACIMIENTO
        defaultDatosUsuarioShouldBeFound("fechaNacimiento.notEquals=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaNacimientoIsInShouldWork() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaNacimiento in DEFAULT_FECHA_NACIMIENTO or UPDATED_FECHA_NACIMIENTO
        defaultDatosUsuarioShouldBeFound("fechaNacimiento.in=" + DEFAULT_FECHA_NACIMIENTO + "," + UPDATED_FECHA_NACIMIENTO);

        // Get all the datosUsuarioList where fechaNacimiento equals to UPDATED_FECHA_NACIMIENTO
        defaultDatosUsuarioShouldNotBeFound("fechaNacimiento.in=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaNacimientoIsNullOrNotNull() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaNacimiento is not null
        defaultDatosUsuarioShouldBeFound("fechaNacimiento.specified=true");

        // Get all the datosUsuarioList where fechaNacimiento is null
        defaultDatosUsuarioShouldNotBeFound("fechaNacimiento.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaNacimientoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaNacimiento is greater than or equal to DEFAULT_FECHA_NACIMIENTO
        defaultDatosUsuarioShouldBeFound("fechaNacimiento.greaterThanOrEqual=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the datosUsuarioList where fechaNacimiento is greater than or equal to UPDATED_FECHA_NACIMIENTO
        defaultDatosUsuarioShouldNotBeFound("fechaNacimiento.greaterThanOrEqual=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaNacimientoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaNacimiento is less than or equal to DEFAULT_FECHA_NACIMIENTO
        defaultDatosUsuarioShouldBeFound("fechaNacimiento.lessThanOrEqual=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the datosUsuarioList where fechaNacimiento is less than or equal to SMALLER_FECHA_NACIMIENTO
        defaultDatosUsuarioShouldNotBeFound("fechaNacimiento.lessThanOrEqual=" + SMALLER_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaNacimientoIsLessThanSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaNacimiento is less than DEFAULT_FECHA_NACIMIENTO
        defaultDatosUsuarioShouldNotBeFound("fechaNacimiento.lessThan=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the datosUsuarioList where fechaNacimiento is less than UPDATED_FECHA_NACIMIENTO
        defaultDatosUsuarioShouldBeFound("fechaNacimiento.lessThan=" + UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByFechaNacimientoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where fechaNacimiento is greater than DEFAULT_FECHA_NACIMIENTO
        defaultDatosUsuarioShouldNotBeFound("fechaNacimiento.greaterThan=" + DEFAULT_FECHA_NACIMIENTO);

        // Get all the datosUsuarioList where fechaNacimiento is greater than SMALLER_FECHA_NACIMIENTO
        defaultDatosUsuarioShouldBeFound("fechaNacimiento.greaterThan=" + SMALLER_FECHA_NACIMIENTO);
    }


    @Test
    @Transactional
    public void getAllDatosUsuariosByGeneroIsEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where genero equals to DEFAULT_GENERO
        defaultDatosUsuarioShouldBeFound("genero.equals=" + DEFAULT_GENERO);

        // Get all the datosUsuarioList where genero equals to UPDATED_GENERO
        defaultDatosUsuarioShouldNotBeFound("genero.equals=" + UPDATED_GENERO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByGeneroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where genero not equals to DEFAULT_GENERO
        defaultDatosUsuarioShouldNotBeFound("genero.notEquals=" + DEFAULT_GENERO);

        // Get all the datosUsuarioList where genero not equals to UPDATED_GENERO
        defaultDatosUsuarioShouldBeFound("genero.notEquals=" + UPDATED_GENERO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByGeneroIsInShouldWork() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where genero in DEFAULT_GENERO or UPDATED_GENERO
        defaultDatosUsuarioShouldBeFound("genero.in=" + DEFAULT_GENERO + "," + UPDATED_GENERO);

        // Get all the datosUsuarioList where genero equals to UPDATED_GENERO
        defaultDatosUsuarioShouldNotBeFound("genero.in=" + UPDATED_GENERO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByGeneroIsNullOrNotNull() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where genero is not null
        defaultDatosUsuarioShouldBeFound("genero.specified=true");

        // Get all the datosUsuarioList where genero is null
        defaultDatosUsuarioShouldNotBeFound("genero.specified=false");
    }
                @Test
    @Transactional
    public void getAllDatosUsuariosByGeneroContainsSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where genero contains DEFAULT_GENERO
        defaultDatosUsuarioShouldBeFound("genero.contains=" + DEFAULT_GENERO);

        // Get all the datosUsuarioList where genero contains UPDATED_GENERO
        defaultDatosUsuarioShouldNotBeFound("genero.contains=" + UPDATED_GENERO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByGeneroNotContainsSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where genero does not contain DEFAULT_GENERO
        defaultDatosUsuarioShouldNotBeFound("genero.doesNotContain=" + DEFAULT_GENERO);

        // Get all the datosUsuarioList where genero does not contain UPDATED_GENERO
        defaultDatosUsuarioShouldBeFound("genero.doesNotContain=" + UPDATED_GENERO);
    }


    @Test
    @Transactional
    public void getAllDatosUsuariosByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where telefono equals to DEFAULT_TELEFONO
        defaultDatosUsuarioShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the datosUsuarioList where telefono equals to UPDATED_TELEFONO
        defaultDatosUsuarioShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByTelefonoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where telefono not equals to DEFAULT_TELEFONO
        defaultDatosUsuarioShouldNotBeFound("telefono.notEquals=" + DEFAULT_TELEFONO);

        // Get all the datosUsuarioList where telefono not equals to UPDATED_TELEFONO
        defaultDatosUsuarioShouldBeFound("telefono.notEquals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultDatosUsuarioShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the datosUsuarioList where telefono equals to UPDATED_TELEFONO
        defaultDatosUsuarioShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where telefono is not null
        defaultDatosUsuarioShouldBeFound("telefono.specified=true");

        // Get all the datosUsuarioList where telefono is null
        defaultDatosUsuarioShouldNotBeFound("telefono.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByTelefonoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where telefono is greater than or equal to DEFAULT_TELEFONO
        defaultDatosUsuarioShouldBeFound("telefono.greaterThanOrEqual=" + DEFAULT_TELEFONO);

        // Get all the datosUsuarioList where telefono is greater than or equal to UPDATED_TELEFONO
        defaultDatosUsuarioShouldNotBeFound("telefono.greaterThanOrEqual=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByTelefonoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where telefono is less than or equal to DEFAULT_TELEFONO
        defaultDatosUsuarioShouldBeFound("telefono.lessThanOrEqual=" + DEFAULT_TELEFONO);

        // Get all the datosUsuarioList where telefono is less than or equal to SMALLER_TELEFONO
        defaultDatosUsuarioShouldNotBeFound("telefono.lessThanOrEqual=" + SMALLER_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByTelefonoIsLessThanSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where telefono is less than DEFAULT_TELEFONO
        defaultDatosUsuarioShouldNotBeFound("telefono.lessThan=" + DEFAULT_TELEFONO);

        // Get all the datosUsuarioList where telefono is less than UPDATED_TELEFONO
        defaultDatosUsuarioShouldBeFound("telefono.lessThan=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByTelefonoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where telefono is greater than DEFAULT_TELEFONO
        defaultDatosUsuarioShouldNotBeFound("telefono.greaterThan=" + DEFAULT_TELEFONO);

        // Get all the datosUsuarioList where telefono is greater than SMALLER_TELEFONO
        defaultDatosUsuarioShouldBeFound("telefono.greaterThan=" + SMALLER_TELEFONO);
    }


    @Test
    @Transactional
    public void getAllDatosUsuariosByPaisIsEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where pais equals to DEFAULT_PAIS
        defaultDatosUsuarioShouldBeFound("pais.equals=" + DEFAULT_PAIS);

        // Get all the datosUsuarioList where pais equals to UPDATED_PAIS
        defaultDatosUsuarioShouldNotBeFound("pais.equals=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByPaisIsNotEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where pais not equals to DEFAULT_PAIS
        defaultDatosUsuarioShouldNotBeFound("pais.notEquals=" + DEFAULT_PAIS);

        // Get all the datosUsuarioList where pais not equals to UPDATED_PAIS
        defaultDatosUsuarioShouldBeFound("pais.notEquals=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByPaisIsInShouldWork() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where pais in DEFAULT_PAIS or UPDATED_PAIS
        defaultDatosUsuarioShouldBeFound("pais.in=" + DEFAULT_PAIS + "," + UPDATED_PAIS);

        // Get all the datosUsuarioList where pais equals to UPDATED_PAIS
        defaultDatosUsuarioShouldNotBeFound("pais.in=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByPaisIsNullOrNotNull() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where pais is not null
        defaultDatosUsuarioShouldBeFound("pais.specified=true");

        // Get all the datosUsuarioList where pais is null
        defaultDatosUsuarioShouldNotBeFound("pais.specified=false");
    }
                @Test
    @Transactional
    public void getAllDatosUsuariosByPaisContainsSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where pais contains DEFAULT_PAIS
        defaultDatosUsuarioShouldBeFound("pais.contains=" + DEFAULT_PAIS);

        // Get all the datosUsuarioList where pais contains UPDATED_PAIS
        defaultDatosUsuarioShouldNotBeFound("pais.contains=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByPaisNotContainsSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where pais does not contain DEFAULT_PAIS
        defaultDatosUsuarioShouldNotBeFound("pais.doesNotContain=" + DEFAULT_PAIS);

        // Get all the datosUsuarioList where pais does not contain UPDATED_PAIS
        defaultDatosUsuarioShouldBeFound("pais.doesNotContain=" + UPDATED_PAIS);
    }


    @Test
    @Transactional
    public void getAllDatosUsuariosByCiudadIsEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where ciudad equals to DEFAULT_CIUDAD
        defaultDatosUsuarioShouldBeFound("ciudad.equals=" + DEFAULT_CIUDAD);

        // Get all the datosUsuarioList where ciudad equals to UPDATED_CIUDAD
        defaultDatosUsuarioShouldNotBeFound("ciudad.equals=" + UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByCiudadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where ciudad not equals to DEFAULT_CIUDAD
        defaultDatosUsuarioShouldNotBeFound("ciudad.notEquals=" + DEFAULT_CIUDAD);

        // Get all the datosUsuarioList where ciudad not equals to UPDATED_CIUDAD
        defaultDatosUsuarioShouldBeFound("ciudad.notEquals=" + UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByCiudadIsInShouldWork() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where ciudad in DEFAULT_CIUDAD or UPDATED_CIUDAD
        defaultDatosUsuarioShouldBeFound("ciudad.in=" + DEFAULT_CIUDAD + "," + UPDATED_CIUDAD);

        // Get all the datosUsuarioList where ciudad equals to UPDATED_CIUDAD
        defaultDatosUsuarioShouldNotBeFound("ciudad.in=" + UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByCiudadIsNullOrNotNull() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where ciudad is not null
        defaultDatosUsuarioShouldBeFound("ciudad.specified=true");

        // Get all the datosUsuarioList where ciudad is null
        defaultDatosUsuarioShouldNotBeFound("ciudad.specified=false");
    }
                @Test
    @Transactional
    public void getAllDatosUsuariosByCiudadContainsSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where ciudad contains DEFAULT_CIUDAD
        defaultDatosUsuarioShouldBeFound("ciudad.contains=" + DEFAULT_CIUDAD);

        // Get all the datosUsuarioList where ciudad contains UPDATED_CIUDAD
        defaultDatosUsuarioShouldNotBeFound("ciudad.contains=" + UPDATED_CIUDAD);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByCiudadNotContainsSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where ciudad does not contain DEFAULT_CIUDAD
        defaultDatosUsuarioShouldNotBeFound("ciudad.doesNotContain=" + DEFAULT_CIUDAD);

        // Get all the datosUsuarioList where ciudad does not contain UPDATED_CIUDAD
        defaultDatosUsuarioShouldBeFound("ciudad.doesNotContain=" + UPDATED_CIUDAD);
    }


    @Test
    @Transactional
    public void getAllDatosUsuariosByDireccionIsEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where direccion equals to DEFAULT_DIRECCION
        defaultDatosUsuarioShouldBeFound("direccion.equals=" + DEFAULT_DIRECCION);

        // Get all the datosUsuarioList where direccion equals to UPDATED_DIRECCION
        defaultDatosUsuarioShouldNotBeFound("direccion.equals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByDireccionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where direccion not equals to DEFAULT_DIRECCION
        defaultDatosUsuarioShouldNotBeFound("direccion.notEquals=" + DEFAULT_DIRECCION);

        // Get all the datosUsuarioList where direccion not equals to UPDATED_DIRECCION
        defaultDatosUsuarioShouldBeFound("direccion.notEquals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByDireccionIsInShouldWork() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where direccion in DEFAULT_DIRECCION or UPDATED_DIRECCION
        defaultDatosUsuarioShouldBeFound("direccion.in=" + DEFAULT_DIRECCION + "," + UPDATED_DIRECCION);

        // Get all the datosUsuarioList where direccion equals to UPDATED_DIRECCION
        defaultDatosUsuarioShouldNotBeFound("direccion.in=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByDireccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where direccion is not null
        defaultDatosUsuarioShouldBeFound("direccion.specified=true");

        // Get all the datosUsuarioList where direccion is null
        defaultDatosUsuarioShouldNotBeFound("direccion.specified=false");
    }
                @Test
    @Transactional
    public void getAllDatosUsuariosByDireccionContainsSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where direccion contains DEFAULT_DIRECCION
        defaultDatosUsuarioShouldBeFound("direccion.contains=" + DEFAULT_DIRECCION);

        // Get all the datosUsuarioList where direccion contains UPDATED_DIRECCION
        defaultDatosUsuarioShouldNotBeFound("direccion.contains=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllDatosUsuariosByDireccionNotContainsSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);

        // Get all the datosUsuarioList where direccion does not contain DEFAULT_DIRECCION
        defaultDatosUsuarioShouldNotBeFound("direccion.doesNotContain=" + DEFAULT_DIRECCION);

        // Get all the datosUsuarioList where direccion does not contain UPDATED_DIRECCION
        defaultDatosUsuarioShouldBeFound("direccion.doesNotContain=" + UPDATED_DIRECCION);
    }


    @Test
    @Transactional
    public void getAllDatosUsuariosByIdUserIsEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);
        User idUser = UserResourceIT.createEntity(em);
        em.persist(idUser);
        em.flush();
        datosUsuario.setIdUser(idUser);
        datosUsuarioRepository.saveAndFlush(datosUsuario);
        Long idUserId = idUser.getId();

        // Get all the datosUsuarioList where idUser equals to idUserId
        defaultDatosUsuarioShouldBeFound("idUserId.equals=" + idUserId);

        // Get all the datosUsuarioList where idUser equals to idUserId + 1
        defaultDatosUsuarioShouldNotBeFound("idUserId.equals=" + (idUserId + 1));
    }


    @Test
    @Transactional
    public void getAllDatosUsuariosByNacionalidadIsEqualToSomething() throws Exception {
        // Initialize the database
        datosUsuarioRepository.saveAndFlush(datosUsuario);
        Pais nacionalidad = PaisResourceIT.createEntity(em);
        em.persist(nacionalidad);
        em.flush();
        datosUsuario.setNacionalidad(nacionalidad);
        datosUsuarioRepository.saveAndFlush(datosUsuario);
        Long nacionalidadId = nacionalidad.getId();

        // Get all the datosUsuarioList where nacionalidad equals to nacionalidadId
        defaultDatosUsuarioShouldBeFound("nacionalidadId.equals=" + nacionalidadId);

        // Get all the datosUsuarioList where nacionalidad equals to nacionalidadId + 1
        defaultDatosUsuarioShouldNotBeFound("nacionalidadId.equals=" + (nacionalidadId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDatosUsuarioShouldBeFound(String filter) throws Exception {
        restDatosUsuarioMockMvc.perform(get("/api/datos-usuarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(datosUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].ciudad").value(hasItem(DEFAULT_CIUDAD)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));

        // Check, that the count call also returns 1
        restDatosUsuarioMockMvc.perform(get("/api/datos-usuarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDatosUsuarioShouldNotBeFound(String filter) throws Exception {
        restDatosUsuarioMockMvc.perform(get("/api/datos-usuarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDatosUsuarioMockMvc.perform(get("/api/datos-usuarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDatosUsuario() throws Exception {
        // Get the datosUsuario
        restDatosUsuarioMockMvc.perform(get("/api/datos-usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDatosUsuario() throws Exception {
        // Initialize the database
        datosUsuarioService.save(datosUsuario);

        int databaseSizeBeforeUpdate = datosUsuarioRepository.findAll().size();

        // Update the datosUsuario
        DatosUsuario updatedDatosUsuario = datosUsuarioRepository.findById(datosUsuario.getId()).get();
        // Disconnect from session so that the updates on updatedDatosUsuario are not directly saved in db
        em.detach(updatedDatosUsuario);
        updatedDatosUsuario
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .genero(UPDATED_GENERO)
            .telefono(UPDATED_TELEFONO)
            .pais(UPDATED_PAIS)
            .ciudad(UPDATED_CIUDAD)
            .direccion(UPDATED_DIRECCION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restDatosUsuarioMockMvc.perform(put("/api/datos-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDatosUsuario)))
            .andExpect(status().isOk());

        // Validate the DatosUsuario in the database
        List<DatosUsuario> datosUsuarioList = datosUsuarioRepository.findAll();
        assertThat(datosUsuarioList).hasSize(databaseSizeBeforeUpdate);
        DatosUsuario testDatosUsuario = datosUsuarioList.get(datosUsuarioList.size() - 1);
        assertThat(testDatosUsuario.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testDatosUsuario.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testDatosUsuario.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testDatosUsuario.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testDatosUsuario.getCiudad()).isEqualTo(UPDATED_CIUDAD);
        assertThat(testDatosUsuario.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testDatosUsuario.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testDatosUsuario.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDatosUsuario() throws Exception {
        int databaseSizeBeforeUpdate = datosUsuarioRepository.findAll().size();

        // Create the DatosUsuario

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatosUsuarioMockMvc.perform(put("/api/datos-usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(datosUsuario)))
            .andExpect(status().isBadRequest());

        // Validate the DatosUsuario in the database
        List<DatosUsuario> datosUsuarioList = datosUsuarioRepository.findAll();
        assertThat(datosUsuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDatosUsuario() throws Exception {
        // Initialize the database
        datosUsuarioService.save(datosUsuario);

        int databaseSizeBeforeDelete = datosUsuarioRepository.findAll().size();

        // Delete the datosUsuario
        restDatosUsuarioMockMvc.perform(delete("/api/datos-usuarios/{id}", datosUsuario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DatosUsuario> datosUsuarioList = datosUsuarioRepository.findAll();
        assertThat(datosUsuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
