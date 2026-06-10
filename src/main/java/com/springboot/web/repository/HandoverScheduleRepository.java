package com.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.web.entity.HandoverSchedule;

public interface HandoverScheduleRepository extends JpaRepository<HandoverSchedule, Long> {

}
