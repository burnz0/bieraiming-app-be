package net.gesundheitsforen.sensordb.payload;

import lombok.Data;

import java.time.Instant;

@Data
public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private Instant joinedAt;
    private String role;

    public UserProfile(Long id, String username, String name, Instant joinedAt, String role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = joinedAt;
        this.role = role;
    }
}
