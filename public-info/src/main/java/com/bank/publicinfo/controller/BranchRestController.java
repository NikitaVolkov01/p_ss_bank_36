package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BranchDTO;
import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.BranchMapper;
import com.bank.publicinfo.service.BranchService;
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

@Tag(name = "Branch REST Controller", description = "Контроллер с CRUD операциями для Branch")
@RestController
@RequestMapping("/branch")
public class BranchRestController {

    private final BranchService branchService;

    @Autowired
    public BranchRestController(BranchService branchService) {
        this.branchService = branchService;
    }

    @Operation(description = "Метод findOneBranch выдает одну запись по ее id из таблицы branch")
    @GetMapping("/{id}")
    public BranchDTO findOneBranch(@PathVariable Long id) {
        return BranchMapper.INSTANCE.toDto(branchService.findOne(id));
    }

    @Operation(description = "Метод findAllBranches выдает все записи из таблицы branch")
    @GetMapping("/branches")
    public List<BranchDTO> findAllBranches() {
        return branchService.findAll().stream().map(BranchMapper.INSTANCE::toDto).toList();
    }

    @Operation(description = "Метод createBranch создает запись в таблицe branch")
    @PostMapping
    public ResponseEntity<HttpStatus> createBranch(@RequestBody BranchDTO branchDTO) {
        Branch result = BranchMapper.INSTANCE.toEntity(branchDTO);
        branchService.save(result);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(description = "Метод updateBranch обновляет запись в таблице branch по ее id")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBranch(@PathVariable Long id, @RequestBody BranchDTO branchDTO) {
        Branch result = BranchMapper.INSTANCE.toEntity(branchDTO);
        branchService.update(id, result);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(description = "Метод deleteBranch удаляет запись в таблице branch по ее id")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBranch(@PathVariable Long id) {
        branchService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse branchErrorResponse = new ErrorResponse(
                "Branch with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(branchErrorResponse, HttpStatus.NOT_FOUND);
    }
}