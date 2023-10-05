package com.iberianmotorsports.service.service.implementation;

import com.google.protobuf.ServiceException;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.controller.DTO.Mappers.SanctionDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.SanctionMapper;
import com.iberianmotorsports.service.controller.DTO.SanctionDTO;
import com.iberianmotorsports.service.model.Sanction;
import com.iberianmotorsports.service.repository.SanctionRepository;
import com.iberianmotorsports.service.service.GridRaceService;
import com.iberianmotorsports.service.service.GridService;
import com.iberianmotorsports.service.service.RaceService;
import com.iberianmotorsports.service.service.SanctionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Transactional
@Service
public class SanctionServiceImpl implements SanctionService {

    SanctionRepository sanctionRepository;

    GridRaceService gridRaceService;

    GridService gridService;

    RaceService raceService;

    SanctionMapper sanctionMapper;

    SanctionDTOMapper sanctionDTOMapper;


    @Override
    public Sanction createSanction(SanctionDTO sanctionDTO) {
        Sanction sanction = sanctionMapper.apply(sanctionDTO);
        //TODO should validate if race and grid exists and are valid
        Sanction sanctionSaved = sanctionRepository.save(sanction);
        gridService.updateGridLicensePoints(sanction.getGridRace().getGridRacePrimaryKey().getGrid().getId(),
                sanction.getLicensePoints(), Boolean.TRUE);
        sanctionSaved.setGridRace(gridRaceService.getGridRace(sanction.getGridRace().getGridRacePrimaryKey().getGrid().getId(), sanction.getGridRace().getGridRacePrimaryKey().getRace().getId()));
        sanctionSaved.getGridRace().setSanctionTime(sanction.getGridRace().getSanctionTime() + Integer.parseInt(sanction.getPenalty()));
        sanctionSaved.getGridRace().setFinalTime(sanction.getGridRace().getFinalTime() + sanction.getGridRace().getSanctionTime());
        gridRaceService.calculateGridRace(sanction.getGridRace().getGridRacePrimaryKey().getRace().getId());
        return sanctionSaved;
    }

    @Override
    public List<Sanction> getSanctionsByRace(Long raceId) {
        return sanctionRepository.findSanctionsByGridRace_GridRacePrimaryKey_Race_Id(raceId);
    }

    @Override
    public void deleteSanction(Long sanctionId) throws ServiceException{
        Sanction sanction = getSanctionById(sanctionId);
        sanctionRepository.delete(sanction);
        gridService.updateGridLicensePoints(sanction.getGridRace().getGridRacePrimaryKey().getGrid().getId(),
                sanction.getLicensePoints(), Boolean.FALSE);
        sanction.getGridRace().setSanctionTime(sanction.getGridRace().getSanctionTime() - Integer.parseInt(sanction.getPenalty()));
        sanction.getGridRace().setFinalTime(sanction.getGridRace().getFinalTime() - sanction.getGridRace().getSanctionTime());
        gridRaceService.calculateGridRace(sanction.getGridRace().getGridRacePrimaryKey().getRace().getId());
    }

    private Sanction getSanctionById(Long sanctionId) throws ServiceException {
        return sanctionRepository.findById(sanctionId)
                .orElseThrow(() -> new ServiceException(ErrorMessages.SANCTION_NOT_FOUND.getDescription()));
    }

}