package net.gesundheitsforen.messageListener.humidity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HumidityRepository extends CrudRepository<Humidity, Long> {


}