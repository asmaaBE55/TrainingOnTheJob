package com.gestioneacquisti.mapper;

import com.gestioneacquisti.dto.ProdottoDto;
import com.gestioneacquisti.model.Prodotto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdottoMapper extends GenericMapper<Prodotto, ProdottoDto>{
}
