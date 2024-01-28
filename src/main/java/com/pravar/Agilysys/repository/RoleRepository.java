package com.pravar.Agilysys.repository;

import com.pravar.Agilysys.model.Role;
import com.pravar.Agilysys.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
