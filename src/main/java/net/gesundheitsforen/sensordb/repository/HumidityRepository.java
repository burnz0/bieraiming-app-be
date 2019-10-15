package net.gesundheitsforen.sensordb.repository;

import net.gesundheitsforen.sensordb.model.Humidity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HumidityRepository extends CrudRepository<Humidity, Long> {


}