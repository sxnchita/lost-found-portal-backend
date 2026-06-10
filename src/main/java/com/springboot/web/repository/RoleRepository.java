package com.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.web.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
