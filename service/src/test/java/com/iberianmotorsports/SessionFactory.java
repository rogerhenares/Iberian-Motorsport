package com.iberianmotorsports;

import com.iberianmotorsports.service.model.Session;

public class SessionFactory {
    public static final Long id = 1L;
    public static final Integer hourOfDay = 1;
    public static final Integer dayOfWeekend = 1;
    public static final Integer timeMultiplier = 1;
    public static final String sessionType = "testSessionType";
    public static final Integer sessionDurationMinutes = 1;

    public static Session session() {
        Session session = new Session();
        session.setId(id);
        session.setHourOfDay(hourOfDay);
        session.setDayOfWeekend(dayOfWeekend);
        session.setTimeMultiplier(timeMultiplier);
        session.setSessionType(sessionType);
        session.setSessionDurationMinutes(sessionDurationMinutes);
        return session;
    }
}
