package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AccntMapperTest {

    private AccntMapper accntMapper;

    @BeforeEach
    public void setUp() {
        accntMapper = new AccntMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(accntMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(accntMapper.fromId(null)).isNull();
    }
}
