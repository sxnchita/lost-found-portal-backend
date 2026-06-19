package com.springboot.web.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.web.entity.ClaimRequest;

public interface ClaimRequestRepository extends JpaRepository<ClaimRequest, Long> {

    Optional<ClaimRequest> findByItemMatch_MatchId(Long matchId);

}