package com.example.bank.repositories;

import com.example.bank.models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchesRepository extends JpaRepository<Branch, Integer> {
}
