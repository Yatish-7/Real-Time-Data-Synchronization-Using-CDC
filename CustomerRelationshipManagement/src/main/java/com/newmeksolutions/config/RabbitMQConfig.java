package com.newmeksolutions.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Exchange jsonExchange() {
        return new TopicExchange("POS_Exchange(CDC)", true, false);
    }

    @Bean
    public Queue jsonQueue() {
        return new Queue("POS_Data_Queue(CDC)", true);
    }

    @Bean
    public Binding binding(Queue jsonQueue, Exchange jsonExchange) {
        return BindingBuilder
                .bind(jsonQueue)
                .to(jsonExchange)
                .with("POS_Key")
                .noargs();
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

  
//    @Bean
//    public MessageListenerAdapter listenerAdapter(Object listener) {
//        MessageListenerAdapter adapter = new MessageListenerAdapter(listener, "consumeMessage");
//        adapter.setMessageConverter(jsonMessageConverter());
//        return adapter;
//    }
//    
}
