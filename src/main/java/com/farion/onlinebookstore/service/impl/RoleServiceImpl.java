package com.farion.onlinebookstore.service.impl;

import static com.farion.onlinebookstore.entity.Role.RoleName;

import com.farion.onlinebookstore.entity.Role;
import com.farion.onlinebookstore.repository.RoleRepository;
import com.farion.onlinebookstore.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role findByName(RoleName name) {
        return roleRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException("Can`t find role by name " + name));
    }
}
