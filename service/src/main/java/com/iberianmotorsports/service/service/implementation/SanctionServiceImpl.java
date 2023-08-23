package com.iberianmotorsports.service.service.implementation;

import com.google.protobuf.ServiceException;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.controller.DTO.Mappers.SanctionDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.SanctionMapper;
import com.iberianmotorsports.service.controller.DTO.SanctionDTO;
import com.iberianmotorsports.service.model.GridRace;
import com.iberianmotorsports.service.model.Sanction;
import com.iberianmotorsports.service.repository.SanctionRepository;
import com.iberianmotorsports.service.service.GridRaceService;
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

    SanctionMapper sanctionMapper;

    SanctionDTOMapper sanctionDTOMapper;


    @Override
    public Sanction createSanction(SanctionDTO sanctionDTO) {
        GridRace gridRace = gridRaceService.getGridRace(sanctionDTO.gridId(), sanctionDTO.raceId());;
        return sanctionRepository.save(sanctionMapper.apply(sanctionDTO));
    }

    @Override
    public List<Sanction> getSanctionsByRace(Long raceId) {
        return sanctionRepository.findSanctionsByGridRace_GridRacePrimaryKey_Race_Id(raceId);
    }

    @Override
    public void deleteSanction(Long sanctionId) throws ServiceException {
        Sanction sanction = sanctionRepository.findById(sanctionId)
                .orElseThrow(() -> new ServiceException(ErrorMessages.SANCTION_NOT_FOUND.getDescription()));
        sanctionRepository.delete(sanction);
        //gridRaceService.calculateGridRace(sanction.getGridRace().getRace().getId());
    }
}
