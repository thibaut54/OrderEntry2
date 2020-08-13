package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OrderEntry2App;
import com.mycompany.myapp.domain.Instrument;
import com.mycompany.myapp.repository.InstrumentRepository;
import com.mycompany.myapp.service.InstrumentService;
import com.mycompany.myapp.service.dto.InstrumentDTO;
import com.mycompany.myapp.service.mapper.InstrumentMapper;

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
 * Integration tests for the {@link InstrumentResource} REST controller.
 */
@SpringBootTest(classes = OrderEntry2App.class)
@AutoConfigureMockMvc
@WithMockUser
public class InstrumentResourceIT {

    private static final Long DEFAULT_INSTRUMENT_ID = 1L;
    private static final Long UPDATED_INSTRUMENT_ID = 2L;

    private static final String DEFAULT_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_4 = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_5 = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_5 = "BBBBBBBBBB";

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private InstrumentMapper instrumentMapper;

    @Autowired
    private InstrumentService instrumentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstrumentMockMvc;

    private Instrument instrument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instrument createEntity(EntityManager em) {
        Instrument instrument = new Instrument()
            .instrumentId(DEFAULT_INSTRUMENT_ID)
            .field1(DEFAULT_FIELD_1)
            .field2(DEFAULT_FIELD_2)
            .field3(DEFAULT_FIELD_3)
            .field4(DEFAULT_FIELD_4)
            .field5(DEFAULT_FIELD_5);
        return instrument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Instrument createUpdatedEntity(EntityManager em) {
        Instrument instrument = new Instrument()
            .instrumentId(UPDATED_INSTRUMENT_ID)
            .field1(UPDATED_FIELD_1)
            .field2(UPDATED_FIELD_2)
            .field3(UPDATED_FIELD_3)
            .field4(UPDATED_FIELD_4)
            .field5(UPDATED_FIELD_5);
        return instrument;
    }

    @BeforeEach
    public void initTest() {
        instrument = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstrument() throws Exception {
        int databaseSizeBeforeCreate = instrumentRepository.findAll().size();
        // Create the Instrument
        InstrumentDTO instrumentDTO = instrumentMapper.toDto(instrument);
        restInstrumentMockMvc.perform(post("/api/instruments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instrumentDTO)))
            .andExpect(status().isCreated());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeCreate + 1);
        Instrument testInstrument = instrumentList.get(instrumentList.size() - 1);
        assertThat(testInstrument.getInstrumentId()).isEqualTo(DEFAULT_INSTRUMENT_ID);
        assertThat(testInstrument.getField1()).isEqualTo(DEFAULT_FIELD_1);
        assertThat(testInstrument.getField2()).isEqualTo(DEFAULT_FIELD_2);
        assertThat(testInstrument.getField3()).isEqualTo(DEFAULT_FIELD_3);
        assertThat(testInstrument.getField4()).isEqualTo(DEFAULT_FIELD_4);
        assertThat(testInstrument.getField5()).isEqualTo(DEFAULT_FIELD_5);
    }

    @Test
    @Transactional
    public void createInstrumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instrumentRepository.findAll().size();

        // Create the Instrument with an existing ID
        instrument.setId(1L);
        InstrumentDTO instrumentDTO = instrumentMapper.toDto(instrument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstrumentMockMvc.perform(post("/api/instruments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instrumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInstruments() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);

        // Get all the instrumentList
        restInstrumentMockMvc.perform(get("/api/instruments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instrument.getId().intValue())))
            .andExpect(jsonPath("$.[*].instrumentId").value(hasItem(DEFAULT_INSTRUMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].field1").value(hasItem(DEFAULT_FIELD_1)))
            .andExpect(jsonPath("$.[*].field2").value(hasItem(DEFAULT_FIELD_2)))
            .andExpect(jsonPath("$.[*].field3").value(hasItem(DEFAULT_FIELD_3)))
            .andExpect(jsonPath("$.[*].field4").value(hasItem(DEFAULT_FIELD_4)))
            .andExpect(jsonPath("$.[*].field5").value(hasItem(DEFAULT_FIELD_5)));
    }
    
    @Test
    @Transactional
    public void getInstrument() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);

        // Get the instrument
        restInstrumentMockMvc.perform(get("/api/instruments/{id}", instrument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instrument.getId().intValue()))
            .andExpect(jsonPath("$.instrumentId").value(DEFAULT_INSTRUMENT_ID.intValue()))
            .andExpect(jsonPath("$.field1").value(DEFAULT_FIELD_1))
            .andExpect(jsonPath("$.field2").value(DEFAULT_FIELD_2))
            .andExpect(jsonPath("$.field3").value(DEFAULT_FIELD_3))
            .andExpect(jsonPath("$.field4").value(DEFAULT_FIELD_4))
            .andExpect(jsonPath("$.field5").value(DEFAULT_FIELD_5));
    }
    @Test
    @Transactional
    public void getNonExistingInstrument() throws Exception {
        // Get the instrument
        restInstrumentMockMvc.perform(get("/api/instruments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstrument() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);

        int databaseSizeBeforeUpdate = instrumentRepository.findAll().size();

        // Update the instrument
        Instrument updatedInstrument = instrumentRepository.findById(instrument.getId()).get();
        // Disconnect from session so that the updates on updatedInstrument are not directly saved in db
        em.detach(updatedInstrument);
        updatedInstrument
            .instrumentId(UPDATED_INSTRUMENT_ID)
            .field1(UPDATED_FIELD_1)
            .field2(UPDATED_FIELD_2)
            .field3(UPDATED_FIELD_3)
            .field4(UPDATED_FIELD_4)
            .field5(UPDATED_FIELD_5);
        InstrumentDTO instrumentDTO = instrumentMapper.toDto(updatedInstrument);

        restInstrumentMockMvc.perform(put("/api/instruments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instrumentDTO)))
            .andExpect(status().isOk());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeUpdate);
        Instrument testInstrument = instrumentList.get(instrumentList.size() - 1);
        assertThat(testInstrument.getInstrumentId()).isEqualTo(UPDATED_INSTRUMENT_ID);
        assertThat(testInstrument.getField1()).isEqualTo(UPDATED_FIELD_1);
        assertThat(testInstrument.getField2()).isEqualTo(UPDATED_FIELD_2);
        assertThat(testInstrument.getField3()).isEqualTo(UPDATED_FIELD_3);
        assertThat(testInstrument.getField4()).isEqualTo(UPDATED_FIELD_4);
        assertThat(testInstrument.getField5()).isEqualTo(UPDATED_FIELD_5);
    }

    @Test
    @Transactional
    public void updateNonExistingInstrument() throws Exception {
        int databaseSizeBeforeUpdate = instrumentRepository.findAll().size();

        // Create the Instrument
        InstrumentDTO instrumentDTO = instrumentMapper.toDto(instrument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstrumentMockMvc.perform(put("/api/instruments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instrumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstrument() throws Exception {
        // Initialize the database
        instrumentRepository.saveAndFlush(instrument);

        int databaseSizeBeforeDelete = instrumentRepository.findAll().size();

        // Delete the instrument
        restInstrumentMockMvc.perform(delete("/api/instruments/{id}", instrument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
