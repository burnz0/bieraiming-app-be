package net.gesundheitsforen.sensordb.service;

import net.gesundheitsforen.sensordb.model.Humidity;
import net.gesundheitsforen.sensordb.repository.HumidityRepository;

import org.junit.Before;
import org.junit.After;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;


class HumidityServiceTest {

    private HumidityService humidityServiceToTest = new HumidityService();

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void clearRepository() {
        humidityServiceToTest.deleteAll();
    }

    @Test
    public void receiveHumidityTest() {
        humidityServiceToTest.receiveHumidity("701389");
        assertEquals("701389", humidityServiceToTest.getLatestHumidityObject().getValue());
    }
    
    @Test
    public void getHumidityByIdTest() {
        Humidity humidity = new Humidity();
        humidity.setId(701389);
        humidityServiceToTest.addHumidity(humidity);
        assertEquals(humidity, humidityServiceToTest.getHumidityById((long) 701389));
    }

    //ist nur Repository.findAll()
    @Test
    public void getHumidities() throws Exception {

    }

    @Test
    public void getRecentHumiditiesIfCondition() throws Exception {
        assertEquals(0, humidityServiceToTest.getRecentHumidities().size());
    }

    @Test
    public void getRecentHumiditiesElseCondition() throws Exception {
        for (int i=0; i<61; i++) {
            Humidity humidity = new Humidity();
            humidity.setId(i+1);
            humidityServiceToTest.addHumidity(humidity);
        }
        assertTrue(humidityServiceToTest.getRecentHumidities().size() <= 60);
    }
    @Test
    public void getLatestHumidityList() throws Exception {
        Humidity humidity = new Humidity();
        humidityServiceToTest.addHumidity(humidity);
        assertEquals(humidity, humidityServiceToTest.getLatestHumidityList().get(0));
    }

    @Test
    public void getLatestHumidityObject() throws Exception {
        Humidity humidity = new Humidity();
        humidityServiceToTest.addHumidity(humidity);
        assertEquals(humidity, humidityServiceToTest.getLatestHumidityObject());
    }
    //nächste Methoden reine Repository Methoden
    @Test
    public void addHumidity() throws Exception {
    }

    @Test
    public void deleteHumidity() throws Exception {
    }

    @Test
    public void deleteAll() throws Exception {
    }
}