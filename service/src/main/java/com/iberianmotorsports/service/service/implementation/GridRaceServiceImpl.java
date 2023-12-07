package com.iberianmotorsports.service.service.implementation;

import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.Championship;
import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.GridRace;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.composeKey.GridRacePrimaryKey;
import com.iberianmotorsports.service.repository.GridRaceRepository;
import com.iberianmotorsports.service.service.ChampionshipService;
import com.iberianmotorsports.service.service.GridRaceService;
import com.iberianmotorsports.service.service.GridService;
import com.iberianmotorsports.service.service.RaceService;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Transactional
@Service
public class GridRaceServiceImpl implements GridRaceService {

    ChampionshipService championshipService;

    RaceService raceService;

    GridService gridService;

    GridRaceRepository gridRaceRepository;

    @Value("#{'${pointsSystem}'}")
    private List<Integer> pointsSystem;

    @Value("#{'${qualyPoints}'}")
    private List<Integer> qualyPoints;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GridRace saveGridRace(GridRace gridRace) {
        return gridRaceRepository.save(gridRace);
    }

    @Override
    public GridRace getGridRace(Long gridId, Long raceId) throws ServiceException {
        Race race = raceService.findRaceById(raceId);
        Grid grid = gridService.getGridById(gridId);
        GridRacePrimaryKey id = new GridRacePrimaryKey(grid, race);
        GridRace gridRace = gridRaceRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorMessages.GRID_RACE_NOT_FOUND.getDescription()));
        gridRace.getGridRacePrimaryKey().getGrid().getGridRaceList().size();
        gridRace.getGridRacePrimaryKey().getGrid().getGridRaceList().stream().map(gridRaceLazy ->{
            gridRaceLazy.getSanctionList().size();
            return gridRaceLazy;
        }).toList();
        return gridRace;
    }

    @Override
    public List<GridRace> getGridRaceForRace(Long raceId) {
        Race race = raceService.findRaceById(raceId);
        return gridRaceRepository.findGridRacesByGridRacePrimaryKey_Race(race);
    }

    @Override
    public void calculateGridRace(Long raceId) {

        List<GridRace> filteredList = getGridRaceForRace(raceId).stream()
                .filter(gridRace -> gridRace.getFinalTime() != null)
                .filter(gridRace -> gridRace.getFinalTime() > 0)
                .map(gridRace -> {
                    gridRace.setTimeWithPenalties(gridRace.getFinalTime() + (gridRace.getSanctionTime() * 1000));
                    return gridRace;
                })
                .sorted(Comparator.comparing(GridRace::getTotalLaps).reversed()
                        .thenComparing(GridRace::getTimeWithPenalties))
                .toList();

        for (GridRace gridRace : filteredList) {
            int position = filteredList.indexOf(gridRace);
            calculatePointsToGridRace(gridRace, position, filteredList.get(0));
            gridRaceRepository.save(gridRace);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void calculateDropRoundForGrid(Grid grid) {
        Grid gridToCheck = gridService.getGridById(grid.getId());
        if (gridToCheck.getGridRaceList().size() > 1) {
            gridToCheck.getGridRaceList().forEach(gridRace -> gridRace.setDropRound(false));
            gridToCheck.getGridRaceList().stream()
                    .min(Comparator.comparing(GridRace::getPoints))
                    .ifPresent(lowestGridRace -> lowestGridRace.setDropRound(true));
            gridToCheck.getGridRaceList().forEach(this::saveGridRace);
        }
    }

    @Override
    public void calculatePointsToGridRace(GridRace gridRace, int position, GridRace winner) {
        long points  = pointsSystem.get(position).longValue();
        if (!Objects.isNull(winner) && winner.getFinalTime() > gridRace.getFinalTime()) {
           points = 0;
        }
        points += gridRace.getFastLap() ? 1 : 0;
        if (gridRace.getQualyPosition() < qualyPoints.size()) {
            points += qualyPoints.get(gridRace.getQualyPosition()).longValue();
        }
        gridRace.setPoints(points);
    }

    @Override
    public void calculateDropRoundForRaceChampionship(Race race) {
        Championship championship = championshipService.findChampionshipById(race.getChampionship().getId());
        for (Grid grid : championship.getGridList()) {
            calculateDropRoundForGrid(grid);
        }
    }
}
