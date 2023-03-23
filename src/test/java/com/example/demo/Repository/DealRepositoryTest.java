package com.example.demo.Repository;

import com.example.demo.Entity.DealRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DealRepositoryTest {

    @Autowired
    private DealRepository dealRepository;

    @Test
    public void testSaveDeal() {
        DealRequest dealRequest = new DealRequest(123L, "USD", "JPY", LocalDateTime.now(), BigDecimal.valueOf(100.00));
        dealRepository.save(dealRequest);

        DealRequest savedDealRequest = dealRepository.findById(123L).orElse(null);

        Assertions.assertNotNull(savedDealRequest);
        Assertions.assertEquals(dealRequest.getFromCurrencyIsoCode(), savedDealRequest.getFromCurrencyIsoCode());
        Assertions.assertEquals(dealRequest.getToCurrencyIsoCode(), savedDealRequest.getToCurrencyIsoCode());
        Assertions.assertEquals(dealRequest.getDealAmount(), savedDealRequest.getDealAmount());
    }

    @Test
    public void testExistsByDealUniqueId() {
        DealRequest dealRequest = new DealRequest(123L, "USD", "JPY", LocalDateTime.now(), BigDecimal.valueOf(100.00));
        dealRepository.save(dealRequest);

        boolean exists = dealRepository.existsByDealUniqueId(123L);

        Assertions.assertTrue(exists);
    }

}
