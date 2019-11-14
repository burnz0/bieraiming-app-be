package net.gesundheitsforen.sensordb.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @GetMapping(
            value = "/{Location}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getWeather(@PathVariable String Location) throws IOException {
        URL url = new URL("http://api.weatherstack.com/current?access_key=0fc3c9bdd92a7d17b4d5ef649884f0af&query=" + Location);
        String result = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                result += sc.nextLine();
            }

        } catch (EntityNotFoundException enfe) {
            //todo logging
            return new ResponseEntity<>("Location not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);


    }

}
