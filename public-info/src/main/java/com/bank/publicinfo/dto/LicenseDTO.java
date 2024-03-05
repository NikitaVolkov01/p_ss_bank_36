package com.bank.publicinfo.dto;

import com.bank.publicinfo.entity.BankDetails;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LicenseDTO {

    private byte[] photo;

    private BankDetails bankDetails;
}
