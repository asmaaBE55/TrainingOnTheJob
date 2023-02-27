package com.gestioneacquisti.mapper;

import com.gestioneacquisti.dto.ClienteDto;
import com.gestioneacquisti.model.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper extends GenericMapper<Cliente, ClienteDto>{
}
