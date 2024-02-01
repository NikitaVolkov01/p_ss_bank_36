package com.bank.publicinfo.controller;

import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.service.AtmService;
import com.bank.publicinfo.util.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Atm REST Controller", description = "Контроллер с CRUD операциями для Atm")
@RestController
@RequestMapping("/atm")
public class AtmRestController {

    private final AtmService atmService;

    @Autowired
    public AtmRestController(AtmService atmService) {
        this.atmService = atmService;
    }

    @Operation(description = "Метод findOneAtm выдает одну запись по ее id из таблицы atm")
    @GetMapping("/{id}")
    public Atm findOneAtm(@PathVariable Long id) {
        return atmService.findOne(id);
    }

    @Operation(description = "Метод findAllAtms выдает все записи из таблицы atm")
    @GetMapping("/atms")
    public List<Atm> findAllAtms() {
        return atmService.findAll();
    }

    @Operation(description = "Метод createAtm создает запись в таблицe atm")
    @PostMapping
    public ResponseEntity<HttpStatus> createAtm(@RequestBody Atm atm) {
        atmService.save(atm);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(description = "Метод updateAtm обновляет запись в таблице atm по ее id")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateAtm(@PathVariable Long id, @RequestBody Atm atm) {
        atmService.update(id, atm);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(description = "Метод deleteAtm удаляет запись в таблице atm по ее id")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAtm(@PathVariable long id) {
        atmService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse atmErrorResponse = new ErrorResponse(
                "Atm with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(atmErrorResponse, HttpStatus.NOT_FOUND);
    }
}
