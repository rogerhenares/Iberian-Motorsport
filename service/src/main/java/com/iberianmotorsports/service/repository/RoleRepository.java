package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByRole(String role);
}
