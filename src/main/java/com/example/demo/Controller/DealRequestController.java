package com.example.demo.Controller;

import com.example.demo.Entity.DealRequest;
import com.example.demo.Exception.DatabaseAccessException;
import com.example.demo.Exception.DuplicateDealTransactionException;
import com.example.demo.Exception.InvalidDealTransactionException;
import com.example.demo.Service.DealRequestService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/deals")
public class DealRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealRequestController.class);

    @Autowired
    private DealRequestService dealRequestService;

    public DealRequestController(DealRequestService dealRequestService) {
        this.dealRequestService = dealRequestService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addDeal(@Valid @RequestBody DealRequest dealRequest) {
        try {
            // Check if the deal already exists in the database
            Optional<DealRequest> existingDeal = dealRequestService.addNewDealTransaction(dealRequest);
            LOGGER.info("Deal added successfully");
            return ResponseEntity.ok("Deal added successfully");
        } catch (InvalidDealTransactionException e) {
            LOGGER.error("Invalid deal transaction: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        } catch (DuplicateDealTransactionException e) {
            LOGGER.error("Duplicate deal transaction: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        } catch (DatabaseAccessException e) {
            LOGGER.error("Unable to add new deal transaction", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to add new deal transaction");
        }
    }
}
