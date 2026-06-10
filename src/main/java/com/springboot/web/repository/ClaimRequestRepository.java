package com.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.web.entity.ClaimRequest;

public interface ClaimRequestRepository extends JpaRepository<ClaimRequest, Long> {

}
