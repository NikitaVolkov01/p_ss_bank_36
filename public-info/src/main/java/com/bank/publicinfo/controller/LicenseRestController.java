package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.LicenseDTO;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.service.LicenseService;
import com.bank.publicinfo.util.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Tag(name = "License REST Controller", description = "Контроллер с CRUD операциями для License")
@RestController
@RequestMapping("/license")
public class LicenseRestController {

    private final LicenseService licenseService;

    @Autowired
    public LicenseRestController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @Operation(description = "Метод findOneLicense выдает одну запись по ее id из таблицы license")
    @GetMapping("/{id}")
    public LicenseDTO findOneLicense(@PathVariable Long id) {
        return LicenseMapper.INSTANCE.toDto(licenseService.findOne(id));
    }

    @Operation(description = "Метод findAllLicenses выдает все записи из таблицы license")
    @GetMapping("/licenses")
    public List<LicenseDTO> findAllLicenses() {
        return licenseService.findAll().stream().map(LicenseMapper.INSTANCE::toDto).toList();
    }

    @Operation(description = "Метод createLicense создает запись в таблицe license")
    @PostMapping
    public ResponseEntity<HttpStatus> createLicense(@RequestBody LicenseDTO licenseDTO) {
        License result = LicenseMapper.INSTANCE.toEntity(licenseDTO);
        licenseService.save(result);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(description = "Метод updateLicense обновляет запись в таблице license по ее id")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateLicense(@PathVariable Long id, @RequestBody LicenseDTO licenseDTO) {
        License result = LicenseMapper.INSTANCE.toEntity(licenseDTO);
        licenseService.update(id, result);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "Метод deleteLicense удаляет запись в таблице license по ее id")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLicense(@PathVariable Long id) {
        licenseService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse licenseErrorResponse = new ErrorResponse(
                "Certificate with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(licenseErrorResponse, HttpStatus.NOT_FOUND);
    }
}
