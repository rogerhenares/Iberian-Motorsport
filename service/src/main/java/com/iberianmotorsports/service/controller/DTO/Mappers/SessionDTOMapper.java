package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.SessionDTO;
import com.iberianmotorsports.service.model.Session;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SessionDTOMapper implements Function<Session, SessionDTO> {
    @Override
    public SessionDTO apply(Session session) {
        if (session == null) return null;
        return new SessionDTO(
                session.getId(),
                session.getHourOfDay(),
                session.getDayOfWeekend(),
                session.getTimeMultiplier(),
                session.getSessionType(),
                session.getSessionDurationMinutes(),
                session.getRace().getId()
        );
    }
}
