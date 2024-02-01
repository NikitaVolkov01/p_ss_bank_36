package com.bank.publicinfo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "branch", schema = "public_bank_information")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Max(value = 370)
    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Max(value = 250)
    @Column(name = "city")
    private String City;

    @Column(name = "start_of_work")
    private LocalTime startOfWork;

    @Column(name = "end_of_work")
    private LocalTime endOfWork;

    @OneToMany(mappedBy = "branch")
    private List<Atm> atms;
}
