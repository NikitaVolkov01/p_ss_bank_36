package com.bank.publicinfo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import java.time.LocalTime;
import java.util.List;

@Entity
@EqualsAndHashCode
@Getter
@Setter
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
    private String city;

    @Column(name = "start_of_work")
    private LocalTime startOfWork;

    @Column(name = "end_of_work")
    private LocalTime endOfWork;

    @JsonIgnore
    @OneToMany(mappedBy = "branch")
    private List<Atm> atms;
}
