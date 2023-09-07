package com.heretic.dmoney.services;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service processes operations data
 * Performs all financial transactions
 */
public interface OperationService {

    /**
     * Perform a financial transaction and save it in database
     *
     * @param operationRequest operation data
     * @return performed operation information
     */
    OperationResponse createOperation(OperationRequest operationRequest);

    /**
     * Get info about operation from database
     *
     * @param id operation ID
     * @return operation info
     */
    OperationResponse getOperation(UUID id);

    /**
     * Get all operations from database
     *
     * @return list of all operations
     */
    List<OperationResponse> getOperations();

    /**
     * Get operations by date
     *
     * @param date day of operations
     * @return list of operations by date
     */
    List<OperationResponse> getOperations(LocalDate date);
}