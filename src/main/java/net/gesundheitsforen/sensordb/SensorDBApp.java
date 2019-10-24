package net.gesundheitsforen.sensordb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        SensorDBApp.class,
        Jsr310JpaConverters.class
})
public class SensorDBApp {

    public static void main(String[] args) {
        SpringApplication.run(SensorDBApp.class, args);
    }

}
