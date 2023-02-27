package com.gestioneacquisti.mapper;

import com.gestioneacquisti.dto.AcquistoDto;
import com.gestioneacquisti.model.Acquisto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcquistoMapper extends GenericMapper<Acquisto, AcquistoDto> {
}
