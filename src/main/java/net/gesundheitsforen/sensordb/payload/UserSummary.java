package net.gesundheitsforen.sensordb.payload;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class UserSummary {
    private Long id;
    private String username;
    private String name;


    public UserSummary(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }
}
