package com.studentaccount.services;

import com.studentaccount.domain.entities.UserRole;

public interface RoleService {
    boolean persist(UserRole role) throws Exception;

    UserRole getByName(String name);
}
