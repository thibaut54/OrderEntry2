package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.AccntDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Accnt} and its DTO {@link AccntDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface AccntMapper extends EntityMapper<AccntDTO, Accnt> {

    @Mapping(source = "order.id", target = "orderId")
    AccntDTO toDto(Accnt accnt);

    @Mapping(source = "orderId", target = "order")
    Accnt toEntity(AccntDTO accntDTO);

    default Accnt fromId(Long id) {
        if (id == null) {
            return null;
        }
        Accnt accnt = new Accnt();
        accnt.setId(id);
        return accnt;
    }
}
