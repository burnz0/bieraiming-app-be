package net.gesundheitsforen.sensordb.service;

import net.gesundheitsforen.sensordb.config.RabbitMQConfig;
import net.gesundheitsforen.sensordb.model.Humidity;
import net.gesundheitsforen.sensordb.model.Temperature;
import net.gesundheitsforen.sensordb.repository.HumidityRepository;
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
    RabbitMQConfig rabbitMQConfig;

    @Autowired
    HumidityRepository humidityRepository;

    @RabbitListener(queues = "${sensor1.queue.name}")
    public void receiveHumidity(String content) {
        Humidity humidity = new Humidity(content);
//        if (!content.equals(tempHum.getValue())) {
//            humidityRepository.save(humidity);
//            log.info("Humidity: {} %.", content);
//        }
        humidityRepository.save(humidity);
        log.info("Humidity: {} %.", content);
        tempHum.setValue(content);
    }

    public Humidity getHumidityById(Long id) throws EntityNotFoundException {
        return humidityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID does not exist"));
    }

    public List<Humidity> getHumidities() {
        return (List<Humidity>) humidityRepository.findAll();
    }

    public List<Humidity> getRecentHumidities() {
        List<Humidity> allHumidities = (List<Humidity>) humidityRepository.findAll();
        if(allHumidities.size() <= 60){
            return allHumidities;
        }
        else{
            List<Humidity> recentHumidities = allHumidities.subList(allHumidities.size()-60, allHumidities.size());
            return recentHumidities;
        }
    }
    //Variante Getter letzte Humidity mit Liste als return
    public List<Humidity> getLatestHumidityList() {
        List<Humidity> allHumidities = (List<Humidity>) humidityRepository.findAll();
        List<Humidity> latestHumidity = allHumidities.subList(allHumidities.size()-1, allHumidities.size());
        return latestHumidity;
    }

    //Variante Getter letzte Humidity mit Object Humidity
    public Humidity getLatestHumidityObject() {
        List<Humidity> allHumidities = (List<Humidity>) humidityRepository.findAll();
        return allHumidities.get(allHumidities.size()-1);
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