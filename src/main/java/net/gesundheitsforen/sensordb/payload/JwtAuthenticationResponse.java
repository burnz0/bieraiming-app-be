package net.gesundheitsforen.sensordb.payload;

import lombok.Data;
import net.gesundheitsforen.sensordb.model.Role;

import java.util.Collection;


@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
