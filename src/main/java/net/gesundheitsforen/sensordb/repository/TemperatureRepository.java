package net.gesundheitsforen.sensordb.repository;

import net.gesundheitsforen.sensordb.model.Temperature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureRepository extends CrudRepository<Temperature, Long> {



}