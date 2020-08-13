package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class InstrumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Instrument.class);
        Instrument instrument1 = new Instrument();
        instrument1.setId(1L);
        Instrument instrument2 = new Instrument();
        instrument2.setId(instrument1.getId());
        assertThat(instrument1).isEqualTo(instrument2);
        instrument2.setId(2L);
        assertThat(instrument1).isNotEqualTo(instrument2);
        instrument1.setId(null);
        assertThat(instrument1).isNotEqualTo(instrument2);
    }
}
