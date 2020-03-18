package net.gesundheitsforen.sensordb.config;

import net.gesundheitsforen.sensordb.model.Humidity;
import net.gesundheitsforen.sensordb.model.Role;
import net.gesundheitsforen.sensordb.model.Temperature;
import net.gesundheitsforen.sensordb.model.User;
import net.gesundheitsforen.sensordb.repository.HumidityRepository;
import net.gesundheitsforen.sensordb.repository.RoleRepository;
import net.gesundheitsforen.sensordb.repository.TemperatureRepository;
import net.gesundheitsforen.sensordb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class InitialDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TemperatureRepository temperatureRepository;

    @Autowired
    private HumidityRepository humidityRepository;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }
        // == create initial roles
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
        final Role userRole = createRoleIfNotFound("ROLE_USER");

        // == create initial
        createUserIfNotFound("SensorDB Admin", "admin", "admin@gesundheitsforen.net", "Abc123!!", new ArrayList<Role>(Arrays.asList(adminRole)));
        createUserIfNotFound("SensorDB User", "user", "user@gesundheitsforen.net", "Abc123!!", new ArrayList<Role>(Arrays.asList(userRole)));
        createTemperaturesIfNoneFound();
        createHumiditiesIfNoneFound();
        alreadySetup = true;
    }

    @Transactional
    protected Role createRoleIfNotFound(String name) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    protected void createUserIfNotFound(final String name, final String username, final String email, final String password, final Collection<Role> roles) {
        Boolean userExists = userRepository.existsByUsername(username);
        if (userExists == false) {
            User user = new User();
            user.setName(name);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(roles);
            userRepository.save(user);
        }
    }

    @Transactional
    protected void createTemperaturesIfNoneFound() {
        if (temperatureRepository.count() > 0) {
            return;
        }
        for (int i=0; i<60; i++) {
            Temperature temperature = new Temperature();
            temperature.setId(i+1);
            temperature.setValue(Integer.valueOf(ThreadLocalRandom.current().nextInt(17, 26+1)).toString());
            temperature.setCreateDateTime(LocalDateTime.now());
            temperatureRepository.save(temperature);
            //manuelles Update der Zeit
            Temperature temperatureTime = temperatureRepository.findById(temperature.getId())
                    .orElseThrow(() -> new EntityNotFoundException("ID does not exist"));
            temperatureTime.setCreateDateTime(LocalDateTime.now().plusSeconds(i*10));
        }
}

    @Transactional
    protected void createHumiditiesIfNoneFound(){
        if (humidityRepository.count() > 0) {
            return;
        }
        for (int i=0; i<60; i++) {
            Humidity humidity = new Humidity();
            humidity.setId(i+1);
            humidity.setValue(Integer.valueOf(ThreadLocalRandom.current().nextInt(15,23+1)).toString());
            humidity.setCreateDateTime(LocalDateTime.now());
            humidityRepository.save(humidity);
            //manuelles Update der Zeit
            Humidity humidityTime = humidityRepository.findById(humidity.getId())
                    .orElseThrow(() -> new EntityNotFoundException("ID does not exist"));
            humidityTime.setCreateDateTime(LocalDateTime.now().plusSeconds(i*10));
        }
    }
}
