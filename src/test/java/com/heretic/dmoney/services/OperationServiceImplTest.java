package com.heretic.dmoney.services;

import com.heretic.dmoney.arguments.operations.*;
import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.CurrencyResponse;
import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Operation;
import com.heretic.dmoney.exceptions.NoFundsException;
import com.heretic.dmoney.mappers.OperationMapperImpl;
import com.heretic.dmoney.mappers.WalletMapperImpl;
import com.heretic.dmoney.repositories.OperationRepository;
import com.heretic.dmoney.services.impl.OperationServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.heretic.dmoney.util.Constants.ENTITY_NOT_FOUND_BY_ID;
import static com.heretic.dmoney.util.Constants.NO_FUNDS;
import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.*;

@DisplayName("OperationService tests")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(PER_CLASS)
class OperationServiceImplTest {

    @InjectMocks
    private OperationServiceImpl operationService;
    @Autowired
    private OperationMapperImpl operationMapper;
    @Autowired
    private WalletMapperImpl walletMapper;
    @Mock
    private static WalletService walletService;
    @Mock
    private static OperationRepository operationRepository;
    @Mock
    private static CurrencyService currencyService;
    private static final UUID ID = randomUUID();

    @BeforeEach
    public void init() {
        operationService = new OperationServiceImpl(
                walletService,
                currencyService,
                operationMapper,
                walletMapper,
                operationRepository);
    }

    @Nested
    @DisplayName("with same currency and sufficient funds")
    public class SufficientFunds {

        @DisplayName("create deposit operation")
        @ParameterizedTest
        @ArgumentsSource(SaveDepositOperationEqualCurrenciesArguments.class)
        public void createDepositOperationTest(OperationRequest request, Operation operation, OperationResponse response, WalletResponse walletResponse) {
            when(walletService.getWallet(anyLong())).thenReturn(walletResponse);
            when(operationRepository.save(operation)).thenReturn(operation);

            OperationResponse actual = operationService.createOperation(request);
            assertThat(actual).isEqualTo(response);

            verify(walletService).getWallet(anyLong());
            verify(operationRepository).save(operation);
        }

        @DisplayName("create withdrawal operation")
        @ParameterizedTest
        @ArgumentsSource(SaveWithdrawalOperationsEqualCurrenciesArguments.class)
        public void createWithdrawalOperationTest(OperationRequest request, Operation operation, OperationResponse response, WalletResponse walletResponse) {
            when(walletService.getWallet(anyLong())).thenReturn(walletResponse);
            when(operationRepository.save(operation)).thenReturn(operation);

            OperationResponse actual = operationService.createOperation(request);
            assertThat(actual).isEqualTo(response);

            verify(walletService).getWallet(anyLong());
            verify(operationRepository).save(operation);
        }

        @DisplayName("create transfer operation")
        @ParameterizedTest
        @ArgumentsSource(SaveTransferOperationEqualCurrenciesArguments.class)
        public void createTransferOperationTest(OperationRequest request, Operation operation, OperationResponse response, WalletResponse walletResponse) {
            when(walletService.getWallet(anyLong())).thenReturn(walletResponse);
            when(operationRepository.save(operation)).thenReturn(operation);

            OperationResponse actual = operationService.createOperation(request);
            assertThat(actual).isEqualTo(response);

            verify(walletService, times(2)).getWallet(anyLong());
            verify(operationRepository).save(operation);
        }

        @DisplayName("get by valid ID")
        @ParameterizedTest
        @ArgumentsSource(GetOperationArguments.class)
        public void getOperationByValidIdTest(Operation operation, OperationResponse response) {
            when(operationRepository.findById(ID)).thenReturn(of(operation));

            OperationResponse actual = operationService.getOperation(ID);
            assertThat(actual).isEqualTo(response);

            assertEquals(response, operationService.getOperation(ID));
            verify(operationRepository, times(2)).findById(ID);
        }

        @DisplayName("get by invalid ID")
        @Test
        public void getOperationByInvalidIdTest() {
            when(operationRepository.findById(ID)).thenReturn(empty());

            assertThatThrownBy(() -> operationService.getOperation(ID)).isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(format(ENTITY_NOT_FOUND_BY_ID, ID));

            verify(operationRepository).findById(ID);
        }

        @DisplayName("get all operations")
        @ParameterizedTest
        @ArgumentsSource(GetOperationArguments.class)
        public void getOperationsTest(Operation operation, OperationResponse response) {
            when(operationRepository.findAll()).thenReturn(List.of(operation));

            List<OperationResponse> actual = operationService.getOperations();
            assertThat(actual).isEqualTo(List.of(response));

            verify(operationRepository).findAll();
        }

        @DisplayName("get operations by date")
        @ParameterizedTest
        @ArgumentsSource(GetOperationArguments.class)
        public void getOperationsByDate(Operation operation, OperationResponse response) {
            LocalDate date = LocalDate.of(2023, 1, 1);

            when(operationRepository.getOperationsByTimeContaining(date)).thenReturn(List.of(operation));

            List<OperationResponse> actual = operationService.getOperations(date);
            assertThat(actual).isEqualTo(List.of(response));
            verify(operationRepository).getOperationsByTimeContaining(date);
        }
    }

    @Nested
    @DisplayName("with insufficient funds")
    public class InsufficientFuds {

        @DisplayName("create withdrawal operation")
        @ParameterizedTest
        @ArgumentsSource(SaveWithdrawalOperationInsufficientFundsArguments.class)
        public void createWithdrawalOperationInsufficientFundsTest(OperationRequest request, WalletResponse walletResponse) {
            when(walletService.getWallet(anyLong())).thenReturn(walletResponse);

            assertThatThrownBy(() -> operationService.createOperation(request)).isInstanceOf(NoFundsException.class)
                    .hasMessage(NO_FUNDS);

            verify(walletService).getWallet(anyLong());
        }

        @DisplayName("create transfer operation")
        @ParameterizedTest
        @ArgumentsSource(SaveTransferOperationInsufficientArguments.class)
        public void createTransferOperationInsufficientFundsTest(OperationRequest request, WalletResponse walletResponse) {
            when(walletService.getWallet(anyLong())).thenReturn(walletResponse);

            assertThatThrownBy(() -> operationService.createOperation(request)).isInstanceOf(NoFundsException.class)
                    .hasMessage(NO_FUNDS);

            verify(walletService, times(2)).getWallet(anyLong());
        }
    }

    @Nested
    @DisplayName("with different currencies")
    public class DifferentCurrencies {

        @DisplayName("create transfer operation different currencies")
        @ParameterizedTest
        @ArgumentsSource(SaveTransferDifferentCurrenciesArguments.class)
        public void createTransferOperationDifferentCurrenciesTest(
                OperationRequest request,
                Operation operation,
                OperationResponse response,
                WalletResponse walletResponseByn,
                WalletResponse walletResponseUsd,
                CurrencyResponse currencyResponse) {

            when(walletService.getWallet(1L)).thenReturn(walletResponseByn);
            when(walletService.getWallet(2L)).thenReturn(walletResponseUsd);
            when(currencyService.getCurrency(anyString())).thenReturn(currencyResponse);
            when(operationRepository.save(operation)).thenReturn(operation);

            OperationResponse actual = operationService.createOperation(request);
            assertThat(actual).isEqualTo(response);

            verify(walletService).getWallet(1L);
            verify(walletService).getWallet(2L);
            verify(operationRepository).save(operation);
        }
    }
}