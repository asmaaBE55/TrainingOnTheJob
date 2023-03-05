package com.management.progettodigestioneacquisti.mapper;

import com.management.progettodigestioneacquisti.dto.ProdottoDto;
import com.management.progettodigestioneacquisti.model.Prodotto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdottoMapper extends GenericMapper<Prodotto, ProdottoDto> {
}
