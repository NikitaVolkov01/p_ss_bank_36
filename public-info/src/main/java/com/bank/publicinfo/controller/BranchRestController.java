package com.bank.publicinfo.controller;

import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.service.BranchService;
import com.bank.publicinfo.util.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Branch findOneBranch(@PathVariable Long id) {
        return branchService.findOne(id);
    }

    @Operation(description = "Метод findAllBranches выдает все записи из таблицы branch")
    @GetMapping("/branches")
    public List<Branch> findAllBranches() {
        return branchService.findAll();
    }

    @Operation(description = "Метод createBranch создает запись в таблицe branch")
    @PostMapping
    public ResponseEntity<HttpStatus> createBranch(@RequestBody Branch branch) {
        branchService.save(branch);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(description = "Метод updateBranch обновляет запись в таблице branch по ее id")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateBranch(@PathVariable Long id, @RequestBody Branch branch) {
        branchService.update(id, branch);
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
