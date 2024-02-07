package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDTO;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.BankDetailsMapper;
import com.bank.publicinfo.service.BankDetailsService;
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

@Tag(name = "BankDetails REST Controller", description = "Контроллер с CRUD операциями для BankDetails")
@RestController
@RequestMapping("/bankDetails")
public class BankDetailsRestController {

    private final BankDetailsService bankDetailsService;

    @Autowired
    public BankDetailsRestController(BankDetailsService bankDetailsService) {
        this.bankDetailsService = bankDetailsService;
    }

    @Operation(description = "Метод findOneBankDetails выдает одну запись по ее id из таблицы bank_details")
    @GetMapping("/{id}")
    public BankDetailsDTO findOneBankDetails(@PathVariable Long id) {
        return BankDetailsMapper.INSTANCE.toDto(bankDetailsService.findOne(id));
    }

    @Operation(description = "Метод findAllBankDetails выдает все записи из таблицы bank_details")
    @GetMapping("/bankDetails")
    public List<BankDetailsDTO> findAllBankDetails() {
        return bankDetailsService.findAll().stream().map(BankDetailsMapper.INSTANCE::toDto).toList();
    }

    @Operation(description = "Метод createBankDetails создает запись в таблицe bank_details")
    @PostMapping()
    public ResponseEntity<HttpStatus> createBankDetails(@RequestBody BankDetailsDTO bankDetailsDTO) {
        BankDetails result = BankDetailsMapper.INSTANCE.toEntity(bankDetailsDTO);
        bankDetailsService.save(result);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(description = "Метод updateBankDetails обновляет запись в таблице bank_details по ее id")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBankDetails(@PathVariable Long id, @RequestBody BankDetailsDTO bankDetailsDTO) {
        BankDetails result = BankDetailsMapper.INSTANCE.toEntity(bankDetailsDTO);
        bankDetailsService.update(id, result);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(description = "Метод deleteBankDetails удаляет запись в таблице bank_details по ее id")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBankDetails(@PathVariable Long id) {
        bankDetailsService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse bankDetailsErrorResponse = new ErrorResponse(
                "BankDetails with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(bankDetailsErrorResponse, HttpStatus.NOT_FOUND);
    }
}
