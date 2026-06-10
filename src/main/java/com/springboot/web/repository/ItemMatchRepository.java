package com.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.web.entity.ItemMatch;

public interface ItemMatchRepository extends JpaRepository<ItemMatch, Long> {

}
