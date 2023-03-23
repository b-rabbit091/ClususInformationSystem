package com.example.demo.Controller;

import com.example.demo.Entity.DealRequest;
import com.example.demo.Exception.DatabaseAccessException;
import com.example.demo.Exception.DuplicateDealTransactionException;
import com.example.demo.Exception.InvalidDealTransactionException;
import com.example.demo.Repository.DealRepository;
import com.example.demo.Service.DealRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DealRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DealRequestService dealRequestService;

    @Autowired
    private DealRepository dealRepository;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new DealRequestController(dealRequestService)).build();
    }



    @Test
    void addDeal_ShouldReturnOkStatus_WhenDealIsAddedSuccessfully() throws Exception {
        DealRequest dealRequest = new DealRequest();
        when(dealRequestService.addNewDealTransaction(any(DealRequest.class))).thenReturn(java.util.Optional.of(dealRequest));

        mockMvc.perform(post("/deals/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"dealUniqueId\": 123, \"fromCurrencyIsoCode\": \"USD\", \"toCurrencyIsoCode\": \"JPY\", \"dealTimestamp\": \"2022-03-23T12:34:56\", \"dealAmount\": 100.00 }"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deal added successfully"));
    }


    @Test
    void addDeal_ShouldReturnBadRequestStatus_WhenDealIsInvalid() throws Exception {
        InvalidDealTransactionException exception = new InvalidDealTransactionException("All required fields must be present");

        doThrow(exception).when(dealRequestService).addNewDealTransaction(any(DealRequest.class));

        mockMvc.perform(post("/deals/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"dealUniqueId\": null, \"fromCurrencyIsoCode\": null, \"toCurrencyIsoCode\": null, \"dealTimestamp\": null, \"dealAmount\": null }"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(exception.getLocalizedMessage()));
    }

    @Test
    void addDeal_ShouldReturnBadRequestStatus_WhenDuplicateDealTransactionExists() throws Exception {


        DuplicateDealTransactionException exception = new DuplicateDealTransactionException("A deal with the same unique id already exists");

        doThrow(exception).when(dealRequestService).addNewDealTransaction(any(DealRequest.class));

        mockMvc.perform(post("/deals/add")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"dealUniqueId\": 123, \"fromCurrencyIsoCode\": \"USD\", \"toCurrencyIsoCode\": \"JPY\", \"dealTimestamp\": \"2022-03-23T12:34:56\", \"dealAmount\": 100.00 }"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(exception.getLocalizedMessage()));
    }


    @Test
    void addDeal_ShouldReturnInternalServerErrorStatus_WhenDatabaseAccessFails() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        DatabaseAccessException exception = new DatabaseAccessException("Unable to add new deal transaction");

        doThrow(exception).when(dealRequestService).addNewDealTransaction(any(DealRequest.class));

        mockMvc.perform(post("/deals/add")

                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new DealRequest())))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Unable to add new deal transaction"));
    }
}