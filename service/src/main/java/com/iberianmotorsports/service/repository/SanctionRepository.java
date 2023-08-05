package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.Sanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionRepository extends JpaRepository<Sanction, Long> {

}
