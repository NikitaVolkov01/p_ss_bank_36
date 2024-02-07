package com.bank.publicinfo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bank_details", schema = "public_bank_information")
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bik")
    private Long bik;

    @Column(name = "inn")
    private Long inn;

    @Column(name = "kpp")
    private Long kpp;

    @Column(name = "cor_account")
    private Integer cor_account;

    @Max(value = 180)
    @Column(name = "city")
    private String city;

    @Max(value = 15)
    @Column(name = "joint_stock_company")
    private String joint_stock_company;

    @Max(value = 80)
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "bankDetails")
    private List<License> licenses;

    @OneToMany(mappedBy = "bankDetails")
    private List<Certificate> certificates;

}
