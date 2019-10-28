package net.gesundheitsforen.sensordb.service;

import net.gesundheitsforen.sensordb.model.Role;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService {

    public String getRole(Collection<Role> roles) {

        ArrayList<Role> role = new ArrayList(roles);

        return role.get(0).getName();
    }
}
