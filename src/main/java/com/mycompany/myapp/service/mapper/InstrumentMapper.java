package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.InstrumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Instrument} and its DTO {@link InstrumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InstrumentMapper extends EntityMapper<InstrumentDTO, Instrument> {


    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "removeOrder", ignore = true)
    Instrument toEntity(InstrumentDTO instrumentDTO);

    default Instrument fromId(Long id) {
        if (id == null) {
            return null;
        }
        Instrument instrument = new Instrument();
        instrument.setId(id);
        return instrument;
    }
}
