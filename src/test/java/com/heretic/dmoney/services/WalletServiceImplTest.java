package com.heretic.dmoney.services;

import com.heretic.dmoney.dto.requests.WalletRequest;
import com.heretic.dmoney.dto.responses.PersonResponse;
import com.heretic.dmoney.dto.responses.WalletResponse;
import com.heretic.dmoney.entities.Person;
import com.heretic.dmoney.entities.Wallet;
import com.heretic.dmoney.extensions.WalletRequestParameterResolver;
import com.heretic.dmoney.mappers.PersonMapper;
import com.heretic.dmoney.mappers.WalletMapper;
import com.heretic.dmoney.mappers.WalletMapperImpl;
import com.heretic.dmoney.repositories.WalletRepository;
import com.heretic.dmoney.services.impl.WalletServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.math.BigDecimal.ONE;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("WalletService tests")
@ExtendWith({MockitoExtension.class, WalletRequestParameterResolver.class})
class WalletServiceImplTest {

    @InjectMocks
    private WalletServiceImpl walletService;
    @Mock
    private PersonService personService;
    @Mock
    private WalletMapper walletMapper;
    @Mock
    private PersonMapper personMapper;
    @Mock
    private WalletRepository walletRepository;
    private static Wallet wallet;
    private static WalletResponse walletResponse;
    private static UUID walletId;

    @BeforeAll
    public static void init(WalletRequest walletRequest) {
        walletId = randomUUID();
        WalletMapperImpl mapper = new WalletMapperImpl();
        wallet = mapper.mapToWallet(walletRequest);
        walletResponse = mapper.mapToWalletResponse(wallet);
    }

    @Test
    void saveWalletTest(WalletRequest walletRequest) {
        UUID personId = randomUUID();
        when(walletMapper.mapToWallet(walletRequest)).thenReturn(wallet);
        when(personService.getPerson(personId)).thenReturn(PersonResponse.builder().build());
        when(personMapper.mapToPerson(personService.getPerson(personId))).thenReturn(Person.builder().build());
        when(walletRepository.save(wallet)).thenReturn(wallet);
        when(walletMapper.mapToWalletResponse(wallet)).thenReturn(walletResponse);

        assertEquals(walletResponse, walletService.saveWallet(walletRequest, personId));
        verify(walletMapper, times(1)).mapToWallet(walletRequest);
        verify(walletMapper, times(1)).mapToWalletResponse(wallet);
        verify(personService, times(2)).getPerson(personId);
        verify(personMapper, times(1)).mapToPerson(personService.getPerson(personId));
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void getWalletByValidIdTest() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        when(walletMapper.mapToWalletResponse(wallet)).thenReturn(walletResponse);

        assertEquals(walletResponse, walletService.getWallet(walletId));
        verify(walletMapper, times(1)).mapToWalletResponse(wallet);
        verify(walletRepository, times(1)).findById(walletId);
    }

    @Test
    void getWalletByInvalidIdTest() {
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> walletService.getWallet(walletId));
        verify(walletRepository, times(1)).findById(walletId);
    }

    @Test
    void getWalletByValidWalletNumber() {
        when(walletRepository.findByWalletNumber(1L)).thenReturn(Optional.of(wallet));
        when(walletMapper.mapToWalletResponse(wallet)).thenReturn(walletResponse);

        assertEquals(walletResponse, walletService.getWallet(1L));
        verify(walletMapper, times(1)).mapToWalletResponse(wallet);
        verify(walletRepository, times(1)).findByWalletNumber(1L);
    }

    @Test
    void getWalletsTest() {
        when(walletRepository.findAll()).thenReturn(List.of(wallet));
        when(walletMapper.mapToWalletResponse(wallet)).thenReturn(walletResponse);

        assertEquals(List.of(walletResponse), walletService.getWallets());
        verify(walletMapper, times(1)).mapToWalletResponse(wallet);
        verify(walletRepository, times(1)).findAll();
    }

    @Test
    void updateWalletTest() {
        assertDoesNotThrow(() -> walletService.updateWallet(ONE, walletId));
    }

    @Test
    void deleteWallet() {
        assertTrue(walletService.deleteWallet(walletId));
    }
}