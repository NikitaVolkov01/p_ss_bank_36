package com.bank.publicinfo.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalTime;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchDTO {

    private String address;

    private Long phoneNumber;

    private String city;

    private LocalTime startOfWork;

    private LocalTime endOfWork;
}
