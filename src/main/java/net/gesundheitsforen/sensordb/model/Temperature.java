package net.gesundheitsforen.sensordb.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
public class Temperature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String value;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    public Temperature(){
    }

    public Temperature(String value) {
        this.value = value;
    }

}
