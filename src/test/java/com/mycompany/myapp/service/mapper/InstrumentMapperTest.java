package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InstrumentMapperTest {

    private InstrumentMapper instrumentMapper;

    @BeforeEach
    public void setUp() {
        instrumentMapper = new InstrumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(instrumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(instrumentMapper.fromId(null)).isNull();
    }
}
