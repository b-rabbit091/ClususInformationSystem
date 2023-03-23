package com.example.demo.Service;


import com.example.demo.Entity.DealRequest;
import com.example.demo.Repository.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface DealRequestService {


    Optional<DealRequest> addNewDealTransaction(DealRequest dealRequest);
}
