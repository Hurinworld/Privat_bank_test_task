package com.serhiihurin.privattestapi4.config;

import com.serhiihurin.privattestapi4.service.interfaces.MessageBrokerService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    private final String service4queueName = "service4Queue";
    private final String service5queueName = "service5Queue";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("exchange");
    }

    @Bean
    public Queue service4Queue() {
        return new Queue(service4queueName, true, false, false);
    }

    @Bean
    public Queue service5Queue() {
        return new Queue(service5queueName, true, false, false);
    }

    @Bean
    public Binding service2Binding(DirectExchange directExchange) {
        return BindingBuilder.bind(service4Queue()).to(directExchange).with(service4queueName);
    }

    @Bean
    public Binding service3Binding(DirectExchange directExchange) {
        return BindingBuilder.bind(service5Queue()).to(directExchange).with(service5queueName);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        var converter = new Jackson2JsonMessageConverter();
        var typeMapper = new DefaultJackson2JavaTypeMapper();

        var idClassMapping = new HashMap<String, Class<?>>();
        idClassMapping.put(
                "com.serhiihurin.privattestapi3.dto.ExternalDataDTO",
                com.serhiihurin.privattestapi4.dto.ClientDataDTO.class
        );
        typeMapper.setIdClassMapping(idClassMapping);

        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(
            ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter
    ) {
        var container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(service4queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(
            MessageConverter messageConverter,
            MessageBrokerService messageBrokerService
    ) {
        var messageListenerAdapter = new MessageListenerAdapter(messageBrokerService, "receiveMessage");
        messageListenerAdapter.setMessageConverter(messageConverter);
        return messageListenerAdapter;
    }
}
