package com.gestioneacquisti.mapper;

import com.gestioneacquisti.dto.OrdineDto;
import com.gestioneacquisti.model.Ordine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdineMapper extends GenericMapper<Ordine, OrdineDto> {
}
