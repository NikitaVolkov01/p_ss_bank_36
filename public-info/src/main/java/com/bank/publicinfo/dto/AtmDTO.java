package com.bank.publicinfo.dto;

import com.bank.publicinfo.entity.Branch;

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
public class AtmDTO {

    private String address;

    private LocalTime start_of_work;

    private LocalTime end_of_work;

    private boolean all_hours;

    private Branch branch;
}
