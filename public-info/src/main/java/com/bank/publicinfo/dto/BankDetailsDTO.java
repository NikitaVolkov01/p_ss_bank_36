package com.bank.publicinfo.dto;

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
public class BankDetailsDTO {

    private Long bik;

    private Long inn;

    private Long kpp;

    private Integer cor_account;

    private String city;

    private String joint_stock_company;

    private String name;
}
