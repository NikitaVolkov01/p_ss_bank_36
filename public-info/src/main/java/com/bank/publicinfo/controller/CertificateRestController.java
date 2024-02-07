package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.CertificateDTO;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.service.CertificateService;
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
    public CertificateDTO findOneCertificate(@PathVariable Long id) {
        return CertificateMapper.INSTANCE.toDto(certificateService.findOne(id));
    }

    @Operation(description = "Метод findAllCertificates выдает все записи из таблицы certificate")
    @GetMapping("/certificates")
    public List<CertificateDTO> findAllCertificates() {
        return certificateService.findAll().stream().map(CertificateMapper.INSTANCE::toDto).toList();
    }

    @Operation(description = "Метод createCertificate создает запись в таблицe certificate")
    @PostMapping
    public ResponseEntity<HttpStatus> createCertificate(@RequestBody CertificateDTO certificateDTO) {
        Certificate result = CertificateMapper.INSTANCE.toEntity(certificateDTO);
        certificateService.save(result);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(description = "Метод updateCertificate обновляет запись в таблице certificate по ее id")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCertificate(@PathVariable Long id, @RequestBody CertificateDTO certificateDTO) {
        Certificate result = CertificateMapper.INSTANCE.toEntity(certificateDTO);
        certificateService.update(id, result);
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
