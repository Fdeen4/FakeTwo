package com.example.demo.model.auth;

import com.example.demo.model.auth.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    UserRole findByRole(String role);
}
