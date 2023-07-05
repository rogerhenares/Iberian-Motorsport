package com.iberianmotorsports.service.features.session;

import com.iberianmotorsports.RaceFactory;
import com.iberianmotorsports.SessionFactory;
import com.iberianmotorsports.service.ErrorMessages;
import com.iberianmotorsports.service.controller.DTO.Mappers.SessionDTOMapper;
import com.iberianmotorsports.service.controller.DTO.Mappers.SessionMapper;
import com.iberianmotorsports.service.model.Race;
import com.iberianmotorsports.service.model.Session;
import com.iberianmotorsports.service.repository.SessionRepository;
import com.iberianmotorsports.service.service.SessionService;
import com.iberianmotorsports.service.service.implementation.SessionServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    private SessionService service;

    private SessionMapper sessionMapper;

    private SessionDTOMapper sessionDTOMapper;

    @Mock
    private SessionRepository sessionRepository;

    @Captor
    ArgumentCaptor<Session> sessionCaptor;

    @BeforeEach
    public void init() {
        sessionMapper = new SessionMapper();
        sessionDTOMapper = new SessionDTOMapper();
        service = new SessionServiceImpl(sessionRepository, sessionMapper);
    }

    @Nested
    class saveSession {

        @Test
        public void saveSession() {
            Session testSession = SessionFactory.session();
            Race testRace = RaceFactory.race();
            givenSessionRepositorySave();

            service.saveSession(sessionDTOMapper.apply(testSession), testRace);

            verify(sessionRepository).save(sessionCaptor.capture());
            assertEquals(SessionFactory.session(), sessionCaptor.getValue());
        }

        @Test
        public void saveDuplicateSession() {
            Session testSession = SessionFactory.session();
            Race testRace = RaceFactory.race();
            givenSessionAlreadyExists();

            RuntimeException exception = assertThrows(ServiceException.class,
                    ()-> service.saveSession(sessionDTOMapper.apply(testSession), testRace));

            verify(sessionRepository, times(0)).save(any());
            Assertions.assertEquals(ErrorMessages.DUPLICATED_SESSION.getDescription(), exception.getMessage());
        }

    }

    @Nested
    class updateSession {

        @Test
        public void updateSession() {
            Session testSession = SessionFactory.session();
            givenSessionRepositorySave();

            service.updateSession(testSession);

            verify(sessionRepository).save(sessionCaptor.capture());
            assertEquals(SessionFactory.session(), sessionCaptor.getValue());

        }
    }


    @Nested
    class deleteSession {

        @Test
        public void deleteSession() {

            service.deleteSession(anyLong());

            verify(sessionRepository).deleteById(anyLong());
        }
    }

    @Nested
    class findSession {

        @Test
        public void findSessionById() {
            when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(SessionFactory.session()));

            sessionRepository.findById(anyLong());

            verify(sessionRepository).findById(anyLong());
        }

        @Test
        public void findSessionInvalidId() {
            when(sessionRepository.findById(anyLong())).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(ServiceException.class,
                    ()-> service.findSessionById(anyLong()));

            verify(sessionRepository).findById(anyLong());
            Assertions.assertEquals(ErrorMessages.SESSION_NOT_IN_DB.getDescription(), exception.getMessage());
        }

    }

    @Test
    @Disabled
    public void exportSession() throws IOException {
        Session testSession = SessionFactory.session();

        String result = service.exportSession(testSession);

        assertTrue(result.startsWith("Session saved to"));

        Path filePath = Paths.get(result.substring("Session saved to ".length()));
    }


    private void givenSessionRepositorySave() {
        when(sessionRepository.save(any(Session.class))).then((Answer<Session>) invocation -> {
            Session session = invocation.getArgument(0);
            session.setId(1L);
            return session;
        });
    }

    private void givenSessionAlreadyExists() {
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(SessionFactory.session()));
    }

}
