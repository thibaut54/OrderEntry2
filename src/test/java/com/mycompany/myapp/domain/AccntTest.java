package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AccntTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accnt.class);
        Accnt accnt1 = new Accnt();
        accnt1.setId(1L);
        Accnt accnt2 = new Accnt();
        accnt2.setId(accnt1.getId());
        assertThat(accnt1).isEqualTo(accnt2);
        accnt2.setId(2L);
        assertThat(accnt1).isNotEqualTo(accnt2);
        accnt1.setId(null);
        assertThat(accnt1).isNotEqualTo(accnt2);
    }
}
