package com.newmeksolutions.config;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig 
{
	  @Value("${rabbitmq.exchange.json.name}")
	    private String exchangeName;

	    @Value("${rabbitmq.queue.json.name}")
	    private String jsonQueueName;

	    @Value("${rabbitmq.routing.json.key}")
	    private String jsonroutingKey;

	    @Bean
	    public TopicExchange exchange() {
	        System.out.println("🔄 Declaring Topic Exchange: " + exchangeName);
	        return new TopicExchange(exchangeName);
	    }

	    @Bean
	    public Queue jsonQueue() {
	        System.out.println("📦 Declaring JSON Queue: " + jsonQueueName);
	        return new Queue(jsonQueueName, true);
	    }

	    @Bean
	    public Binding jsonBinding(Queue jsonQueue, TopicExchange exchange) {
	        System.out.println("🔗 Binding JSON Queue to Exchange with Routing Key: " + jsonroutingKey);
	        return BindingBuilder.bind(jsonQueue).to(exchange).with(jsonroutingKey);
	    }

	    @Bean
	    public MessageConverter jsonMessageConverter() {
	        return new Jackson2JsonMessageConverter();
	    }

	    @Bean
	    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
	        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	        rabbitTemplate.setMessageConverter(jsonMessageConverter());
	        System.out.println("✅ RabbitMQ Template initialized with JSON converter");
	        return rabbitTemplate;
	    }
}
