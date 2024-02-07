package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.AtmDTO;
import com.bank.publicinfo.entity.Atm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AtmMapper {
    AtmMapper INSTANCE = Mappers.getMapper(AtmMapper.class);
    Atm toEntity(AtmDTO atmMainDTO);
    AtmDTO toDto(Atm atm);
}
