package com.gestioneacquisti.mapper;

import com.gestioneacquisti.dto.ScontrinoDto;
import com.gestioneacquisti.model.Scontrino;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScontrinoMapper extends GenericMapper<Scontrino, ScontrinoDto>{
}
