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
            LEFT JOIN c.raceList r
        WHERE c.disabled = false AND c.finished = false AND gu = :driver
        GROUP BY c
        ORDER BY TIMESTAMPDIFF(SECOND, :now, MIN(r.startDate)) ASC
    """)
    Page<Championship> findByLoggedUser(@Param("driver") User loggedUser,
                                        @Param("now") LocalDateTime now,
                                        Pageable pageable);

    @Query("""
           SELECT c FROM Championship c LEFT JOIN c.raceList r
           WHERE c.disabled = :disabled AND c.started = :started AND c.finished = :finished
           GROUP BY c
           ORDER BY TIMESTAMPDIFF(SECOND, :now, MIN(r.startDate)) ASC
    """)
    Page<Championship> findByDisabledAndStartedAndFinished(@Param("disabled") Boolean disabled,
                                                           @Param("started") Boolean started,
                                                           @Param("finished") Boolean finished,
                                                           @Param("now") LocalDateTime now,
                                                           Pageable pageable);
}
