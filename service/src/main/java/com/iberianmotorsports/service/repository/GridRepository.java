package com.iberianmotorsports.service.repository;

import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GridRepository extends JpaRepository<Grid, Long> {

    List<Grid> findGridsByChampionshipId(Long championshipId);
    Optional<Grid> findGridByChampionshipIdAndDriversContains(Long championshipId, User driver);
    Optional<Grid> findGridByChampionshipIdAndCarNumber(Long championshipId, Integer carNumber);
    List<Grid> findGridsByDriversContainsAndDisabledIsFalse(User user);

    List<Grid> findGridsByChampionshipIdAndTeamName(Long championshipId, String teamName);
    Grid findGridByPassword(String password);
}
