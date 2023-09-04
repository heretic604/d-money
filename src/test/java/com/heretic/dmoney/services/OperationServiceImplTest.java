package com.heretic.dmoney.services;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Operation;
import com.heretic.dmoney.entities.Wallet;
import com.heretic.dmoney.exceptions.NoFundsException;
import com.heretic.dmoney.extensions.operations.DepositOperationsSameCurrenciesArgumentProvider;
import com.heretic.dmoney.extensions.operations.TransferOperationsSameCurrenciesArgumentProvider;
import com.heretic.dmoney.extensions.operations.WalletDataForOperationsParameterResolver;
import com.heretic.dmoney.extensions.operations.WithdrawalOperationsSameCurrenciesArgumentProvider;
import com.heretic.dmoney.mappers.OperationMapper;
import com.heretic.dmoney.mappers.WalletMapper;
import com.heretic.dmoney.repositories.OperationRepository;
import com.heretic.dmoney.services.impl.OperationServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.heretic.dmoney.util.Constants.DEFAULT_CURRENCY;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("OperationService tests")
@ExtendWith(MockitoExtension.class)
class OperationServiceImplTest {

    @InjectMocks
    private OperationServiceImpl operationService;
    @Mock
    private WalletService walletService;
    @Mock
    private OperationMapper operationMapper;
    @Mock
    private WalletMapper walletMapper;
    @Mock
    private OperationRepository operationRepository;
    @Mock
    private CurrencyService currencyService;
    private static Wallet sender;
    private static Wallet receiver;

    @Nested
    @DisplayName("with same currency and sufficient funds")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @ExtendWith(WalletDataForOperationsParameterResolver.class)
    public class SufficientFunds {
        private Wallet sender;
        private Wallet receiver;

        @BeforeAll
        public void init(Wallet walletParameter) {
            sender = walletParameter;
            sender.setCurrency(DEFAULT_CURRENCY);
            sender.setAmount(valueOf(100L));
            receiver = walletParameter;
            receiver.setCurrency(DEFAULT_CURRENCY);
            receiver.setAmount(valueOf(100L));
        }

        @DisplayName("create deposit operation")
        @ParameterizedTest
        @ArgumentsSource(DepositOperationsSameCurrenciesArgumentProvider.class)
        public void createDepositOperationTest(OperationRequest request, Operation operation, OperationResponse response) {
            operation.setReceiver(receiver);
            when(operationMapper.mapToOperation(request)).thenReturn(operation);
            when(walletService.getWallet(request.getReceiverNumber())).thenReturn(WalletResponse.builder().build());
            when(walletMapper.mapToWallet(WalletResponse.builder().build())).thenReturn(operation.getReceiver());
            when(operationRepository.save(operation)).thenReturn(operation);
            when(operationMapper.mapToOperationResponse(operation)).thenReturn(response);

            assertEquals(response, operationService.createOperation(request));
            verify(operationMapper, times(1)).mapToOperation(request);
            verify(walletService, times(1)).getWallet(request.getReceiverNumber());
            verify(walletMapper, times(1)).mapToWallet(WalletResponse.builder().build());
            verify(operationRepository, times(1)).save(operation);
            verify(operationMapper, times(1)).mapToOperationResponse(operation);
        }

        @DisplayName("create withdrawal operation")
        @ParameterizedTest
        @ArgumentsSource(WithdrawalOperationsSameCurrenciesArgumentProvider.class)
        public void createWithdrawalOperationTest(OperationRequest request, Operation operation, OperationResponse response) {
            operation.setSender(sender);
            when(operationMapper.mapToOperation(request)).thenReturn(operation);
            when(walletService.getWallet(request.getSenderNumber())).thenReturn(WalletResponse.builder().build());
            when(walletMapper.mapToWallet(WalletResponse.builder().build())).thenReturn(operation.getSender());
            when(operationRepository.save(operation)).thenReturn(operation);
            when(operationMapper.mapToOperationResponse(operation)).thenReturn(response);

            assertEquals(response, operationService.createOperation(request));
            verify(operationMapper, times(1)).mapToOperation(request);
            verify(walletService, times(1)).getWallet(request.getSenderNumber());
            verify(walletMapper, times(1)).mapToWallet(WalletResponse.builder().build());
            verify(operationRepository, times(1)).save(operation);
            verify(operationMapper, times(1)).mapToOperationResponse(operation);
        }

