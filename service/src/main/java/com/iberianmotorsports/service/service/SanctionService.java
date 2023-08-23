package com.iberianmotorsports.service.service;

import com.google.protobuf.ServiceException;
import com.iberianmotorsports.service.controller.DTO.SanctionDTO;
import com.iberianmotorsports.service.model.Sanction;

import java.util.List;

public interface SanctionService {

    Sanction createSanction(SanctionDTO sanctionDTO);

    List<Sanction> getSanctionsByRace(Long raceId);

    void deleteSanction(Long sanctionId) throws ServiceException;
}
