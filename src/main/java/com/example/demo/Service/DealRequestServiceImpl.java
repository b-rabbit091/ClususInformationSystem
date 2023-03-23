package com.example.demo.Service;

import com.example.demo.Entity.DealRequest;
import com.example.demo.Exception.DatabaseAccessException;
import com.example.demo.Exception.DuplicateDealTransactionException;
import com.example.demo.Exception.InvalidDealTransactionException;
import com.example.demo.Repository.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;
import java.time.LocalDateTime;


@Service
public class DealRequestServiceImpl implements DealRequestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealRequestServiceImpl.class);

    @Autowired
    DealRepository dealRepository;

    @Autowired
    public DealRequestServiceImpl(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    /*
    1 - All required fields are present
    2 - Currency codes are 3 characters long
    3 - Deal amount is a positive decimal number
    4 - No deal with the same unique id already exists in the database

    If any of these validations fail, an appropriate exception is thrown.
    */

    @Override
    public Optional<DealRequest> addNewDealTransaction(DealRequest dealRequest) {
        try {
            validateRequiredFields(dealRequest);
            validateCurrencyCodes(dealRequest);
            validateDealAmount(dealRequest);
            validateUniqueDealId(dealRequest);
            validateDealTimestamp(dealRequest);

            DealRequest savedDealRequest = dealRepository.save(dealRequest);
            LOGGER.info("New deal added: {}", savedDealRequest);
            return Optional.of(savedDealRequest);
        } catch (InvalidDealTransactionException | DuplicateDealTransactionException e) {
            LOGGER.error("Error adding new deal transaction: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error adding new deal transaction", e);
            throw new DatabaseAccessException("Unable to add new deal transaction", e);
        }
    }


    private void validateRequiredFields(DealRequest dealRequest) {
        if (dealRequest.getDealUniqueId() == null || dealRequest.getFromCurrencyIsoCode() == null
                || dealRequest.getToCurrencyIsoCode() == null || dealRequest.getDealTimestamp() == null
                || dealRequest.getDealAmount() == null) {
            throw new InvalidDealTransactionException("All required fields must be present");
        }
    }

    private void validateCurrencyCodes(DealRequest dealRequest) {
        if (dealRequest.getFromCurrencyIsoCode().length() != 3 || dealRequest.getToCurrencyIsoCode().length() != 3) {
            throw new InvalidDealTransactionException("Currency codes must be 3 characters long");
        }
    }

    private void validateDealAmount(DealRequest dealRequest) {
        if (dealRequest.getDealAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidDealTransactionException("Deal amount must be a positive decimal number");
        }
    }

    private void validateUniqueDealId(DealRequest dealRequest) {
        if (dealRepository.existsByDealUniqueId(dealRequest.getDealUniqueId())) {
            throw new DuplicateDealTransactionException("A deal with the same unique id already exists");
        }
    }

    private void validateDealTimestamp(DealRequest dealRequest) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime dealDateTime = dealRequest.getDealTimestamp();
        if (dealDateTime.isAfter(currentDateTime)) {
            throw new InvalidDealTransactionException("Deal timestamp must not be in the future");
        }
    }

}
