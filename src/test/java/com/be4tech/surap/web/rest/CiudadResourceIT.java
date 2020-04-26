package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.Ciudad;
import com.be4tech.surap.domain.Pais;
import com.be4tech.surap.repository.CiudadRepository;
import com.be4tech.surap.service.CiudadService;
import com.be4tech.surap.service.dto.CiudadCriteria;
import com.be4tech.surap.service.CiudadQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CiudadResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class CiudadResourceIT {

    private static final String DEFAULT_NOMBRE_CIUDAD = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CIUDAD = "BBBBBBBBBB";

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private CiudadQueryService ciudadQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCiudadMockMvc;

    private Ciudad ciudad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ciudad createEntity(EntityManager em) {
        Ciudad ciudad = new Ciudad()
            .nombreCiudad(DEFAULT_NOMBRE_CIUDAD);
        return ciudad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ciudad createUpdatedEntity(EntityManager em) {
        Ciudad ciudad = new Ciudad()
            .nombreCiudad(UPDATED_NOMBRE_CIUDAD);
        return ciudad;
    }

    @BeforeEach
    public void initTest() {
        ciudad = createEntity(em);
    }

    @Test
    @Transactional
    public void createCiudad() throws Exception {
        int databaseSizeBeforeCreate = ciudadRepository.findAll().size();

        // Create the Ciudad
        restCiudadMockMvc.perform(post("/api/ciudads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ciudad)))
            .andExpect(status().isCreated());

        // Validate the Ciudad in the database
        List<Ciudad> ciudadList = ciudadRepository.findAll();
        assertThat(ciudadList).hasSize(databaseSizeBeforeCreate + 1);
        Ciudad testCiudad = ciudadList.get(ciudadList.size() - 1);
        assertThat(testCiudad.getNombreCiudad()).isEqualTo(DEFAULT_NOMBRE_CIUDAD);
    }

    @Test
    @Transactional
    public void createCiudadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ciudadRepository.findAll().size();

        // Create the Ciudad with an existing ID
        ciudad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCiudadMockMvc.perform(post("/api/ciudads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ciudad)))
            .andExpect(status().isBadRequest());

        // Validate the Ciudad in the database
        List<Ciudad> ciudadList = ciudadRepository.findAll();
        assertThat(ciudadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreCiudadIsRequired() throws Exception {
        int databaseSizeBeforeTest = ciudadRepository.findAll().size();
        // set the field null
        ciudad.setNombreCiudad(null);

        // Create the Ciudad, which fails.

        restCiudadMockMvc.perform(post("/api/ciudads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ciudad)))
            .andExpect(status().isBadRequest());

        List<Ciudad> ciudadList = ciudadRepository.findAll();
        assertThat(ciudadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCiudads() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);

        // Get all the ciudadList
        restCiudadMockMvc.perform(get("/api/ciudads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciudad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreCiudad").value(hasItem(DEFAULT_NOMBRE_CIUDAD)));
    }
    
    @Test
    @Transactional
    public void getCiudad() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);

        // Get the ciudad
        restCiudadMockMvc.perform(get("/api/ciudads/{id}", ciudad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ciudad.getId().intValue()))
            .andExpect(jsonPath("$.nombreCiudad").value(DEFAULT_NOMBRE_CIUDAD));
    }


    @Test
    @Transactional
    public void getCiudadsByIdFiltering() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);

        Long id = ciudad.getId();

        defaultCiudadShouldBeFound("id.equals=" + id);
        defaultCiudadShouldNotBeFound("id.notEquals=" + id);

        defaultCiudadShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCiudadShouldNotBeFound("id.greaterThan=" + id);

        defaultCiudadShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCiudadShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCiudadsByNombreCiudadIsEqualToSomething() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);

        // Get all the ciudadList where nombreCiudad equals to DEFAULT_NOMBRE_CIUDAD
        defaultCiudadShouldBeFound("nombreCiudad.equals=" + DEFAULT_NOMBRE_CIUDAD);

        // Get all the ciudadList where nombreCiudad equals to UPDATED_NOMBRE_CIUDAD
        defaultCiudadShouldNotBeFound("nombreCiudad.equals=" + UPDATED_NOMBRE_CIUDAD);
    }

    @Test
    @Transactional
    public void getAllCiudadsByNombreCiudadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);

        // Get all the ciudadList where nombreCiudad not equals to DEFAULT_NOMBRE_CIUDAD
        defaultCiudadShouldNotBeFound("nombreCiudad.notEquals=" + DEFAULT_NOMBRE_CIUDAD);

        // Get all the ciudadList where nombreCiudad not equals to UPDATED_NOMBRE_CIUDAD
        defaultCiudadShouldBeFound("nombreCiudad.notEquals=" + UPDATED_NOMBRE_CIUDAD);
    }

    @Test
    @Transactional
    public void getAllCiudadsByNombreCiudadIsInShouldWork() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);

        // Get all the ciudadList where nombreCiudad in DEFAULT_NOMBRE_CIUDAD or UPDATED_NOMBRE_CIUDAD
        defaultCiudadShouldBeFound("nombreCiudad.in=" + DEFAULT_NOMBRE_CIUDAD + "," + UPDATED_NOMBRE_CIUDAD);

        // Get all the ciudadList where nombreCiudad equals to UPDATED_NOMBRE_CIUDAD
        defaultCiudadShouldNotBeFound("nombreCiudad.in=" + UPDATED_NOMBRE_CIUDAD);
    }

    @Test
    @Transactional
    public void getAllCiudadsByNombreCiudadIsNullOrNotNull() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);

        // Get all the ciudadList where nombreCiudad is not null
        defaultCiudadShouldBeFound("nombreCiudad.specified=true");

        // Get all the ciudadList where nombreCiudad is null
        defaultCiudadShouldNotBeFound("nombreCiudad.specified=false");
    }
                @Test
    @Transactional
    public void getAllCiudadsByNombreCiudadContainsSomething() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);

        // Get all the ciudadList where nombreCiudad contains DEFAULT_NOMBRE_CIUDAD
        defaultCiudadShouldBeFound("nombreCiudad.contains=" + DEFAULT_NOMBRE_CIUDAD);

        // Get all the ciudadList where nombreCiudad contains UPDATED_NOMBRE_CIUDAD
        defaultCiudadShouldNotBeFound("nombreCiudad.contains=" + UPDATED_NOMBRE_CIUDAD);
    }

    @Test
    @Transactional
    public void getAllCiudadsByNombreCiudadNotContainsSomething() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);

        // Get all the ciudadList where nombreCiudad does not contain DEFAULT_NOMBRE_CIUDAD
        defaultCiudadShouldNotBeFound("nombreCiudad.doesNotContain=" + DEFAULT_NOMBRE_CIUDAD);

        // Get all the ciudadList where nombreCiudad does not contain UPDATED_NOMBRE_CIUDAD
        defaultCiudadShouldBeFound("nombreCiudad.doesNotContain=" + UPDATED_NOMBRE_CIUDAD);
    }


    @Test
    @Transactional
    public void getAllCiudadsByPaisIsEqualToSomething() throws Exception {
        // Initialize the database
        ciudadRepository.saveAndFlush(ciudad);
        Pais pais = PaisResourceIT.createEntity(em);
        em.persist(pais);
        em.flush();
        ciudad.setPais(pais);
        ciudadRepository.saveAndFlush(ciudad);
        Long paisId = pais.getId();

        // Get all the ciudadList where pais equals to paisId
        defaultCiudadShouldBeFound("paisId.equals=" + paisId);

        // Get all the ciudadList where pais equals to paisId + 1
        defaultCiudadShouldNotBeFound("paisId.equals=" + (paisId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCiudadShouldBeFound(String filter) throws Exception {
        restCiudadMockMvc.perform(get("/api/ciudads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ciudad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreCiudad").value(hasItem(DEFAULT_NOMBRE_CIUDAD)));

        // Check, that the count call also returns 1
        restCiudadMockMvc.perform(get("/api/ciudads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCiudadShouldNotBeFound(String filter) throws Exception {
        restCiudadMockMvc.perform(get("/api/ciudads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCiudadMockMvc.perform(get("/api/ciudads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCiudad() throws Exception {
        // Get the ciudad
        restCiudadMockMvc.perform(get("/api/ciudads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCiudad() throws Exception {
        // Initialize the database
        ciudadService.save(ciudad);

        int databaseSizeBeforeUpdate = ciudadRepository.findAll().size();

        // Update the ciudad
        Ciudad updatedCiudad = ciudadRepository.findById(ciudad.getId()).get();
        // Disconnect from session so that the updates on updatedCiudad are not directly saved in db
        em.detach(updatedCiudad);
        updatedCiudad
            .nombreCiudad(UPDATED_NOMBRE_CIUDAD);

        restCiudadMockMvc.perform(put("/api/ciudads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCiudad)))
            .andExpect(status().isOk());

        // Validate the Ciudad in the database
        List<Ciudad> ciudadList = ciudadRepository.findAll();
        assertThat(ciudadList).hasSize(databaseSizeBeforeUpdate);
        Ciudad testCiudad = ciudadList.get(ciudadList.size() - 1);
        assertThat(testCiudad.getNombreCiudad()).isEqualTo(UPDATED_NOMBRE_CIUDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingCiudad() throws Exception {
        int databaseSizeBeforeUpdate = ciudadRepository.findAll().size();

        // Create the Ciudad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCiudadMockMvc.perform(put("/api/ciudads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ciudad)))
            .andExpect(status().isBadRequest());

        // Validate the Ciudad in the database
        List<Ciudad> ciudadList = ciudadRepository.findAll();
        assertThat(ciudadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCiudad() throws Exception {
        // Initialize the database
        ciudadService.save(ciudad);

        int databaseSizeBeforeDelete = ciudadRepository.findAll().size();

        // Delete the ciudad
        restCiudadMockMvc.perform(delete("/api/ciudads/{id}", ciudad.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ciudad> ciudadList = ciudadRepository.findAll();
        assertThat(ciudadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
