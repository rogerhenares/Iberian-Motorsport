package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {

    Optional<Championship> findByName(String name);

    @Query("""
        SELECT c FROM Championship c
            JOIN c.gridList g
            JOIN g.drivers gu
        WHERE c.disabled = false AND gu = :driver
    """)
    Page<Championship> findByLoggedUser(@Param("driver") User loggedUser, Pageable pageable);

    @Query("""
           SELECT c FROM Championship c LEFT JOIN c.raceList r
           WHERE c.disabled = :disabled AND c.started = :started AND c.finished = :finished
              AND (c.finished = true OR c.disabled = true OR (c.finished = false AND r.startDate >= :now))
           GROUP BY c
           ORDER BY TIMESTAMPDIFF(SECOND, :now, MIN(r.startDate)) ASC
    """)
    Page<Championship> findByDisabledAndStartedAndFinished(Boolean disabled, Boolean started,
                                                           Boolean finished, LocalDateTime now,
                                                           Pageable pageable);
}
