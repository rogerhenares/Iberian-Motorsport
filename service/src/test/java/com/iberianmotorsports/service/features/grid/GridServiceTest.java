package com.iberianmotorsports.service.features.grid;

import com.iberianmotorsports.GridFactory;
import com.iberianmotorsports.UserFactory;
import com.iberianmotorsports.service.controller.DTO.Mappers.*;
import com.iberianmotorsports.service.model.Grid;
import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.model.parsing.properties.SteamClientProperties;
import com.iberianmotorsports.service.repository.*;
import com.iberianmotorsports.service.service.CarService;
import com.iberianmotorsports.service.service.ChampionshipService;
import com.iberianmotorsports.service.service.GridService;
import com.iberianmotorsports.service.service.UserService;
import com.iberianmotorsports.service.service.implementation.GridServiceImpl;
import com.iberianmotorsports.service.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GridServiceTest {

    private GridService gridService;
    private ChampionshipService championshipService;
    private UserService userService;
    private CarService carService;
    @Mock
    private GridRepository gridRepository;
    @Mock
    private GridUserRepository gridUserRepository;
    @Mock
    private ChampionshipRepository championshipRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private SanctionRepository sanctionRepository;
    @Mock
    private SteamClientProperties steamClientProperties;
    private CarMapper carMapper;
    private CarDTOMapper carDTOMapper;
    private UserMapper userMapper;
    private UserDTOMapper userDTOMapper;
    private ChampionshipMapper championshipMapper;
    private ChampionshipDTOMapper championshipDTOMapper;
    private GridMapper gridMapper;
    private GridDTOMapper gridDTOMapper;
    @Captor
    ArgumentCaptor<Grid> gridCaptor;
    private RestTemplate restTemplate;
    private Environment environment;


    @BeforeEach
    public void init() {
        carMapper = new CarMapper();
        carDTOMapper = new CarDTOMapper();
        championshipMapper = new ChampionshipMapper();
        championshipDTOMapper = new ChampionshipDTOMapper();
        userMapper = new UserMapper();
        userDTOMapper = new UserDTOMapper();
        //gridMapper = new GridMapper(carMapper, championshipMapper, userMapper);
        //gridDTOMapper = new GridDTOMapper(championshipDTOMapper, userDTOMapper, carDTOMapper);
        userService = new UserServiceImpl(userRepository, roleRepository, restTemplate, steamClientProperties);
        //championshipService = new ChampionshipServiceImpl(championshipRepository, championshipMapper);
        gridService = new GridServiceImpl(championshipService, userService, carService, gridRepository, gridUserRepository, gridMapper, gridDTOMapper, sanctionRepository);
    }


    @Nested
    public class saveGrid {

        @Test
        public void createGridEntry() {
            Grid testGrid = GridFactory.grid();

            givenGridRepositorySave();

            gridService.createGridEntry(gridDTOMapper.apply(testGrid));

            verify(gridRepository).save(gridCaptor.capture());
            assertEquals(GridFactory.grid(), gridCaptor.getValue());
        }

    }

    @Nested
    public class drivers {

        @Test
        public void addDriver() {
            User user = UserFactory.user();
            Grid grid = GridFactory.grid();
            String password = "";
            givenGridRepositorySave();
            givenUserRepositorySave();
            when(gridRepository.findById(grid.getId())).thenReturn(Optional.of(GridFactory.grid()));
            when(userRepository.findBySteamId(anyLong())).thenReturn(Optional.of(UserFactory.user()));

            gridService.addDriver(grid.getId(), user.getSteamId(), password);
            verify(gridRepository).save(gridCaptor.capture());
        }
    }

    @Nested
    public class findGrid {

        @Test
        public void getGridForChampionship() {
            when(gridRepository.findGridsByChampionshipId(anyLong())).thenReturn(GridFactory.gridList(5));

            gridRepository.findGridsByChampionshipId(anyLong());

            verify(gridRepository).findGridsByChampionshipId(anyLong());
        }

        @Test
        public void getGridById() {
            when(gridRepository.findById(anyLong())).thenReturn(Optional.of(GridFactory.grid()));

            gridRepository.findById(anyLong());

            verify(gridRepository).findById(anyLong());
        }

    }

    private void givenGridRepositorySave() {
        when(gridRepository.save(any(Grid.class))).then((Answer<Grid>) invocation -> {
            Grid grid = invocation.getArgument(0);
            grid.setId(1L);
            return grid;
        });
    }

    private void givenUserRepositorySave() {
        when(userRepository.save(any(User.class))).then((Answer<User>) invocation -> {
            User user = UserFactory.user();
            user.setUserId(1L);
            return user;
        });
    }

}
