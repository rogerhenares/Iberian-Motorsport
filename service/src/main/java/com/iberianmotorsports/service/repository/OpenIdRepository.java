package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.model.UserOpenIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenIdRepository extends JpaRepository<UserOpenIds, String> {
    UserOpenIds findByUser(User user);

}
