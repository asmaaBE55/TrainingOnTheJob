package com.management.progettodigestioneacquisti.mapper;

import com.management.progettodigestioneacquisti.dto.ScontrinoDto;
import com.management.progettodigestioneacquisti.model.Scontrino;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScontrinoMapper extends GenericMapper<Scontrino, ScontrinoDto> {
}
