package com.heretic.dmoney.repositories;

import com.heretic.dmoney.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, UUID> {

    List<Operation> getOperationsByTimeContaining(LocalDate date);
}