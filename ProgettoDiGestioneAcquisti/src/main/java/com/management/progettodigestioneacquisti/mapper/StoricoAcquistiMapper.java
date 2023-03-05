package com.management.progettodigestioneacquisti.mapper;


import com.management.progettodigestioneacquisti.dto.StoricoAcquistiDto;
import com.management.progettodigestioneacquisti.model.StoricoAcquisti;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoricoAcquistiMapper extends GenericMapper<StoricoAcquisti, StoricoAcquistiDto> {
}
