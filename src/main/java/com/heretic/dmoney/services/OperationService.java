package com.heretic.dmoney.services;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OperationService {

    OperationResponse saveOperation(OperationRequest operationRequest);

    OperationResponse getOperation(UUID id);

    List<OperationResponse> getOperations();

    List<OperationResponse> getOperations(LocalDate date);
}