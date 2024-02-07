package com.bank.publicinfo.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class BranchDTO {

    private String address;

    private Long phoneNumber;

    private String city;

    private LocalTime startOfWork;

    private LocalTime endOfWork;
}
