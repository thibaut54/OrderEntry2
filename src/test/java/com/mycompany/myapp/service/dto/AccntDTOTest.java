package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AccntDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccntDTO.class);
        AccntDTO accntDTO1 = new AccntDTO();
        accntDTO1.setId(1L);
        AccntDTO accntDTO2 = new AccntDTO();
        assertThat(accntDTO1).isNotEqualTo(accntDTO2);
        accntDTO2.setId(accntDTO1.getId());
        assertThat(accntDTO1).isEqualTo(accntDTO2);
        accntDTO2.setId(2L);
        assertThat(accntDTO1).isNotEqualTo(accntDTO2);
        accntDTO1.setId(null);
        assertThat(accntDTO1).isNotEqualTo(accntDTO2);
    }
}
