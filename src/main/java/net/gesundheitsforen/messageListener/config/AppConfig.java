package net.gesundheitsforen.messageListener.config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;


@Configuration
@Data
public class AppConfig {

    @Value("${spring.datasource.username}")
    private String username;

}


