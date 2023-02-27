package com.gestioneacquisti.mapper;

import com.gestioneacquisti.dto.StoricoAcquistiDto;
import com.gestioneacquisti.model.StoricoAcquisti;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoricoAcquistiMapper extends GenericMapper<StoricoAcquisti, StoricoAcquistiDto> {
}
