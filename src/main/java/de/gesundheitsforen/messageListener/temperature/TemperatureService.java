package de.gesundheitsforen.messageListener.temperature;

import de.gesundheitsforen.messageListener.config.ApplicationConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TemperatureService {

    private static final Logger log = LoggerFactory.getLogger(TemperatureService.class);
    private Temperature tempTemp = new Temperature();

    @Autowired
    ApplicationConfigReader applicationConfigReader;

    @Autowired
    TemperatureRepository temperatureRepository;

    @RabbitListener(queues = "${sensor2.queue.name}")
    public void receiveTemperature(String content) {
        Temperature temperature = new Temperature(content);
        if (!content.equals(tempTemp.getValue())) {
            temperatureRepository.save(temperature);
            log.info("Temperature: {} °C", content);
        }
        tempTemp.setValue(content);
    }

    public Temperature getTemperatureById(Long id) throws EntityNotFoundException {
        return temperatureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID does not exist"));
    }

    public List<Temperature> getTemperatures() {
        return (List<Temperature>) temperatureRepository.findAll();
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