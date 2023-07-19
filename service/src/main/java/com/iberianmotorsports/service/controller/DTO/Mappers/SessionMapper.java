package com.iberianmotorsports.service.controller.DTO.Mappers;

import com.iberianmotorsports.service.controller.DTO.SessionDTO;
import com.iberianmotorsports.service.model.Session;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SessionMapper implements Function<SessionDTO, Session> {
    @Override
    public Session apply(SessionDTO sessionDTO) {
        Session session = new Session();
        if (sessionDTO == null) return session;
        session.setId(sessionDTO.id());
        session.setHourOfDay(sessionDTO.hourOfDay());
        session.setDayOfWeekend(sessionDTO.dayOfWeekend());
        session.setTimeMultiplier(sessionDTO.timeMultiplier());
        session.setSessionType(sessionDTO.sessionType());
        session.setSessionDurationMinutes(sessionDTO.sessionDurationMinutes());
        return session;
    }
}
