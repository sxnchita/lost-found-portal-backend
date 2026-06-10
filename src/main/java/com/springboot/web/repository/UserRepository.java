package com.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.web.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
