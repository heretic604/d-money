package com.heretic.dmoney.services;

import com.heretic.dmoney.arguments.wallets.WalletGetArguments;
import com.heretic.dmoney.arguments.wallets.WalletSaveArguments;
import com.heretic.dmoney.dto.requests.WalletRequest;
import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Wallet;
import com.heretic.dmoney.mappers.PersonMapper;
import com.heretic.dmoney.mappers.WalletMapper;
import com.heretic.dmoney.repositories.WalletRepository;
import com.heretic.dmoney.services.impl.WalletServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import java.util.UUID;

import static com.heretic.dmoney.util.Constants.ENTITY_NOT_FOUND_BY_ID;
import static java.lang.String.format;
import static java.math.BigDecimal.ONE;
import static java.util.Optional.empty;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("WalletService tests")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(PER_CLASS)
class WalletServiceImplTest {

    @InjectMocks
    private WalletServiceImpl walletService;
    @Autowired
    private WalletMapper walletMapper;
    @Autowired
    private PersonMapper personMapper;
    @Mock
    private PersonService personService;
    @Mock
    private WalletRepository walletRepository;
    private static final UUID WALLET_ID = randomUUID();
    private static final UUID PERSON_ID = randomUUID();


    @BeforeEach
    public void init() {
        walletService = new WalletServiceImpl(personService, walletMapper, personMapper, walletRepository);
    }

    @DisplayName("save wallet")
    @ParameterizedTest()
    @ArgumentsSource(WalletSaveArguments.class)
    void saveWalletTest(WalletRequest request, Wallet wallet, WalletResponse walletResponse, PersonResponse personResponse) {
        when(personService.getPerson(PERSON_ID)).thenReturn(personResponse);
        when(walletRepository.save(wallet)).thenReturn(wallet);

        WalletResponse actual = walletService.saveWallet(request, PERSON_ID);
        assertThat(actual).isEqualTo(walletResponse);

        verify(personService).getPerson(PERSON_ID);
        verify(walletRepository).save(wallet);
    }

    @DisplayName("get wallet by valid id")
    @ParameterizedTest()
    @ArgumentsSource(WalletGetArguments.class)
    void getWalletByValidIdTest(Wallet wallet, WalletResponse response) {
        when(walletRepository.findById(WALLET_ID)).thenReturn(Optional.of(wallet));

        WalletResponse actual = walletService.getWallet(WALLET_ID);
        assertThat(actual).isEqualTo(response);

        verify(walletRepository).findById(WALLET_ID);
    }

    @DisplayName("get wallet by invalid id")
    @Test
    void getWalletByInvalidIdTest() {
        when(walletRepository.findById(WALLET_ID)).thenReturn(empty());

        assertThatThrownBy(() -> walletService.getWallet(WALLET_ID)).isInstanceOf(EntityNotFoundException.class)
                .hasMessage(format(ENTITY_NOT_FOUND_BY_ID, WALLET_ID));

        verify(walletRepository).findById(WALLET_ID);
    }

    @DisplayName("get wallet by valid id")
    @ParameterizedTest()
    @ArgumentsSource(WalletGetArguments.class)
    void getWalletByValidWalletNumber(Wallet wallet, WalletResponse response) {
        when(walletRepository.findByWalletNumber(anyLong())).thenReturn(Optional.of(wallet));

        WalletResponse actual = walletService.getWallet(anyLong());
        assertThat(actual).isEqualTo(response);

        verify(walletRepository).findByWalletNumber(anyLong());
    }

    @DisplayName("get all wallets")
    @ParameterizedTest()
    @ArgumentsSource(WalletGetArguments.class)
    void getWalletsTest(Wallet wallet, WalletResponse response) {
        when(walletRepository.findAll()).thenReturn(List.of(wallet));

        List<WalletResponse> actual = walletService.getWallets();
        assertThat(actual).isEqualTo(List.of(response));

        verify(walletRepository).findAll();
    }

    @DisplayName("update wallet")
    @Test
    void updateWalletTest() {
        assertDoesNotThrow(() -> walletService.updateWallet(ONE, WALLET_ID));
    }

    @DisplayName("delete wallet")
    @Test
    void deleteWallet() {
        assertThat(walletService.deleteWallet(WALLET_ID)).isTrue();
    }
}