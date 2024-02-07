package com.bank.publicinfo.dto;

import lombok.Data;

@Data
public class BankDetailsDTO {

    private Long bik;

    private Long inn;

    private Long kpp;

    private Integer cor_account;

    private String city;

    private String joint_stock_company;

    private String name;
}
