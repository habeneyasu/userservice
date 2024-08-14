package com.ecommerce.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.userservice.model.Role;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{

    /**
     * Get role by name
     */
    public Optional<Role> getByName(String name);
} 
