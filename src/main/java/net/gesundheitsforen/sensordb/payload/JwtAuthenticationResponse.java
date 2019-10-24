package net.gesundheitsforen.sensordb.payload;

import lombok.Data;
import net.gesundheitsforen.sensordb.model.Role;

import java.util.Set;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Set<Role> roles;

    public JwtAuthenticationResponse(String accessToken, Set<Role> roles) {
        this.accessToken = accessToken;
        this.roles = roles;
    }
}
