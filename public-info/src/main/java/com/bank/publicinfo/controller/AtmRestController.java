package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.AtmDTO;
import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.AtmMapper;
import com.bank.publicinfo.service.AtmService;
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

import java.time.temporal.ChronoUnit;
import java.util.List;

@Tag(name = "Atm REST Controller", description = "Контроллер с CRUD операциями для Atm")
@RestController
@RequestMapping("/atms")
public class AtmRestController {

    private final AtmService atmService;

    @Autowired
    public AtmRestController(AtmService atmService) {
        this.atmService = atmService;
    }

    @Operation(description = "Метод findOneAtm выдает одну запись по ее id из таблицы atm")
    @GetMapping("/{id}")
    public AtmDTO findOneAtm(@PathVariable Long id) {
        return AtmMapper.INSTANCE.toDto(atmService.findOne(id));
    }

    @Operation(description = "Метод findAllAtms выдает все записи из таблицы atm")
    @GetMapping()
    public List<AtmDTO> findAllAtms() {
        return atmService.findAll().stream().map(AtmMapper.INSTANCE::toDto).toList();
    }

    @Operation(description = "Метод createAtm создает запись в таблицe atm")
    @PostMapping
    public ResponseEntity<HttpStatus> createAtm(@RequestBody AtmDTO atmDTO) {
        Atm result = AtmMapper.INSTANCE.toEntity(atmDTO);
        long minutesBetween = ChronoUnit.MINUTES.between(result.getEnd_of_work(), result.getStart_of_work());
        if (minutesBetween == 0) {
            result.setAll_hours(true);
            atmService.save(result);
        } else {
            result.setAll_hours(false);
            atmService.save(result);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(description = "Метод updateAtm обновляет запись в таблице atm по ее id")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateAtm(@PathVariable Long id, @RequestBody AtmDTO atmDTO) {
        Atm result = AtmMapper.INSTANCE.toEntity(atmDTO);
        long minutesBetween = ChronoUnit.MINUTES.between(result.getEnd_of_work(), result.getStart_of_work());
        if (minutesBetween == 0) {
            result.setAll_hours(true);
            atmService.update(id, result);
        } else {
            result.setAll_hours(false);
            atmService.update(id, result);
        }
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