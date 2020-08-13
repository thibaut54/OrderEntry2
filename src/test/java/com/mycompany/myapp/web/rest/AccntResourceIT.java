package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OrderEntry2App;
import com.mycompany.myapp.domain.Accnt;
import com.mycompany.myapp.repository.AccntRepository;
import com.mycompany.myapp.service.AccntService;
import com.mycompany.myapp.service.dto.AccntDTO;
import com.mycompany.myapp.service.mapper.AccntMapper;

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
 * Integration tests for the {@link AccntResource} REST controller.
 */
@SpringBootTest(classes = OrderEntry2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class AccntResourceIT {

    private static final String DEFAULT_ACCT_CD = "AAAAAAAAAA";
    private static final String UPDATED_ACCT_CD = "BBBBBBBBBB";

    private static final String DEFAULT_ACCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCT_NAME = "BBBBBBBBBB";

    @Autowired
    private AccntRepository accntRepository;

    @Autowired
    private AccntMapper accntMapper;

    @Autowired
    private AccntService accntService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccntMockMvc;

    private Accnt accnt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accnt createEntity(EntityManager em) {
        Accnt accnt = new Accnt()
            .acctCd(DEFAULT_ACCT_CD)
            .acctName(DEFAULT_ACCT_NAME);
        return accnt;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accnt createUpdatedEntity(EntityManager em) {
        Accnt accnt = new Accnt()
            .acctCd(UPDATED_ACCT_CD)
            .acctName(UPDATED_ACCT_NAME);
        return accnt;
    }

    @BeforeEach
    public void initTest() {
        accnt = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccnt() throws Exception {
        int databaseSizeBeforeCreate = accntRepository.findAll().size();
        // Create the Accnt
        AccntDTO accntDTO = accntMapper.toDto(accnt);
        restAccntMockMvc.perform(post("/api/accnts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accntDTO)))
            .andExpect(status().isCreated());

        // Validate the Accnt in the database
        List<Accnt> accntList = accntRepository.findAll();
        assertThat(accntList).hasSize(databaseSizeBeforeCreate + 1);
        Accnt testAccnt = accntList.get(accntList.size() - 1);
        assertThat(testAccnt.getAcctCd()).isEqualTo(DEFAULT_ACCT_CD);
        assertThat(testAccnt.getAcctName()).isEqualTo(DEFAULT_ACCT_NAME);
    }

    @Test
    @Transactional
    public void createAccntWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accntRepository.findAll().size();

        // Create the Accnt with an existing ID
        accnt.setId(1L);
        AccntDTO accntDTO = accntMapper.toDto(accnt);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccntMockMvc.perform(post("/api/accnts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accntDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Accnt in the database
        List<Accnt> accntList = accntRepository.findAll();
        assertThat(accntList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAccnts() throws Exception {
        // Initialize the database
        accntRepository.saveAndFlush(accnt);

        // Get all the accntList
        restAccntMockMvc.perform(get("/api/accnts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accnt.getId().intValue())))
            .andExpect(jsonPath("$.[*].acctCd").value(hasItem(DEFAULT_ACCT_CD)))
            .andExpect(jsonPath("$.[*].acctName").value(hasItem(DEFAULT_ACCT_NAME)));
    }
    
    @Test
    @Transactional
    public void getAccnt() throws Exception {
        // Initialize the database
        accntRepository.saveAndFlush(accnt);

        // Get the accnt
        restAccntMockMvc.perform(get("/api/accnts/{id}", accnt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accnt.getId().intValue()))
            .andExpect(jsonPath("$.acctCd").value(DEFAULT_ACCT_CD))
            .andExpect(jsonPath("$.acctName").value(DEFAULT_ACCT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingAccnt() throws Exception {
        // Get the accnt
        restAccntMockMvc.perform(get("/api/accnts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccnt() throws Exception {
        // Initialize the database
        accntRepository.saveAndFlush(accnt);

        int databaseSizeBeforeUpdate = accntRepository.findAll().size();

        // Update the accnt
        Accnt updatedAccnt = accntRepository.findById(accnt.getId()).get();
        // Disconnect from session so that the updates on updatedAccnt are not directly saved in db
        em.detach(updatedAccnt);
        updatedAccnt
            .acctCd(UPDATED_ACCT_CD)
            .acctName(UPDATED_ACCT_NAME);
        AccntDTO accntDTO = accntMapper.toDto(updatedAccnt);

        restAccntMockMvc.perform(put("/api/accnts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accntDTO)))
            .andExpect(status().isOk());

        // Validate the Accnt in the database
        List<Accnt> accntList = accntRepository.findAll();
        assertThat(accntList).hasSize(databaseSizeBeforeUpdate);
        Accnt testAccnt = accntList.get(accntList.size() - 1);
        assertThat(testAccnt.getAcctCd()).isEqualTo(UPDATED_ACCT_CD);
        assertThat(testAccnt.getAcctName()).isEqualTo(UPDATED_ACCT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAccnt() throws Exception {
        int databaseSizeBeforeUpdate = accntRepository.findAll().size();

        // Create the Accnt
        AccntDTO accntDTO = accntMapper.toDto(accnt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccntMockMvc.perform(put("/api/accnts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(accntDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Accnt in the database
        List<Accnt> accntList = accntRepository.findAll();
        assertThat(accntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccnt() throws Exception {
        // Initialize the database
        accntRepository.saveAndFlush(accnt);

        int databaseSizeBeforeDelete = accntRepository.findAll().size();

        // Delete the accnt
        restAccntMockMvc.perform(delete("/api/accnts/{id}", accnt.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accnt> accntList = accntRepository.findAll();
        assertThat(accntList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
