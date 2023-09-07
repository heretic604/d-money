package com.heretic.dmoney.controllers;

import com.heretic.dmoney.aspects.ExcludeLogging;
import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.services.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @PostMapping(value = "/operation")
    public OperationResponse createOperation(@RequestBody OperationRequest operationRequest) {
        return operationService.createOperation(operationRequest);
    }

    @GetMapping(value = "/operation/{id}")
    public OperationResponse getOperation(@PathVariable UUID id) {
        return operationService.getOperation(id);
    }

    @ExcludeLogging
    @GetMapping(value = "/operations")
    public List<OperationResponse> getOperations() {
        return operationService.getOperations();
    }

    @GetMapping(value = "/operationsByDate")
    public List<OperationResponse> getOperations(LocalDate date) {
        return operationService.getOperations(date);
    }
}