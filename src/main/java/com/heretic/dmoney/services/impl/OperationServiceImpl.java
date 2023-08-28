package com.heretic.dmoney.services.impl;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.mappers.EntityDtoMapper;
import com.heretic.dmoney.repositories.OperationRepository;
import com.heretic.dmoney.services.OperationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.heretic.dmoney.util.Constants.ENTITY_NOT_FOUND_BY_ID;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final EntityDtoMapper mapper;

    @Override
    public OperationResponse saveOperation(OperationRequest operationRequest) {
        return mapper.operationEntityToDTO((operationRepository.save(mapper.operationDTOtoEntity(operationRequest))));
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
}
