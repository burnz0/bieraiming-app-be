package net.gesundheitsforen.sensordb.config;

import net.gesundheitsforen.sensordb.model.Role;
import net.gesundheitsforen.sensordb.model.User;
import net.gesundheitsforen.sensordb.repository.RoleRepository;
import net.gesundheitsforen.sensordb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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
        alreadySetup = true;
    }

    @Transactional
    protected Role createRoleIfNotFound(
            String name) {

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
}
