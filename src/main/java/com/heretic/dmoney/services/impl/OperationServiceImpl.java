package com.heretic.dmoney.services.impl;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.repositories.OperationRepository;
import com.heretic.dmoney.services.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.heretic.dmoney.mappers.OperationMapper.INSTANCE;
import static com.heretic.dmoney.util.Constants.ENTITY_NOT_FOUND_BY_ID_MASSAGE;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;

    @Override
    public OperationResponse saveOperation(OperationRequest operationRequest) {
        return INSTANCE.toDto(operationRepository.save(INSTANCE.toEntity(operationRequest)));
    }

    @Override
    public OperationResponse getOperation(UUID id) {
        return operationRepository.findById(id)
                .map(INSTANCE::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID_MASSAGE, id)));
    }

    @Override
    public List<OperationResponse> getOperations() {
        return operationRepository.findAll()
                .stream()
                .map(INSTANCE::toDto)
                .toList();
    }

    @Override
    public List<OperationResponse> getOperations(LocalDate date) {
        return operationRepository.getOperationsByTimeContaining(date)
                .stream()
                .map(INSTANCE::toDto)
                .toList();
    }
}
