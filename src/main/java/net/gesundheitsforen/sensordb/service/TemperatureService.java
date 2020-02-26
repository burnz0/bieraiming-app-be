package net.gesundheitsforen.sensordb.service;

import net.gesundheitsforen.sensordb.config.RabbitMQConfig;
import net.gesundheitsforen.sensordb.model.Temperature;
import net.gesundheitsforen.sensordb.repository.TemperatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Service
public class TemperatureService {

    private static final Logger log = LoggerFactory.getLogger(TemperatureService.class);
    private Temperature tempTemp = new Temperature();

    @Autowired
    RabbitMQConfig rabbitMQConfig;

    @Autowired
    TemperatureRepository temperatureRepository;

    @RabbitListener(queues = "${sensor2.queue.name}")
    public void receiveTemperature(String content) {
        Temperature temperature = new Temperature(content);
//        if (!content.equals(tempTemp.getValue())) {
//            temperatureRepository.save(temperature);
//            log.info("Temperature: {} °C", content);
//        }
        temperatureRepository.save(temperature);
        log.info("Temperature: {} °C", content);
        tempTemp.setValue(content);
    }

    public Temperature getTemperatureById(Long id) throws EntityNotFoundException {
        return temperatureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID does not exist"));
    }

    public List<Temperature> getTemperatures() {
        return (List<Temperature>) temperatureRepository.findAll();
    }

    public List<Temperature> getRecentTemperatures() {
        List<Temperature> allTemperatures = (List<Temperature>) temperatureRepository.findAll();
        if(allTemperatures.size() <= 60){
            return allTemperatures;
        }
        else{
            List<Temperature> recentTemperatures = allTemperatures.subList(allTemperatures.size()-60, allTemperatures.size());
            return recentTemperatures;
        }
    }

    //Variante Getter für letzte Temp mit List als return
    public List<Temperature> getLatestTemperatureList() {
        List<Temperature> allTemperatures = (List<Temperature>) temperatureRepository.findAll();
        List<Temperature> latestTemperature = allTemperatures.subList(allTemperatures.size()-1, allTemperatures.size());
        return latestTemperature;
    }

    //Variante Getter für letzte Temp mit Temperatur Objekt als return
    public Temperature getLatestTemperatureObject(){
        List <Temperature> allTemperatures = (List<Temperature>) temperatureRepository.findAll();
        return allTemperatures.get(allTemperatures.size()-1);
    }

    public void addTemperature(Temperature temperature) {
        temperatureRepository.save(temperature);
    }

    public void deleteTemperature(Long id) {
        temperatureRepository.deleteById(id);
    }

    public void deleteAll() {
        temperatureRepository.deleteAll();
    }
}