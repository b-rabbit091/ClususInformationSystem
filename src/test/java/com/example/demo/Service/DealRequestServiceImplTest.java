package com.example.demo.Service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.Entity.DealRequest;
import com.example.demo.Exception.DatabaseAccessException;
import com.example.demo.Exception.DuplicateDealTransactionException;
import com.example.demo.Exception.InvalidDealTransactionException;
import com.example.demo.Repository.DealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DealRequestServiceImplTest {

    private DealRequestServiceImpl dealRequestService;

    @Mock
    private DealRepository dealRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        dealRequestService = new DealRequestServiceImpl(dealRepository);
    }

    @Test
    void addNewDealTransaction_ShouldThrowInvalidDealTransactionException_WhenRequiredFieldsAreMissing() {
        DealRequest dealRequest = new DealRequest();
        InvalidDealTransactionException exception = assertThrows(InvalidDealTransactionException.class,
                () -> dealRequestService.addNewDealTransaction(dealRequest));
        assertEquals("All required fields must be present", exception.getMessage());
    }

    @Test
    void addNewDealTransaction_ShouldThrowInvalidDealTransactionException_WhenCurrencyCodesAreNotThreeCharactersLong() {
        DealRequest dealRequest = new DealRequest();
        dealRequest.setDealUniqueId(1234L);
        dealRequest.setFromCurrencyIsoCode("USD");
        dealRequest.setToCurrencyIsoCode("EURO");
        dealRequest.setDealAmount(BigDecimal.ONE);
        dealRequest.setDealTimestamp(LocalDateTime.now());
        InvalidDealTransactionException exception = assertThrows(InvalidDealTransactionException.class,
                () -> dealRequestService.addNewDealTransaction(dealRequest));
        assertEquals("Currency codes must be 3 characters long", exception.getMessage());
    }

    @Test
    void addNewDealTransaction_ShouldThrowInvalidDealTransactionException_WhenDealAmountIsNotAPositiveDecimalNumber() {
        DealRequest dealRequest = new DealRequest();
        dealRequest.setDealUniqueId(1234L);
        dealRequest.setFromCurrencyIsoCode("USD");
        dealRequest.setToCurrencyIsoCode("EUR");
        dealRequest.setDealAmount(BigDecimal.valueOf(-1));
        dealRequest.setDealTimestamp(LocalDateTime.now());
        InvalidDealTransactionException exception = assertThrows(InvalidDealTransactionException.class,
                () -> dealRequestService.addNewDealTransaction(dealRequest));
        assertEquals("Deal amount must be a positive decimal number", exception.getMessage());
    }

    @Test
    void addNewDealTransaction_ShouldThrowDuplicateDealTransactionException_WhenDealUniqueIdAlreadyExists() {
        DealRequest dealRequest = new DealRequest();
        dealRequest.setDealUniqueId(1234L);
        dealRequest.setFromCurrencyIsoCode("USD");
        dealRequest.setToCurrencyIsoCode("EUR");
        dealRequest.setDealAmount(BigDecimal.ONE);
        dealRequest.setDealTimestamp(LocalDateTime.now());
        when(dealRepository.existsByDealUniqueId(1234L)).thenReturn(true);
        DuplicateDealTransactionException exception = assertThrows(DuplicateDealTransactionException.class,
                () -> dealRequestService.addNewDealTransaction(dealRequest));
        assertEquals("A deal with the same unique id already exists", exception.getMessage());
    }

    @Test
    void addNewDealTransaction() {
        DealRequest dealRequest = new DealRequest(1L, "USD", "GBP", LocalDateTime.now(), BigDecimal.TEN);
        Mockito.when(dealRepository.existsByDealUniqueId(Mockito.anyLong())).thenReturn(false);
        Mockito.when(dealRepository.save(Mockito.any(DealRequest.class))).thenReturn(dealRequest);
        Optional<DealRequest> savedDealRequest = dealRequestService.addNewDealTransaction(dealRequest);
        assertTrue(savedDealRequest.isPresent());
        assertEquals(dealRequest.getDealUniqueId(), savedDealRequest.get().getDealUniqueId());
        assertEquals(dealRequest.getFromCurrencyIsoCode(), savedDealRequest.get().getFromCurrencyIsoCode());
        assertEquals(dealRequest.getToCurrencyIsoCode(), savedDealRequest.get().getToCurrencyIsoCode());
        assertEquals(dealRequest.getDealTimestamp(), savedDealRequest.get().getDealTimestamp());
        assertEquals(dealRequest.getDealAmount(), savedDealRequest.get().getDealAmount());
    }

}
