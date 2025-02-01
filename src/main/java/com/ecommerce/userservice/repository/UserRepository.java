package com.ecommerce.userservice.repository;

import com.ecommerce.userservice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<AppUser, Long>{

    public Optional<AppUser> findByUsername(String username);
}
