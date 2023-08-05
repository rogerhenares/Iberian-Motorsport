package com.iberianmotorsports.service.service;

import com.google.protobuf.ServiceException;
import com.iberianmotorsports.service.controller.DTO.SanctionDTO;
import com.iberianmotorsports.service.model.Sanction;

public interface SanctionService {

    Sanction createSanction(SanctionDTO sanctionDTO);

    void deleteSanction(Long sanctionId) throws ServiceException;
}
