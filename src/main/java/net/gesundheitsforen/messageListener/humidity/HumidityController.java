package net.gesundheitsforen.messageListener.humidity;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/humidity")
public class HumidityController {

    @Autowired
    HumidityRepository humidityRepository;

    @Autowired
    HumidityService humidityService;

    @GetMapping(
            path = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Humidity> getHumidities() {
        return humidityService.getHumidities();
    }

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