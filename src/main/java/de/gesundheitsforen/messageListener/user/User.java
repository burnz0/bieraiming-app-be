package de.gesundheitsforen.messageListener.user;

import javax.persistence.*;

import javax.validation.constraints.NotNull;


import lombok.Data;
import lombok.ToString;


@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String userName;
    @NotNull
    private String password;




}
