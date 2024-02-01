package com.bank.publicinfo.controller;

import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.service.LicenseService;
import com.bank.publicinfo.util.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public License findOneLicense(@PathVariable Long id) {
        return licenseService.findOne(id);
    }

    @Operation(description = "Метод findAllLicenses выдает все записи из таблицы license")
    @GetMapping("/licenses")
    public List<License> findAllLicenses() {
        return licenseService.findAll();
    }

    @Operation(description = "Метод createLicense создает запись в таблицe license")
    @PostMapping
    public ResponseEntity<HttpStatus> createLicense(@RequestBody License license) {
        licenseService.save(license);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(description = "Метод updateLicense обновляет запись в таблице license по ее id")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateLicense(@PathVariable Long id, @RequestBody License license) {
        licenseService.update(id, license);
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
