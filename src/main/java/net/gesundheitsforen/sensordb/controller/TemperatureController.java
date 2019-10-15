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
@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<?> showHumidity(@PathVariable Long id) {
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
            path = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Temperature> getTemperatures() {
        return temperatureService.getTemperatures();
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