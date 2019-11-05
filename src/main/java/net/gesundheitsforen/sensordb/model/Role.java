package net.gesundheitsforen.sensordb.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany
    private Collection<User> users;

    public Role() {
        super();
    }

    public Role(final String name) {
        super();
        this.name = name;
    }

}
