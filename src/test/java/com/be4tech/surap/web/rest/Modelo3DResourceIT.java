package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.Modelo3D;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.repository.Modelo3DRepository;
import com.be4tech.surap.service.Modelo3DService;
import com.be4tech.surap.service.dto.Modelo3DCriteria;
import com.be4tech.surap.service.Modelo3DQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link Modelo3DResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class Modelo3DResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_URT = "AAAAAAAAAA";
    private static final String UPDATED_URT = "BBBBBBBBBB";

    private static final String DEFAULT_COLIDER = "AAAAAAAAAA";
    private static final String UPDATED_COLIDER = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTURE_A = "AAAAAAAAAA";
    private static final String UPDATED_TEXTURE_A = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTURE_BC = "AAAAAAAAAA";
    private static final String UPDATED_TEXTURE_BC = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTURE_N = "AAAAAAAAAA";
    private static final String UPDATED_TEXTURE_N = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTURE_R = "AAAAAAAAAA";
    private static final String UPDATED_TEXTURE_R = "BBBBBBBBBB";

    private static final String DEFAULT_SHADOW = "AAAAAAAAAA";
    private static final String UPDATED_SHADOW = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACUTALIZADO = false;
    private static final Boolean UPDATED_ACUTALIZADO = true;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA = LocalDate.ofEpochDay(-1L);

    @Autowired
    private Modelo3DRepository modelo3DRepository;

    @Autowired
    private Modelo3DService modelo3DService;

    @Autowired
    private Modelo3DQueryService modelo3DQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModelo3DMockMvc;

    private Modelo3D modelo3D;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Modelo3D createEntity(EntityManager em) {
        Modelo3D modelo3D = new Modelo3D()
            .nombre(DEFAULT_NOMBRE)
            .urt(DEFAULT_URT)
            .colider(DEFAULT_COLIDER)
            .textureA(DEFAULT_TEXTURE_A)
            .textureBC(DEFAULT_TEXTURE_BC)
            .textureN(DEFAULT_TEXTURE_N)
            .textureR(DEFAULT_TEXTURE_R)
            .shadow(DEFAULT_SHADOW)
            .acutalizado(DEFAULT_ACUTALIZADO)
            .fecha(DEFAULT_FECHA);
        return modelo3D;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Modelo3D createUpdatedEntity(EntityManager em) {
        Modelo3D modelo3D = new Modelo3D()
            .nombre(UPDATED_NOMBRE)
            .urt(UPDATED_URT)
            .colider(UPDATED_COLIDER)
            .textureA(UPDATED_TEXTURE_A)
            .textureBC(UPDATED_TEXTURE_BC)
            .textureN(UPDATED_TEXTURE_N)
            .textureR(UPDATED_TEXTURE_R)
            .shadow(UPDATED_SHADOW)
            .acutalizado(UPDATED_ACUTALIZADO)
            .fecha(UPDATED_FECHA);
        return modelo3D;
    }

    @BeforeEach
    public void initTest() {
        modelo3D = createEntity(em);
    }

    @Test
    @Transactional
    public void createModelo3D() throws Exception {
        int databaseSizeBeforeCreate = modelo3DRepository.findAll().size();

        // Create the Modelo3D
        restModelo3DMockMvc.perform(post("/api/modelo-3-ds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modelo3D)))
            .andExpect(status().isCreated());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeCreate + 1);
        Modelo3D testModelo3D = modelo3DList.get(modelo3DList.size() - 1);
        assertThat(testModelo3D.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testModelo3D.getUrt()).isEqualTo(DEFAULT_URT);
        assertThat(testModelo3D.getColider()).isEqualTo(DEFAULT_COLIDER);
        assertThat(testModelo3D.getTextureA()).isEqualTo(DEFAULT_TEXTURE_A);
        assertThat(testModelo3D.getTextureBC()).isEqualTo(DEFAULT_TEXTURE_BC);
        assertThat(testModelo3D.getTextureN()).isEqualTo(DEFAULT_TEXTURE_N);
        assertThat(testModelo3D.getTextureR()).isEqualTo(DEFAULT_TEXTURE_R);
        assertThat(testModelo3D.getShadow()).isEqualTo(DEFAULT_SHADOW);
        assertThat(testModelo3D.isAcutalizado()).isEqualTo(DEFAULT_ACUTALIZADO);
        assertThat(testModelo3D.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createModelo3DWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modelo3DRepository.findAll().size();

        // Create the Modelo3D with an existing ID
        modelo3D.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModelo3DMockMvc.perform(post("/api/modelo-3-ds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modelo3D)))
            .andExpect(status().isBadRequest());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllModelo3DS() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList
        restModelo3DMockMvc.perform(get("/api/modelo-3-ds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelo3D.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].urt").value(hasItem(DEFAULT_URT)))
            .andExpect(jsonPath("$.[*].colider").value(hasItem(DEFAULT_COLIDER)))
            .andExpect(jsonPath("$.[*].textureA").value(hasItem(DEFAULT_TEXTURE_A)))
            .andExpect(jsonPath("$.[*].textureBC").value(hasItem(DEFAULT_TEXTURE_BC)))
            .andExpect(jsonPath("$.[*].textureN").value(hasItem(DEFAULT_TEXTURE_N)))
            .andExpect(jsonPath("$.[*].textureR").value(hasItem(DEFAULT_TEXTURE_R)))
            .andExpect(jsonPath("$.[*].shadow").value(hasItem(DEFAULT_SHADOW)))
            .andExpect(jsonPath("$.[*].acutalizado").value(hasItem(DEFAULT_ACUTALIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getModelo3D() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get the modelo3D
        restModelo3DMockMvc.perform(get("/api/modelo-3-ds/{id}", modelo3D.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modelo3D.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.urt").value(DEFAULT_URT))
            .andExpect(jsonPath("$.colider").value(DEFAULT_COLIDER))
            .andExpect(jsonPath("$.textureA").value(DEFAULT_TEXTURE_A))
            .andExpect(jsonPath("$.textureBC").value(DEFAULT_TEXTURE_BC))
            .andExpect(jsonPath("$.textureN").value(DEFAULT_TEXTURE_N))
            .andExpect(jsonPath("$.textureR").value(DEFAULT_TEXTURE_R))
            .andExpect(jsonPath("$.shadow").value(DEFAULT_SHADOW))
            .andExpect(jsonPath("$.acutalizado").value(DEFAULT_ACUTALIZADO.booleanValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }


    @Test
    @Transactional
    public void getModelo3DSByIdFiltering() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        Long id = modelo3D.getId();

        defaultModelo3DShouldBeFound("id.equals=" + id);
        defaultModelo3DShouldNotBeFound("id.notEquals=" + id);

        defaultModelo3DShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultModelo3DShouldNotBeFound("id.greaterThan=" + id);

        defaultModelo3DShouldBeFound("id.lessThanOrEqual=" + id);
        defaultModelo3DShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllModelo3DSByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where nombre equals to DEFAULT_NOMBRE
        defaultModelo3DShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the modelo3DList where nombre equals to UPDATED_NOMBRE
        defaultModelo3DShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where nombre not equals to DEFAULT_NOMBRE
        defaultModelo3DShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the modelo3DList where nombre not equals to UPDATED_NOMBRE
        defaultModelo3DShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultModelo3DShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the modelo3DList where nombre equals to UPDATED_NOMBRE
        defaultModelo3DShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where nombre is not null
        defaultModelo3DShouldBeFound("nombre.specified=true");

        // Get all the modelo3DList where nombre is null
        defaultModelo3DShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllModelo3DSByNombreContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where nombre contains DEFAULT_NOMBRE
        defaultModelo3DShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the modelo3DList where nombre contains UPDATED_NOMBRE
        defaultModelo3DShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where nombre does not contain DEFAULT_NOMBRE
        defaultModelo3DShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the modelo3DList where nombre does not contain UPDATED_NOMBRE
        defaultModelo3DShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllModelo3DSByUrtIsEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where urt equals to DEFAULT_URT
        defaultModelo3DShouldBeFound("urt.equals=" + DEFAULT_URT);

        // Get all the modelo3DList where urt equals to UPDATED_URT
        defaultModelo3DShouldNotBeFound("urt.equals=" + UPDATED_URT);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByUrtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where urt not equals to DEFAULT_URT
        defaultModelo3DShouldNotBeFound("urt.notEquals=" + DEFAULT_URT);

        // Get all the modelo3DList where urt not equals to UPDATED_URT
        defaultModelo3DShouldBeFound("urt.notEquals=" + UPDATED_URT);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByUrtIsInShouldWork() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where urt in DEFAULT_URT or UPDATED_URT
        defaultModelo3DShouldBeFound("urt.in=" + DEFAULT_URT + "," + UPDATED_URT);

        // Get all the modelo3DList where urt equals to UPDATED_URT
        defaultModelo3DShouldNotBeFound("urt.in=" + UPDATED_URT);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByUrtIsNullOrNotNull() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where urt is not null
        defaultModelo3DShouldBeFound("urt.specified=true");

        // Get all the modelo3DList where urt is null
        defaultModelo3DShouldNotBeFound("urt.specified=false");
    }
                @Test
    @Transactional
    public void getAllModelo3DSByUrtContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where urt contains DEFAULT_URT
        defaultModelo3DShouldBeFound("urt.contains=" + DEFAULT_URT);

        // Get all the modelo3DList where urt contains UPDATED_URT
        defaultModelo3DShouldNotBeFound("urt.contains=" + UPDATED_URT);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByUrtNotContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where urt does not contain DEFAULT_URT
        defaultModelo3DShouldNotBeFound("urt.doesNotContain=" + DEFAULT_URT);

        // Get all the modelo3DList where urt does not contain UPDATED_URT
        defaultModelo3DShouldBeFound("urt.doesNotContain=" + UPDATED_URT);
    }


    @Test
    @Transactional
    public void getAllModelo3DSByColiderIsEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where colider equals to DEFAULT_COLIDER
        defaultModelo3DShouldBeFound("colider.equals=" + DEFAULT_COLIDER);

        // Get all the modelo3DList where colider equals to UPDATED_COLIDER
        defaultModelo3DShouldNotBeFound("colider.equals=" + UPDATED_COLIDER);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByColiderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where colider not equals to DEFAULT_COLIDER
        defaultModelo3DShouldNotBeFound("colider.notEquals=" + DEFAULT_COLIDER);

        // Get all the modelo3DList where colider not equals to UPDATED_COLIDER
        defaultModelo3DShouldBeFound("colider.notEquals=" + UPDATED_COLIDER);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByColiderIsInShouldWork() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where colider in DEFAULT_COLIDER or UPDATED_COLIDER
        defaultModelo3DShouldBeFound("colider.in=" + DEFAULT_COLIDER + "," + UPDATED_COLIDER);

        // Get all the modelo3DList where colider equals to UPDATED_COLIDER
        defaultModelo3DShouldNotBeFound("colider.in=" + UPDATED_COLIDER);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByColiderIsNullOrNotNull() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where colider is not null
        defaultModelo3DShouldBeFound("colider.specified=true");

        // Get all the modelo3DList where colider is null
        defaultModelo3DShouldNotBeFound("colider.specified=false");
    }
                @Test
    @Transactional
    public void getAllModelo3DSByColiderContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where colider contains DEFAULT_COLIDER
        defaultModelo3DShouldBeFound("colider.contains=" + DEFAULT_COLIDER);

        // Get all the modelo3DList where colider contains UPDATED_COLIDER
        defaultModelo3DShouldNotBeFound("colider.contains=" + UPDATED_COLIDER);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByColiderNotContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where colider does not contain DEFAULT_COLIDER
        defaultModelo3DShouldNotBeFound("colider.doesNotContain=" + DEFAULT_COLIDER);

        // Get all the modelo3DList where colider does not contain UPDATED_COLIDER
        defaultModelo3DShouldBeFound("colider.doesNotContain=" + UPDATED_COLIDER);
    }


    @Test
    @Transactional
    public void getAllModelo3DSByTextureAIsEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureA equals to DEFAULT_TEXTURE_A
        defaultModelo3DShouldBeFound("textureA.equals=" + DEFAULT_TEXTURE_A);

        // Get all the modelo3DList where textureA equals to UPDATED_TEXTURE_A
        defaultModelo3DShouldNotBeFound("textureA.equals=" + UPDATED_TEXTURE_A);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureAIsNotEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureA not equals to DEFAULT_TEXTURE_A
        defaultModelo3DShouldNotBeFound("textureA.notEquals=" + DEFAULT_TEXTURE_A);

        // Get all the modelo3DList where textureA not equals to UPDATED_TEXTURE_A
        defaultModelo3DShouldBeFound("textureA.notEquals=" + UPDATED_TEXTURE_A);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureAIsInShouldWork() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureA in DEFAULT_TEXTURE_A or UPDATED_TEXTURE_A
        defaultModelo3DShouldBeFound("textureA.in=" + DEFAULT_TEXTURE_A + "," + UPDATED_TEXTURE_A);

        // Get all the modelo3DList where textureA equals to UPDATED_TEXTURE_A
        defaultModelo3DShouldNotBeFound("textureA.in=" + UPDATED_TEXTURE_A);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureAIsNullOrNotNull() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureA is not null
        defaultModelo3DShouldBeFound("textureA.specified=true");

        // Get all the modelo3DList where textureA is null
        defaultModelo3DShouldNotBeFound("textureA.specified=false");
    }
                @Test
    @Transactional
    public void getAllModelo3DSByTextureAContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureA contains DEFAULT_TEXTURE_A
        defaultModelo3DShouldBeFound("textureA.contains=" + DEFAULT_TEXTURE_A);

        // Get all the modelo3DList where textureA contains UPDATED_TEXTURE_A
        defaultModelo3DShouldNotBeFound("textureA.contains=" + UPDATED_TEXTURE_A);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureANotContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureA does not contain DEFAULT_TEXTURE_A
        defaultModelo3DShouldNotBeFound("textureA.doesNotContain=" + DEFAULT_TEXTURE_A);

        // Get all the modelo3DList where textureA does not contain UPDATED_TEXTURE_A
        defaultModelo3DShouldBeFound("textureA.doesNotContain=" + UPDATED_TEXTURE_A);
    }


    @Test
    @Transactional
    public void getAllModelo3DSByTextureBCIsEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureBC equals to DEFAULT_TEXTURE_BC
        defaultModelo3DShouldBeFound("textureBC.equals=" + DEFAULT_TEXTURE_BC);

        // Get all the modelo3DList where textureBC equals to UPDATED_TEXTURE_BC
        defaultModelo3DShouldNotBeFound("textureBC.equals=" + UPDATED_TEXTURE_BC);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureBCIsNotEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureBC not equals to DEFAULT_TEXTURE_BC
        defaultModelo3DShouldNotBeFound("textureBC.notEquals=" + DEFAULT_TEXTURE_BC);

        // Get all the modelo3DList where textureBC not equals to UPDATED_TEXTURE_BC
        defaultModelo3DShouldBeFound("textureBC.notEquals=" + UPDATED_TEXTURE_BC);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureBCIsInShouldWork() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureBC in DEFAULT_TEXTURE_BC or UPDATED_TEXTURE_BC
        defaultModelo3DShouldBeFound("textureBC.in=" + DEFAULT_TEXTURE_BC + "," + UPDATED_TEXTURE_BC);

        // Get all the modelo3DList where textureBC equals to UPDATED_TEXTURE_BC
        defaultModelo3DShouldNotBeFound("textureBC.in=" + UPDATED_TEXTURE_BC);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureBCIsNullOrNotNull() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureBC is not null
        defaultModelo3DShouldBeFound("textureBC.specified=true");

        // Get all the modelo3DList where textureBC is null
        defaultModelo3DShouldNotBeFound("textureBC.specified=false");
    }
                @Test
    @Transactional
    public void getAllModelo3DSByTextureBCContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureBC contains DEFAULT_TEXTURE_BC
        defaultModelo3DShouldBeFound("textureBC.contains=" + DEFAULT_TEXTURE_BC);

        // Get all the modelo3DList where textureBC contains UPDATED_TEXTURE_BC
        defaultModelo3DShouldNotBeFound("textureBC.contains=" + UPDATED_TEXTURE_BC);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureBCNotContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureBC does not contain DEFAULT_TEXTURE_BC
        defaultModelo3DShouldNotBeFound("textureBC.doesNotContain=" + DEFAULT_TEXTURE_BC);

        // Get all the modelo3DList where textureBC does not contain UPDATED_TEXTURE_BC
        defaultModelo3DShouldBeFound("textureBC.doesNotContain=" + UPDATED_TEXTURE_BC);
    }


    @Test
    @Transactional
    public void getAllModelo3DSByTextureNIsEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureN equals to DEFAULT_TEXTURE_N
        defaultModelo3DShouldBeFound("textureN.equals=" + DEFAULT_TEXTURE_N);

        // Get all the modelo3DList where textureN equals to UPDATED_TEXTURE_N
        defaultModelo3DShouldNotBeFound("textureN.equals=" + UPDATED_TEXTURE_N);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureNIsNotEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureN not equals to DEFAULT_TEXTURE_N
        defaultModelo3DShouldNotBeFound("textureN.notEquals=" + DEFAULT_TEXTURE_N);

        // Get all the modelo3DList where textureN not equals to UPDATED_TEXTURE_N
        defaultModelo3DShouldBeFound("textureN.notEquals=" + UPDATED_TEXTURE_N);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureNIsInShouldWork() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureN in DEFAULT_TEXTURE_N or UPDATED_TEXTURE_N
        defaultModelo3DShouldBeFound("textureN.in=" + DEFAULT_TEXTURE_N + "," + UPDATED_TEXTURE_N);

        // Get all the modelo3DList where textureN equals to UPDATED_TEXTURE_N
        defaultModelo3DShouldNotBeFound("textureN.in=" + UPDATED_TEXTURE_N);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureNIsNullOrNotNull() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureN is not null
        defaultModelo3DShouldBeFound("textureN.specified=true");

        // Get all the modelo3DList where textureN is null
        defaultModelo3DShouldNotBeFound("textureN.specified=false");
    }
                @Test
    @Transactional
    public void getAllModelo3DSByTextureNContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureN contains DEFAULT_TEXTURE_N
        defaultModelo3DShouldBeFound("textureN.contains=" + DEFAULT_TEXTURE_N);

        // Get all the modelo3DList where textureN contains UPDATED_TEXTURE_N
        defaultModelo3DShouldNotBeFound("textureN.contains=" + UPDATED_TEXTURE_N);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureNNotContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureN does not contain DEFAULT_TEXTURE_N
        defaultModelo3DShouldNotBeFound("textureN.doesNotContain=" + DEFAULT_TEXTURE_N);

        // Get all the modelo3DList where textureN does not contain UPDATED_TEXTURE_N
        defaultModelo3DShouldBeFound("textureN.doesNotContain=" + UPDATED_TEXTURE_N);
    }


    @Test
    @Transactional
    public void getAllModelo3DSByTextureRIsEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureR equals to DEFAULT_TEXTURE_R
        defaultModelo3DShouldBeFound("textureR.equals=" + DEFAULT_TEXTURE_R);

        // Get all the modelo3DList where textureR equals to UPDATED_TEXTURE_R
        defaultModelo3DShouldNotBeFound("textureR.equals=" + UPDATED_TEXTURE_R);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureRIsNotEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureR not equals to DEFAULT_TEXTURE_R
        defaultModelo3DShouldNotBeFound("textureR.notEquals=" + DEFAULT_TEXTURE_R);

        // Get all the modelo3DList where textureR not equals to UPDATED_TEXTURE_R
        defaultModelo3DShouldBeFound("textureR.notEquals=" + UPDATED_TEXTURE_R);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureRIsInShouldWork() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureR in DEFAULT_TEXTURE_R or UPDATED_TEXTURE_R
        defaultModelo3DShouldBeFound("textureR.in=" + DEFAULT_TEXTURE_R + "," + UPDATED_TEXTURE_R);

        // Get all the modelo3DList where textureR equals to UPDATED_TEXTURE_R
        defaultModelo3DShouldNotBeFound("textureR.in=" + UPDATED_TEXTURE_R);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureRIsNullOrNotNull() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureR is not null
        defaultModelo3DShouldBeFound("textureR.specified=true");

        // Get all the modelo3DList where textureR is null
        defaultModelo3DShouldNotBeFound("textureR.specified=false");
    }
                @Test
    @Transactional
    public void getAllModelo3DSByTextureRContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureR contains DEFAULT_TEXTURE_R
        defaultModelo3DShouldBeFound("textureR.contains=" + DEFAULT_TEXTURE_R);

        // Get all the modelo3DList where textureR contains UPDATED_TEXTURE_R
        defaultModelo3DShouldNotBeFound("textureR.contains=" + UPDATED_TEXTURE_R);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByTextureRNotContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where textureR does not contain DEFAULT_TEXTURE_R
        defaultModelo3DShouldNotBeFound("textureR.doesNotContain=" + DEFAULT_TEXTURE_R);

        // Get all the modelo3DList where textureR does not contain UPDATED_TEXTURE_R
        defaultModelo3DShouldBeFound("textureR.doesNotContain=" + UPDATED_TEXTURE_R);
    }


    @Test
    @Transactional
    public void getAllModelo3DSByShadowIsEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where shadow equals to DEFAULT_SHADOW
        defaultModelo3DShouldBeFound("shadow.equals=" + DEFAULT_SHADOW);

        // Get all the modelo3DList where shadow equals to UPDATED_SHADOW
        defaultModelo3DShouldNotBeFound("shadow.equals=" + UPDATED_SHADOW);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByShadowIsNotEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where shadow not equals to DEFAULT_SHADOW
        defaultModelo3DShouldNotBeFound("shadow.notEquals=" + DEFAULT_SHADOW);

        // Get all the modelo3DList where shadow not equals to UPDATED_SHADOW
        defaultModelo3DShouldBeFound("shadow.notEquals=" + UPDATED_SHADOW);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByShadowIsInShouldWork() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where shadow in DEFAULT_SHADOW or UPDATED_SHADOW
        defaultModelo3DShouldBeFound("shadow.in=" + DEFAULT_SHADOW + "," + UPDATED_SHADOW);

        // Get all the modelo3DList where shadow equals to UPDATED_SHADOW
        defaultModelo3DShouldNotBeFound("shadow.in=" + UPDATED_SHADOW);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByShadowIsNullOrNotNull() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where shadow is not null
        defaultModelo3DShouldBeFound("shadow.specified=true");

        // Get all the modelo3DList where shadow is null
        defaultModelo3DShouldNotBeFound("shadow.specified=false");
    }
                @Test
    @Transactional
    public void getAllModelo3DSByShadowContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where shadow contains DEFAULT_SHADOW
        defaultModelo3DShouldBeFound("shadow.contains=" + DEFAULT_SHADOW);

        // Get all the modelo3DList where shadow contains UPDATED_SHADOW
        defaultModelo3DShouldNotBeFound("shadow.contains=" + UPDATED_SHADOW);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByShadowNotContainsSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where shadow does not contain DEFAULT_SHADOW
        defaultModelo3DShouldNotBeFound("shadow.doesNotContain=" + DEFAULT_SHADOW);

        // Get all the modelo3DList where shadow does not contain UPDATED_SHADOW
        defaultModelo3DShouldBeFound("shadow.doesNotContain=" + UPDATED_SHADOW);
    }


    @Test
    @Transactional
    public void getAllModelo3DSByAcutalizadoIsEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where acutalizado equals to DEFAULT_ACUTALIZADO
        defaultModelo3DShouldBeFound("acutalizado.equals=" + DEFAULT_ACUTALIZADO);

        // Get all the modelo3DList where acutalizado equals to UPDATED_ACUTALIZADO
        defaultModelo3DShouldNotBeFound("acutalizado.equals=" + UPDATED_ACUTALIZADO);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByAcutalizadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where acutalizado not equals to DEFAULT_ACUTALIZADO
        defaultModelo3DShouldNotBeFound("acutalizado.notEquals=" + DEFAULT_ACUTALIZADO);

        // Get all the modelo3DList where acutalizado not equals to UPDATED_ACUTALIZADO
        defaultModelo3DShouldBeFound("acutalizado.notEquals=" + UPDATED_ACUTALIZADO);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByAcutalizadoIsInShouldWork() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where acutalizado in DEFAULT_ACUTALIZADO or UPDATED_ACUTALIZADO
        defaultModelo3DShouldBeFound("acutalizado.in=" + DEFAULT_ACUTALIZADO + "," + UPDATED_ACUTALIZADO);

        // Get all the modelo3DList where acutalizado equals to UPDATED_ACUTALIZADO
        defaultModelo3DShouldNotBeFound("acutalizado.in=" + UPDATED_ACUTALIZADO);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByAcutalizadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where acutalizado is not null
        defaultModelo3DShouldBeFound("acutalizado.specified=true");

        // Get all the modelo3DList where acutalizado is null
        defaultModelo3DShouldNotBeFound("acutalizado.specified=false");
    }

    @Test
    @Transactional
    public void getAllModelo3DSByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where fecha equals to DEFAULT_FECHA
        defaultModelo3DShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the modelo3DList where fecha equals to UPDATED_FECHA
        defaultModelo3DShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where fecha not equals to DEFAULT_FECHA
        defaultModelo3DShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the modelo3DList where fecha not equals to UPDATED_FECHA
        defaultModelo3DShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultModelo3DShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the modelo3DList where fecha equals to UPDATED_FECHA
        defaultModelo3DShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where fecha is not null
        defaultModelo3DShouldBeFound("fecha.specified=true");

        // Get all the modelo3DList where fecha is null
        defaultModelo3DShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllModelo3DSByFechaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where fecha is greater than or equal to DEFAULT_FECHA
        defaultModelo3DShouldBeFound("fecha.greaterThanOrEqual=" + DEFAULT_FECHA);

        // Get all the modelo3DList where fecha is greater than or equal to UPDATED_FECHA
        defaultModelo3DShouldNotBeFound("fecha.greaterThanOrEqual=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByFechaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where fecha is less than or equal to DEFAULT_FECHA
        defaultModelo3DShouldBeFound("fecha.lessThanOrEqual=" + DEFAULT_FECHA);

        // Get all the modelo3DList where fecha is less than or equal to SMALLER_FECHA
        defaultModelo3DShouldNotBeFound("fecha.lessThanOrEqual=" + SMALLER_FECHA);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByFechaIsLessThanSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where fecha is less than DEFAULT_FECHA
        defaultModelo3DShouldNotBeFound("fecha.lessThan=" + DEFAULT_FECHA);

        // Get all the modelo3DList where fecha is less than UPDATED_FECHA
        defaultModelo3DShouldBeFound("fecha.lessThan=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllModelo3DSByFechaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList where fecha is greater than DEFAULT_FECHA
        defaultModelo3DShouldNotBeFound("fecha.greaterThan=" + DEFAULT_FECHA);

        // Get all the modelo3DList where fecha is greater than SMALLER_FECHA
        defaultModelo3DShouldBeFound("fecha.greaterThan=" + SMALLER_FECHA);
    }


    @Test
    @Transactional
    public void getAllModelo3DSByIdUserIsEqualToSomething() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);
        User idUser = UserResourceIT.createEntity(em);
        em.persist(idUser);
        em.flush();
        modelo3D.setIdUser(idUser);
        modelo3DRepository.saveAndFlush(modelo3D);
        Long idUserId = idUser.getId();

        // Get all the modelo3DList where idUser equals to idUserId
        defaultModelo3DShouldBeFound("idUserId.equals=" + idUserId);

        // Get all the modelo3DList where idUser equals to idUserId + 1
        defaultModelo3DShouldNotBeFound("idUserId.equals=" + (idUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultModelo3DShouldBeFound(String filter) throws Exception {
        restModelo3DMockMvc.perform(get("/api/modelo-3-ds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelo3D.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].urt").value(hasItem(DEFAULT_URT)))
            .andExpect(jsonPath("$.[*].colider").value(hasItem(DEFAULT_COLIDER)))
            .andExpect(jsonPath("$.[*].textureA").value(hasItem(DEFAULT_TEXTURE_A)))
            .andExpect(jsonPath("$.[*].textureBC").value(hasItem(DEFAULT_TEXTURE_BC)))
            .andExpect(jsonPath("$.[*].textureN").value(hasItem(DEFAULT_TEXTURE_N)))
            .andExpect(jsonPath("$.[*].textureR").value(hasItem(DEFAULT_TEXTURE_R)))
            .andExpect(jsonPath("$.[*].shadow").value(hasItem(DEFAULT_SHADOW)))
            .andExpect(jsonPath("$.[*].acutalizado").value(hasItem(DEFAULT_ACUTALIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));

        // Check, that the count call also returns 1
        restModelo3DMockMvc.perform(get("/api/modelo-3-ds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultModelo3DShouldNotBeFound(String filter) throws Exception {
        restModelo3DMockMvc.perform(get("/api/modelo-3-ds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restModelo3DMockMvc.perform(get("/api/modelo-3-ds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingModelo3D() throws Exception {
        // Get the modelo3D
        restModelo3DMockMvc.perform(get("/api/modelo-3-ds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModelo3D() throws Exception {
        // Initialize the database
        modelo3DService.save(modelo3D);

        int databaseSizeBeforeUpdate = modelo3DRepository.findAll().size();

        // Update the modelo3D
        Modelo3D updatedModelo3D = modelo3DRepository.findById(modelo3D.getId()).get();
        // Disconnect from session so that the updates on updatedModelo3D are not directly saved in db
        em.detach(updatedModelo3D);
        updatedModelo3D
            .nombre(UPDATED_NOMBRE)
            .urt(UPDATED_URT)
            .colider(UPDATED_COLIDER)
            .textureA(UPDATED_TEXTURE_A)
            .textureBC(UPDATED_TEXTURE_BC)
            .textureN(UPDATED_TEXTURE_N)
            .textureR(UPDATED_TEXTURE_R)
            .shadow(UPDATED_SHADOW)
            .acutalizado(UPDATED_ACUTALIZADO)
            .fecha(UPDATED_FECHA);

        restModelo3DMockMvc.perform(put("/api/modelo-3-ds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedModelo3D)))
            .andExpect(status().isOk());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeUpdate);
        Modelo3D testModelo3D = modelo3DList.get(modelo3DList.size() - 1);
        assertThat(testModelo3D.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testModelo3D.getUrt()).isEqualTo(UPDATED_URT);
        assertThat(testModelo3D.getColider()).isEqualTo(UPDATED_COLIDER);
        assertThat(testModelo3D.getTextureA()).isEqualTo(UPDATED_TEXTURE_A);
        assertThat(testModelo3D.getTextureBC()).isEqualTo(UPDATED_TEXTURE_BC);
        assertThat(testModelo3D.getTextureN()).isEqualTo(UPDATED_TEXTURE_N);
        assertThat(testModelo3D.getTextureR()).isEqualTo(UPDATED_TEXTURE_R);
        assertThat(testModelo3D.getShadow()).isEqualTo(UPDATED_SHADOW);
        assertThat(testModelo3D.isAcutalizado()).isEqualTo(UPDATED_ACUTALIZADO);
        assertThat(testModelo3D.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingModelo3D() throws Exception {
        int databaseSizeBeforeUpdate = modelo3DRepository.findAll().size();

        // Create the Modelo3D

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModelo3DMockMvc.perform(put("/api/modelo-3-ds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modelo3D)))
            .andExpect(status().isBadRequest());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModelo3D() throws Exception {
        // Initialize the database
        modelo3DService.save(modelo3D);

        int databaseSizeBeforeDelete = modelo3DRepository.findAll().size();

        // Delete the modelo3D
        restModelo3DMockMvc.perform(delete("/api/modelo-3-ds/{id}", modelo3D.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
