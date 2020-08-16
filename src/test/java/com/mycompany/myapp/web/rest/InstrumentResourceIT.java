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

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BLOOMBERG_TICKER = "AAAAAAAAAA";
    private static final String UPDATED_BLOOMBERG_TICKER = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY_QUOTATION = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_QUOTATION = "BBBBBBBBBB";

    private static final String DEFAULT_ISIN = "AAAAAAAAAA";
    private static final String UPDATED_ISIN = "BBBBBBBBBB";

    private static final String DEFAULT_MARKET = "AAAAAAAAAA";
    private static final String UPDATED_MARKET = "BBBBBBBBBB";

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
            .name(DEFAULT_NAME)
            .bloombergTicker(DEFAULT_BLOOMBERG_TICKER)
            .currencyQuotation(DEFAULT_CURRENCY_QUOTATION)
            .isin(DEFAULT_ISIN)
            .market(DEFAULT_MARKET);
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
            .name(UPDATED_NAME)
            .bloombergTicker(UPDATED_BLOOMBERG_TICKER)
            .currencyQuotation(UPDATED_CURRENCY_QUOTATION)
            .isin(UPDATED_ISIN)
            .market(UPDATED_MARKET);
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
        assertThat(testInstrument.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstrument.getBloombergTicker()).isEqualTo(DEFAULT_BLOOMBERG_TICKER);
        assertThat(testInstrument.getCurrencyQuotation()).isEqualTo(DEFAULT_CURRENCY_QUOTATION);
        assertThat(testInstrument.getIsin()).isEqualTo(DEFAULT_ISIN);
        assertThat(testInstrument.getMarket()).isEqualTo(DEFAULT_MARKET);
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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].bloombergTicker").value(hasItem(DEFAULT_BLOOMBERG_TICKER)))
            .andExpect(jsonPath("$.[*].currencyQuotation").value(hasItem(DEFAULT_CURRENCY_QUOTATION)))
            .andExpect(jsonPath("$.[*].isin").value(hasItem(DEFAULT_ISIN)))
            .andExpect(jsonPath("$.[*].market").value(hasItem(DEFAULT_MARKET)));
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.bloombergTicker").value(DEFAULT_BLOOMBERG_TICKER))
            .andExpect(jsonPath("$.currencyQuotation").value(DEFAULT_CURRENCY_QUOTATION))
            .andExpect(jsonPath("$.isin").value(DEFAULT_ISIN))
            .andExpect(jsonPath("$.market").value(DEFAULT_MARKET));
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
            .name(UPDATED_NAME)
            .bloombergTicker(UPDATED_BLOOMBERG_TICKER)
            .currencyQuotation(UPDATED_CURRENCY_QUOTATION)
            .isin(UPDATED_ISIN)
            .market(UPDATED_MARKET);
        InstrumentDTO instrumentDTO = instrumentMapper.toDto(updatedInstrument);

        restInstrumentMockMvc.perform(put("/api/instruments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instrumentDTO)))
            .andExpect(status().isOk());

        // Validate the Instrument in the database
        List<Instrument> instrumentList = instrumentRepository.findAll();
        assertThat(instrumentList).hasSize(databaseSizeBeforeUpdate);
        Instrument testInstrument = instrumentList.get(instrumentList.size() - 1);
        assertThat(testInstrument.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstrument.getBloombergTicker()).isEqualTo(UPDATED_BLOOMBERG_TICKER);
        assertThat(testInstrument.getCurrencyQuotation()).isEqualTo(UPDATED_CURRENCY_QUOTATION);
        assertThat(testInstrument.getIsin()).isEqualTo(UPDATED_ISIN);
        assertThat(testInstrument.getMarket()).isEqualTo(UPDATED_MARKET);
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
