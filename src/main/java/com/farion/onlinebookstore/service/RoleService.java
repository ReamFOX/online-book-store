package com.farion.onlinebookstore.service;

import com.farion.onlinebookstore.entity.Role;

public interface RoleService {
    Role findByName(Role.RoleName name);
}