        @DisplayName("create transfer operation")
        @ParameterizedTest
        @ArgumentsSource(TransferOperationsSameCurrenciesArgumentProvider.class)
        public void createTransferOperationTest(OperationRequest request, Operation operation, OperationResponse response) {
            operation.setSender(sender);
            operation.setReceiver(receiver);
            when(operationMapper.mapToOperation(request)).thenReturn(operation);
            when(walletService.getWallet(request.getSenderNumber())).thenReturn(WalletResponse.builder().build());
            when(walletMapper.mapToWallet(WalletResponse.builder().build())).thenReturn(operation.getSender());
            when(operationRepository.save(operation)).thenReturn(operation);
            when(operationMapper.mapToOperationResponse(operation)).thenReturn(response);

            assertEquals(response, operationService.createOperation(request));
            verify(operationMapper, times(1)).mapToOperation(request);
            verify(walletService, times(2)).getWallet(request.getSenderNumber());
            verify(walletMapper, times(2)).mapToWallet(WalletResponse.builder().build());
            verify(operationRepository, times(1)).save(operation);
            verify(operationMapper, times(1)).mapToOperationResponse(operation);
        }

        @DisplayName("get by valid ID")
        @ParameterizedTest
        @ArgumentsSource(DepositOperationsSameCurrenciesArgumentProvider.class)
        public void getOperationByValidIdTest(OperationRequest request, Operation operation, OperationResponse response) {
            when(operationRepository.findById(operation.getOperationId())).thenReturn(Optional.of(operation));
            when(operationMapper.mapToOperationResponse(operation)).thenReturn(response);

            assertEquals(response, operationService.getOperation(operation.getOperationId()));
            verify(operationRepository, times(1)).findById(operation.getOperationId());
            verify(operationMapper, times(1)).mapToOperationResponse(operation);
        }

        @DisplayName("get by invalid ID")
        @ParameterizedTest
        @ArgumentsSource(DepositOperationsSameCurrenciesArgumentProvider.class)
        public void getOperationByInvalidIdTest(OperationRequest request, Operation operation, OperationResponse response) {
            when(operationRepository.findById(operation.getOperationId())).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> operationService.getOperation(operation.getOperationId()));
            verify(operationRepository, times(1)).findById(operation.getOperationId());
        }

        @DisplayName("get all operations")
        @ParameterizedTest
        @ArgumentsSource(DepositOperationsSameCurrenciesArgumentProvider.class)
        public void getOperationsTest(OperationRequest request, Operation operation, OperationResponse response) {
            when(operationRepository.findAll()).thenReturn(List.of(operation));
            when(operationMapper.mapToOperationResponse(operation)).thenReturn(response);

            assertEquals(List.of(response), operationService.getOperations());
            verify(operationRepository, times(1)).findAll();
            verify(operationMapper, times(1)).mapToOperationResponse(operation);
        }

        @DisplayName("get operations by date")
        @ParameterizedTest
        @ArgumentsSource(DepositOperationsSameCurrenciesArgumentProvider.class)
        public void getOperationsByDate(OperationRequest request, Operation operation, OperationResponse response) {
            when(operationRepository.getOperationsByTimeContaining(now())).thenReturn(List.of(operation));
            when(operationMapper.mapToOperationResponse(operation)).thenReturn(response);

            assertEquals(List.of(response), operationService.getOperations(now()));
            verify(operationRepository, times(1)).getOperationsByTimeContaining(now());
            verify(operationMapper, times(1)).mapToOperationResponse(operation);
        }
    }

    @Nested
    @DisplayName("with insufficient funds")
    public class InsufficientFuds {

        @DisplayName("create withdrawal operation")
        @ParameterizedTest
        @ArgumentsSource(WithdrawalOperationsSameCurrenciesArgumentProvider.class)
        public void createWithdrawalOperationTest(OperationRequest request, Operation operation, OperationResponse response) {
            when(operationMapper.mapToOperation(request)).thenReturn(operation);
            when(walletService.getWallet(request.getSenderNumber())).thenReturn(WalletResponse.builder().build());
            when(walletMapper.mapToWallet(WalletResponse.builder().build())).thenReturn(operation.getSender());

            assertThrows(NoFundsException.class, () -> operationService.createOperation(request));
            verify(operationMapper, times(1)).mapToOperation(request);
            verify(walletService, times(1)).getWallet(request.getSenderNumber());
            verify(walletMapper, times(1)).mapToWallet(WalletResponse.builder().build());
        }

        @DisplayName("create transfer operation")
        @ParameterizedTest
        @ArgumentsSource(TransferOperationsSameCurrenciesArgumentProvider.class)
        public void createTransferOperationTest(OperationRequest request, Operation operation, OperationResponse response) {
            when(operationMapper.mapToOperation(request)).thenReturn(operation);
            when(walletService.getWallet(request.getSenderNumber())).thenReturn(WalletResponse.builder().build());
            when(walletMapper.mapToWallet(WalletResponse.builder().build())).thenReturn(operation.getSender());

            assertThrows(NoFundsException.class, () -> operationService.createOperation(request));
            verify(operationMapper, times(1)).mapToOperation(request);
            verify(walletService, times(2)).getWallet(request.getSenderNumber());
            verify(walletMapper, times(2)).mapToWallet(WalletResponse.builder().build());
        }
    }

    @Nested
    @DisplayName("with different currencies")
    public class DifferentCurrencies {

    }
}