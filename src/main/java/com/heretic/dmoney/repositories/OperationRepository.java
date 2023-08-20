package com.heretic.dmoney.repositories;

import com.heretic.dmoney.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, UUID> {

    Optional<Operation> updateOperationByOperationId(Operation operation, UUID id);
}