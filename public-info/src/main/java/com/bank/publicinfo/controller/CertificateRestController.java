package com.bank.publicinfo.controller;

import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.service.CertificateService;
import com.bank.publicinfo.util.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Certificate REST Controller", description = "Контроллер с CRUD операциями для Certificate")
@RestController
@RequestMapping("/certificate")
public class CertificateRestController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateRestController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @Operation(description = "Метод findOneCertificate выдает одну запись по ее id из таблицы certificate")
    @GetMapping("/{id}")
    public Certificate findOneCertificate(@PathVariable Long id) {
        return certificateService.findOne(id);
    }

    @Operation(description = "Метод findAllCertificates выдает все записи из таблицы certificate")
    @GetMapping("/certificates")
    public List<Certificate> findAllCertificates() {
        return certificateService.findAll();
    }

    @Operation(description = "Метод createCertificate создает запись в таблицe certificate")
    @PostMapping
    public ResponseEntity<HttpStatus> createCertificate(@RequestBody Certificate certificate) {
        certificateService.save(certificate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(description = "Метод updateCertificate обновляет запись в таблице certificate по ее id")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCertificate(@PathVariable Long id, @RequestBody Certificate certificate) {
        certificateService.update(id, certificate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "Метод deleteCertificate удаляет запись в таблице branch по ее id")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCertificate(@PathVariable Long id) {
        certificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse certificateErrorResponse = new ErrorResponse(
                "Certificate with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(certificateErrorResponse, HttpStatus.NOT_FOUND);
    }
}
