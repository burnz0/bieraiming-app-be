package net.gesundheitsforen.sensordb.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Data
@Configuration
public class RabbitMQConfig implements RabbitListenerConfigurer {
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

    /* Creating a bean for the Message queue Exchange */
    @Bean
    public TopicExchange getApp1Exchange() {
        return new TopicExchange(app1Exchange);
    }

    /* Creating a bean for the Message queue */
    @Bean
    public Queue getApp1Queue() {
        return new Queue(app1Queue);
    }

    /* Binding between Exchange and Queue using routing key */
    @Bean
    public Binding declareBindingApp1() {
        return BindingBuilder.bind(getApp1Queue()).to(getApp1Exchange()).with(getApp1RoutingKey());
    }

    /* Creating a bean for the Message queue Exchange */
    @Bean
    public TopicExchange getApp2Exchange() {
        return new TopicExchange(app2Exchange);
    }

    /* Creating a bean for the Message queue */
    @Bean
    public Queue getApp2Queue() {
        return new Queue(app2Queue);
    }

    /* Binding between Exchange and Queue using routing key */
    @Bean
    public Binding declareBindingApp2() {
        return BindingBuilder.bind(getApp2Queue()).to(getApp2Exchange()).with(getApp2RoutingKey());
    }

    /* Bean for rabbitTemplate */
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}
