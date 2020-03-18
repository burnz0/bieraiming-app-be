package net.gesundheitsforen.sensordb.config;

import net.gesundheitsforen.sensordb.model.Humidity;
import net.gesundheitsforen.sensordb.model.Role;
import net.gesundheitsforen.sensordb.model.Temperature;
import net.gesundheitsforen.sensordb.model.User;
import net.gesundheitsforen.sensordb.repository.HumidityRepository;
import net.gesundheitsforen.sensordb.repository.RoleRepository;
import net.gesundheitsforen.sensordb.repository.TemperatureRepository;
import net.gesundheitsforen.sensordb.repository.UserRepository;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class InitialDataLoaderTest {

    private boolean alreadySetup;

    private InitialDataLoader initialDataLoaderToTest;

    @Mock
    ContextRefreshedEvent event;

    @Mock
    UserRepository mockUserRepository;
    RoleRepository mockRoleRepository;
    HumidityRepository mockHumidityRepository;
    TemperatureRepository mockTemperatureRepository;
    InitialDataLoader mockInitialDataLoader;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    //hier passiert nichts
    @Test
    public void onApplicationEventIfCondition() {
    }

    @Test
    public void onApplicationEventElseCondition() {
        initialDataLoaderToTest.onApplicationEvent(event);
    }

    @Test
    public void createRoleIfNotFoundIfCondition() {
        Role test_role = new Role("test_role");
        mockRoleRepository.save(test_role);
        assertEquals(test_role, mockRoleRepository.findByName("test_role"));
    }

    @Test void createRoleIfNotFoundElseCondition() {
        Role test_role_delete = new Role("test_role");
        mockRoleRepository.save(test_role_delete);
        mockRoleRepository.delete(test_role_delete);
        Role test_role = mockRoleRepository.findByName("test_role");
        if(test_role == null) {
            test_role = new Role("test_role");
            mockRoleRepository.save(test_role);
            assertEquals(test_role,mockRoleRepository.findByName("test_role"));
        }
    }

    @Test
    public void createUserIfNotFoundIfCondition() {
        User test_user = new User();
        test_user.setUsername("test_user");
        mockUserRepository.save(test_user);
        assertTrue(mockUserRepository.existsByUsername("test_user"));
    }

    @Test
    public void createUserIfNoteFoundElseCondition() {
        mockUserRepository.deleteAll();
        assertFalse(mockUserRepository.existsByUsername("test_user"));
        User test_user = new User();
        test_user.setUsername("test_user");
        mockUserRepository.save(test_user);
        assertTrue(mockUserRepository.existsByUsername("test_user"));
    }

    @Test
    public void createTemperaturesIfNoneFoundElseCondition() {
        mockTemperatureRepository.deleteAll();
        for (int i = 0; i < 60; i++) {

            Temperature temperature = new Temperature();
            temperature.setId(i+1);
            temperature.setValue(Integer.valueOf(ThreadLocalRandom.current().nextInt(17, 26+1)).toString());
            temperature.setCreateDateTime(LocalDateTime.now());
            mockTemperatureRepository.save(temperature);
            //manuelles Update der Zeit
            Temperature temperatureTime = mockTemperatureRepository.findById(temperature.getId())
                    .orElseThrow(() -> new EntityNotFoundException("ID does not exist"));
            LocalDateTime ldT = LocalDateTime.now().plusSeconds(i*10);
            temperatureTime.setCreateDateTime(ldT);
            assertTrue(temperature.getCreateDateTime().equals(ldT));
        }
        assertTrue(mockTemperatureRepository.count() == 60);
    }

    //hier passiert nichts
    @Test
    public void createTemperaturesIfNoneFoundIfCondition() {
    }

    @Test
    public void createHumiditiesIfNoneFoundElseCondition() {
        mockHumidityRepository.deleteAll();
        for (int i = 0; i < 60; i++) {
            Humidity humidity = new Humidity();
            humidity.setId(i+1);
            humidity.setValue(Integer.valueOf(ThreadLocalRandom.current().nextInt(15,23+1)).toString());
            humidity.setCreateDateTime(LocalDateTime.now());
            mockHumidityRepository.save(humidity);
            //manuelles Update der Zeit
            Humidity humidityTime = mockHumidityRepository.findById(humidity.getId())
                    .orElseThrow(() -> new EntityNotFoundException("ID does not exist"));
            LocalDateTime ldT = LocalDateTime.now().plusSeconds(i*10);
            humidityTime.setCreateDateTime(ldT);
            assertTrue(humidity.getCreateDateTime().equals(ldT));
        }
        assertTrue(mockHumidityRepository.count() == 60);
    }

    //hier passiert nichts
    @Test
    public void createHumiditiesIfNoneFoundIfCondition() {
    }
}