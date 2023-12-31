package com.heretic.dmoney.services.impl;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.entities.Operation;
import com.heretic.dmoney.exceptions.NoFundsException;
import com.heretic.dmoney.mappers.OperationMapper;
import com.heretic.dmoney.mappers.WalletMapper;
import com.heretic.dmoney.repositories.OperationRepository;
import com.heretic.dmoney.services.CurrencyService;
import com.heretic.dmoney.services.OperationService;
import com.heretic.dmoney.services.WalletService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.heretic.dmoney.enums.OperationStatus.SUCCEED;
import static com.heretic.dmoney.util.Constants.*;
import static java.lang.String.format;
import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final WalletService walletService;
    private final CurrencyService currencyService;
    private final OperationMapper operationMapper;
    private final WalletMapper walletMapper;
    private final OperationRepository operationRepository;

    @Override
    @Transactional
    public OperationResponse createOperation(OperationRequest operationRequest) {
        Operation operation = operationMapper.mapToOperation(operationRequest);

        if (operationRequest.getSenderNumber() != null) {
            operation.setSender(walletMapper.mapToWallet(walletService.getWallet(operationRequest.getSenderNumber())));
        }
        if (operationRequest.getReceiverNumber() != null) {
            operation.setReceiver(walletMapper.mapToWallet(walletService.getWallet(operationRequest.getReceiverNumber())));
        }

        operation = operationRepository.save(executeOperation(operation));
        return operationMapper.mapToOperationResponse(operation);
    }

    @Override
    @Transactional(readOnly = true)
    public OperationResponse getOperation(UUID id) {
        return operationRepository.findById(id)
                .map(operationMapper::mapToOperationResponse)
                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID, id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperationResponse> getOperations() {
        return operationRepository.findAll()
                .stream()
                .map(operationMapper::mapToOperationResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperationResponse> getOperations(LocalDate date) {
        return operationRepository.getOperationsByTimeContaining(date)
                .stream()
                .map(operationMapper::mapToOperationResponse)
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
        BigDecimal rate = getExRate(operation.getReceiver().getCurrency(), operation.getCurrencyOut());
        BigDecimal amountIn = operation.getAmountOut().multiply(rate);
        walletService.updateWallet(amountIn, operation.getReceiver().getWalletId());
        return enrichOperation(operation, amountIn, rate);
    }

    private Operation executeWithdrawal(Operation operation) {
        BigDecimal rate = getExRate(operation.getCurrencyIn(), operation.getSender().getCurrency());
        if (isSufficientFunds(operation, rate)) {
            BigDecimal amountIn = operation.getAmountOut().multiply(rate);
            walletService.updateWallet(operation.getAmountOut().negate(), operation.getSender().getWalletId());
            return enrichOperation(operation, amountIn, rate);
        } else {
            throw new NoFundsException(NO_FUNDS);
        }
    }

    private Operation executeTransfer(Operation operation) {
        BigDecimal rate = getExRate(operation.getReceiver().getCurrency(), operation.getSender().getCurrency());
        if (isSufficientFunds(operation, rate)) {
            BigDecimal amountIn = operation.getAmountOut().multiply(rate);
            walletService.updateWallet(operation.getAmountOut().negate(), operation.getSender().getWalletId());
            walletService.updateWallet(amountIn, operation.getReceiver().getWalletId());
            return enrichOperation(operation, amountIn, rate);
        } else {
            throw new NoFundsException(NO_FUNDS);
        }
    }

    private BigDecimal getExRate(String currencyIn, String currencyOut) {

        BigDecimal rateIn;
        BigDecimal rateOut;

        if (currencyIn.equals(currencyOut)) {
            return ONE;
        } else if (currencyIn.equals(DEFAULT_CURRENCY)) {
            rateIn = ONE;
            rateOut = currencyService.getCurrency(currencyOut).getRate();
        } else if (currencyOut.equals(DEFAULT_CURRENCY)) {
            rateIn = currencyService.getCurrency(currencyIn).getRate();
            rateOut = ONE;
        } else {
            rateIn = currencyService.getCurrency(currencyIn).getRate();
            rateOut = currencyService.getCurrency(currencyOut).getRate();
        }
        return rateOut.divide(rateIn, 2, HALF_UP);
    }

    private Operation enrichOperation(Operation operation, BigDecimal amountIn, BigDecimal rate) {
        operation.setAmountIn(amountIn);
        operation.setExRate(rate);
        operation.setStatus(SUCCEED);
        return operation;
    }

    private boolean isSufficientFunds(Operation operation, BigDecimal rate) {
        return operation.getSender().getAmount().multiply(rate).compareTo(operation.getAmountOut()) >= 0;
    }
}
