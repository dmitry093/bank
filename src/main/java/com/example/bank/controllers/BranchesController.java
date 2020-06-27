package com.example.bank.controllers;

import com.example.bank.models.Branch;
import com.example.bank.models.ErrorResponse;
import com.example.bank.repositories.BranchesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BranchesController {
    private final BranchesRepository branchesRepository;

    @GetMapping("/branches/{id}")
    public ResponseEntity<?> getBranch(@PathVariable Integer id) {
        Optional<Branch> branch = branchesRepository.findById(id);
        if (branch.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("Not found"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(branch);
    }
}
