package com.management.progettodigestioneacquisti.mapper;

import com.management.progettodigestioneacquisti.dto.AcquistoDto;
import com.management.progettodigestioneacquisti.model.Acquisto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AcquistoMapper extends GenericMapper<Acquisto, AcquistoDto> {
}
