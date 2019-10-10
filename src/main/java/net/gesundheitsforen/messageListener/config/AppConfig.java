package net.gesundheitsforen.messageListener.config;

import lombok.Data;
import net.gesundheitsforen.messageListener.user.Authorities;
import net.gesundheitsforen.messageListener.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@Data
public class AppConfig {

    @Value("${sensor1.exchange.name}")
    private String app1Exchange;

    @Value("${sensor1.queue.name}")
    private String app1Queue;

    @Value("${sensor1.routing.key}")
    private String app1RoutingKey;

    @Value("${sensor2.exchange.name}")
    private String app2Exchange;

    @Value("${sensor2.queue.name}")
    private String app2Queue;

    @Value("${sensor2.routing.key}")
    private String app2RoutingKey;

}
