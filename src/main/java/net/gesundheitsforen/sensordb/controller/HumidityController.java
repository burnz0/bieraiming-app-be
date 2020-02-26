package net.gesundheitsforen.sensordb.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.gesundheitsforen.sensordb.model.Humidity;
import net.gesundheitsforen.sensordb.model.Temperature;
import net.gesundheitsforen.sensordb.repository.HumidityRepository;
import net.gesundheitsforen.sensordb.service.HumidityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/humidity")
public class HumidityController {

    @Autowired
    HumidityRepository humidityRepository;

    @Autowired
    HumidityService humidityService;

    @ApiOperation(value = "Show a Humidity by an ID")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "ID does not exist.")
    })

    @GetMapping(
            value = "/show/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> showHumidity(@PathVariable Long id) {
        Humidity result = null;
        try {
            result = humidityService.getHumidityById(id);
        } catch (EntityNotFoundException enfe) {
            //todo logging
            return new ResponseEntity<>("ID not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(
            path = "/show/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Humidity> getHumidities() {
        return humidityService.getHumidities();
    }

    @ApiOperation(value = "Get the 60 most recent Humidities")
    @GetMapping(
            path="/show/recent",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Humidity> getRecentHumidities() {
        return humidityService.getRecentHumidities();
    }
//    Variante Getter Latest mit List als return
    @ApiOperation(value = "Get the latest Humidity")
    @GetMapping(
            path="/show/latest/list",
            produces =  MediaType.APPLICATION_JSON_VALUE
    )
    public List<Humidity> getLatestHumidityList() {
        return humidityService.getLatestHumidityList();
    }

    //Variante Getter latest mit Humidity Object return
    @ApiOperation(value = "Get the latest Humidity")
    @GetMapping(
            path="/show/latest/object",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Humidity getLatestHumidityObject() {
        return  humidityService.getLatestHumidityObject();
    }

    @PostMapping("/add")
    void addHumidity(@RequestBody Humidity humidity) {
        humidityService.addHumidity(humidity);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteHumidity(@PathVariable Long id) {
        humidityService.deleteHumidity(id);
    }

    @DeleteMapping(value = "/delete/all")
    public void deleteAllHumidities() {
        humidityService.deleteAll();
    }
}