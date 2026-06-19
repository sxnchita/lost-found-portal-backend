package com.springboot.web.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.web.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}