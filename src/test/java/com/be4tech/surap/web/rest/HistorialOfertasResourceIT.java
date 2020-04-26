package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.HistorialOfertas;
import com.be4tech.surap.domain.Productos;
import com.be4tech.surap.repository.HistorialOfertasRepository;
import com.be4tech.surap.service.HistorialOfertasService;
import com.be4tech.surap.service.dto.HistorialOfertasCriteria;
import com.be4tech.surap.service.HistorialOfertasQueryService;

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
 * Integration tests for the {@link HistorialOfertasResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class HistorialOfertasResourceIT {

    private static final Integer DEFAULT_VALOR_PRODUCTO = 1;
    private static final Integer UPDATED_VALOR_PRODUCTO = 2;
    private static final Integer SMALLER_VALOR_PRODUCTO = 1 - 1;

    private static final Integer DEFAULT_VALOR_OFERTA = 1;
    private static final Integer UPDATED_VALOR_OFERTA = 2;
    private static final Integer SMALLER_VALOR_OFERTA = 1 - 1;

    private static final LocalDate DEFAULT_FECHA_INICIAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIAL = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_INICIAL = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FECHA_FINAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FINAL = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_FINAL = LocalDate.ofEpochDay(-1L);

    @Autowired
    private HistorialOfertasRepository historialOfertasRepository;

    @Autowired
    private HistorialOfertasService historialOfertasService;

    @Autowired
    private HistorialOfertasQueryService historialOfertasQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistorialOfertasMockMvc;

    private HistorialOfertas historialOfertas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistorialOfertas createEntity(EntityManager em) {
        HistorialOfertas historialOfertas = new HistorialOfertas()
            .valorProducto(DEFAULT_VALOR_PRODUCTO)
            .valorOferta(DEFAULT_VALOR_OFERTA)
            .fechaInicial(DEFAULT_FECHA_INICIAL)
            .fechaFinal(DEFAULT_FECHA_FINAL);
        return historialOfertas;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistorialOfertas createUpdatedEntity(EntityManager em) {
        HistorialOfertas historialOfertas = new HistorialOfertas()
            .valorProducto(UPDATED_VALOR_PRODUCTO)
            .valorOferta(UPDATED_VALOR_OFERTA)
            .fechaInicial(UPDATED_FECHA_INICIAL)
            .fechaFinal(UPDATED_FECHA_FINAL);
        return historialOfertas;
    }

    @BeforeEach
    public void initTest() {
        historialOfertas = createEntity(em);
    }

    @Test
    @Transactional
    public void createHistorialOfertas() throws Exception {
        int databaseSizeBeforeCreate = historialOfertasRepository.findAll().size();

        // Create the HistorialOfertas
        restHistorialOfertasMockMvc.perform(post("/api/historial-ofertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historialOfertas)))
            .andExpect(status().isCreated());

        // Validate the HistorialOfertas in the database
        List<HistorialOfertas> historialOfertasList = historialOfertasRepository.findAll();
        assertThat(historialOfertasList).hasSize(databaseSizeBeforeCreate + 1);
        HistorialOfertas testHistorialOfertas = historialOfertasList.get(historialOfertasList.size() - 1);
        assertThat(testHistorialOfertas.getValorProducto()).isEqualTo(DEFAULT_VALOR_PRODUCTO);
        assertThat(testHistorialOfertas.getValorOferta()).isEqualTo(DEFAULT_VALOR_OFERTA);
        assertThat(testHistorialOfertas.getFechaInicial()).isEqualTo(DEFAULT_FECHA_INICIAL);
        assertThat(testHistorialOfertas.getFechaFinal()).isEqualTo(DEFAULT_FECHA_FINAL);
    }

    @Test
    @Transactional
    public void createHistorialOfertasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = historialOfertasRepository.findAll().size();

        // Create the HistorialOfertas with an existing ID
        historialOfertas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistorialOfertasMockMvc.perform(post("/api/historial-ofertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historialOfertas)))
            .andExpect(status().isBadRequest());

        // Validate the HistorialOfertas in the database
        List<HistorialOfertas> historialOfertasList = historialOfertasRepository.findAll();
        assertThat(historialOfertasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHistorialOfertas() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList
        restHistorialOfertasMockMvc.perform(get("/api/historial-ofertas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historialOfertas.getId().intValue())))
            .andExpect(jsonPath("$.[*].valorProducto").value(hasItem(DEFAULT_VALOR_PRODUCTO)))
            .andExpect(jsonPath("$.[*].valorOferta").value(hasItem(DEFAULT_VALOR_OFERTA)))
            .andExpect(jsonPath("$.[*].fechaInicial").value(hasItem(DEFAULT_FECHA_INICIAL.toString())))
            .andExpect(jsonPath("$.[*].fechaFinal").value(hasItem(DEFAULT_FECHA_FINAL.toString())));
    }
    
    @Test
    @Transactional
    public void getHistorialOfertas() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get the historialOfertas
        restHistorialOfertasMockMvc.perform(get("/api/historial-ofertas/{id}", historialOfertas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historialOfertas.getId().intValue()))
            .andExpect(jsonPath("$.valorProducto").value(DEFAULT_VALOR_PRODUCTO))
            .andExpect(jsonPath("$.valorOferta").value(DEFAULT_VALOR_OFERTA))
            .andExpect(jsonPath("$.fechaInicial").value(DEFAULT_FECHA_INICIAL.toString()))
            .andExpect(jsonPath("$.fechaFinal").value(DEFAULT_FECHA_FINAL.toString()));
    }


    @Test
    @Transactional
    public void getHistorialOfertasByIdFiltering() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        Long id = historialOfertas.getId();

        defaultHistorialOfertasShouldBeFound("id.equals=" + id);
        defaultHistorialOfertasShouldNotBeFound("id.notEquals=" + id);

        defaultHistorialOfertasShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHistorialOfertasShouldNotBeFound("id.greaterThan=" + id);

        defaultHistorialOfertasShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHistorialOfertasShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllHistorialOfertasByValorProductoIsEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorProducto equals to DEFAULT_VALOR_PRODUCTO
        defaultHistorialOfertasShouldBeFound("valorProducto.equals=" + DEFAULT_VALOR_PRODUCTO);

        // Get all the historialOfertasList where valorProducto equals to UPDATED_VALOR_PRODUCTO
        defaultHistorialOfertasShouldNotBeFound("valorProducto.equals=" + UPDATED_VALOR_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByValorProductoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorProducto not equals to DEFAULT_VALOR_PRODUCTO
        defaultHistorialOfertasShouldNotBeFound("valorProducto.notEquals=" + DEFAULT_VALOR_PRODUCTO);

        // Get all the historialOfertasList where valorProducto not equals to UPDATED_VALOR_PRODUCTO
        defaultHistorialOfertasShouldBeFound("valorProducto.notEquals=" + UPDATED_VALOR_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByValorProductoIsInShouldWork() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorProducto in DEFAULT_VALOR_PRODUCTO or UPDATED_VALOR_PRODUCTO
        defaultHistorialOfertasShouldBeFound("valorProducto.in=" + DEFAULT_VALOR_PRODUCTO + "," + UPDATED_VALOR_PRODUCTO);

        // Get all the historialOfertasList where valorProducto equals to UPDATED_VALOR_PRODUCTO
        defaultHistorialOfertasShouldNotBeFound("valorProducto.in=" + UPDATED_VALOR_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByValorProductoIsNullOrNotNull() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorProducto is not null
        defaultHistorialOfertasShouldBeFound("valorProducto.specified=true");

        // Get all the historialOfertasList where valorProducto is null
        defaultHistorialOfertasShouldNotBeFound("valorProducto.specified=false");
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByValorProductoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorProducto is greater than or equal to DEFAULT_VALOR_PRODUCTO
        defaultHistorialOfertasShouldBeFound("valorProducto.greaterThanOrEqual=" + DEFAULT_VALOR_PRODUCTO);

        // Get all the historialOfertasList where valorProducto is greater than or equal to UPDATED_VALOR_PRODUCTO
        defaultHistorialOfertasShouldNotBeFound("valorProducto.greaterThanOrEqual=" + UPDATED_VALOR_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByValorProductoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorProducto is less than or equal to DEFAULT_VALOR_PRODUCTO
        defaultHistorialOfertasShouldBeFound("valorProducto.lessThanOrEqual=" + DEFAULT_VALOR_PRODUCTO);

        // Get all the historialOfertasList where valorProducto is less than or equal to SMALLER_VALOR_PRODUCTO
        defaultHistorialOfertasShouldNotBeFound("valorProducto.lessThanOrEqual=" + SMALLER_VALOR_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByValorProductoIsLessThanSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorProducto is less than DEFAULT_VALOR_PRODUCTO
        defaultHistorialOfertasShouldNotBeFound("valorProducto.lessThan=" + DEFAULT_VALOR_PRODUCTO);

        // Get all the historialOfertasList where valorProducto is less than UPDATED_VALOR_PRODUCTO
        defaultHistorialOfertasShouldBeFound("valorProducto.lessThan=" + UPDATED_VALOR_PRODUCTO);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByValorProductoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorProducto is greater than DEFAULT_VALOR_PRODUCTO
        defaultHistorialOfertasShouldNotBeFound("valorProducto.greaterThan=" + DEFAULT_VALOR_PRODUCTO);

        // Get all the historialOfertasList where valorProducto is greater than SMALLER_VALOR_PRODUCTO
        defaultHistorialOfertasShouldBeFound("valorProducto.greaterThan=" + SMALLER_VALOR_PRODUCTO);
    }


    @Test
    @Transactional
    public void getAllHistorialOfertasByValorOfertaIsEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorOferta equals to DEFAULT_VALOR_OFERTA
        defaultHistorialOfertasShouldBeFound("valorOferta.equals=" + DEFAULT_VALOR_OFERTA);

        // Get all the historialOfertasList where valorOferta equals to UPDATED_VALOR_OFERTA
        defaultHistorialOfertasShouldNotBeFound("valorOferta.equals=" + UPDATED_VALOR_OFERTA);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByValorOfertaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorOferta not equals to DEFAULT_VALOR_OFERTA
        defaultHistorialOfertasShouldNotBeFound("valorOferta.notEquals=" + DEFAULT_VALOR_OFERTA);

        // Get all the historialOfertasList where valorOferta not equals to UPDATED_VALOR_OFERTA
        defaultHistorialOfertasShouldBeFound("valorOferta.notEquals=" + UPDATED_VALOR_OFERTA);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByValorOfertaIsInShouldWork() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorOferta in DEFAULT_VALOR_OFERTA or UPDATED_VALOR_OFERTA
        defaultHistorialOfertasShouldBeFound("valorOferta.in=" + DEFAULT_VALOR_OFERTA + "," + UPDATED_VALOR_OFERTA);

        // Get all the historialOfertasList where valorOferta equals to UPDATED_VALOR_OFERTA
        defaultHistorialOfertasShouldNotBeFound("valorOferta.in=" + UPDATED_VALOR_OFERTA);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByValorOfertaIsNullOrNotNull() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorOferta is not null
        defaultHistorialOfertasShouldBeFound("valorOferta.specified=true");

        // Get all the historialOfertasList where valorOferta is null
        defaultHistorialOfertasShouldNotBeFound("valorOferta.specified=false");
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByValorOfertaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorOferta is greater than or equal to DEFAULT_VALOR_OFERTA
        defaultHistorialOfertasShouldBeFound("valorOferta.greaterThanOrEqual=" + DEFAULT_VALOR_OFERTA);

        // Get all the historialOfertasList where valorOferta is greater than or equal to UPDATED_VALOR_OFERTA
        defaultHistorialOfertasShouldNotBeFound("valorOferta.greaterThanOrEqual=" + UPDATED_VALOR_OFERTA);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByValorOfertaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorOferta is less than or equal to DEFAULT_VALOR_OFERTA
        defaultHistorialOfertasShouldBeFound("valorOferta.lessThanOrEqual=" + DEFAULT_VALOR_OFERTA);

        // Get all the historialOfertasList where valorOferta is less than or equal to SMALLER_VALOR_OFERTA
        defaultHistorialOfertasShouldNotBeFound("valorOferta.lessThanOrEqual=" + SMALLER_VALOR_OFERTA);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByValorOfertaIsLessThanSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorOferta is less than DEFAULT_VALOR_OFERTA
        defaultHistorialOfertasShouldNotBeFound("valorOferta.lessThan=" + DEFAULT_VALOR_OFERTA);

        // Get all the historialOfertasList where valorOferta is less than UPDATED_VALOR_OFERTA
        defaultHistorialOfertasShouldBeFound("valorOferta.lessThan=" + UPDATED_VALOR_OFERTA);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByValorOfertaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where valorOferta is greater than DEFAULT_VALOR_OFERTA
        defaultHistorialOfertasShouldNotBeFound("valorOferta.greaterThan=" + DEFAULT_VALOR_OFERTA);

        // Get all the historialOfertasList where valorOferta is greater than SMALLER_VALOR_OFERTA
        defaultHistorialOfertasShouldBeFound("valorOferta.greaterThan=" + SMALLER_VALOR_OFERTA);
    }


    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaInicialIsEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaInicial equals to DEFAULT_FECHA_INICIAL
        defaultHistorialOfertasShouldBeFound("fechaInicial.equals=" + DEFAULT_FECHA_INICIAL);

        // Get all the historialOfertasList where fechaInicial equals to UPDATED_FECHA_INICIAL
        defaultHistorialOfertasShouldNotBeFound("fechaInicial.equals=" + UPDATED_FECHA_INICIAL);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaInicialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaInicial not equals to DEFAULT_FECHA_INICIAL
        defaultHistorialOfertasShouldNotBeFound("fechaInicial.notEquals=" + DEFAULT_FECHA_INICIAL);

        // Get all the historialOfertasList where fechaInicial not equals to UPDATED_FECHA_INICIAL
        defaultHistorialOfertasShouldBeFound("fechaInicial.notEquals=" + UPDATED_FECHA_INICIAL);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaInicialIsInShouldWork() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaInicial in DEFAULT_FECHA_INICIAL or UPDATED_FECHA_INICIAL
        defaultHistorialOfertasShouldBeFound("fechaInicial.in=" + DEFAULT_FECHA_INICIAL + "," + UPDATED_FECHA_INICIAL);

        // Get all the historialOfertasList where fechaInicial equals to UPDATED_FECHA_INICIAL
        defaultHistorialOfertasShouldNotBeFound("fechaInicial.in=" + UPDATED_FECHA_INICIAL);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaInicialIsNullOrNotNull() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaInicial is not null
        defaultHistorialOfertasShouldBeFound("fechaInicial.specified=true");

        // Get all the historialOfertasList where fechaInicial is null
        defaultHistorialOfertasShouldNotBeFound("fechaInicial.specified=false");
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaInicialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaInicial is greater than or equal to DEFAULT_FECHA_INICIAL
        defaultHistorialOfertasShouldBeFound("fechaInicial.greaterThanOrEqual=" + DEFAULT_FECHA_INICIAL);

        // Get all the historialOfertasList where fechaInicial is greater than or equal to UPDATED_FECHA_INICIAL
        defaultHistorialOfertasShouldNotBeFound("fechaInicial.greaterThanOrEqual=" + UPDATED_FECHA_INICIAL);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaInicialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaInicial is less than or equal to DEFAULT_FECHA_INICIAL
        defaultHistorialOfertasShouldBeFound("fechaInicial.lessThanOrEqual=" + DEFAULT_FECHA_INICIAL);

        // Get all the historialOfertasList where fechaInicial is less than or equal to SMALLER_FECHA_INICIAL
        defaultHistorialOfertasShouldNotBeFound("fechaInicial.lessThanOrEqual=" + SMALLER_FECHA_INICIAL);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaInicialIsLessThanSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaInicial is less than DEFAULT_FECHA_INICIAL
        defaultHistorialOfertasShouldNotBeFound("fechaInicial.lessThan=" + DEFAULT_FECHA_INICIAL);

        // Get all the historialOfertasList where fechaInicial is less than UPDATED_FECHA_INICIAL
        defaultHistorialOfertasShouldBeFound("fechaInicial.lessThan=" + UPDATED_FECHA_INICIAL);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaInicialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaInicial is greater than DEFAULT_FECHA_INICIAL
        defaultHistorialOfertasShouldNotBeFound("fechaInicial.greaterThan=" + DEFAULT_FECHA_INICIAL);

        // Get all the historialOfertasList where fechaInicial is greater than SMALLER_FECHA_INICIAL
        defaultHistorialOfertasShouldBeFound("fechaInicial.greaterThan=" + SMALLER_FECHA_INICIAL);
    }


    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaFinal equals to DEFAULT_FECHA_FINAL
        defaultHistorialOfertasShouldBeFound("fechaFinal.equals=" + DEFAULT_FECHA_FINAL);

        // Get all the historialOfertasList where fechaFinal equals to UPDATED_FECHA_FINAL
        defaultHistorialOfertasShouldNotBeFound("fechaFinal.equals=" + UPDATED_FECHA_FINAL);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaFinalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaFinal not equals to DEFAULT_FECHA_FINAL
        defaultHistorialOfertasShouldNotBeFound("fechaFinal.notEquals=" + DEFAULT_FECHA_FINAL);

        // Get all the historialOfertasList where fechaFinal not equals to UPDATED_FECHA_FINAL
        defaultHistorialOfertasShouldBeFound("fechaFinal.notEquals=" + UPDATED_FECHA_FINAL);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaFinalIsInShouldWork() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaFinal in DEFAULT_FECHA_FINAL or UPDATED_FECHA_FINAL
        defaultHistorialOfertasShouldBeFound("fechaFinal.in=" + DEFAULT_FECHA_FINAL + "," + UPDATED_FECHA_FINAL);

        // Get all the historialOfertasList where fechaFinal equals to UPDATED_FECHA_FINAL
        defaultHistorialOfertasShouldNotBeFound("fechaFinal.in=" + UPDATED_FECHA_FINAL);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaFinal is not null
        defaultHistorialOfertasShouldBeFound("fechaFinal.specified=true");

        // Get all the historialOfertasList where fechaFinal is null
        defaultHistorialOfertasShouldNotBeFound("fechaFinal.specified=false");
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaFinalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaFinal is greater than or equal to DEFAULT_FECHA_FINAL
        defaultHistorialOfertasShouldBeFound("fechaFinal.greaterThanOrEqual=" + DEFAULT_FECHA_FINAL);

        // Get all the historialOfertasList where fechaFinal is greater than or equal to UPDATED_FECHA_FINAL
        defaultHistorialOfertasShouldNotBeFound("fechaFinal.greaterThanOrEqual=" + UPDATED_FECHA_FINAL);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaFinalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaFinal is less than or equal to DEFAULT_FECHA_FINAL
        defaultHistorialOfertasShouldBeFound("fechaFinal.lessThanOrEqual=" + DEFAULT_FECHA_FINAL);

        // Get all the historialOfertasList where fechaFinal is less than or equal to SMALLER_FECHA_FINAL
        defaultHistorialOfertasShouldNotBeFound("fechaFinal.lessThanOrEqual=" + SMALLER_FECHA_FINAL);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaFinalIsLessThanSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaFinal is less than DEFAULT_FECHA_FINAL
        defaultHistorialOfertasShouldNotBeFound("fechaFinal.lessThan=" + DEFAULT_FECHA_FINAL);

        // Get all the historialOfertasList where fechaFinal is less than UPDATED_FECHA_FINAL
        defaultHistorialOfertasShouldBeFound("fechaFinal.lessThan=" + UPDATED_FECHA_FINAL);
    }

    @Test
    @Transactional
    public void getAllHistorialOfertasByFechaFinalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);

        // Get all the historialOfertasList where fechaFinal is greater than DEFAULT_FECHA_FINAL
        defaultHistorialOfertasShouldNotBeFound("fechaFinal.greaterThan=" + DEFAULT_FECHA_FINAL);

        // Get all the historialOfertasList where fechaFinal is greater than SMALLER_FECHA_FINAL
        defaultHistorialOfertasShouldBeFound("fechaFinal.greaterThan=" + SMALLER_FECHA_FINAL);
    }


    @Test
    @Transactional
    public void getAllHistorialOfertasByProductoIdIsEqualToSomething() throws Exception {
        // Initialize the database
        historialOfertasRepository.saveAndFlush(historialOfertas);
        Productos productoId = ProductosResourceIT.createEntity(em);
        em.persist(productoId);
        em.flush();
        historialOfertas.setProductoId(productoId);
        historialOfertasRepository.saveAndFlush(historialOfertas);
        Long productoIdId = productoId.getId();

        // Get all the historialOfertasList where productoId equals to productoIdId
        defaultHistorialOfertasShouldBeFound("productoIdId.equals=" + productoIdId);

        // Get all the historialOfertasList where productoId equals to productoIdId + 1
        defaultHistorialOfertasShouldNotBeFound("productoIdId.equals=" + (productoIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHistorialOfertasShouldBeFound(String filter) throws Exception {
        restHistorialOfertasMockMvc.perform(get("/api/historial-ofertas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historialOfertas.getId().intValue())))
            .andExpect(jsonPath("$.[*].valorProducto").value(hasItem(DEFAULT_VALOR_PRODUCTO)))
            .andExpect(jsonPath("$.[*].valorOferta").value(hasItem(DEFAULT_VALOR_OFERTA)))
            .andExpect(jsonPath("$.[*].fechaInicial").value(hasItem(DEFAULT_FECHA_INICIAL.toString())))
            .andExpect(jsonPath("$.[*].fechaFinal").value(hasItem(DEFAULT_FECHA_FINAL.toString())));

        // Check, that the count call also returns 1
        restHistorialOfertasMockMvc.perform(get("/api/historial-ofertas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHistorialOfertasShouldNotBeFound(String filter) throws Exception {
        restHistorialOfertasMockMvc.perform(get("/api/historial-ofertas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHistorialOfertasMockMvc.perform(get("/api/historial-ofertas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingHistorialOfertas() throws Exception {
        // Get the historialOfertas
        restHistorialOfertasMockMvc.perform(get("/api/historial-ofertas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHistorialOfertas() throws Exception {
        // Initialize the database
        historialOfertasService.save(historialOfertas);

        int databaseSizeBeforeUpdate = historialOfertasRepository.findAll().size();

        // Update the historialOfertas
        HistorialOfertas updatedHistorialOfertas = historialOfertasRepository.findById(historialOfertas.getId()).get();
        // Disconnect from session so that the updates on updatedHistorialOfertas are not directly saved in db
        em.detach(updatedHistorialOfertas);
        updatedHistorialOfertas
            .valorProducto(UPDATED_VALOR_PRODUCTO)
            .valorOferta(UPDATED_VALOR_OFERTA)
            .fechaInicial(UPDATED_FECHA_INICIAL)
            .fechaFinal(UPDATED_FECHA_FINAL);

        restHistorialOfertasMockMvc.perform(put("/api/historial-ofertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHistorialOfertas)))
            .andExpect(status().isOk());

        // Validate the HistorialOfertas in the database
        List<HistorialOfertas> historialOfertasList = historialOfertasRepository.findAll();
        assertThat(historialOfertasList).hasSize(databaseSizeBeforeUpdate);
        HistorialOfertas testHistorialOfertas = historialOfertasList.get(historialOfertasList.size() - 1);
        assertThat(testHistorialOfertas.getValorProducto()).isEqualTo(UPDATED_VALOR_PRODUCTO);
        assertThat(testHistorialOfertas.getValorOferta()).isEqualTo(UPDATED_VALOR_OFERTA);
        assertThat(testHistorialOfertas.getFechaInicial()).isEqualTo(UPDATED_FECHA_INICIAL);
        assertThat(testHistorialOfertas.getFechaFinal()).isEqualTo(UPDATED_FECHA_FINAL);
    }

    @Test
    @Transactional
    public void updateNonExistingHistorialOfertas() throws Exception {
        int databaseSizeBeforeUpdate = historialOfertasRepository.findAll().size();

        // Create the HistorialOfertas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistorialOfertasMockMvc.perform(put("/api/historial-ofertas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(historialOfertas)))
            .andExpect(status().isBadRequest());

        // Validate the HistorialOfertas in the database
        List<HistorialOfertas> historialOfertasList = historialOfertasRepository.findAll();
        assertThat(historialOfertasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHistorialOfertas() throws Exception {
        // Initialize the database
        historialOfertasService.save(historialOfertas);

        int databaseSizeBeforeDelete = historialOfertasRepository.findAll().size();

        // Delete the historialOfertas
        restHistorialOfertasMockMvc.perform(delete("/api/historial-ofertas/{id}", historialOfertas.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HistorialOfertas> historialOfertasList = historialOfertasRepository.findAll();
        assertThat(historialOfertasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
