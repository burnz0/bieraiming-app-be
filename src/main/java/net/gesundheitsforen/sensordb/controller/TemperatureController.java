package net.gesundheitsforen.sensordb.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.gesundheitsforen.sensordb.model.Temperature;
import net.gesundheitsforen.sensordb.repository.TemperatureRepository;
import net.gesundheitsforen.sensordb.service.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/temperature")
public class TemperatureController {

    @Autowired
    TemperatureRepository temperatureRepository;

    @Autowired
    TemperatureService temperatureService;

    @ApiOperation(value = "Search a Temperature by an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "ID does not exist.")
    })
    @GetMapping(
            value = "/show/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> showTemperature(@PathVariable Long id) {
        Temperature result = null;
        try {
            result = temperatureService.getTemperatureById(id);
        }
        catch (EntityNotFoundException enfe) {
            //todo logging
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "List all Temperatures")
    @GetMapping(
            path = "/show/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Temperature> getTemperatures() {
        return temperatureService.getTemperatures();
    }

    @ApiOperation(value = "Get 60 most recent Temperatures ")
    @GetMapping(
            path="/show/recent",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Temperature> getRecentTemperatures() {
        return temperatureService.getRecentTemperatures();
    }

    //Variante Getter letzte Temp in Liste
    @ApiOperation(value = "Get the latest Temperature in List")
    @GetMapping(
            path="/show/latest/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Temperature> getLatestTemperatureList() {
        return temperatureService.getLatestTemperatureList();
    }
    //Variante Getter letzte Temp als Objekt
    @ApiOperation(value = "Get the latest Temperature")
    @GetMapping(
            path="/show/latest/object",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Temperature getLatestTemperatureObject() {
        return temperatureService.getLatestTemperatureObject();
    }
    @PostMapping("/add")
    public void addTemperature(@RequestBody Temperature temperature) {
        temperatureService.addTemperature(temperature);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteTemperature(@PathVariable Long id) {
        temperatureService.deleteTemperature(id);
    }

    @DeleteMapping(value = "/delete/all")
    public void deleteAllTemperatures() {
        temperatureService.deleteAll();
    }
}
