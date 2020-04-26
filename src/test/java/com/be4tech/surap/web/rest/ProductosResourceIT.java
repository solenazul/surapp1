package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.Productos;
import com.be4tech.surap.repository.ProductosRepository;
import com.be4tech.surap.service.ProductosService;
import com.be4tech.surap.service.dto.ProductosCriteria;
import com.be4tech.surap.service.ProductosQueryService;

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
 * Integration tests for the {@link ProductosResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class ProductosResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_INVENTARIO = 1;
    private static final Integer UPDATED_INVENTARIO = 2;
    private static final Integer SMALLER_INVENTARIO = 1 - 1;

    private static final Integer DEFAULT_TIPO = 1;
    private static final Integer UPDATED_TIPO = 2;
    private static final Integer SMALLER_TIPO = 1 - 1;

    private static final Integer DEFAULT_IMPUESTO = 1;
    private static final Integer UPDATED_IMPUESTO = 2;
    private static final Integer SMALLER_IMPUESTO = 1 - 1;

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;
    private static final Integer SMALLER_VALOR = 1 - 1;

    private static final Integer DEFAULT_UNIDAD = 1;
    private static final Integer UPDATED_UNIDAD = 2;
    private static final Integer SMALLER_UNIDAD = 1 - 1;

    private static final Integer DEFAULT_ESTADO = 1;
    private static final Integer UPDATED_ESTADO = 2;
    private static final Integer SMALLER_ESTADO = 1 - 1;

    private static final Integer DEFAULT_TIEMPO_ENTREGA = 1;
    private static final Integer UPDATED_TIEMPO_ENTREGA = 2;
    private static final Integer SMALLER_TIEMPO_ENTREGA = 1 - 1;

    private static final Boolean DEFAULT_DISPINIBILIDAD = false;
    private static final Boolean UPDATED_DISPINIBILIDAD = true;

    private static final Boolean DEFAULT_NUEVO = false;
    private static final Boolean UPDATED_NUEVO = true;

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
    private ProductosRepository productosRepository;

    @Autowired
    private ProductosService productosService;

    @Autowired
    private ProductosQueryService productosQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductosMockMvc;

    private Productos productos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Productos createEntity(EntityManager em) {
        Productos productos = new Productos()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .inventario(DEFAULT_INVENTARIO)
            .tipo(DEFAULT_TIPO)
            .impuesto(DEFAULT_IMPUESTO)
            .valor(DEFAULT_VALOR)
            .unidad(DEFAULT_UNIDAD)
            .estado(DEFAULT_ESTADO)
            .tiempoEntrega(DEFAULT_TIEMPO_ENTREGA)
            .dispinibilidad(DEFAULT_DISPINIBILIDAD)
            .nuevo(DEFAULT_NUEVO)
            .descuento(DEFAULT_DESCUENTO)
            .remate(DEFAULT_REMATE)
            .tags(DEFAULT_TAGS)
            .puntuacion(DEFAULT_PUNTUACION)
            .vistos(DEFAULT_VISTOS)
            .oferta(DEFAULT_OFERTA)
            .tiempoOferta(DEFAULT_TIEMPO_OFERTA);
        return productos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Productos createUpdatedEntity(EntityManager em) {
        Productos productos = new Productos()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .inventario(UPDATED_INVENTARIO)
            .tipo(UPDATED_TIPO)
            .impuesto(UPDATED_IMPUESTO)
            .valor(UPDATED_VALOR)
            .unidad(UPDATED_UNIDAD)
            .estado(UPDATED_ESTADO)
            .tiempoEntrega(UPDATED_TIEMPO_ENTREGA)
            .dispinibilidad(UPDATED_DISPINIBILIDAD)
            .nuevo(UPDATED_NUEVO)
            .descuento(UPDATED_DESCUENTO)
            .remate(UPDATED_REMATE)
            .tags(UPDATED_TAGS)
            .puntuacion(UPDATED_PUNTUACION)
            .vistos(UPDATED_VISTOS)
            .oferta(UPDATED_OFERTA)
            .tiempoOferta(UPDATED_TIEMPO_OFERTA);
        return productos;
    }

    @BeforeEach
    public void initTest() {
        productos = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductos() throws Exception {
        int databaseSizeBeforeCreate = productosRepository.findAll().size();

        // Create the Productos
        restProductosMockMvc.perform(post("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productos)))
            .andExpect(status().isCreated());

        // Validate the Productos in the database
        List<Productos> productosList = productosRepository.findAll();
        assertThat(productosList).hasSize(databaseSizeBeforeCreate + 1);
        Productos testProductos = productosList.get(productosList.size() - 1);
        assertThat(testProductos.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProductos.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProductos.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testProductos.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testProductos.getInventario()).isEqualTo(DEFAULT_INVENTARIO);
        assertThat(testProductos.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testProductos.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);
        assertThat(testProductos.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testProductos.getUnidad()).isEqualTo(DEFAULT_UNIDAD);
        assertThat(testProductos.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testProductos.getTiempoEntrega()).isEqualTo(DEFAULT_TIEMPO_ENTREGA);
        assertThat(testProductos.isDispinibilidad()).isEqualTo(DEFAULT_DISPINIBILIDAD);
        assertThat(testProductos.isNuevo()).isEqualTo(DEFAULT_NUEVO);
        assertThat(testProductos.getDescuento()).isEqualTo(DEFAULT_DESCUENTO);
        assertThat(testProductos.isRemate()).isEqualTo(DEFAULT_REMATE);
        assertThat(testProductos.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testProductos.getPuntuacion()).isEqualTo(DEFAULT_PUNTUACION);
        assertThat(testProductos.getVistos()).isEqualTo(DEFAULT_VISTOS);
        assertThat(testProductos.getOferta()).isEqualTo(DEFAULT_OFERTA);
        assertThat(testProductos.getTiempoOferta()).isEqualTo(DEFAULT_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void createProductosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productosRepository.findAll().size();

        // Create the Productos with an existing ID
        productos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductosMockMvc.perform(post("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productos)))
            .andExpect(status().isBadRequest());

        // Validate the Productos in the database
        List<Productos> productosList = productosRepository.findAll();
        assertThat(productosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductos() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList
        restProductosMockMvc.perform(get("/api/productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].inventario").value(hasItem(DEFAULT_INVENTARIO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].unidad").value(hasItem(DEFAULT_UNIDAD)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].tiempoEntrega").value(hasItem(DEFAULT_TIEMPO_ENTREGA)))
            .andExpect(jsonPath("$.[*].dispinibilidad").value(hasItem(DEFAULT_DISPINIBILIDAD.booleanValue())))
            .andExpect(jsonPath("$.[*].nuevo").value(hasItem(DEFAULT_NUEVO.booleanValue())))
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
    public void getProductos() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get the productos
        restProductosMockMvc.perform(get("/api/productos/{id}", productos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productos.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.inventario").value(DEFAULT_INVENTARIO))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.unidad").value(DEFAULT_UNIDAD))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.tiempoEntrega").value(DEFAULT_TIEMPO_ENTREGA))
            .andExpect(jsonPath("$.dispinibilidad").value(DEFAULT_DISPINIBILIDAD.booleanValue()))
            .andExpect(jsonPath("$.nuevo").value(DEFAULT_NUEVO.booleanValue()))
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
    public void getProductosByIdFiltering() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        Long id = productos.getId();

        defaultProductosShouldBeFound("id.equals=" + id);
        defaultProductosShouldNotBeFound("id.notEquals=" + id);

        defaultProductosShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductosShouldNotBeFound("id.greaterThan=" + id);

        defaultProductosShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductosShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where nombre equals to DEFAULT_NOMBRE
        defaultProductosShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the productosList where nombre equals to UPDATED_NOMBRE
        defaultProductosShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllProductosByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where nombre not equals to DEFAULT_NOMBRE
        defaultProductosShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the productosList where nombre not equals to UPDATED_NOMBRE
        defaultProductosShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllProductosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultProductosShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the productosList where nombre equals to UPDATED_NOMBRE
        defaultProductosShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllProductosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where nombre is not null
        defaultProductosShouldBeFound("nombre.specified=true");

        // Get all the productosList where nombre is null
        defaultProductosShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductosByNombreContainsSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where nombre contains DEFAULT_NOMBRE
        defaultProductosShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the productosList where nombre contains UPDATED_NOMBRE
        defaultProductosShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllProductosByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where nombre does not contain DEFAULT_NOMBRE
        defaultProductosShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the productosList where nombre does not contain UPDATED_NOMBRE
        defaultProductosShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllProductosByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where descripcion equals to DEFAULT_DESCRIPCION
        defaultProductosShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the productosList where descripcion equals to UPDATED_DESCRIPCION
        defaultProductosShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllProductosByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultProductosShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the productosList where descripcion not equals to UPDATED_DESCRIPCION
        defaultProductosShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllProductosByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultProductosShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the productosList where descripcion equals to UPDATED_DESCRIPCION
        defaultProductosShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllProductosByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where descripcion is not null
        defaultProductosShouldBeFound("descripcion.specified=true");

        // Get all the productosList where descripcion is null
        defaultProductosShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductosByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where descripcion contains DEFAULT_DESCRIPCION
        defaultProductosShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the productosList where descripcion contains UPDATED_DESCRIPCION
        defaultProductosShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllProductosByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultProductosShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the productosList where descripcion does not contain UPDATED_DESCRIPCION
        defaultProductosShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }


    @Test
    @Transactional
    public void getAllProductosByInventarioIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where inventario equals to DEFAULT_INVENTARIO
        defaultProductosShouldBeFound("inventario.equals=" + DEFAULT_INVENTARIO);

        // Get all the productosList where inventario equals to UPDATED_INVENTARIO
        defaultProductosShouldNotBeFound("inventario.equals=" + UPDATED_INVENTARIO);
    }

    @Test
    @Transactional
    public void getAllProductosByInventarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where inventario not equals to DEFAULT_INVENTARIO
        defaultProductosShouldNotBeFound("inventario.notEquals=" + DEFAULT_INVENTARIO);

        // Get all the productosList where inventario not equals to UPDATED_INVENTARIO
        defaultProductosShouldBeFound("inventario.notEquals=" + UPDATED_INVENTARIO);
    }

    @Test
    @Transactional
    public void getAllProductosByInventarioIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where inventario in DEFAULT_INVENTARIO or UPDATED_INVENTARIO
        defaultProductosShouldBeFound("inventario.in=" + DEFAULT_INVENTARIO + "," + UPDATED_INVENTARIO);

        // Get all the productosList where inventario equals to UPDATED_INVENTARIO
        defaultProductosShouldNotBeFound("inventario.in=" + UPDATED_INVENTARIO);
    }

    @Test
    @Transactional
    public void getAllProductosByInventarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where inventario is not null
        defaultProductosShouldBeFound("inventario.specified=true");

        // Get all the productosList where inventario is null
        defaultProductosShouldNotBeFound("inventario.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByInventarioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where inventario is greater than or equal to DEFAULT_INVENTARIO
        defaultProductosShouldBeFound("inventario.greaterThanOrEqual=" + DEFAULT_INVENTARIO);

        // Get all the productosList where inventario is greater than or equal to UPDATED_INVENTARIO
        defaultProductosShouldNotBeFound("inventario.greaterThanOrEqual=" + UPDATED_INVENTARIO);
    }

    @Test
    @Transactional
    public void getAllProductosByInventarioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where inventario is less than or equal to DEFAULT_INVENTARIO
        defaultProductosShouldBeFound("inventario.lessThanOrEqual=" + DEFAULT_INVENTARIO);

        // Get all the productosList where inventario is less than or equal to SMALLER_INVENTARIO
        defaultProductosShouldNotBeFound("inventario.lessThanOrEqual=" + SMALLER_INVENTARIO);
    }

    @Test
    @Transactional
    public void getAllProductosByInventarioIsLessThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where inventario is less than DEFAULT_INVENTARIO
        defaultProductosShouldNotBeFound("inventario.lessThan=" + DEFAULT_INVENTARIO);

        // Get all the productosList where inventario is less than UPDATED_INVENTARIO
        defaultProductosShouldBeFound("inventario.lessThan=" + UPDATED_INVENTARIO);
    }

    @Test
    @Transactional
    public void getAllProductosByInventarioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where inventario is greater than DEFAULT_INVENTARIO
        defaultProductosShouldNotBeFound("inventario.greaterThan=" + DEFAULT_INVENTARIO);

        // Get all the productosList where inventario is greater than SMALLER_INVENTARIO
        defaultProductosShouldBeFound("inventario.greaterThan=" + SMALLER_INVENTARIO);
    }


    @Test
    @Transactional
    public void getAllProductosByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tipo equals to DEFAULT_TIPO
        defaultProductosShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the productosList where tipo equals to UPDATED_TIPO
        defaultProductosShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllProductosByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tipo not equals to DEFAULT_TIPO
        defaultProductosShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the productosList where tipo not equals to UPDATED_TIPO
        defaultProductosShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllProductosByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultProductosShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the productosList where tipo equals to UPDATED_TIPO
        defaultProductosShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllProductosByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tipo is not null
        defaultProductosShouldBeFound("tipo.specified=true");

        // Get all the productosList where tipo is null
        defaultProductosShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByTipoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tipo is greater than or equal to DEFAULT_TIPO
        defaultProductosShouldBeFound("tipo.greaterThanOrEqual=" + DEFAULT_TIPO);

        // Get all the productosList where tipo is greater than or equal to UPDATED_TIPO
        defaultProductosShouldNotBeFound("tipo.greaterThanOrEqual=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllProductosByTipoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tipo is less than or equal to DEFAULT_TIPO
        defaultProductosShouldBeFound("tipo.lessThanOrEqual=" + DEFAULT_TIPO);

        // Get all the productosList where tipo is less than or equal to SMALLER_TIPO
        defaultProductosShouldNotBeFound("tipo.lessThanOrEqual=" + SMALLER_TIPO);
    }

    @Test
    @Transactional
    public void getAllProductosByTipoIsLessThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tipo is less than DEFAULT_TIPO
        defaultProductosShouldNotBeFound("tipo.lessThan=" + DEFAULT_TIPO);

        // Get all the productosList where tipo is less than UPDATED_TIPO
        defaultProductosShouldBeFound("tipo.lessThan=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void getAllProductosByTipoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tipo is greater than DEFAULT_TIPO
        defaultProductosShouldNotBeFound("tipo.greaterThan=" + DEFAULT_TIPO);

        // Get all the productosList where tipo is greater than SMALLER_TIPO
        defaultProductosShouldBeFound("tipo.greaterThan=" + SMALLER_TIPO);
    }


    @Test
    @Transactional
    public void getAllProductosByImpuestoIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where impuesto equals to DEFAULT_IMPUESTO
        defaultProductosShouldBeFound("impuesto.equals=" + DEFAULT_IMPUESTO);

        // Get all the productosList where impuesto equals to UPDATED_IMPUESTO
        defaultProductosShouldNotBeFound("impuesto.equals=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllProductosByImpuestoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where impuesto not equals to DEFAULT_IMPUESTO
        defaultProductosShouldNotBeFound("impuesto.notEquals=" + DEFAULT_IMPUESTO);

        // Get all the productosList where impuesto not equals to UPDATED_IMPUESTO
        defaultProductosShouldBeFound("impuesto.notEquals=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllProductosByImpuestoIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where impuesto in DEFAULT_IMPUESTO or UPDATED_IMPUESTO
        defaultProductosShouldBeFound("impuesto.in=" + DEFAULT_IMPUESTO + "," + UPDATED_IMPUESTO);

        // Get all the productosList where impuesto equals to UPDATED_IMPUESTO
        defaultProductosShouldNotBeFound("impuesto.in=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllProductosByImpuestoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where impuesto is not null
        defaultProductosShouldBeFound("impuesto.specified=true");

        // Get all the productosList where impuesto is null
        defaultProductosShouldNotBeFound("impuesto.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByImpuestoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where impuesto is greater than or equal to DEFAULT_IMPUESTO
        defaultProductosShouldBeFound("impuesto.greaterThanOrEqual=" + DEFAULT_IMPUESTO);

        // Get all the productosList where impuesto is greater than or equal to UPDATED_IMPUESTO
        defaultProductosShouldNotBeFound("impuesto.greaterThanOrEqual=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllProductosByImpuestoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where impuesto is less than or equal to DEFAULT_IMPUESTO
        defaultProductosShouldBeFound("impuesto.lessThanOrEqual=" + DEFAULT_IMPUESTO);

        // Get all the productosList where impuesto is less than or equal to SMALLER_IMPUESTO
        defaultProductosShouldNotBeFound("impuesto.lessThanOrEqual=" + SMALLER_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllProductosByImpuestoIsLessThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where impuesto is less than DEFAULT_IMPUESTO
        defaultProductosShouldNotBeFound("impuesto.lessThan=" + DEFAULT_IMPUESTO);

        // Get all the productosList where impuesto is less than UPDATED_IMPUESTO
        defaultProductosShouldBeFound("impuesto.lessThan=" + UPDATED_IMPUESTO);
    }

    @Test
    @Transactional
    public void getAllProductosByImpuestoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where impuesto is greater than DEFAULT_IMPUESTO
        defaultProductosShouldNotBeFound("impuesto.greaterThan=" + DEFAULT_IMPUESTO);

        // Get all the productosList where impuesto is greater than SMALLER_IMPUESTO
        defaultProductosShouldBeFound("impuesto.greaterThan=" + SMALLER_IMPUESTO);
    }


    @Test
    @Transactional
    public void getAllProductosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where valor equals to DEFAULT_VALOR
        defaultProductosShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the productosList where valor equals to UPDATED_VALOR
        defaultProductosShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllProductosByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where valor not equals to DEFAULT_VALOR
        defaultProductosShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the productosList where valor not equals to UPDATED_VALOR
        defaultProductosShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllProductosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultProductosShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the productosList where valor equals to UPDATED_VALOR
        defaultProductosShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllProductosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where valor is not null
        defaultProductosShouldBeFound("valor.specified=true");

        // Get all the productosList where valor is null
        defaultProductosShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where valor is greater than or equal to DEFAULT_VALOR
        defaultProductosShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the productosList where valor is greater than or equal to UPDATED_VALOR
        defaultProductosShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllProductosByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where valor is less than or equal to DEFAULT_VALOR
        defaultProductosShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the productosList where valor is less than or equal to SMALLER_VALOR
        defaultProductosShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllProductosByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where valor is less than DEFAULT_VALOR
        defaultProductosShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the productosList where valor is less than UPDATED_VALOR
        defaultProductosShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllProductosByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where valor is greater than DEFAULT_VALOR
        defaultProductosShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the productosList where valor is greater than SMALLER_VALOR
        defaultProductosShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllProductosByUnidadIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where unidad equals to DEFAULT_UNIDAD
        defaultProductosShouldBeFound("unidad.equals=" + DEFAULT_UNIDAD);

        // Get all the productosList where unidad equals to UPDATED_UNIDAD
        defaultProductosShouldNotBeFound("unidad.equals=" + UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByUnidadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where unidad not equals to DEFAULT_UNIDAD
        defaultProductosShouldNotBeFound("unidad.notEquals=" + DEFAULT_UNIDAD);

        // Get all the productosList where unidad not equals to UPDATED_UNIDAD
        defaultProductosShouldBeFound("unidad.notEquals=" + UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByUnidadIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where unidad in DEFAULT_UNIDAD or UPDATED_UNIDAD
        defaultProductosShouldBeFound("unidad.in=" + DEFAULT_UNIDAD + "," + UPDATED_UNIDAD);

        // Get all the productosList where unidad equals to UPDATED_UNIDAD
        defaultProductosShouldNotBeFound("unidad.in=" + UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByUnidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where unidad is not null
        defaultProductosShouldBeFound("unidad.specified=true");

        // Get all the productosList where unidad is null
        defaultProductosShouldNotBeFound("unidad.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByUnidadIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where unidad is greater than or equal to DEFAULT_UNIDAD
        defaultProductosShouldBeFound("unidad.greaterThanOrEqual=" + DEFAULT_UNIDAD);

        // Get all the productosList where unidad is greater than or equal to UPDATED_UNIDAD
        defaultProductosShouldNotBeFound("unidad.greaterThanOrEqual=" + UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByUnidadIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where unidad is less than or equal to DEFAULT_UNIDAD
        defaultProductosShouldBeFound("unidad.lessThanOrEqual=" + DEFAULT_UNIDAD);

        // Get all the productosList where unidad is less than or equal to SMALLER_UNIDAD
        defaultProductosShouldNotBeFound("unidad.lessThanOrEqual=" + SMALLER_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByUnidadIsLessThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where unidad is less than DEFAULT_UNIDAD
        defaultProductosShouldNotBeFound("unidad.lessThan=" + DEFAULT_UNIDAD);

        // Get all the productosList where unidad is less than UPDATED_UNIDAD
        defaultProductosShouldBeFound("unidad.lessThan=" + UPDATED_UNIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByUnidadIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where unidad is greater than DEFAULT_UNIDAD
        defaultProductosShouldNotBeFound("unidad.greaterThan=" + DEFAULT_UNIDAD);

        // Get all the productosList where unidad is greater than SMALLER_UNIDAD
        defaultProductosShouldBeFound("unidad.greaterThan=" + SMALLER_UNIDAD);
    }


    @Test
    @Transactional
    public void getAllProductosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where estado equals to DEFAULT_ESTADO
        defaultProductosShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the productosList where estado equals to UPDATED_ESTADO
        defaultProductosShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllProductosByEstadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where estado not equals to DEFAULT_ESTADO
        defaultProductosShouldNotBeFound("estado.notEquals=" + DEFAULT_ESTADO);

        // Get all the productosList where estado not equals to UPDATED_ESTADO
        defaultProductosShouldBeFound("estado.notEquals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllProductosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultProductosShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the productosList where estado equals to UPDATED_ESTADO
        defaultProductosShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllProductosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where estado is not null
        defaultProductosShouldBeFound("estado.specified=true");

        // Get all the productosList where estado is null
        defaultProductosShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByEstadoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where estado is greater than or equal to DEFAULT_ESTADO
        defaultProductosShouldBeFound("estado.greaterThanOrEqual=" + DEFAULT_ESTADO);

        // Get all the productosList where estado is greater than or equal to UPDATED_ESTADO
        defaultProductosShouldNotBeFound("estado.greaterThanOrEqual=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllProductosByEstadoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where estado is less than or equal to DEFAULT_ESTADO
        defaultProductosShouldBeFound("estado.lessThanOrEqual=" + DEFAULT_ESTADO);

        // Get all the productosList where estado is less than or equal to SMALLER_ESTADO
        defaultProductosShouldNotBeFound("estado.lessThanOrEqual=" + SMALLER_ESTADO);
    }

    @Test
    @Transactional
    public void getAllProductosByEstadoIsLessThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where estado is less than DEFAULT_ESTADO
        defaultProductosShouldNotBeFound("estado.lessThan=" + DEFAULT_ESTADO);

        // Get all the productosList where estado is less than UPDATED_ESTADO
        defaultProductosShouldBeFound("estado.lessThan=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllProductosByEstadoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where estado is greater than DEFAULT_ESTADO
        defaultProductosShouldNotBeFound("estado.greaterThan=" + DEFAULT_ESTADO);

        // Get all the productosList where estado is greater than SMALLER_ESTADO
        defaultProductosShouldBeFound("estado.greaterThan=" + SMALLER_ESTADO);
    }


    @Test
    @Transactional
    public void getAllProductosByTiempoEntregaIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoEntrega equals to DEFAULT_TIEMPO_ENTREGA
        defaultProductosShouldBeFound("tiempoEntrega.equals=" + DEFAULT_TIEMPO_ENTREGA);

        // Get all the productosList where tiempoEntrega equals to UPDATED_TIEMPO_ENTREGA
        defaultProductosShouldNotBeFound("tiempoEntrega.equals=" + UPDATED_TIEMPO_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllProductosByTiempoEntregaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoEntrega not equals to DEFAULT_TIEMPO_ENTREGA
        defaultProductosShouldNotBeFound("tiempoEntrega.notEquals=" + DEFAULT_TIEMPO_ENTREGA);

        // Get all the productosList where tiempoEntrega not equals to UPDATED_TIEMPO_ENTREGA
        defaultProductosShouldBeFound("tiempoEntrega.notEquals=" + UPDATED_TIEMPO_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllProductosByTiempoEntregaIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoEntrega in DEFAULT_TIEMPO_ENTREGA or UPDATED_TIEMPO_ENTREGA
        defaultProductosShouldBeFound("tiempoEntrega.in=" + DEFAULT_TIEMPO_ENTREGA + "," + UPDATED_TIEMPO_ENTREGA);

        // Get all the productosList where tiempoEntrega equals to UPDATED_TIEMPO_ENTREGA
        defaultProductosShouldNotBeFound("tiempoEntrega.in=" + UPDATED_TIEMPO_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllProductosByTiempoEntregaIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoEntrega is not null
        defaultProductosShouldBeFound("tiempoEntrega.specified=true");

        // Get all the productosList where tiempoEntrega is null
        defaultProductosShouldNotBeFound("tiempoEntrega.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByTiempoEntregaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoEntrega is greater than or equal to DEFAULT_TIEMPO_ENTREGA
        defaultProductosShouldBeFound("tiempoEntrega.greaterThanOrEqual=" + DEFAULT_TIEMPO_ENTREGA);

        // Get all the productosList where tiempoEntrega is greater than or equal to UPDATED_TIEMPO_ENTREGA
        defaultProductosShouldNotBeFound("tiempoEntrega.greaterThanOrEqual=" + UPDATED_TIEMPO_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllProductosByTiempoEntregaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoEntrega is less than or equal to DEFAULT_TIEMPO_ENTREGA
        defaultProductosShouldBeFound("tiempoEntrega.lessThanOrEqual=" + DEFAULT_TIEMPO_ENTREGA);

        // Get all the productosList where tiempoEntrega is less than or equal to SMALLER_TIEMPO_ENTREGA
        defaultProductosShouldNotBeFound("tiempoEntrega.lessThanOrEqual=" + SMALLER_TIEMPO_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllProductosByTiempoEntregaIsLessThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoEntrega is less than DEFAULT_TIEMPO_ENTREGA
        defaultProductosShouldNotBeFound("tiempoEntrega.lessThan=" + DEFAULT_TIEMPO_ENTREGA);

        // Get all the productosList where tiempoEntrega is less than UPDATED_TIEMPO_ENTREGA
        defaultProductosShouldBeFound("tiempoEntrega.lessThan=" + UPDATED_TIEMPO_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllProductosByTiempoEntregaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoEntrega is greater than DEFAULT_TIEMPO_ENTREGA
        defaultProductosShouldNotBeFound("tiempoEntrega.greaterThan=" + DEFAULT_TIEMPO_ENTREGA);

        // Get all the productosList where tiempoEntrega is greater than SMALLER_TIEMPO_ENTREGA
        defaultProductosShouldBeFound("tiempoEntrega.greaterThan=" + SMALLER_TIEMPO_ENTREGA);
    }


    @Test
    @Transactional
    public void getAllProductosByDispinibilidadIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where dispinibilidad equals to DEFAULT_DISPINIBILIDAD
        defaultProductosShouldBeFound("dispinibilidad.equals=" + DEFAULT_DISPINIBILIDAD);

        // Get all the productosList where dispinibilidad equals to UPDATED_DISPINIBILIDAD
        defaultProductosShouldNotBeFound("dispinibilidad.equals=" + UPDATED_DISPINIBILIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByDispinibilidadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where dispinibilidad not equals to DEFAULT_DISPINIBILIDAD
        defaultProductosShouldNotBeFound("dispinibilidad.notEquals=" + DEFAULT_DISPINIBILIDAD);

        // Get all the productosList where dispinibilidad not equals to UPDATED_DISPINIBILIDAD
        defaultProductosShouldBeFound("dispinibilidad.notEquals=" + UPDATED_DISPINIBILIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByDispinibilidadIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where dispinibilidad in DEFAULT_DISPINIBILIDAD or UPDATED_DISPINIBILIDAD
        defaultProductosShouldBeFound("dispinibilidad.in=" + DEFAULT_DISPINIBILIDAD + "," + UPDATED_DISPINIBILIDAD);

        // Get all the productosList where dispinibilidad equals to UPDATED_DISPINIBILIDAD
        defaultProductosShouldNotBeFound("dispinibilidad.in=" + UPDATED_DISPINIBILIDAD);
    }

    @Test
    @Transactional
    public void getAllProductosByDispinibilidadIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where dispinibilidad is not null
        defaultProductosShouldBeFound("dispinibilidad.specified=true");

        // Get all the productosList where dispinibilidad is null
        defaultProductosShouldNotBeFound("dispinibilidad.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByNuevoIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where nuevo equals to DEFAULT_NUEVO
        defaultProductosShouldBeFound("nuevo.equals=" + DEFAULT_NUEVO);

        // Get all the productosList where nuevo equals to UPDATED_NUEVO
        defaultProductosShouldNotBeFound("nuevo.equals=" + UPDATED_NUEVO);
    }

    @Test
    @Transactional
    public void getAllProductosByNuevoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where nuevo not equals to DEFAULT_NUEVO
        defaultProductosShouldNotBeFound("nuevo.notEquals=" + DEFAULT_NUEVO);

        // Get all the productosList where nuevo not equals to UPDATED_NUEVO
        defaultProductosShouldBeFound("nuevo.notEquals=" + UPDATED_NUEVO);
    }

    @Test
    @Transactional
    public void getAllProductosByNuevoIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where nuevo in DEFAULT_NUEVO or UPDATED_NUEVO
        defaultProductosShouldBeFound("nuevo.in=" + DEFAULT_NUEVO + "," + UPDATED_NUEVO);

        // Get all the productosList where nuevo equals to UPDATED_NUEVO
        defaultProductosShouldNotBeFound("nuevo.in=" + UPDATED_NUEVO);
    }

    @Test
    @Transactional
    public void getAllProductosByNuevoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where nuevo is not null
        defaultProductosShouldBeFound("nuevo.specified=true");

        // Get all the productosList where nuevo is null
        defaultProductosShouldNotBeFound("nuevo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByDescuentoIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where descuento equals to DEFAULT_DESCUENTO
        defaultProductosShouldBeFound("descuento.equals=" + DEFAULT_DESCUENTO);

        // Get all the productosList where descuento equals to UPDATED_DESCUENTO
        defaultProductosShouldNotBeFound("descuento.equals=" + UPDATED_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllProductosByDescuentoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where descuento not equals to DEFAULT_DESCUENTO
        defaultProductosShouldNotBeFound("descuento.notEquals=" + DEFAULT_DESCUENTO);

        // Get all the productosList where descuento not equals to UPDATED_DESCUENTO
        defaultProductosShouldBeFound("descuento.notEquals=" + UPDATED_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllProductosByDescuentoIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where descuento in DEFAULT_DESCUENTO or UPDATED_DESCUENTO
        defaultProductosShouldBeFound("descuento.in=" + DEFAULT_DESCUENTO + "," + UPDATED_DESCUENTO);

        // Get all the productosList where descuento equals to UPDATED_DESCUENTO
        defaultProductosShouldNotBeFound("descuento.in=" + UPDATED_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllProductosByDescuentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where descuento is not null
        defaultProductosShouldBeFound("descuento.specified=true");

        // Get all the productosList where descuento is null
        defaultProductosShouldNotBeFound("descuento.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByDescuentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where descuento is greater than or equal to DEFAULT_DESCUENTO
        defaultProductosShouldBeFound("descuento.greaterThanOrEqual=" + DEFAULT_DESCUENTO);

        // Get all the productosList where descuento is greater than or equal to UPDATED_DESCUENTO
        defaultProductosShouldNotBeFound("descuento.greaterThanOrEqual=" + UPDATED_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllProductosByDescuentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where descuento is less than or equal to DEFAULT_DESCUENTO
        defaultProductosShouldBeFound("descuento.lessThanOrEqual=" + DEFAULT_DESCUENTO);

        // Get all the productosList where descuento is less than or equal to SMALLER_DESCUENTO
        defaultProductosShouldNotBeFound("descuento.lessThanOrEqual=" + SMALLER_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllProductosByDescuentoIsLessThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where descuento is less than DEFAULT_DESCUENTO
        defaultProductosShouldNotBeFound("descuento.lessThan=" + DEFAULT_DESCUENTO);

        // Get all the productosList where descuento is less than UPDATED_DESCUENTO
        defaultProductosShouldBeFound("descuento.lessThan=" + UPDATED_DESCUENTO);
    }

    @Test
    @Transactional
    public void getAllProductosByDescuentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where descuento is greater than DEFAULT_DESCUENTO
        defaultProductosShouldNotBeFound("descuento.greaterThan=" + DEFAULT_DESCUENTO);

        // Get all the productosList where descuento is greater than SMALLER_DESCUENTO
        defaultProductosShouldBeFound("descuento.greaterThan=" + SMALLER_DESCUENTO);
    }


    @Test
    @Transactional
    public void getAllProductosByRemateIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where remate equals to DEFAULT_REMATE
        defaultProductosShouldBeFound("remate.equals=" + DEFAULT_REMATE);

        // Get all the productosList where remate equals to UPDATED_REMATE
        defaultProductosShouldNotBeFound("remate.equals=" + UPDATED_REMATE);
    }

    @Test
    @Transactional
    public void getAllProductosByRemateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where remate not equals to DEFAULT_REMATE
        defaultProductosShouldNotBeFound("remate.notEquals=" + DEFAULT_REMATE);

        // Get all the productosList where remate not equals to UPDATED_REMATE
        defaultProductosShouldBeFound("remate.notEquals=" + UPDATED_REMATE);
    }

    @Test
    @Transactional
    public void getAllProductosByRemateIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where remate in DEFAULT_REMATE or UPDATED_REMATE
        defaultProductosShouldBeFound("remate.in=" + DEFAULT_REMATE + "," + UPDATED_REMATE);

        // Get all the productosList where remate equals to UPDATED_REMATE
        defaultProductosShouldNotBeFound("remate.in=" + UPDATED_REMATE);
    }

    @Test
    @Transactional
    public void getAllProductosByRemateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where remate is not null
        defaultProductosShouldBeFound("remate.specified=true");

        // Get all the productosList where remate is null
        defaultProductosShouldNotBeFound("remate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByTagsIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tags equals to DEFAULT_TAGS
        defaultProductosShouldBeFound("tags.equals=" + DEFAULT_TAGS);

        // Get all the productosList where tags equals to UPDATED_TAGS
        defaultProductosShouldNotBeFound("tags.equals=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllProductosByTagsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tags not equals to DEFAULT_TAGS
        defaultProductosShouldNotBeFound("tags.notEquals=" + DEFAULT_TAGS);

        // Get all the productosList where tags not equals to UPDATED_TAGS
        defaultProductosShouldBeFound("tags.notEquals=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllProductosByTagsIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tags in DEFAULT_TAGS or UPDATED_TAGS
        defaultProductosShouldBeFound("tags.in=" + DEFAULT_TAGS + "," + UPDATED_TAGS);

        // Get all the productosList where tags equals to UPDATED_TAGS
        defaultProductosShouldNotBeFound("tags.in=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllProductosByTagsIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tags is not null
        defaultProductosShouldBeFound("tags.specified=true");

        // Get all the productosList where tags is null
        defaultProductosShouldNotBeFound("tags.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductosByTagsContainsSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tags contains DEFAULT_TAGS
        defaultProductosShouldBeFound("tags.contains=" + DEFAULT_TAGS);

        // Get all the productosList where tags contains UPDATED_TAGS
        defaultProductosShouldNotBeFound("tags.contains=" + UPDATED_TAGS);
    }

    @Test
    @Transactional
    public void getAllProductosByTagsNotContainsSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tags does not contain DEFAULT_TAGS
        defaultProductosShouldNotBeFound("tags.doesNotContain=" + DEFAULT_TAGS);

        // Get all the productosList where tags does not contain UPDATED_TAGS
        defaultProductosShouldBeFound("tags.doesNotContain=" + UPDATED_TAGS);
    }


    @Test
    @Transactional
    public void getAllProductosByPuntuacionIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where puntuacion equals to DEFAULT_PUNTUACION
        defaultProductosShouldBeFound("puntuacion.equals=" + DEFAULT_PUNTUACION);

        // Get all the productosList where puntuacion equals to UPDATED_PUNTUACION
        defaultProductosShouldNotBeFound("puntuacion.equals=" + UPDATED_PUNTUACION);
    }

    @Test
    @Transactional
    public void getAllProductosByPuntuacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where puntuacion not equals to DEFAULT_PUNTUACION
        defaultProductosShouldNotBeFound("puntuacion.notEquals=" + DEFAULT_PUNTUACION);

        // Get all the productosList where puntuacion not equals to UPDATED_PUNTUACION
        defaultProductosShouldBeFound("puntuacion.notEquals=" + UPDATED_PUNTUACION);
    }

    @Test
    @Transactional
    public void getAllProductosByPuntuacionIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where puntuacion in DEFAULT_PUNTUACION or UPDATED_PUNTUACION
        defaultProductosShouldBeFound("puntuacion.in=" + DEFAULT_PUNTUACION + "," + UPDATED_PUNTUACION);

        // Get all the productosList where puntuacion equals to UPDATED_PUNTUACION
        defaultProductosShouldNotBeFound("puntuacion.in=" + UPDATED_PUNTUACION);
    }

    @Test
    @Transactional
    public void getAllProductosByPuntuacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where puntuacion is not null
        defaultProductosShouldBeFound("puntuacion.specified=true");

        // Get all the productosList where puntuacion is null
        defaultProductosShouldNotBeFound("puntuacion.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByPuntuacionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where puntuacion is greater than or equal to DEFAULT_PUNTUACION
        defaultProductosShouldBeFound("puntuacion.greaterThanOrEqual=" + DEFAULT_PUNTUACION);

        // Get all the productosList where puntuacion is greater than or equal to UPDATED_PUNTUACION
        defaultProductosShouldNotBeFound("puntuacion.greaterThanOrEqual=" + UPDATED_PUNTUACION);
    }

    @Test
    @Transactional
    public void getAllProductosByPuntuacionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where puntuacion is less than or equal to DEFAULT_PUNTUACION
        defaultProductosShouldBeFound("puntuacion.lessThanOrEqual=" + DEFAULT_PUNTUACION);

        // Get all the productosList where puntuacion is less than or equal to SMALLER_PUNTUACION
        defaultProductosShouldNotBeFound("puntuacion.lessThanOrEqual=" + SMALLER_PUNTUACION);
    }

    @Test
    @Transactional
    public void getAllProductosByPuntuacionIsLessThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where puntuacion is less than DEFAULT_PUNTUACION
        defaultProductosShouldNotBeFound("puntuacion.lessThan=" + DEFAULT_PUNTUACION);

        // Get all the productosList where puntuacion is less than UPDATED_PUNTUACION
        defaultProductosShouldBeFound("puntuacion.lessThan=" + UPDATED_PUNTUACION);
    }

    @Test
    @Transactional
    public void getAllProductosByPuntuacionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where puntuacion is greater than DEFAULT_PUNTUACION
        defaultProductosShouldNotBeFound("puntuacion.greaterThan=" + DEFAULT_PUNTUACION);

        // Get all the productosList where puntuacion is greater than SMALLER_PUNTUACION
        defaultProductosShouldBeFound("puntuacion.greaterThan=" + SMALLER_PUNTUACION);
    }


    @Test
    @Transactional
    public void getAllProductosByVistosIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where vistos equals to DEFAULT_VISTOS
        defaultProductosShouldBeFound("vistos.equals=" + DEFAULT_VISTOS);

        // Get all the productosList where vistos equals to UPDATED_VISTOS
        defaultProductosShouldNotBeFound("vistos.equals=" + UPDATED_VISTOS);
    }

    @Test
    @Transactional
    public void getAllProductosByVistosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where vistos not equals to DEFAULT_VISTOS
        defaultProductosShouldNotBeFound("vistos.notEquals=" + DEFAULT_VISTOS);

        // Get all the productosList where vistos not equals to UPDATED_VISTOS
        defaultProductosShouldBeFound("vistos.notEquals=" + UPDATED_VISTOS);
    }

    @Test
    @Transactional
    public void getAllProductosByVistosIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where vistos in DEFAULT_VISTOS or UPDATED_VISTOS
        defaultProductosShouldBeFound("vistos.in=" + DEFAULT_VISTOS + "," + UPDATED_VISTOS);

        // Get all the productosList where vistos equals to UPDATED_VISTOS
        defaultProductosShouldNotBeFound("vistos.in=" + UPDATED_VISTOS);
    }

    @Test
    @Transactional
    public void getAllProductosByVistosIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where vistos is not null
        defaultProductosShouldBeFound("vistos.specified=true");

        // Get all the productosList where vistos is null
        defaultProductosShouldNotBeFound("vistos.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByVistosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where vistos is greater than or equal to DEFAULT_VISTOS
        defaultProductosShouldBeFound("vistos.greaterThanOrEqual=" + DEFAULT_VISTOS);

        // Get all the productosList where vistos is greater than or equal to UPDATED_VISTOS
        defaultProductosShouldNotBeFound("vistos.greaterThanOrEqual=" + UPDATED_VISTOS);
    }

    @Test
    @Transactional
    public void getAllProductosByVistosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where vistos is less than or equal to DEFAULT_VISTOS
        defaultProductosShouldBeFound("vistos.lessThanOrEqual=" + DEFAULT_VISTOS);

        // Get all the productosList where vistos is less than or equal to SMALLER_VISTOS
        defaultProductosShouldNotBeFound("vistos.lessThanOrEqual=" + SMALLER_VISTOS);
    }

    @Test
    @Transactional
    public void getAllProductosByVistosIsLessThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where vistos is less than DEFAULT_VISTOS
        defaultProductosShouldNotBeFound("vistos.lessThan=" + DEFAULT_VISTOS);

        // Get all the productosList where vistos is less than UPDATED_VISTOS
        defaultProductosShouldBeFound("vistos.lessThan=" + UPDATED_VISTOS);
    }

    @Test
    @Transactional
    public void getAllProductosByVistosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where vistos is greater than DEFAULT_VISTOS
        defaultProductosShouldNotBeFound("vistos.greaterThan=" + DEFAULT_VISTOS);

        // Get all the productosList where vistos is greater than SMALLER_VISTOS
        defaultProductosShouldBeFound("vistos.greaterThan=" + SMALLER_VISTOS);
    }


    @Test
    @Transactional
    public void getAllProductosByOfertaIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where oferta equals to DEFAULT_OFERTA
        defaultProductosShouldBeFound("oferta.equals=" + DEFAULT_OFERTA);

        // Get all the productosList where oferta equals to UPDATED_OFERTA
        defaultProductosShouldNotBeFound("oferta.equals=" + UPDATED_OFERTA);
    }

    @Test
    @Transactional
    public void getAllProductosByOfertaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where oferta not equals to DEFAULT_OFERTA
        defaultProductosShouldNotBeFound("oferta.notEquals=" + DEFAULT_OFERTA);

        // Get all the productosList where oferta not equals to UPDATED_OFERTA
        defaultProductosShouldBeFound("oferta.notEquals=" + UPDATED_OFERTA);
    }

    @Test
    @Transactional
    public void getAllProductosByOfertaIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where oferta in DEFAULT_OFERTA or UPDATED_OFERTA
        defaultProductosShouldBeFound("oferta.in=" + DEFAULT_OFERTA + "," + UPDATED_OFERTA);

        // Get all the productosList where oferta equals to UPDATED_OFERTA
        defaultProductosShouldNotBeFound("oferta.in=" + UPDATED_OFERTA);
    }

    @Test
    @Transactional
    public void getAllProductosByOfertaIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where oferta is not null
        defaultProductosShouldBeFound("oferta.specified=true");

        // Get all the productosList where oferta is null
        defaultProductosShouldNotBeFound("oferta.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByOfertaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where oferta is greater than or equal to DEFAULT_OFERTA
        defaultProductosShouldBeFound("oferta.greaterThanOrEqual=" + DEFAULT_OFERTA);

        // Get all the productosList where oferta is greater than or equal to UPDATED_OFERTA
        defaultProductosShouldNotBeFound("oferta.greaterThanOrEqual=" + UPDATED_OFERTA);
    }

    @Test
    @Transactional
    public void getAllProductosByOfertaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where oferta is less than or equal to DEFAULT_OFERTA
        defaultProductosShouldBeFound("oferta.lessThanOrEqual=" + DEFAULT_OFERTA);

        // Get all the productosList where oferta is less than or equal to SMALLER_OFERTA
        defaultProductosShouldNotBeFound("oferta.lessThanOrEqual=" + SMALLER_OFERTA);
    }

    @Test
    @Transactional
    public void getAllProductosByOfertaIsLessThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where oferta is less than DEFAULT_OFERTA
        defaultProductosShouldNotBeFound("oferta.lessThan=" + DEFAULT_OFERTA);

        // Get all the productosList where oferta is less than UPDATED_OFERTA
        defaultProductosShouldBeFound("oferta.lessThan=" + UPDATED_OFERTA);
    }

    @Test
    @Transactional
    public void getAllProductosByOfertaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where oferta is greater than DEFAULT_OFERTA
        defaultProductosShouldNotBeFound("oferta.greaterThan=" + DEFAULT_OFERTA);

        // Get all the productosList where oferta is greater than SMALLER_OFERTA
        defaultProductosShouldBeFound("oferta.greaterThan=" + SMALLER_OFERTA);
    }


    @Test
    @Transactional
    public void getAllProductosByTiempoOfertaIsEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoOferta equals to DEFAULT_TIEMPO_OFERTA
        defaultProductosShouldBeFound("tiempoOferta.equals=" + DEFAULT_TIEMPO_OFERTA);

        // Get all the productosList where tiempoOferta equals to UPDATED_TIEMPO_OFERTA
        defaultProductosShouldNotBeFound("tiempoOferta.equals=" + UPDATED_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void getAllProductosByTiempoOfertaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoOferta not equals to DEFAULT_TIEMPO_OFERTA
        defaultProductosShouldNotBeFound("tiempoOferta.notEquals=" + DEFAULT_TIEMPO_OFERTA);

        // Get all the productosList where tiempoOferta not equals to UPDATED_TIEMPO_OFERTA
        defaultProductosShouldBeFound("tiempoOferta.notEquals=" + UPDATED_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void getAllProductosByTiempoOfertaIsInShouldWork() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoOferta in DEFAULT_TIEMPO_OFERTA or UPDATED_TIEMPO_OFERTA
        defaultProductosShouldBeFound("tiempoOferta.in=" + DEFAULT_TIEMPO_OFERTA + "," + UPDATED_TIEMPO_OFERTA);

        // Get all the productosList where tiempoOferta equals to UPDATED_TIEMPO_OFERTA
        defaultProductosShouldNotBeFound("tiempoOferta.in=" + UPDATED_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void getAllProductosByTiempoOfertaIsNullOrNotNull() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoOferta is not null
        defaultProductosShouldBeFound("tiempoOferta.specified=true");

        // Get all the productosList where tiempoOferta is null
        defaultProductosShouldNotBeFound("tiempoOferta.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByTiempoOfertaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoOferta is greater than or equal to DEFAULT_TIEMPO_OFERTA
        defaultProductosShouldBeFound("tiempoOferta.greaterThanOrEqual=" + DEFAULT_TIEMPO_OFERTA);

        // Get all the productosList where tiempoOferta is greater than or equal to UPDATED_TIEMPO_OFERTA
        defaultProductosShouldNotBeFound("tiempoOferta.greaterThanOrEqual=" + UPDATED_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void getAllProductosByTiempoOfertaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoOferta is less than or equal to DEFAULT_TIEMPO_OFERTA
        defaultProductosShouldBeFound("tiempoOferta.lessThanOrEqual=" + DEFAULT_TIEMPO_OFERTA);

        // Get all the productosList where tiempoOferta is less than or equal to SMALLER_TIEMPO_OFERTA
        defaultProductosShouldNotBeFound("tiempoOferta.lessThanOrEqual=" + SMALLER_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void getAllProductosByTiempoOfertaIsLessThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoOferta is less than DEFAULT_TIEMPO_OFERTA
        defaultProductosShouldNotBeFound("tiempoOferta.lessThan=" + DEFAULT_TIEMPO_OFERTA);

        // Get all the productosList where tiempoOferta is less than UPDATED_TIEMPO_OFERTA
        defaultProductosShouldBeFound("tiempoOferta.lessThan=" + UPDATED_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void getAllProductosByTiempoOfertaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productosRepository.saveAndFlush(productos);

        // Get all the productosList where tiempoOferta is greater than DEFAULT_TIEMPO_OFERTA
        defaultProductosShouldNotBeFound("tiempoOferta.greaterThan=" + DEFAULT_TIEMPO_OFERTA);

        // Get all the productosList where tiempoOferta is greater than SMALLER_TIEMPO_OFERTA
        defaultProductosShouldBeFound("tiempoOferta.greaterThan=" + SMALLER_TIEMPO_OFERTA);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductosShouldBeFound(String filter) throws Exception {
        restProductosMockMvc.perform(get("/api/productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].inventario").value(hasItem(DEFAULT_INVENTARIO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].unidad").value(hasItem(DEFAULT_UNIDAD)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].tiempoEntrega").value(hasItem(DEFAULT_TIEMPO_ENTREGA)))
            .andExpect(jsonPath("$.[*].dispinibilidad").value(hasItem(DEFAULT_DISPINIBILIDAD.booleanValue())))
            .andExpect(jsonPath("$.[*].nuevo").value(hasItem(DEFAULT_NUEVO.booleanValue())))
            .andExpect(jsonPath("$.[*].descuento").value(hasItem(DEFAULT_DESCUENTO)))
            .andExpect(jsonPath("$.[*].remate").value(hasItem(DEFAULT_REMATE.booleanValue())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)))
            .andExpect(jsonPath("$.[*].puntuacion").value(hasItem(DEFAULT_PUNTUACION)))
            .andExpect(jsonPath("$.[*].vistos").value(hasItem(DEFAULT_VISTOS)))
            .andExpect(jsonPath("$.[*].oferta").value(hasItem(DEFAULT_OFERTA)))
            .andExpect(jsonPath("$.[*].tiempoOferta").value(hasItem(DEFAULT_TIEMPO_OFERTA)));

        // Check, that the count call also returns 1
        restProductosMockMvc.perform(get("/api/productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductosShouldNotBeFound(String filter) throws Exception {
        restProductosMockMvc.perform(get("/api/productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductosMockMvc.perform(get("/api/productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductos() throws Exception {
        // Get the productos
        restProductosMockMvc.perform(get("/api/productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductos() throws Exception {
        // Initialize the database
        productosService.save(productos);

        int databaseSizeBeforeUpdate = productosRepository.findAll().size();

        // Update the productos
        Productos updatedProductos = productosRepository.findById(productos.getId()).get();
        // Disconnect from session so that the updates on updatedProductos are not directly saved in db
        em.detach(updatedProductos);
        updatedProductos
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .inventario(UPDATED_INVENTARIO)
            .tipo(UPDATED_TIPO)
            .impuesto(UPDATED_IMPUESTO)
            .valor(UPDATED_VALOR)
            .unidad(UPDATED_UNIDAD)
            .estado(UPDATED_ESTADO)
            .tiempoEntrega(UPDATED_TIEMPO_ENTREGA)
            .dispinibilidad(UPDATED_DISPINIBILIDAD)
            .nuevo(UPDATED_NUEVO)
            .descuento(UPDATED_DESCUENTO)
            .remate(UPDATED_REMATE)
            .tags(UPDATED_TAGS)
            .puntuacion(UPDATED_PUNTUACION)
            .vistos(UPDATED_VISTOS)
            .oferta(UPDATED_OFERTA)
            .tiempoOferta(UPDATED_TIEMPO_OFERTA);

        restProductosMockMvc.perform(put("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductos)))
            .andExpect(status().isOk());

        // Validate the Productos in the database
        List<Productos> productosList = productosRepository.findAll();
        assertThat(productosList).hasSize(databaseSizeBeforeUpdate);
        Productos testProductos = productosList.get(productosList.size() - 1);
        assertThat(testProductos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProductos.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProductos.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testProductos.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testProductos.getInventario()).isEqualTo(UPDATED_INVENTARIO);
        assertThat(testProductos.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testProductos.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);
        assertThat(testProductos.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testProductos.getUnidad()).isEqualTo(UPDATED_UNIDAD);
        assertThat(testProductos.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testProductos.getTiempoEntrega()).isEqualTo(UPDATED_TIEMPO_ENTREGA);
        assertThat(testProductos.isDispinibilidad()).isEqualTo(UPDATED_DISPINIBILIDAD);
        assertThat(testProductos.isNuevo()).isEqualTo(UPDATED_NUEVO);
        assertThat(testProductos.getDescuento()).isEqualTo(UPDATED_DESCUENTO);
        assertThat(testProductos.isRemate()).isEqualTo(UPDATED_REMATE);
        assertThat(testProductos.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testProductos.getPuntuacion()).isEqualTo(UPDATED_PUNTUACION);
        assertThat(testProductos.getVistos()).isEqualTo(UPDATED_VISTOS);
        assertThat(testProductos.getOferta()).isEqualTo(UPDATED_OFERTA);
        assertThat(testProductos.getTiempoOferta()).isEqualTo(UPDATED_TIEMPO_OFERTA);
    }

    @Test
    @Transactional
    public void updateNonExistingProductos() throws Exception {
        int databaseSizeBeforeUpdate = productosRepository.findAll().size();

        // Create the Productos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductosMockMvc.perform(put("/api/productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productos)))
            .andExpect(status().isBadRequest());

        // Validate the Productos in the database
        List<Productos> productosList = productosRepository.findAll();
        assertThat(productosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductos() throws Exception {
        // Initialize the database
        productosService.save(productos);

        int databaseSizeBeforeDelete = productosRepository.findAll().size();

        // Delete the productos
        restProductosMockMvc.perform(delete("/api/productos/{id}", productos.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Productos> productosList = productosRepository.findAll();
        assertThat(productosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
