package com.be4tech.surap.web.rest;

import com.be4tech.surap.Surapp1App;
import com.be4tech.surap.domain.IPS;
import com.be4tech.surap.repository.IPSRepository;
import com.be4tech.surap.service.IPSService;
import com.be4tech.surap.service.dto.IPSCriteria;
import com.be4tech.surap.service.IPSQueryService;

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
 * Integration tests for the {@link IPSResource} REST controller.
 */
@SpringBootTest(classes = Surapp1App.class)

@AutoConfigureMockMvc
@WithMockUser
public class IPSResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_NIT = "AAAAAAAAAA";
    private static final String UPDATED_NIT = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO_ELECTRONICO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO_ELECTRONICO = "BBBBBBBBBB";

    @Autowired
    private IPSRepository iPSRepository;

    @Autowired
    private IPSService iPSService;

    @Autowired
    private IPSQueryService iPSQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIPSMockMvc;

    private IPS iPS;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IPS createEntity(EntityManager em) {
        IPS iPS = new IPS()
            .nombre(DEFAULT_NOMBRE)
            .nit(DEFAULT_NIT)
            .direccion(DEFAULT_DIRECCION)
            .telefono(DEFAULT_TELEFONO)
            .correoElectronico(DEFAULT_CORREO_ELECTRONICO);
        return iPS;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IPS createUpdatedEntity(EntityManager em) {
        IPS iPS = new IPS()
            .nombre(UPDATED_NOMBRE)
            .nit(UPDATED_NIT)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .correoElectronico(UPDATED_CORREO_ELECTRONICO);
        return iPS;
    }

    @BeforeEach
    public void initTest() {
        iPS = createEntity(em);
    }

    @Test
    @Transactional
    public void createIPS() throws Exception {
        int databaseSizeBeforeCreate = iPSRepository.findAll().size();

        // Create the IPS
        restIPSMockMvc.perform(post("/api/ips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(iPS)))
            .andExpect(status().isCreated());

        // Validate the IPS in the database
        List<IPS> iPSList = iPSRepository.findAll();
        assertThat(iPSList).hasSize(databaseSizeBeforeCreate + 1);
        IPS testIPS = iPSList.get(iPSList.size() - 1);
        assertThat(testIPS.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testIPS.getNit()).isEqualTo(DEFAULT_NIT);
        assertThat(testIPS.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testIPS.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testIPS.getCorreoElectronico()).isEqualTo(DEFAULT_CORREO_ELECTRONICO);
    }

    @Test
    @Transactional
    public void createIPSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = iPSRepository.findAll().size();

        // Create the IPS with an existing ID
        iPS.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIPSMockMvc.perform(post("/api/ips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(iPS)))
            .andExpect(status().isBadRequest());

        // Validate the IPS in the database
        List<IPS> iPSList = iPSRepository.findAll();
        assertThat(iPSList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIPS() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList
        restIPSMockMvc.perform(get("/api/ips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iPS.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].nit").value(hasItem(DEFAULT_NIT)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correoElectronico").value(hasItem(DEFAULT_CORREO_ELECTRONICO)));
    }
    
    @Test
    @Transactional
    public void getIPS() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get the iPS
        restIPSMockMvc.perform(get("/api/ips/{id}", iPS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(iPS.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.nit").value(DEFAULT_NIT))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.correoElectronico").value(DEFAULT_CORREO_ELECTRONICO));
    }


    @Test
    @Transactional
    public void getIPSByIdFiltering() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        Long id = iPS.getId();

        defaultIPSShouldBeFound("id.equals=" + id);
        defaultIPSShouldNotBeFound("id.notEquals=" + id);

        defaultIPSShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIPSShouldNotBeFound("id.greaterThan=" + id);

        defaultIPSShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIPSShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllIPSByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where nombre equals to DEFAULT_NOMBRE
        defaultIPSShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the iPSList where nombre equals to UPDATED_NOMBRE
        defaultIPSShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllIPSByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where nombre not equals to DEFAULT_NOMBRE
        defaultIPSShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the iPSList where nombre not equals to UPDATED_NOMBRE
        defaultIPSShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllIPSByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultIPSShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the iPSList where nombre equals to UPDATED_NOMBRE
        defaultIPSShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllIPSByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where nombre is not null
        defaultIPSShouldBeFound("nombre.specified=true");

        // Get all the iPSList where nombre is null
        defaultIPSShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllIPSByNombreContainsSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where nombre contains DEFAULT_NOMBRE
        defaultIPSShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the iPSList where nombre contains UPDATED_NOMBRE
        defaultIPSShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllIPSByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where nombre does not contain DEFAULT_NOMBRE
        defaultIPSShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the iPSList where nombre does not contain UPDATED_NOMBRE
        defaultIPSShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllIPSByNitIsEqualToSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where nit equals to DEFAULT_NIT
        defaultIPSShouldBeFound("nit.equals=" + DEFAULT_NIT);

        // Get all the iPSList where nit equals to UPDATED_NIT
        defaultIPSShouldNotBeFound("nit.equals=" + UPDATED_NIT);
    }

    @Test
    @Transactional
    public void getAllIPSByNitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where nit not equals to DEFAULT_NIT
        defaultIPSShouldNotBeFound("nit.notEquals=" + DEFAULT_NIT);

        // Get all the iPSList where nit not equals to UPDATED_NIT
        defaultIPSShouldBeFound("nit.notEquals=" + UPDATED_NIT);
    }

    @Test
    @Transactional
    public void getAllIPSByNitIsInShouldWork() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where nit in DEFAULT_NIT or UPDATED_NIT
        defaultIPSShouldBeFound("nit.in=" + DEFAULT_NIT + "," + UPDATED_NIT);

        // Get all the iPSList where nit equals to UPDATED_NIT
        defaultIPSShouldNotBeFound("nit.in=" + UPDATED_NIT);
    }

    @Test
    @Transactional
    public void getAllIPSByNitIsNullOrNotNull() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where nit is not null
        defaultIPSShouldBeFound("nit.specified=true");

        // Get all the iPSList where nit is null
        defaultIPSShouldNotBeFound("nit.specified=false");
    }
                @Test
    @Transactional
    public void getAllIPSByNitContainsSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where nit contains DEFAULT_NIT
        defaultIPSShouldBeFound("nit.contains=" + DEFAULT_NIT);

        // Get all the iPSList where nit contains UPDATED_NIT
        defaultIPSShouldNotBeFound("nit.contains=" + UPDATED_NIT);
    }

    @Test
    @Transactional
    public void getAllIPSByNitNotContainsSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where nit does not contain DEFAULT_NIT
        defaultIPSShouldNotBeFound("nit.doesNotContain=" + DEFAULT_NIT);

        // Get all the iPSList where nit does not contain UPDATED_NIT
        defaultIPSShouldBeFound("nit.doesNotContain=" + UPDATED_NIT);
    }


    @Test
    @Transactional
    public void getAllIPSByDireccionIsEqualToSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where direccion equals to DEFAULT_DIRECCION
        defaultIPSShouldBeFound("direccion.equals=" + DEFAULT_DIRECCION);

        // Get all the iPSList where direccion equals to UPDATED_DIRECCION
        defaultIPSShouldNotBeFound("direccion.equals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllIPSByDireccionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where direccion not equals to DEFAULT_DIRECCION
        defaultIPSShouldNotBeFound("direccion.notEquals=" + DEFAULT_DIRECCION);

        // Get all the iPSList where direccion not equals to UPDATED_DIRECCION
        defaultIPSShouldBeFound("direccion.notEquals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllIPSByDireccionIsInShouldWork() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where direccion in DEFAULT_DIRECCION or UPDATED_DIRECCION
        defaultIPSShouldBeFound("direccion.in=" + DEFAULT_DIRECCION + "," + UPDATED_DIRECCION);

        // Get all the iPSList where direccion equals to UPDATED_DIRECCION
        defaultIPSShouldNotBeFound("direccion.in=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllIPSByDireccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where direccion is not null
        defaultIPSShouldBeFound("direccion.specified=true");

        // Get all the iPSList where direccion is null
        defaultIPSShouldNotBeFound("direccion.specified=false");
    }
                @Test
    @Transactional
    public void getAllIPSByDireccionContainsSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where direccion contains DEFAULT_DIRECCION
        defaultIPSShouldBeFound("direccion.contains=" + DEFAULT_DIRECCION);

        // Get all the iPSList where direccion contains UPDATED_DIRECCION
        defaultIPSShouldNotBeFound("direccion.contains=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllIPSByDireccionNotContainsSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where direccion does not contain DEFAULT_DIRECCION
        defaultIPSShouldNotBeFound("direccion.doesNotContain=" + DEFAULT_DIRECCION);

        // Get all the iPSList where direccion does not contain UPDATED_DIRECCION
        defaultIPSShouldBeFound("direccion.doesNotContain=" + UPDATED_DIRECCION);
    }


    @Test
    @Transactional
    public void getAllIPSByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where telefono equals to DEFAULT_TELEFONO
        defaultIPSShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the iPSList where telefono equals to UPDATED_TELEFONO
        defaultIPSShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllIPSByTelefonoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where telefono not equals to DEFAULT_TELEFONO
        defaultIPSShouldNotBeFound("telefono.notEquals=" + DEFAULT_TELEFONO);

        // Get all the iPSList where telefono not equals to UPDATED_TELEFONO
        defaultIPSShouldBeFound("telefono.notEquals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllIPSByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultIPSShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the iPSList where telefono equals to UPDATED_TELEFONO
        defaultIPSShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllIPSByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where telefono is not null
        defaultIPSShouldBeFound("telefono.specified=true");

        // Get all the iPSList where telefono is null
        defaultIPSShouldNotBeFound("telefono.specified=false");
    }
                @Test
    @Transactional
    public void getAllIPSByTelefonoContainsSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where telefono contains DEFAULT_TELEFONO
        defaultIPSShouldBeFound("telefono.contains=" + DEFAULT_TELEFONO);

        // Get all the iPSList where telefono contains UPDATED_TELEFONO
        defaultIPSShouldNotBeFound("telefono.contains=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllIPSByTelefonoNotContainsSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where telefono does not contain DEFAULT_TELEFONO
        defaultIPSShouldNotBeFound("telefono.doesNotContain=" + DEFAULT_TELEFONO);

        // Get all the iPSList where telefono does not contain UPDATED_TELEFONO
        defaultIPSShouldBeFound("telefono.doesNotContain=" + UPDATED_TELEFONO);
    }


    @Test
    @Transactional
    public void getAllIPSByCorreoElectronicoIsEqualToSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where correoElectronico equals to DEFAULT_CORREO_ELECTRONICO
        defaultIPSShouldBeFound("correoElectronico.equals=" + DEFAULT_CORREO_ELECTRONICO);

        // Get all the iPSList where correoElectronico equals to UPDATED_CORREO_ELECTRONICO
        defaultIPSShouldNotBeFound("correoElectronico.equals=" + UPDATED_CORREO_ELECTRONICO);
    }

    @Test
    @Transactional
    public void getAllIPSByCorreoElectronicoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where correoElectronico not equals to DEFAULT_CORREO_ELECTRONICO
        defaultIPSShouldNotBeFound("correoElectronico.notEquals=" + DEFAULT_CORREO_ELECTRONICO);

        // Get all the iPSList where correoElectronico not equals to UPDATED_CORREO_ELECTRONICO
        defaultIPSShouldBeFound("correoElectronico.notEquals=" + UPDATED_CORREO_ELECTRONICO);
    }

    @Test
    @Transactional
    public void getAllIPSByCorreoElectronicoIsInShouldWork() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where correoElectronico in DEFAULT_CORREO_ELECTRONICO or UPDATED_CORREO_ELECTRONICO
        defaultIPSShouldBeFound("correoElectronico.in=" + DEFAULT_CORREO_ELECTRONICO + "," + UPDATED_CORREO_ELECTRONICO);

        // Get all the iPSList where correoElectronico equals to UPDATED_CORREO_ELECTRONICO
        defaultIPSShouldNotBeFound("correoElectronico.in=" + UPDATED_CORREO_ELECTRONICO);
    }

    @Test
    @Transactional
    public void getAllIPSByCorreoElectronicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where correoElectronico is not null
        defaultIPSShouldBeFound("correoElectronico.specified=true");

        // Get all the iPSList where correoElectronico is null
        defaultIPSShouldNotBeFound("correoElectronico.specified=false");
    }
                @Test
    @Transactional
    public void getAllIPSByCorreoElectronicoContainsSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where correoElectronico contains DEFAULT_CORREO_ELECTRONICO
        defaultIPSShouldBeFound("correoElectronico.contains=" + DEFAULT_CORREO_ELECTRONICO);

        // Get all the iPSList where correoElectronico contains UPDATED_CORREO_ELECTRONICO
        defaultIPSShouldNotBeFound("correoElectronico.contains=" + UPDATED_CORREO_ELECTRONICO);
    }

    @Test
    @Transactional
    public void getAllIPSByCorreoElectronicoNotContainsSomething() throws Exception {
        // Initialize the database
        iPSRepository.saveAndFlush(iPS);

        // Get all the iPSList where correoElectronico does not contain DEFAULT_CORREO_ELECTRONICO
        defaultIPSShouldNotBeFound("correoElectronico.doesNotContain=" + DEFAULT_CORREO_ELECTRONICO);

        // Get all the iPSList where correoElectronico does not contain UPDATED_CORREO_ELECTRONICO
        defaultIPSShouldBeFound("correoElectronico.doesNotContain=" + UPDATED_CORREO_ELECTRONICO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIPSShouldBeFound(String filter) throws Exception {
        restIPSMockMvc.perform(get("/api/ips?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iPS.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].nit").value(hasItem(DEFAULT_NIT)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correoElectronico").value(hasItem(DEFAULT_CORREO_ELECTRONICO)));

        // Check, that the count call also returns 1
        restIPSMockMvc.perform(get("/api/ips/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIPSShouldNotBeFound(String filter) throws Exception {
        restIPSMockMvc.perform(get("/api/ips?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIPSMockMvc.perform(get("/api/ips/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingIPS() throws Exception {
        // Get the iPS
        restIPSMockMvc.perform(get("/api/ips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIPS() throws Exception {
        // Initialize the database
        iPSService.save(iPS);

        int databaseSizeBeforeUpdate = iPSRepository.findAll().size();

        // Update the iPS
        IPS updatedIPS = iPSRepository.findById(iPS.getId()).get();
        // Disconnect from session so that the updates on updatedIPS are not directly saved in db
        em.detach(updatedIPS);
        updatedIPS
            .nombre(UPDATED_NOMBRE)
            .nit(UPDATED_NIT)
            .direccion(UPDATED_DIRECCION)
            .telefono(UPDATED_TELEFONO)
            .correoElectronico(UPDATED_CORREO_ELECTRONICO);

        restIPSMockMvc.perform(put("/api/ips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIPS)))
            .andExpect(status().isOk());

        // Validate the IPS in the database
        List<IPS> iPSList = iPSRepository.findAll();
        assertThat(iPSList).hasSize(databaseSizeBeforeUpdate);
        IPS testIPS = iPSList.get(iPSList.size() - 1);
        assertThat(testIPS.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testIPS.getNit()).isEqualTo(UPDATED_NIT);
        assertThat(testIPS.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testIPS.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testIPS.getCorreoElectronico()).isEqualTo(UPDATED_CORREO_ELECTRONICO);
    }

    @Test
    @Transactional
    public void updateNonExistingIPS() throws Exception {
        int databaseSizeBeforeUpdate = iPSRepository.findAll().size();

        // Create the IPS

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIPSMockMvc.perform(put("/api/ips")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(iPS)))
            .andExpect(status().isBadRequest());

        // Validate the IPS in the database
        List<IPS> iPSList = iPSRepository.findAll();
        assertThat(iPSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIPS() throws Exception {
        // Initialize the database
        iPSService.save(iPS);

        int databaseSizeBeforeDelete = iPSRepository.findAll().size();

        // Delete the iPS
        restIPSMockMvc.perform(delete("/api/ips/{id}", iPS.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IPS> iPSList = iPSRepository.findAll();
        assertThat(iPSList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
