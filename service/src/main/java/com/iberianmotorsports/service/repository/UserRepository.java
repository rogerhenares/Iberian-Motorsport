package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Page<User> findAllByUserId(long userId, Pageable pageable);

    Optional<User> findByPlayerId(long id);

}
