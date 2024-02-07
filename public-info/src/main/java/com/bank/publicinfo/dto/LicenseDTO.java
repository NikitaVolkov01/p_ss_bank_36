package com.bank.publicinfo.dto;

import com.bank.publicinfo.entity.BankDetails;
import lombok.Data;
@Data
public class LicenseDTO {

    private byte[] photo;

    private BankDetails bankDetails;
}
