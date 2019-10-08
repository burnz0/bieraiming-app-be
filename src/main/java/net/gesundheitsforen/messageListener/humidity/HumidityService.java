package net.gesundheitsforen.messageListener.humidity;

import net.gesundheitsforen.messageListener.config.ApplicationConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class HumidityService {

    private static final Logger log = LoggerFactory.getLogger(HumidityService.class);
    private Humidity tempHum = new Humidity();

    @Autowired
    ApplicationConfigReader applicationConfigReader;

    @Autowired
    HumidityRepository humidityRepository;

    @RabbitListener(queues = "${sensor1.queue.name}")
    public void receiveHumidity(String content) {
        Humidity humidity = new Humidity(content);
        if (!content.equals(tempHum.getValue())) {
            humidityRepository.save(humidity);
            log.info("Humidity: {} %.", content);
        }
        tempHum.setValue(content);
    }

    public Humidity getHumidityById(Long id) throws EntityNotFoundException {
        return humidityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID does not exist"));
    }

    public List<Humidity> getHumidities() {
        return (List<Humidity>) humidityRepository.findAll();
    }

    public void addHumidity(Humidity humidity) {
        humidityRepository.save(humidity);
    }

    public void deleteHumidity(Long id) {
        humidityRepository.deleteById(id);
    }

    public void deleteAll() {
        humidityRepository.deleteAll();
    }




}