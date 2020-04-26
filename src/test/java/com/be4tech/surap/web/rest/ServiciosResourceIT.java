package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.Servicios;
import com.be4tech.surap.repository.ServiciosRepository;
import com.be4tech.surap.service.ServiciosService;
import com.be4tech.surap.service.dto.ServiciosCriteria;
import com.be4tech.surap.service.ServiciosQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ServiciosResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class ServiciosResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_VIDEOS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIDEOS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_VIDEOS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIDEOS_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_DOCUMENTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PROVEEDOR = "AAAAAAAAAA";
    private static final String UPDATED_PROVEEDOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_IMPUESTO = 1;
    private static final Integer UPDATED_IMPUESTO = 2;
    private static final Integer SMALLER_IMPUESTO = 1 - 1;

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;
    private static final Integer SMALLER_VALOR = 1 - 1;

    private static final Integer DEFAULT_UNIDAD = 1;
    private static final Integer UPDATED_UNIDAD = 2;
    private static final Integer SMALLER_UNIDAD = 1 - 1;

    private static final Boolean DEFAULT_DISPINIBILIDAD = false;
    private static final Boolean UPDATED_DISPINIBILIDAD = true;

    private static final Integer DEFAULT_DESCUENTO = 1;
    private static final Integer UPDATED_DESCUENTO = 2;
    private static final Integer SMALLER_DESCUENTO = 1 - 1;

    private static final Boolean DEFAULT_REMATE = false;
    private static final Boolean UPDATED_REMATE = true;

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PUNTUACION = 1;
    private static final Integer UPDATED_PUNTUACION = 2;
    private static final Integer SMALLER_PUNTUACION = 1 - 1;

    private static final Integer DEFAULT_VISTOS = 1;
    private static final Integer UPDATED_VISTOS = 2;
    private static final Integer SMALLER_VISTOS = 1 - 1;

    private static final Integer DEFAULT_OFERTA = 1;
    private static final Integer UPDATED_OFERTA = 2;
    private static final Integer SMALLER_OFERTA = 1 - 1;

    private static final Integer DEFAULT_TIEMPO_OFERTA = 1;
    private static final Integer UPDATED_TIEMPO_OFERTA = 2;
    private static final Integer SMALLER_TIEMPO_OFERTA = 1 - 1;

    @Autowired
    private ServiciosRepository serviciosRepository;

    @Autowired
    private ServiciosService serviciosService;

    @Autowired
    private ServiciosQueryService serviciosQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiciosMockMvc;

    private Servicios servicios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicios createEntity(EntityManager em) {
        Servicios servicios = new Servicios()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .videos(DEFAULT_VIDEOS)
            .videosContentType(DEFAULT_VIDEOS_CONTENT_TYPE)
            .documento(DEFAULT_DOCUMENTO)
            .documentoContentType(DEFAULT_DOCUMENTO_CONTENT_TYPE)
            .proveedor(DEFAULT_PROVEEDOR)
            .impuesto(DEFAULT_IMPUESTO)
            .valor(DEFAULT_VALOR)
            .unidad(DEFAULT_UNIDAD)
            .dispinibilidad(DEFAULT_DISPINIBILIDAD)
            .descuento(DEFAULT_DESCUENTO)
            .remate(DEFAULT_REMATE)
            .tags(DEFAULT_TAGS)
            .puntuacion(DEFAULT_PUNTUACION)
            .vistos(DEFAULT_VISTOS)
            .oferta(DEFAULT_OFERTA)
            .tiempoOferta(DEFAULT_TIEMPO_OFERTA);
        return servicios;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicios createUpdatedEntity(EntityManager em) {
        Servicios servicios = new Servicios()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .videos(UPDATED_VIDEOS)
            .videosContentType(UPDATED_VIDEOS_CONTENT_TYPE)
            .documento(UPDATED_DOCUMENTO)
            .documentoContentType(UPDATED_DOCUMENTO_CONTENT_TYPE)
            .proveedor(UPDATED_PROVEEDOR)
            .impuesto(UPDATED_IMPUESTO)
            .valor(UPDATED_VALOR)
            .unidad(UPDATED_UNIDAD)
            .dispinibilidad(UPDATED_DISPINIBILIDAD)
            .descuento(UPDATED_DESCUENTO)
            .remate(UPDATED_REMATE)
            .tags(UPDATED_TAGS)
            .puntuacion(UPDATED_PUNTUACION)
            .vistos(UPDATED_VISTOS)
            .oferta(UPDATED_OFERTA)
            .tiempoOferta(UPDATED_TIEMPO_OFERTA);
        return servicios;
    }

    @BeforeEach
    public void initTest() {
        servicios = createEntity(em);
    }

    @Test
    @Transactional
    public void createServicios() throws Exception {
        int databaseSizeBeforeCreate = serviciosRepository.findAll().size();

        // Create the Servicios
        restServiciosMockMvc.perform(post("/api/servicios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servicios)))
            .andExpect(status().isCreated());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeCreate + 1);
        Servicios testServicios = serviciosList.get(serviciosList.size() - 1);
        assertThat(testServicios.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testServicios.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testServicios.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testServicios.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testServicios.getVideos()).isEqualTo(DEFAULT_VIDEOS);
        assertThat(testServicios.getVideosContentType()).isEqualTo(DEFAULT_VIDEOS_CONTENT_TYPE);
        assertThat(testServicios.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testServicios.getDocumentoContentType()).isEqualTo(DEFAULT_DOCUMENTO_CONTENT_TYPE);
        assertThat(testServicios.getProveedor()).isEqualTo(DEFAULT_PROVEEDOR);
        assertThat(testServicios.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);
        assertThat(testServicios.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testServicios.getUnidad()).isEqualTo(DEFAULT_UNIDAD);
        assertThat(testServicios.isDispinibilidad()).isEqualTo(DEFAULT_DISPINIBILIDAD);
        assertThat(testServicios.getDescuento()).isEqualTo(DEFAULT_DESCUENTO);
        assertThat(testServicios.isRemate()).isEqualTo(DEFAULT_REMATE);
        assertThat(testServicios.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testServicios.getPuntuacion()).isEqualTo(DEFAULT_PUNTUACION);
        assertThat(testServicios.getVistos()).isEqualTo(DEFAULT_VISTOS);
        assertThat(testServicios.getOferta()).isEqualTo(DEFAULT_OFERTA);
        assertThat(testServicios.getTiempoOferta()).isEqualTo(DEFAULT_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void createServiciosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviciosRepository.findAll().size();

        // Create the Servicios with an existing ID
        servicios.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiciosMockMvc.perform(post("/api/servicios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servicios)))
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServicios() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList
        restServiciosMockMvc.perform(get("/api/servicios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicios.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].videosContentType").value(hasItem(DEFAULT_VIDEOS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].videos").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEOS))))
            .andExpect(jsonPath("$.[*].documentoContentType").value(hasItem(DEFAULT_DOCUMENTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENTO))))
            .andExpect(jsonPath("$.[*].proveedor").value(hasItem(DEFAULT_PROVEEDOR)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].unidad").value(hasItem(DEFAULT_UNIDAD)))
            .andExpect(jsonPath("$.[*].dispinibilidad").value(hasItem(DEFAULT_DISPINIBILIDAD.booleanValue())))
            .andExpect(jsonPath("$.[*].descuento").value(hasItem(DEFAULT_DESCUENTO)))
            .andExpect(jsonPath("$.[*].remate").value(hasItem(DEFAULT_REMATE.booleanValue())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)))
            .andExpect(jsonPath("$.[*].puntuacion").value(hasItem(DEFAULT_PUNTUACION)))
            .andExpect(jsonPath("$.[*].vistos").value(hasItem(DEFAULT_VISTOS)))
            .andExpect(jsonPath("$.[*].oferta").value(hasItem(DEFAULT_OFERTA)))
            .andExpect(jsonPath("$.[*].tiempoOferta").value(hasItem(DEFAULT_TIEMPO_OFERTA)));
    }
    
    @Test
    @Transactional
    public void getServicios() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get the servicios
        restServiciosMockMvc.perform(get("/api/servicios/{id}", servicios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicios.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.videosContentType").value(DEFAULT_VIDEOS_CONTENT_TYPE))
            .andExpect(jsonPath("$.videos").value(Base64Utils.encodeToString(DEFAULT_VIDEOS)))
            .andExpect(jsonPath("$.documentoContentType").value(DEFAULT_DOCUMENTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.documento").value(Base64Utils.encodeToString(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.proveedor").value(DEFAULT_PROVEEDOR))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.unidad").value(DEFAULT_UNIDAD))
            .andExpect(jsonPath("$.dispinibilidad").value(DEFAULT_DISPINIBILIDAD.booleanValue()))
            .andExpect(jsonPath("$.descuento").value(DEFAULT_DESCUENTO))
            .andExpect(jsonPath("$.remate").value(DEFAULT_REMATE.booleanValue()))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS))
            .andExpect(jsonPath("$.puntuacion").value(DEFAULT_PUNTUACION))
            .andExpect(jsonPath("$.vistos").value(DEFAULT_VISTOS))
            .andExpect(jsonPath("$.oferta").value(DEFAULT_OFERTA))
            .andExpect(jsonPath("$.tiempoOferta").value(DEFAULT_TIEMPO_OFERTA));
    }


    @Test
    @Transactional
    public void getServiciosByIdFiltering() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        Long id = servicios.getId();

        defaultServiciosShouldBeFound("id.equals=" + id);
        defaultServiciosShouldNotBeFound("id.notEquals=" + id);

        defaultServiciosShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServiciosShouldNotBeFound("id.greaterThan=" + id);

        defaultServiciosShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServiciosShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllServiciosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where nombre equals to DEFAULT_NOMBRE
        defaultServiciosShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the serviciosList where nombre equals to UPDATED_NOMBRE
        defaultServiciosShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllServiciosByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where nombre not equals to DEFAULT_NOMBRE
        defaultServiciosShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the serviciosList where nombre not equals to UPDATED_NOMBRE
        defaultServiciosShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllServiciosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultServiciosShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the serviciosList where nombre equals to UPDATED_NOMBRE
        defaultServiciosShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllServiciosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where nombre is not null
        defaultServiciosShouldBeFound("nombre.specified=true");

        // Get all the serviciosList where nombre is null
        defaultServiciosShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiciosByNombreContainsSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where nombre contains DEFAULT_NOMBRE
        defaultServiciosShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the serviciosList where nombre contains UPDATED_NOMBRE
        defaultServiciosShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllServiciosByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where nombre does not contain DEFAULT_NOMBRE
        defaultServiciosShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the serviciosList where nombre does not contain UPDATED_NOMBRE
        defaultServiciosShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllServiciosByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where descripcion equals to DEFAULT_DESCRIPCION
        defaultServiciosShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the serviciosList where descripcion equals to UPDATED_DESCRIPCION
        defaultServiciosShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllServiciosByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultServiciosShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the serviciosList where descripcion not equals to UPDATED_DESCRIPCION
        defaultServiciosShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllServiciosByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultServiciosShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the serviciosList where descripcion equals to UPDATED_DESCRIPCION
        defaultServiciosShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllServiciosByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where descripcion is not null
        defaultServiciosShouldBeFound("descripcion.specified=true");

        // Get all the serviciosList where descripcion is null
        defaultServiciosShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiciosByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where descripcion contains DEFAULT_DESCRIPCION
        defaultServiciosShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the serviciosList where descripcion contains UPDATED_DESCRIPCION
        defaultServiciosShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllServiciosByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultServiciosShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the serviciosList where descripcion does not contain UPDATED_DESCRIPCION
        defaultServiciosShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }


    @Test
    @Transactional
    public void getAllServiciosByProveedorIsEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where proveedor equals to DEFAULT_PROVEEDOR
        defaultServiciosShouldBeFound("proveedor.equals=" + DEFAULT_PROVEEDOR);

        // Get all the serviciosList where proveedor equals to UPDATED_PROVEEDOR
        defaultServiciosShouldNotBeFound("proveedor.equals=" + UPDATED_PROVEEDOR);
    }

    @Test
    @Transactional
    public void getAllServiciosByProveedorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where proveedor not equals to DEFAULT_PROVEEDOR
        defaultServiciosShouldNotBeFound("proveedor.notEquals=" + DEFAULT_PROVEEDOR);

        // Get all the serviciosList where proveedor not equals to UPDATED_PROVEEDOR
        defaultServiciosShouldBeFound("proveedor.notEquals=" + UPDATED_PROVEEDOR);
    }

    @Test
    @Transactional
    public void getAllServiciosByProveedorIsInShouldWork() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where proveedor in DEFAULT_PROVEEDOR or UPDATED_PROVEEDOR
        defaultServiciosShouldBeFound("proveedor.in=" + DEFAULT_PROVEEDOR + "," + UPDATED_PROVEEDOR);

        // Get all the serviciosList where proveedor equals to UPDATED_PROVEEDOR
        defaultServiciosShouldNotBeFound("proveedor.in=" + UPDATED_PROVEEDOR);
    }

    @Test
    @Transactional
    public void getAllServiciosByProveedorIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where proveedor is not null
        defaultServiciosShouldBeFound("proveedor.specified=true");

        // Get all the serviciosList where proveedor is null
        defaultServiciosShouldNotBeFound("proveedor.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiciosByProveedorContainsSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where proveedor contains DEFAULT_PROVEEDOR
        defaultServiciosShouldBeFound("proveedor.contains=" + DEFAULT_PROVEEDOR);

        // Get all the serviciosList where proveedor contains UPDATED_PROVEEDOR
        defaultServiciosShouldNotBeFound("proveedor.contains=" + UPDATED_PROVEEDOR);
    }

    @Test
    @Transactional
    public void getAllServiciosByProveedorNotContainsSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where proveedor does not contain DEFAULT_PROVEEDOR
        defaultServiciosShouldNotBeFound("proveedor.doesNotContain=" + DEFAULT_PROVEEDOR);

        // Get all the serviciosList where proveedor does not contain UPDATED_PROVEEDOR
        defaultServiciosShouldBeFound("proveedor.doesNotContain=" + UPDATED_PROVEEDOR);
    }


    @Test
    @Transactional
    public void getAllServiciosByImpuestoIsEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where impuesto equals to DEFAULT_IMPUESTO
        defaultServiciosShouldBeFound("impuesto.equals=" + DEFAULT_IMPUESTO);

        // Get all the serviciosList where impuesto equals to UPDATED_IMPUESTO
        defaultServiciosShouldNotBeFound("impuesto.equals=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllServiciosByImpuestoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where impuesto not equals to DEFAULT_IMPUESTO
        defaultServiciosShouldNotBeFound("impuesto.notEquals=" + DEFAULT_IMPUESTO);

        // Get all the serviciosList where impuesto not equals to UPDATED_IMPUESTO
        defaultServiciosShouldBeFound("impuesto.notEquals=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllServiciosByImpuestoIsInShouldWork() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where impuesto in DEFAULT_IMPUESTO or UPDATED_IMPUESTO
        defaultServiciosShouldBeFound("impuesto.in=" + DEFAULT_IMPUESTO + "," + UPDATED_IMPUESTO);

        // Get all the serviciosList where impuesto equals to UPDATED_IMPUESTO
        defaultServiciosShouldNotBeFound("impuesto.in=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllServiciosByImpuestoIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where impuesto is not null
        defaultServiciosShouldBeFound("impuesto.specified=true");

        // Get all the serviciosList where impuesto is null
        defaultServiciosShouldNotBeFound("impuesto.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiciosByImpuestoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where impuesto is greater than or equal to DEFAULT_IMPUESTO
        defaultServiciosShouldBeFound("impuesto.greaterThanOrEqual=" + DEFAULT_IMPUESTO);

        // Get all the serviciosList where impuesto is greater than or equal to UPDATED_IMPUESTO
        defaultServiciosShouldNotBeFound("impuesto.greaterThanOrEqual=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllServiciosByImpuestoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where impuesto is less than or equal to DEFAULT_IMPUESTO
        defaultServiciosShouldBeFound("impuesto.lessThanOrEqual=" + DEFAULT_IMPUESTO);

        // Get all the serviciosList where impuesto is less than or equal to SMALLER_IMPUESTO
        defaultServiciosShouldNotBeFound("impuesto.lessThanOrEqual=" + SMALLER_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllServiciosByImpuestoIsLessThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where impuesto is less than DEFAULT_IMPUESTO
        defaultServiciosShouldNotBeFound("impuesto.lessThan=" + DEFAULT_IMPUESTO);

        // Get all the serviciosList where impuesto is less than UPDATED_IMPUESTO
        defaultServiciosShouldBeFound("impuesto.lessThan=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllServiciosByImpuestoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where impuesto is greater than DEFAULT_IMPUESTO
        defaultServiciosShouldNotBeFound("impuesto.greaterThan=" + DEFAULT_IMPUESTO);

        // Get all the serviciosList where impuesto is greater than SMALLER_IMPUESTO
        defaultServiciosShouldBeFound("impuesto.greaterThan=" + SMALLER_IMPUESTO);
    }


    @Test
    @Transactional
    public void getAllServiciosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where valor equals to DEFAULT_VALOR
        defaultServiciosShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the serviciosList where valor equals to UPDATED_VALOR
        defaultServiciosShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllServiciosByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where valor not equals to DEFAULT_VALOR
        defaultServiciosShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the serviciosList where valor not equals to UPDATED_VALOR
        defaultServiciosShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllServiciosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultServiciosShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the serviciosList where valor equals to UPDATED_VALOR
        defaultServiciosShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllServiciosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where valor is not null
        defaultServiciosShouldBeFound("valor.specified=true");

        // Get all the serviciosList where valor is null
        defaultServiciosShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiciosByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where valor is greater than or equal to DEFAULT_VALOR
        defaultServiciosShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the serviciosList where valor is greater than or equal to UPDATED_VALOR
        defaultServiciosShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllServiciosByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where valor is less than or equal to DEFAULT_VALOR
        defaultServiciosShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the serviciosList where valor is less than or equal to SMALLER_VALOR
        defaultServiciosShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllServiciosByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where valor is less than DEFAULT_VALOR
        defaultServiciosShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the serviciosList where valor is less than UPDATED_VALOR
        defaultServiciosShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllServiciosByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where valor is greater than DEFAULT_VALOR
        defaultServiciosShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the serviciosList where valor is greater than SMALLER_VALOR
        defaultServiciosShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllServiciosByUnidadIsEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where unidad equals to DEFAULT_UNIDAD
        defaultServiciosShouldBeFound("unidad.equals=" + DEFAULT_UNIDAD);

        // Get all the serviciosList where unidad equals to UPDATED_UNIDAD
        defaultServiciosShouldNotBeFound("unidad.equals=" + UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllServiciosByUnidadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where unidad not equals to DEFAULT_UNIDAD
        defaultServiciosShouldNotBeFound("unidad.notEquals=" + DEFAULT_UNIDAD);

        // Get all the serviciosList where unidad not equals to UPDATED_UNIDAD
        defaultServiciosShouldBeFound("unidad.notEquals=" + UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllServiciosByUnidadIsInShouldWork() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where unidad in DEFAULT_UNIDAD or UPDATED_UNIDAD
        defaultServiciosShouldBeFound("unidad.in=" + DEFAULT_UNIDAD + "," + UPDATED_UNIDAD);

        // Get all the serviciosList where unidad equals to UPDATED_UNIDAD
        defaultServiciosShouldNotBeFound("unidad.in=" + UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllServiciosByUnidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where unidad is not null
        defaultServiciosShouldBeFound("unidad.specified=true");

        // Get all the serviciosList where unidad is null
        defaultServiciosShouldNotBeFound("unidad.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiciosByUnidadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where unidad is greater than or equal to DEFAULT_UNIDAD
        defaultServiciosShouldBeFound("unidad.greaterThanOrEqual=" + DEFAULT_UNIDAD);

        // Get all the serviciosList where unidad is greater than or equal to UPDATED_UNIDAD
        defaultServiciosShouldNotBeFound("unidad.greaterThanOrEqual=" + UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllServiciosByUnidadIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where unidad is less than or equal to DEFAULT_UNIDAD
        defaultServiciosShouldBeFound("unidad.lessThanOrEqual=" + DEFAULT_UNIDAD);

        // Get all the serviciosList where unidad is less than or equal to SMALLER_UNIDAD
        defaultServiciosShouldNotBeFound("unidad.lessThanOrEqual=" + SMALLER_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllServiciosByUnidadIsLessThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where unidad is less than DEFAULT_UNIDAD
        defaultServiciosShouldNotBeFound("unidad.lessThan=" + DEFAULT_UNIDAD);

        // Get all the serviciosList where unidad is less than UPDATED_UNIDAD
        defaultServiciosShouldBeFound("unidad.lessThan=" + UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllServiciosByUnidadIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where unidad is greater than DEFAULT_UNIDAD
        defaultServiciosShouldNotBeFound("unidad.greaterThan=" + DEFAULT_UNIDAD);

        // Get all the serviciosList where unidad is greater than SMALLER_UNIDAD
        defaultServiciosShouldBeFound("unidad.greaterThan=" + SMALLER_UNIDAD);
    }


    @Test
    @Transactional
    public void getAllServiciosByDispinibilidadIsEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where dispinibilidad equals to DEFAULT_DISPINIBILIDAD
        defaultServiciosShouldBeFound("dispinibilidad.equals=" + DEFAULT_DISPINIBILIDAD);

        // Get all the serviciosList where dispinibilidad equals to UPDATED_DISPINIBILIDAD
        defaultServiciosShouldNotBeFound("dispinibilidad.equals=" + UPDATED_DISPINIBILIDAD);
    }

    @Test
    @Transactional
    public void getAllServiciosByDispinibilidadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where dispinibilidad not equals to DEFAULT_DISPINIBILIDAD
        defaultServiciosShouldNotBeFound("dispinibilidad.notEquals=" + DEFAULT_DISPINIBILIDAD);

        // Get all the serviciosList where dispinibilidad not equals to UPDATED_DISPINIBILIDAD
        defaultServiciosShouldBeFound("dispinibilidad.notEquals=" + UPDATED_DISPINIBILIDAD);
    }

    @Test
    @Transactional
    public void getAllServiciosByDispinibilidadIsInShouldWork() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where dispinibilidad in DEFAULT_DISPINIBILIDAD or UPDATED_DISPINIBILIDAD
        defaultServiciosShouldBeFound("dispinibilidad.in=" + DEFAULT_DISPINIBILIDAD + "," + UPDATED_DISPINIBILIDAD);

        // Get all the serviciosList where dispinibilidad equals to UPDATED_DISPINIBILIDAD
        defaultServiciosShouldNotBeFound("dispinibilidad.in=" + UPDATED_DISPINIBILIDAD);
    }

    @Test
    @Transactional
    public void getAllServiciosByDispinibilidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where dispinibilidad is not null
        defaultServiciosShouldBeFound("dispinibilidad.specified=true");

        // Get all the serviciosList where dispinibilidad is null
        defaultServiciosShouldNotBeFound("dispinibilidad.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiciosByDescuentoIsEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where descuento equals to DEFAULT_DESCUENTO
        defaultServiciosShouldBeFound("descuento.equals=" + DEFAULT_DESCUENTO);

        // Get all the serviciosList where descuento equals to UPDATED_DESCUENTO
        defaultServiciosShouldNotBeFound("descuento.equals=" + UPDATED_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllServiciosByDescuentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where descuento not equals to DEFAULT_DESCUENTO
        defaultServiciosShouldNotBeFound("descuento.notEquals=" + DEFAULT_DESCUENTO);

        // Get all the serviciosList where descuento not equals to UPDATED_DESCUENTO
        defaultServiciosShouldBeFound("descuento.notEquals=" + UPDATED_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllServiciosByDescuentoIsInShouldWork() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where descuento in DEFAULT_DESCUENTO or UPDATED_DESCUENTO
        defaultServiciosShouldBeFound("descuento.in=" + DEFAULT_DESCUENTO + "," + UPDATED_DESCUENTO);

        // Get all the serviciosList where descuento equals to UPDATED_DESCUENTO
        defaultServiciosShouldNotBeFound("descuento.in=" + UPDATED_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllServiciosByDescuentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where descuento is not null
        defaultServiciosShouldBeFound("descuento.specified=true");

        // Get all the serviciosList where descuento is null
        defaultServiciosShouldNotBeFound("descuento.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiciosByDescuentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where descuento is greater than or equal to DEFAULT_DESCUENTO
        defaultServiciosShouldBeFound("descuento.greaterThanOrEqual=" + DEFAULT_DESCUENTO);

        // Get all the serviciosList where descuento is greater than or equal to UPDATED_DESCUENTO
        defaultServiciosShouldNotBeFound("descuento.greaterThanOrEqual=" + UPDATED_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllServiciosByDescuentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where descuento is less than or equal to DEFAULT_DESCUENTO
        defaultServiciosShouldBeFound("descuento.lessThanOrEqual=" + DEFAULT_DESCUENTO);

        // Get all the serviciosList where descuento is less than or equal to SMALLER_DESCUENTO
        defaultServiciosShouldNotBeFound("descuento.lessThanOrEqual=" + SMALLER_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllServiciosByDescuentoIsLessThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where descuento is less than DEFAULT_DESCUENTO
        defaultServiciosShouldNotBeFound("descuento.lessThan=" + DEFAULT_DESCUENTO);

        // Get all the serviciosList where descuento is less than UPDATED_DESCUENTO
        defaultServiciosShouldBeFound("descuento.lessThan=" + UPDATED_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllServiciosByDescuentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where descuento is greater than DEFAULT_DESCUENTO
        defaultServiciosShouldNotBeFound("descuento.greaterThan=" + DEFAULT_DESCUENTO);

        // Get all the serviciosList where descuento is greater than SMALLER_DESCUENTO
        defaultServiciosShouldBeFound("descuento.greaterThan=" + SMALLER_DESCUENTO);
    }


    @Test
    @Transactional
    public void getAllServiciosByRemateIsEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where remate equals to DEFAULT_REMATE
        defaultServiciosShouldBeFound("remate.equals=" + DEFAULT_REMATE);

        // Get all the serviciosList where remate equals to UPDATED_REMATE
        defaultServiciosShouldNotBeFound("remate.equals=" + UPDATED_REMATE);
    }

    @Test
    @Transactional
    public void getAllServiciosByRemateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where remate not equals to DEFAULT_REMATE
        defaultServiciosShouldNotBeFound("remate.notEquals=" + DEFAULT_REMATE);

        // Get all the serviciosList where remate not equals to UPDATED_REMATE
        defaultServiciosShouldBeFound("remate.notEquals=" + UPDATED_REMATE);
    }

    @Test
    @Transactional
    public void getAllServiciosByRemateIsInShouldWork() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where remate in DEFAULT_REMATE or UPDATED_REMATE
        defaultServiciosShouldBeFound("remate.in=" + DEFAULT_REMATE + "," + UPDATED_REMATE);

        // Get all the serviciosList where remate equals to UPDATED_REMATE
        defaultServiciosShouldNotBeFound("remate.in=" + UPDATED_REMATE);
    }

    @Test
    @Transactional
    public void getAllServiciosByRemateIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where remate is not null
        defaultServiciosShouldBeFound("remate.specified=true");

        // Get all the serviciosList where remate is null
        defaultServiciosShouldNotBeFound("remate.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiciosByTagsIsEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where tags equals to DEFAULT_TAGS
        defaultServiciosShouldBeFound("tags.equals=" + DEFAULT_TAGS);

        // Get all the serviciosList where tags equals to UPDATED_TAGS
        defaultServiciosShouldNotBeFound("tags.equals=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllServiciosByTagsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where tags not equals to DEFAULT_TAGS
        defaultServiciosShouldNotBeFound("tags.notEquals=" + DEFAULT_TAGS);

        // Get all the serviciosList where tags not equals to UPDATED_TAGS
        defaultServiciosShouldBeFound("tags.notEquals=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllServiciosByTagsIsInShouldWork() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where tags in DEFAULT_TAGS or UPDATED_TAGS
        defaultServiciosShouldBeFound("tags.in=" + DEFAULT_TAGS + "," + UPDATED_TAGS);

        // Get all the serviciosList where tags equals to UPDATED_TAGS
        defaultServiciosShouldNotBeFound("tags.in=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllServiciosByTagsIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where tags is not null
        defaultServiciosShouldBeFound("tags.specified=true");

        // Get all the serviciosList where tags is null
        defaultServiciosShouldNotBeFound("tags.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiciosByTagsContainsSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where tags contains DEFAULT_TAGS
        defaultServiciosShouldBeFound("tags.contains=" + DEFAULT_TAGS);

        // Get all the serviciosList where tags contains UPDATED_TAGS
        defaultServiciosShouldNotBeFound("tags.contains=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllServiciosByTagsNotContainsSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where tags does not contain DEFAULT_TAGS
        defaultServiciosShouldNotBeFound("tags.doesNotContain=" + DEFAULT_TAGS);

        // Get all the serviciosList where tags does not contain UPDATED_TAGS
        defaultServiciosShouldBeFound("tags.doesNotContain=" + UPDATED_TAGS);
    }


    @Test
    @Transactional
    public void getAllServiciosByPuntuacionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where puntuacion equals to DEFAULT_PUNTUACION
        defaultServiciosShouldBeFound("puntuacion.equals=" + DEFAULT_PUNTUACION);

        // Get all the serviciosList where puntuacion equals to UPDATED_PUNTUACION
        defaultServiciosShouldNotBeFound("puntuacion.equals=" + UPDATED_PUNTUACION);
    }

    @Test
    @Transactional
    public void getAllServiciosByPuntuacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where puntuacion not equals to DEFAULT_PUNTUACION
        defaultServiciosShouldNotBeFound("puntuacion.notEquals=" + DEFAULT_PUNTUACION);

        // Get all the serviciosList where puntuacion not equals to UPDATED_PUNTUACION
        defaultServiciosShouldBeFound("puntuacion.notEquals=" + UPDATED_PUNTUACION);
    }

    @Test
    @Transactional
    public void getAllServiciosByPuntuacionIsInShouldWork() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where puntuacion in DEFAULT_PUNTUACION or UPDATED_PUNTUACION
        defaultServiciosShouldBeFound("puntuacion.in=" + DEFAULT_PUNTUACION + "," + UPDATED_PUNTUACION);

        // Get all the serviciosList where puntuacion equals to UPDATED_PUNTUACION
        defaultServiciosShouldNotBeFound("puntuacion.in=" + UPDATED_PUNTUACION);
    }

    @Test
    @Transactional
    public void getAllServiciosByPuntuacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where puntuacion is not null
        defaultServiciosShouldBeFound("puntuacion.specified=true");

        // Get all the serviciosList where puntuacion is null
        defaultServiciosShouldNotBeFound("puntuacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiciosByPuntuacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where puntuacion is greater than or equal to DEFAULT_PUNTUACION
        defaultServiciosShouldBeFound("puntuacion.greaterThanOrEqual=" + DEFAULT_PUNTUACION);

        // Get all the serviciosList where puntuacion is greater than or equal to UPDATED_PUNTUACION
        defaultServiciosShouldNotBeFound("puntuacion.greaterThanOrEqual=" + UPDATED_PUNTUACION);
    }

    @Test
    @Transactional
    public void getAllServiciosByPuntuacionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where puntuacion is less than or equal to DEFAULT_PUNTUACION
        defaultServiciosShouldBeFound("puntuacion.lessThanOrEqual=" + DEFAULT_PUNTUACION);

        // Get all the serviciosList where puntuacion is less than or equal to SMALLER_PUNTUACION
        defaultServiciosShouldNotBeFound("puntuacion.lessThanOrEqual=" + SMALLER_PUNTUACION);
    }

    @Test
    @Transactional
    public void getAllServiciosByPuntuacionIsLessThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where puntuacion is less than DEFAULT_PUNTUACION
        defaultServiciosShouldNotBeFound("puntuacion.lessThan=" + DEFAULT_PUNTUACION);

        // Get all the serviciosList where puntuacion is less than UPDATED_PUNTUACION
        defaultServiciosShouldBeFound("puntuacion.lessThan=" + UPDATED_PUNTUACION);
    }

    @Test
    @Transactional
    public void getAllServiciosByPuntuacionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where puntuacion is greater than DEFAULT_PUNTUACION
        defaultServiciosShouldNotBeFound("puntuacion.greaterThan=" + DEFAULT_PUNTUACION);

        // Get all the serviciosList where puntuacion is greater than SMALLER_PUNTUACION
        defaultServiciosShouldBeFound("puntuacion.greaterThan=" + SMALLER_PUNTUACION);
    }


    @Test
    @Transactional
    public void getAllServiciosByVistosIsEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where vistos equals to DEFAULT_VISTOS
        defaultServiciosShouldBeFound("vistos.equals=" + DEFAULT_VISTOS);

        // Get all the serviciosList where vistos equals to UPDATED_VISTOS
        defaultServiciosShouldNotBeFound("vistos.equals=" + UPDATED_VISTOS);
    }

    @Test
    @Transactional
    public void getAllServiciosByVistosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where vistos not equals to DEFAULT_VISTOS
        defaultServiciosShouldNotBeFound("vistos.notEquals=" + DEFAULT_VISTOS);

        // Get all the serviciosList where vistos not equals to UPDATED_VISTOS
        defaultServiciosShouldBeFound("vistos.notEquals=" + UPDATED_VISTOS);
    }

    @Test
    @Transactional
    public void getAllServiciosByVistosIsInShouldWork() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where vistos in DEFAULT_VISTOS or UPDATED_VISTOS
        defaultServiciosShouldBeFound("vistos.in=" + DEFAULT_VISTOS + "," + UPDATED_VISTOS);

        // Get all the serviciosList where vistos equals to UPDATED_VISTOS
        defaultServiciosShouldNotBeFound("vistos.in=" + UPDATED_VISTOS);
    }

    @Test
    @Transactional
    public void getAllServiciosByVistosIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where vistos is not null
        defaultServiciosShouldBeFound("vistos.specified=true");

        // Get all the serviciosList where vistos is null
        defaultServiciosShouldNotBeFound("vistos.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiciosByVistosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where vistos is greater than or equal to DEFAULT_VISTOS
        defaultServiciosShouldBeFound("vistos.greaterThanOrEqual=" + DEFAULT_VISTOS);

        // Get all the serviciosList where vistos is greater than or equal to UPDATED_VISTOS
        defaultServiciosShouldNotBeFound("vistos.greaterThanOrEqual=" + UPDATED_VISTOS);
    }

    @Test
    @Transactional
    public void getAllServiciosByVistosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where vistos is less than or equal to DEFAULT_VISTOS
        defaultServiciosShouldBeFound("vistos.lessThanOrEqual=" + DEFAULT_VISTOS);

        // Get all the serviciosList where vistos is less than or equal to SMALLER_VISTOS
        defaultServiciosShouldNotBeFound("vistos.lessThanOrEqual=" + SMALLER_VISTOS);
    }

    @Test
    @Transactional
    public void getAllServiciosByVistosIsLessThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where vistos is less than DEFAULT_VISTOS
        defaultServiciosShouldNotBeFound("vistos.lessThan=" + DEFAULT_VISTOS);

        // Get all the serviciosList where vistos is less than UPDATED_VISTOS
        defaultServiciosShouldBeFound("vistos.lessThan=" + UPDATED_VISTOS);
    }

    @Test
    @Transactional
    public void getAllServiciosByVistosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where vistos is greater than DEFAULT_VISTOS
        defaultServiciosShouldNotBeFound("vistos.greaterThan=" + DEFAULT_VISTOS);

        // Get all the serviciosList where vistos is greater than SMALLER_VISTOS
        defaultServiciosShouldBeFound("vistos.greaterThan=" + SMALLER_VISTOS);
    }


    @Test
    @Transactional
    public void getAllServiciosByOfertaIsEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where oferta equals to DEFAULT_OFERTA
        defaultServiciosShouldBeFound("oferta.equals=" + DEFAULT_OFERTA);

        // Get all the serviciosList where oferta equals to UPDATED_OFERTA
        defaultServiciosShouldNotBeFound("oferta.equals=" + UPDATED_OFERTA);
    }

    @Test
    @Transactional
    public void getAllServiciosByOfertaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where oferta not equals to DEFAULT_OFERTA
        defaultServiciosShouldNotBeFound("oferta.notEquals=" + DEFAULT_OFERTA);

        // Get all the serviciosList where oferta not equals to UPDATED_OFERTA
        defaultServiciosShouldBeFound("oferta.notEquals=" + UPDATED_OFERTA);
    }

    @Test
    @Transactional
    public void getAllServiciosByOfertaIsInShouldWork() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where oferta in DEFAULT_OFERTA or UPDATED_OFERTA
        defaultServiciosShouldBeFound("oferta.in=" + DEFAULT_OFERTA + "," + UPDATED_OFERTA);

        // Get all the serviciosList where oferta equals to UPDATED_OFERTA
        defaultServiciosShouldNotBeFound("oferta.in=" + UPDATED_OFERTA);
    }

    @Test
    @Transactional
    public void getAllServiciosByOfertaIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where oferta is not null
        defaultServiciosShouldBeFound("oferta.specified=true");

        // Get all the serviciosList where oferta is null
        defaultServiciosShouldNotBeFound("oferta.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiciosByOfertaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where oferta is greater than or equal to DEFAULT_OFERTA
        defaultServiciosShouldBeFound("oferta.greaterThanOrEqual=" + DEFAULT_OFERTA);

        // Get all the serviciosList where oferta is greater than or equal to UPDATED_OFERTA
        defaultServiciosShouldNotBeFound("oferta.greaterThanOrEqual=" + UPDATED_OFERTA);
    }

    @Test
    @Transactional
    public void getAllServiciosByOfertaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where oferta is less than or equal to DEFAULT_OFERTA
        defaultServiciosShouldBeFound("oferta.lessThanOrEqual=" + DEFAULT_OFERTA);

        // Get all the serviciosList where oferta is less than or equal to SMALLER_OFERTA
        defaultServiciosShouldNotBeFound("oferta.lessThanOrEqual=" + SMALLER_OFERTA);
    }

    @Test
    @Transactional
    public void getAllServiciosByOfertaIsLessThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where oferta is less than DEFAULT_OFERTA
        defaultServiciosShouldNotBeFound("oferta.lessThan=" + DEFAULT_OFERTA);

        // Get all the serviciosList where oferta is less than UPDATED_OFERTA
        defaultServiciosShouldBeFound("oferta.lessThan=" + UPDATED_OFERTA);
    }

    @Test
    @Transactional
    public void getAllServiciosByOfertaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where oferta is greater than DEFAULT_OFERTA
        defaultServiciosShouldNotBeFound("oferta.greaterThan=" + DEFAULT_OFERTA);

        // Get all the serviciosList where oferta is greater than SMALLER_OFERTA
        defaultServiciosShouldBeFound("oferta.greaterThan=" + SMALLER_OFERTA);
    }


    @Test
    @Transactional
    public void getAllServiciosByTiempoOfertaIsEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where tiempoOferta equals to DEFAULT_TIEMPO_OFERTA
        defaultServiciosShouldBeFound("tiempoOferta.equals=" + DEFAULT_TIEMPO_OFERTA);

        // Get all the serviciosList where tiempoOferta equals to UPDATED_TIEMPO_OFERTA
        defaultServiciosShouldNotBeFound("tiempoOferta.equals=" + UPDATED_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void getAllServiciosByTiempoOfertaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where tiempoOferta not equals to DEFAULT_TIEMPO_OFERTA
        defaultServiciosShouldNotBeFound("tiempoOferta.notEquals=" + DEFAULT_TIEMPO_OFERTA);

        // Get all the serviciosList where tiempoOferta not equals to UPDATED_TIEMPO_OFERTA
        defaultServiciosShouldBeFound("tiempoOferta.notEquals=" + UPDATED_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void getAllServiciosByTiempoOfertaIsInShouldWork() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where tiempoOferta in DEFAULT_TIEMPO_OFERTA or UPDATED_TIEMPO_OFERTA
        defaultServiciosShouldBeFound("tiempoOferta.in=" + DEFAULT_TIEMPO_OFERTA + "," + UPDATED_TIEMPO_OFERTA);

        // Get all the serviciosList where tiempoOferta equals to UPDATED_TIEMPO_OFERTA
        defaultServiciosShouldNotBeFound("tiempoOferta.in=" + UPDATED_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void getAllServiciosByTiempoOfertaIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where tiempoOferta is not null
        defaultServiciosShouldBeFound("tiempoOferta.specified=true");

        // Get all the serviciosList where tiempoOferta is null
        defaultServiciosShouldNotBeFound("tiempoOferta.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiciosByTiempoOfertaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where tiempoOferta is greater than or equal to DEFAULT_TIEMPO_OFERTA
        defaultServiciosShouldBeFound("tiempoOferta.greaterThanOrEqual=" + DEFAULT_TIEMPO_OFERTA);

        // Get all the serviciosList where tiempoOferta is greater than or equal to UPDATED_TIEMPO_OFERTA
        defaultServiciosShouldNotBeFound("tiempoOferta.greaterThanOrEqual=" + UPDATED_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void getAllServiciosByTiempoOfertaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where tiempoOferta is less than or equal to DEFAULT_TIEMPO_OFERTA
        defaultServiciosShouldBeFound("tiempoOferta.lessThanOrEqual=" + DEFAULT_TIEMPO_OFERTA);

        // Get all the serviciosList where tiempoOferta is less than or equal to SMALLER_TIEMPO_OFERTA
        defaultServiciosShouldNotBeFound("tiempoOferta.lessThanOrEqual=" + SMALLER_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void getAllServiciosByTiempoOfertaIsLessThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where tiempoOferta is less than DEFAULT_TIEMPO_OFERTA
        defaultServiciosShouldNotBeFound("tiempoOferta.lessThan=" + DEFAULT_TIEMPO_OFERTA);

        // Get all the serviciosList where tiempoOferta is less than UPDATED_TIEMPO_OFERTA
        defaultServiciosShouldBeFound("tiempoOferta.lessThan=" + UPDATED_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void getAllServiciosByTiempoOfertaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviciosRepository.saveAndFlush(servicios);

        // Get all the serviciosList where tiempoOferta is greater than DEFAULT_TIEMPO_OFERTA
        defaultServiciosShouldNotBeFound("tiempoOferta.greaterThan=" + DEFAULT_TIEMPO_OFERTA);

        // Get all the serviciosList where tiempoOferta is greater than SMALLER_TIEMPO_OFERTA
        defaultServiciosShouldBeFound("tiempoOferta.greaterThan=" + SMALLER_TIEMPO_OFERTA);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiciosShouldBeFound(String filter) throws Exception {
        restServiciosMockMvc.perform(get("/api/servicios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicios.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].videosContentType").value(hasItem(DEFAULT_VIDEOS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].videos").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEOS))))
            .andExpect(jsonPath("$.[*].documentoContentType").value(hasItem(DEFAULT_DOCUMENTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENTO))))
            .andExpect(jsonPath("$.[*].proveedor").value(hasItem(DEFAULT_PROVEEDOR)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].unidad").value(hasItem(DEFAULT_UNIDAD)))
            .andExpect(jsonPath("$.[*].dispinibilidad").value(hasItem(DEFAULT_DISPINIBILIDAD.booleanValue())))
            .andExpect(jsonPath("$.[*].descuento").value(hasItem(DEFAULT_DESCUENTO)))
            .andExpect(jsonPath("$.[*].remate").value(hasItem(DEFAULT_REMATE.booleanValue())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)))
            .andExpect(jsonPath("$.[*].puntuacion").value(hasItem(DEFAULT_PUNTUACION)))
            .andExpect(jsonPath("$.[*].vistos").value(hasItem(DEFAULT_VISTOS)))
            .andExpect(jsonPath("$.[*].oferta").value(hasItem(DEFAULT_OFERTA)))
            .andExpect(jsonPath("$.[*].tiempoOferta").value(hasItem(DEFAULT_TIEMPO_OFERTA)));

        // Check, that the count call also returns 1
        restServiciosMockMvc.perform(get("/api/servicios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiciosShouldNotBeFound(String filter) throws Exception {
        restServiciosMockMvc.perform(get("/api/servicios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiciosMockMvc.perform(get("/api/servicios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServicios() throws Exception {
        // Get the servicios
        restServiciosMockMvc.perform(get("/api/servicios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServicios() throws Exception {
        // Initialize the database
        serviciosService.save(servicios);

        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();

        // Update the servicios
        Servicios updatedServicios = serviciosRepository.findById(servicios.getId()).get();
        // Disconnect from session so that the updates on updatedServicios are not directly saved in db
        em.detach(updatedServicios);
        updatedServicios
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .videos(UPDATED_VIDEOS)
            .videosContentType(UPDATED_VIDEOS_CONTENT_TYPE)
            .documento(UPDATED_DOCUMENTO)
            .documentoContentType(UPDATED_DOCUMENTO_CONTENT_TYPE)
            .proveedor(UPDATED_PROVEEDOR)
            .impuesto(UPDATED_IMPUESTO)
            .valor(UPDATED_VALOR)
            .unidad(UPDATED_UNIDAD)
            .dispinibilidad(UPDATED_DISPINIBILIDAD)
            .descuento(UPDATED_DESCUENTO)
            .remate(UPDATED_REMATE)
            .tags(UPDATED_TAGS)
            .puntuacion(UPDATED_PUNTUACION)
            .vistos(UPDATED_VISTOS)
            .oferta(UPDATED_OFERTA)
            .tiempoOferta(UPDATED_TIEMPO_OFERTA);

        restServiciosMockMvc.perform(put("/api/servicios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedServicios)))
            .andExpect(status().isOk());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
        Servicios testServicios = serviciosList.get(serviciosList.size() - 1);
        assertThat(testServicios.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testServicios.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testServicios.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testServicios.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testServicios.getVideos()).isEqualTo(UPDATED_VIDEOS);
        assertThat(testServicios.getVideosContentType()).isEqualTo(UPDATED_VIDEOS_CONTENT_TYPE);
        assertThat(testServicios.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testServicios.getDocumentoContentType()).isEqualTo(UPDATED_DOCUMENTO_CONTENT_TYPE);
        assertThat(testServicios.getProveedor()).isEqualTo(UPDATED_PROVEEDOR);
        assertThat(testServicios.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);
        assertThat(testServicios.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testServicios.getUnidad()).isEqualTo(UPDATED_UNIDAD);
        assertThat(testServicios.isDispinibilidad()).isEqualTo(UPDATED_DISPINIBILIDAD);
        assertThat(testServicios.getDescuento()).isEqualTo(UPDATED_DESCUENTO);
        assertThat(testServicios.isRemate()).isEqualTo(UPDATED_REMATE);
        assertThat(testServicios.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testServicios.getPuntuacion()).isEqualTo(UPDATED_PUNTUACION);
        assertThat(testServicios.getVistos()).isEqualTo(UPDATED_VISTOS);
        assertThat(testServicios.getOferta()).isEqualTo(UPDATED_OFERTA);
        assertThat(testServicios.getTiempoOferta()).isEqualTo(UPDATED_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void updateNonExistingServicios() throws Exception {
        int databaseSizeBeforeUpdate = serviciosRepository.findAll().size();

        // Create the Servicios

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiciosMockMvc.perform(put("/api/servicios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(servicios)))
            .andExpect(status().isBadRequest());

        // Validate the Servicios in the database
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServicios() throws Exception {
        // Initialize the database
        serviciosService.save(servicios);

        int databaseSizeBeforeDelete = serviciosRepository.findAll().size();

        // Delete the servicios
        restServiciosMockMvc.perform(delete("/api/servicios/{id}", servicios.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Servicios> serviciosList = serviciosRepository.findAll();
        assertThat(serviciosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
