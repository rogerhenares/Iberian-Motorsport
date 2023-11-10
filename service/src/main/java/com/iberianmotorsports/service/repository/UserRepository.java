package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findBySteamId(Long steamId);

    Optional<User> findByUserId(Long userId);

    Optional<User> findByFirstName(String name);

    void deleteBySteamId(Long steamId);
}
