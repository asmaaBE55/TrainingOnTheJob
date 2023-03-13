package com.management.progettodigestioneacquisti.mapper;

import com.management.progettodigestioneacquisti.dto.FidelityCardDto;
import com.management.progettodigestioneacquisti.model.FidelityCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FidelityCardMapper extends GenericMapper<FidelityCard,FidelityCardDto> {
}
