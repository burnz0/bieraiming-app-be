package net.gesundheitsforen.sensordb.service;

import net.gesundheitsforen.sensordb.model.Temperature;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureServiceTest {

    private TemperatureService temperatureServiceToTest;

    @BeforeAll
    public void init() {
        temperatureServiceToTest = new TemperatureService();
    }
    @BeforeEach
    public void clearRepository() {
        temperatureServiceToTest.deleteAll();
    }

    @Test
    public void receiveTemperature() throws Exception {
        temperatureServiceToTest.receiveTemperature("701389");
        assertEquals("701389", temperatureServiceToTest.getLatestTemperatureObject().getValue());
    }

    @Test
    public void getTemperatureById() throws Exception {
        Temperature temperature = new Temperature();
        temperature.setId(701389);
        assertEquals(temperature, temperatureServiceToTest.getTemperatureById((long) 701389));
    }

    //ist nur Repository.findAll()
    @Test
    public void getTemperatures() throws Exception {
    }

    @Test
    public void getRecentTemperaturesIfCondition() throws Exception {
        //wegen Reset vor Tests sollte List mit letzten Temperaturen leer sein
        assertEquals(0, temperatureServiceToTest.getRecentTemperatures().size());
    }

    @Test
    public void getRecentTemperaturesElseCondition() throws Exception {
        for (int i=0; i<61; i++) {
            Temperature temperature = new Temperature();
            temperatureServiceToTest.addTemperature(temperature);
        }
        assertTrue(temperatureServiceToTest.getRecentTemperatures().size() <= 60);
    }

    @Test
    public void getLatestTemperatureList() throws Exception {
        Temperature temperature = new Temperature();
        temperatureServiceToTest.addTemperature(temperature);
        assertEquals(temperature, temperatureServiceToTest.getLatestTemperatureList().get(0));
    }

    @Test
    public void getLatestTemperatureObject() throws Exception {
        Temperature temperature = new Temperature();
        temperatureServiceToTest.addTemperature(temperature);
        assertEquals(temperature, temperatureServiceToTest.getLatestTemperatureObject());
    }
    //letzte Methoden sind reine Repository Methoden
    @Test
    public void addTemperature() throws Exception {
    }

    @Test
    public void deleteTemperature() throws Exception {
    }

    @Test
    public void deleteAll() throws Exception {
    }
}