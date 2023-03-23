package com.example.demo.Repository;


import com.example.demo.Entity.DealRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DealRepository extends JpaRepository<DealRequest, Long> {

    boolean existsByDealUniqueId(Long dealUniqueId);
}
