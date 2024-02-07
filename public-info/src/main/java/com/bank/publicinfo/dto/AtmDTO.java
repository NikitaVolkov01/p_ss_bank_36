package com.bank.publicinfo.dto;

import com.bank.publicinfo.entity.Branch;
import lombok.Data;

import java.time.LocalTime;

@Data
public class AtmDTO {

    private String address;

    private LocalTime start_of_work;

    private LocalTime end_of_work;

    private boolean all_hours;

    private Branch branch;
}
