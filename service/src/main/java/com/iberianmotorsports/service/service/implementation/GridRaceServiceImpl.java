package com.iberianmotorsports.service.service.implementation;

import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.GridRace;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.composeKey.GridRacePrimaryKey;
import com.iberianmotorsports.service.repository.GridRaceRepository;
import com.iberianmotorsports.service.service.GridRaceService;
import com.iberianmotorsports.service.service.GridService;
import com.iberianmotorsports.service.service.RaceService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
@Service
public class GridRaceServiceImpl implements GridRaceService {

    //TODO generate grid race using .log file from acc server :D happy flowers (Dúchate hippy)

    RaceService raceService;

    GridService gridService;

    GridRaceRepository gridRaceRepository;

    @Value("${pointsSystem}")
    private List<Integer> pointsSystem;

    @Override
    public GridRace saveGridRace(GridRace gridRace){
        return gridRaceRepository.save(gridRace);
    }

    @Override
    public GridRace getGridRace(Long gridId, Long raceId) throws ServiceException {
        Race race = raceService.findRaceById(raceId);
        Grid grid = gridService.getGridById(gridId);
        GridRacePrimaryKey id = new GridRacePrimaryKey(grid, race);
        return gridRaceRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorMessages.GRID_RACE_NOT_FOUND.getDescription()));
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
                .sorted(Comparator.comparing(gridRace -> gridRace.getFinalTime() + gridRace.getSanctionTime()))
                .toList();

        for (GridRace gridRace : filteredList) {
            gridRace.setPoints(pointsSystem.get(filteredList.indexOf(gridRace)).longValue());
            gridRaceRepository.save(gridRace);
        }
    }
}
