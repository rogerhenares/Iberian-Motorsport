package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

    Optional<UserAuth> findUserAuthByToken(String token);
}
