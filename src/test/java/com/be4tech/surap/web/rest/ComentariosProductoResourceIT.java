package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.ComentariosProducto;
import com.be4tech.surap.domain.User;
import com.be4tech.surap.domain.Productos;
import com.be4tech.surap.repository.ComentariosProductoRepository;
import com.be4tech.surap.service.ComentariosProductoService;
import com.be4tech.surap.service.dto.ComentariosProductoCriteria;
import com.be4tech.surap.service.ComentariosProductoQueryService;

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
 * Integration tests for the {@link ComentariosProductoResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class ComentariosProductoResourceIT {

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    @Autowired
    private ComentariosProductoRepository comentariosProductoRepository;

    @Autowired
    private ComentariosProductoService comentariosProductoService;

    @Autowired
    private ComentariosProductoQueryService comentariosProductoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComentariosProductoMockMvc;

    private ComentariosProducto comentariosProducto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComentariosProducto createEntity(EntityManager em) {
        ComentariosProducto comentariosProducto = new ComentariosProducto()
            .comentario(DEFAULT_COMENTARIO)
            .fecha(DEFAULT_FECHA)
            .estado(DEFAULT_ESTADO);
        return comentariosProducto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComentariosProducto createUpdatedEntity(EntityManager em) {
        ComentariosProducto comentariosProducto = new ComentariosProducto()
            .comentario(UPDATED_COMENTARIO)
            .fecha(UPDATED_FECHA)
            .estado(UPDATED_ESTADO);
        return comentariosProducto;
    }

    @BeforeEach
    public void initTest() {
        comentariosProducto = createEntity(em);
    }

    @Test
    @Transactional
    public void createComentariosProducto() throws Exception {
        int databaseSizeBeforeCreate = comentariosProductoRepository.findAll().size();

        // Create the ComentariosProducto
        restComentariosProductoMockMvc.perform(post("/api/comentarios-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentariosProducto)))
            .andExpect(status().isCreated());

        // Validate the ComentariosProducto in the database
        List<ComentariosProducto> comentariosProductoList = comentariosProductoRepository.findAll();
        assertThat(comentariosProductoList).hasSize(databaseSizeBeforeCreate + 1);
        ComentariosProducto testComentariosProducto = comentariosProductoList.get(comentariosProductoList.size() - 1);
        assertThat(testComentariosProducto.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testComentariosProducto.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testComentariosProducto.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createComentariosProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comentariosProductoRepository.findAll().size();

        // Create the ComentariosProducto with an existing ID
        comentariosProducto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComentariosProductoMockMvc.perform(post("/api/comentarios-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentariosProducto)))
            .andExpect(status().isBadRequest());

        // Validate the ComentariosProducto in the database
        List<ComentariosProducto> comentariosProductoList = comentariosProductoRepository.findAll();
        assertThat(comentariosProductoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllComentariosProductos() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList
        restComentariosProductoMockMvc.perform(get("/api/comentarios-productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentariosProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));
    }
    
    @Test
    @Transactional
    public void getComentariosProducto() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get the comentariosProducto
        restComentariosProductoMockMvc.perform(get("/api/comentarios-productos/{id}", comentariosProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comentariosProducto.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO));
    }


    @Test
    @Transactional
    public void getComentariosProductosByIdFiltering() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        Long id = comentariosProducto.getId();

        defaultComentariosProductoShouldBeFound("id.equals=" + id);
        defaultComentariosProductoShouldNotBeFound("id.notEquals=" + id);

        defaultComentariosProductoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultComentariosProductoShouldNotBeFound("id.greaterThan=" + id);

        defaultComentariosProductoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultComentariosProductoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllComentariosProductosByComentarioIsEqualToSomething() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where comentario equals to DEFAULT_COMENTARIO
        defaultComentariosProductoShouldBeFound("comentario.equals=" + DEFAULT_COMENTARIO);

        // Get all the comentariosProductoList where comentario equals to UPDATED_COMENTARIO
        defaultComentariosProductoShouldNotBeFound("comentario.equals=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllComentariosProductosByComentarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where comentario not equals to DEFAULT_COMENTARIO
        defaultComentariosProductoShouldNotBeFound("comentario.notEquals=" + DEFAULT_COMENTARIO);

        // Get all the comentariosProductoList where comentario not equals to UPDATED_COMENTARIO
        defaultComentariosProductoShouldBeFound("comentario.notEquals=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllComentariosProductosByComentarioIsInShouldWork() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where comentario in DEFAULT_COMENTARIO or UPDATED_COMENTARIO
        defaultComentariosProductoShouldBeFound("comentario.in=" + DEFAULT_COMENTARIO + "," + UPDATED_COMENTARIO);

        // Get all the comentariosProductoList where comentario equals to UPDATED_COMENTARIO
        defaultComentariosProductoShouldNotBeFound("comentario.in=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllComentariosProductosByComentarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where comentario is not null
        defaultComentariosProductoShouldBeFound("comentario.specified=true");

        // Get all the comentariosProductoList where comentario is null
        defaultComentariosProductoShouldNotBeFound("comentario.specified=false");
    }
                @Test
    @Transactional
    public void getAllComentariosProductosByComentarioContainsSomething() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where comentario contains DEFAULT_COMENTARIO
        defaultComentariosProductoShouldBeFound("comentario.contains=" + DEFAULT_COMENTARIO);

        // Get all the comentariosProductoList where comentario contains UPDATED_COMENTARIO
        defaultComentariosProductoShouldNotBeFound("comentario.contains=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllComentariosProductosByComentarioNotContainsSomething() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where comentario does not contain DEFAULT_COMENTARIO
        defaultComentariosProductoShouldNotBeFound("comentario.doesNotContain=" + DEFAULT_COMENTARIO);

        // Get all the comentariosProductoList where comentario does not contain UPDATED_COMENTARIO
        defaultComentariosProductoShouldBeFound("comentario.doesNotContain=" + UPDATED_COMENTARIO);
    }


    @Test
    @Transactional
    public void getAllComentariosProductosByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where fecha equals to DEFAULT_FECHA
        defaultComentariosProductoShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the comentariosProductoList where fecha equals to UPDATED_FECHA
        defaultComentariosProductoShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentariosProductosByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where fecha not equals to DEFAULT_FECHA
        defaultComentariosProductoShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the comentariosProductoList where fecha not equals to UPDATED_FECHA
        defaultComentariosProductoShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentariosProductosByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultComentariosProductoShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the comentariosProductoList where fecha equals to UPDATED_FECHA
        defaultComentariosProductoShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllComentariosProductosByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where fecha is not null
        defaultComentariosProductoShouldBeFound("fecha.specified=true");

        // Get all the comentariosProductoList where fecha is null
        defaultComentariosProductoShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllComentariosProductosByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where estado equals to DEFAULT_ESTADO
        defaultComentariosProductoShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the comentariosProductoList where estado equals to UPDATED_ESTADO
        defaultComentariosProductoShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllComentariosProductosByEstadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where estado not equals to DEFAULT_ESTADO
        defaultComentariosProductoShouldNotBeFound("estado.notEquals=" + DEFAULT_ESTADO);

        // Get all the comentariosProductoList where estado not equals to UPDATED_ESTADO
        defaultComentariosProductoShouldBeFound("estado.notEquals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllComentariosProductosByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultComentariosProductoShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the comentariosProductoList where estado equals to UPDATED_ESTADO
        defaultComentariosProductoShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllComentariosProductosByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where estado is not null
        defaultComentariosProductoShouldBeFound("estado.specified=true");

        // Get all the comentariosProductoList where estado is null
        defaultComentariosProductoShouldNotBeFound("estado.specified=false");
    }
                @Test
    @Transactional
    public void getAllComentariosProductosByEstadoContainsSomething() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where estado contains DEFAULT_ESTADO
        defaultComentariosProductoShouldBeFound("estado.contains=" + DEFAULT_ESTADO);

        // Get all the comentariosProductoList where estado contains UPDATED_ESTADO
        defaultComentariosProductoShouldNotBeFound("estado.contains=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllComentariosProductosByEstadoNotContainsSomething() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);

        // Get all the comentariosProductoList where estado does not contain DEFAULT_ESTADO
        defaultComentariosProductoShouldNotBeFound("estado.doesNotContain=" + DEFAULT_ESTADO);

        // Get all the comentariosProductoList where estado does not contain UPDATED_ESTADO
        defaultComentariosProductoShouldBeFound("estado.doesNotContain=" + UPDATED_ESTADO);
    }


    @Test
    @Transactional
    public void getAllComentariosProductosByIdUserIsEqualToSomething() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);
        User idUser = UserResourceIT.createEntity(em);
        em.persist(idUser);
        em.flush();
        comentariosProducto.setIdUser(idUser);
        comentariosProductoRepository.saveAndFlush(comentariosProducto);
        Long idUserId = idUser.getId();

        // Get all the comentariosProductoList where idUser equals to idUserId
        defaultComentariosProductoShouldBeFound("idUserId.equals=" + idUserId);

        // Get all the comentariosProductoList where idUser equals to idUserId + 1
        defaultComentariosProductoShouldNotBeFound("idUserId.equals=" + (idUserId + 1));
    }


    @Test
    @Transactional
    public void getAllComentariosProductosByProductoIdIsEqualToSomething() throws Exception {
        // Initialize the database
        comentariosProductoRepository.saveAndFlush(comentariosProducto);
        Productos productoId = ProductosResourceIT.createEntity(em);
        em.persist(productoId);
        em.flush();
        comentariosProducto.setProductoId(productoId);
        comentariosProductoRepository.saveAndFlush(comentariosProducto);
        Long productoIdId = productoId.getId();

        // Get all the comentariosProductoList where productoId equals to productoIdId
        defaultComentariosProductoShouldBeFound("productoIdId.equals=" + productoIdId);

        // Get all the comentariosProductoList where productoId equals to productoIdId + 1
        defaultComentariosProductoShouldNotBeFound("productoIdId.equals=" + (productoIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComentariosProductoShouldBeFound(String filter) throws Exception {
        restComentariosProductoMockMvc.perform(get("/api/comentarios-productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentariosProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));

        // Check, that the count call also returns 1
        restComentariosProductoMockMvc.perform(get("/api/comentarios-productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComentariosProductoShouldNotBeFound(String filter) throws Exception {
        restComentariosProductoMockMvc.perform(get("/api/comentarios-productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComentariosProductoMockMvc.perform(get("/api/comentarios-productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingComentariosProducto() throws Exception {
        // Get the comentariosProducto
        restComentariosProductoMockMvc.perform(get("/api/comentarios-productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentariosProducto() throws Exception {
        // Initialize the database
        comentariosProductoService.save(comentariosProducto);

        int databaseSizeBeforeUpdate = comentariosProductoRepository.findAll().size();

        // Update the comentariosProducto
        ComentariosProducto updatedComentariosProducto = comentariosProductoRepository.findById(comentariosProducto.getId()).get();
        // Disconnect from session so that the updates on updatedComentariosProducto are not directly saved in db
        em.detach(updatedComentariosProducto);
        updatedComentariosProducto
            .comentario(UPDATED_COMENTARIO)
            .fecha(UPDATED_FECHA)
            .estado(UPDATED_ESTADO);

        restComentariosProductoMockMvc.perform(put("/api/comentarios-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedComentariosProducto)))
            .andExpect(status().isOk());

        // Validate the ComentariosProducto in the database
        List<ComentariosProducto> comentariosProductoList = comentariosProductoRepository.findAll();
        assertThat(comentariosProductoList).hasSize(databaseSizeBeforeUpdate);
        ComentariosProducto testComentariosProducto = comentariosProductoList.get(comentariosProductoList.size() - 1);
        assertThat(testComentariosProducto.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testComentariosProducto.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testComentariosProducto.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingComentariosProducto() throws Exception {
        int databaseSizeBeforeUpdate = comentariosProductoRepository.findAll().size();

        // Create the ComentariosProducto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComentariosProductoMockMvc.perform(put("/api/comentarios-productos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comentariosProducto)))
            .andExpect(status().isBadRequest());

        // Validate the ComentariosProducto in the database
        List<ComentariosProducto> comentariosProductoList = comentariosProductoRepository.findAll();
        assertThat(comentariosProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComentariosProducto() throws Exception {
        // Initialize the database
        comentariosProductoService.save(comentariosProducto);

        int databaseSizeBeforeDelete = comentariosProductoRepository.findAll().size();

        // Delete the comentariosProducto
        restComentariosProductoMockMvc.perform(delete("/api/comentarios-productos/{id}", comentariosProducto.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComentariosProducto> comentariosProductoList = comentariosProductoRepository.findAll();
        assertThat(comentariosProductoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
