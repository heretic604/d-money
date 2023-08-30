package com.heretic.dmoney.services.impl;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.entities.Operation;
import com.heretic.dmoney.enums.OperationStatus;
import com.heretic.dmoney.exceptions.NoFundsException;
import com.heretic.dmoney.mappers.OperationMapper;
import com.heretic.dmoney.repositories.OperationRepository;
import com.heretic.dmoney.repositories.WalletRepository;
import com.heretic.dmoney.services.OperationService;
import com.heretic.dmoney.services.WalletService;
import com.heretic.dmoney.util.Constants;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.heretic.dmoney.enums.OperationStatus.*;
import static com.heretic.dmoney.util.Constants.*;
import static com.heretic.dmoney.util.Constants.ENTITY_NOT_FOUND_BY_ID;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final WalletService walletService;
    private final OperationMapper mapper;
    private final OperationRepository operationRepository;

    @Override
    public OperationResponse createOperation(OperationRequest operationRequest) {
        Operation operation = mapper.mapToOperation(operationRequest);
        operationRepository.save(executeOperation(operation));
        return mapper.operationEntityToDTO(operation);
    }

    @Override
    public OperationResponse getOperation(UUID id) {
        return operationRepository.findById(id)
                .map(mapper::operationEntityToDTO)
                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID, id)));
    }

    @Override
    public List<OperationResponse> getOperations() {
        return operationRepository.findAll()
                .stream()
                .map(mapper::operationEntityToDTO)
                .toList();
    }

    @Override
    public List<OperationResponse> getOperations(LocalDate date) {
        return operationRepository.getOperationsByTimeContaining(date)
                .stream()
                .map(mapper::operationEntityToDTO)
                .toList();
    }

    private Operation executeOperation(Operation operation) {
        if (operation.getSender() == null) {
            return executeDeposit(operation);
        } else if (operation.getReceiver() == null) {
            return executeWithdrawal(operation);
        } else {
            return executeTransfer(operation);
        }
    }

    private Operation executeDeposit(Operation operation) {
        walletService.updateWallet(operation.getAmountIn(), operation.getReceiver());
        operation.setStatus(SUCCEED);
        return operation;
    }

    private Operation executeWithdrawal(Operation operation) {
        if (operation.getSender().getAmount().compareTo(operation.getAmountOut()) >= 0) {
            walletService.updateWallet(operation.getAmountIn().negate(), operation.getSender());
            operation.setStatus(SUCCEED);
            return operation;
        } else {
            throw new NoFundsException(NO_FUNDS);
        }
    }

    private Operation executeTransfer(Operation operation) {
        return null;
    }

    private BigDecimal getExRate(Operation operation) {
        return null;
    }
}
