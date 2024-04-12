package com.farion.onlinebookstore.repository;

import static com.farion.onlinebookstore.entity.Role.RoleName;

import com.farion.onlinebookstore.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
