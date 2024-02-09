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
import javax.validation.constraints.Max;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "audit", schema = "public_bank_information")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Max(value = 40)
    @Column(name = "entity_type")
    private String entityType;

    @Max(value = 255)
    @Column(name = "operation_type")
    private String operationType;

    @Max(value = 255)
    @Column(name = "created_By")
    private String createdBy;

    @Max(value = 255)
    @Column(name = "modified_By")
    private String modifiedBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @Column(name = "new_entity_json")
    private String newEntityJson;

    @Column(name = "entity_json")
    private String entityJson;

}
