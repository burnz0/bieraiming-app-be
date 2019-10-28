package net.gesundheitsforen.sensordb.payload;

import lombok.Data;

@Data
public class UserSummary {
    private Long id;
    private String username;
    private String name;
    private String role;


    public UserSummary(Long id, String username, String name, String role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.role = role;
    }
}
