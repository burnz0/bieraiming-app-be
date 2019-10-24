package net.gesundheitsforen.sensordb.payload;

import lombok.Data;
import net.gesundheitsforen.sensordb.model.Role;

import java.time.Instant;
import java.util.Set;

@Data
public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private Instant joinedAt;
    private Set<Role> roles;

    public UserProfile(Long id, String username, String name, Instant joinedAt, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = joinedAt;
        this.roles = roles;
    }
}
