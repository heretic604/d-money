package com.heretic.dmoney.services;

import com.heretic.dmoney.dto.requests.OperationRequest;
import com.heretic.dmoney.dto.responses.OperationResponse;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Operation;
import com.heretic.dmoney.entities.Wallet;
import com.heretic.dmoney.enums.OperationStatus;
import com.heretic.dmoney.exceptions.NoFundsException;
import com.heretic.dmoney.arguments.operations.DepositOperationsSameCurrenciesArgumentProvider;
import com.heretic.dmoney.arguments.operations.TransferOperationsSameCurrenciesArgumentProvider;
import com.heretic.dmoney.arguments.operations.WalletDataForOperationsParameterResolver;
import com.heretic.dmoney.arguments.operations.WithdrawalOperationsSameCurrenciesArgumentProvider;
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

import java.util.List;
import java.util.Optional;

import static com.heretic.dmoney.util.Constants.DEFAULT_CURRENCY;
import static java.math.BigDecimal.*;
import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

//@RunWith(SpringRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@DisplayName("OperationService tests")
@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest
//@TestConfiguration
class OperationServiceImplTest {

    @InjectMocks
    private OperationServiceImpl operationService;
    @Mock
    private static WalletService walletService;
    @Autowired
    private OperationMapperImpl operationMapper;
    @Autowired
    private WalletMapperImpl walletMapper;
    @Mock
    private static OperationRepository operationRepository;
    @Mock
    private static CurrencyService currencyService;
    private static Wallet sender;
    private static Wallet receiver;

    @Nested
    @DisplayName("with same currency and sufficient funds")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @ExtendWith(WalletDataForOperationsParameterResolver.class)
//    @SpringBootTest
    public class SufficientFunds {
//        @InjectMocks
//        private static OperationServiceImpl operationService;
//        @Mock
//        private WalletService walletService;
//        @Autowired
//        private OperationMapper operationMapper;
//        @Autowired
//        private WalletMapperImpl walletMapper;
//        @Mock
//        private OperationRepository operationRepository;
//        @Mock
//        private CurrencyService currencyService;
        private Wallet sender;
        private Wallet receiver;
        private Operation operation1;

        @BeforeAll
        public void setUp(){

        }

        @BeforeEach
        public void init(Wallet walletParameter) {
            operationService = new OperationServiceImpl(walletService, currencyService, operationMapper, walletMapper, operationRepository);
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
            operation1 = operation;
            operation1.setReceiver(receiver);
            Operation enrichOperation = operation1;
            enrichOperation.setExRate(ONE);
            enrichOperation.setAmountIn(TEN);
            enrichOperation.setStatus(OperationStatus.SUCCEED);
//            operation1.setOperationId(UUID.randomUUID());
//            when(operationMapper.mapToOperation(request)).thenReturn(operation);
            when(walletService.getWallet(anyLong())).thenReturn(walletMapper.mapToWalletResponse(receiver));
//            when(walletMapper.mapToWallet(WalletResponse.builder().build())).thenReturn(operation.getReceiver());
            when(operationRepository.save(enrichOperation)).thenReturn(enrichOperation);
//            when(operationMapper.mapToOperationResponse(operation)).thenReturn(response);

            assertEquals(response, operationService.createOperation(request));
//            verify(operationMapper, times(1)).mapToOperation(request);
            verify(walletService, times(1)).getWallet(request.getReceiverNumber());
//            verify(walletMapper, times(1)).mapToWallet(WalletResponse.builder().build());
//            verify(operationRepository, times(1)).save(operation1);
//            verify(operationMapper, times(1)).mapToOperationResponse(operation);
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