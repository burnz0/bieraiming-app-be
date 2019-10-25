package net.gesundheitsforen.sensordb.service;

import net.gesundheitsforen.sensordb.model.Role;
import net.gesundheitsforen.sensordb.model.User;
import net.gesundheitsforen.sensordb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public String getUserRole(Long ID) {
        Optional<User> user = userRepository.findById(ID);

        return "TODO";
    }
}
