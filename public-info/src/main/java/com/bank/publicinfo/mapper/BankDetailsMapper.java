package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDTO;
import com.bank.publicinfo.entity.BankDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BankDetailsMapper {
    BankDetailsMapper INSTANCE = Mappers.getMapper(BankDetailsMapper.class);
    BankDetails toEntity(BankDetailsDTO bankDetailsDTO);
    BankDetailsDTO toDto(BankDetails bankDetails);
}
