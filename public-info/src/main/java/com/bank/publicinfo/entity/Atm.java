package com.bank.publicinfo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "atm", schema = "public_bank_information")
public class Atm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Max(value = 370)
    @Column(name = "address")
    private String address;

    @Column(name = "start_of_work")
    private LocalTime start_of_work;

    @Column(name = "end_of_work")
    private LocalTime end_of_work;

    @Column(name = "all_hours")
    private boolean all_hours;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private Branch branch;
}
