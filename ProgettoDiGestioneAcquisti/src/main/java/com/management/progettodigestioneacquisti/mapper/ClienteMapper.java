package com.management.progettodigestioneacquisti.mapper;

import com.management.progettodigestioneacquisti.dto.ClienteDto;
import com.management.progettodigestioneacquisti.model.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper extends GenericMapper<Cliente, ClienteDto> {
}
