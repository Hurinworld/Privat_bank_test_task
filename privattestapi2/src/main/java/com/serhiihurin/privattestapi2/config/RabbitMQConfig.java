package com.serhiihurin.privattestapi2.config;

import com.serhiihurin.privattestapi2.service.interfaces.MessageBrokerService;
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
    private final String service2queueName = "service2Queue";
    private final String service3queueName = "service3Queue";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("exchange");
    }

    @Bean
    public Queue service2Queue() {
        return new Queue(service2queueName, true, false, false);
    }

    @Bean
    public Queue service3Queue() {
        return new Queue(service3queueName, true, false, false);
    }

    @Bean
    public Binding service2Binding(DirectExchange directExchange) {
        return BindingBuilder.bind(service2Queue()).to(directExchange).with(service2queueName);
    }

    @Bean
    public Binding service3Binding(DirectExchange directExchange) {
        return BindingBuilder.bind(service3Queue()).to(directExchange).with(service3queueName);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        var converter = new Jackson2JsonMessageConverter();
        var typeMapper = new DefaultJackson2JavaTypeMapper();

        var idClassMapping = new HashMap<String, Class<?>>();
        idClassMapping.put(
                "com.serhiihurin.privattestapi1.dto.ClientDataDTO",
                com.serhiihurin.privattestapi2.dto.ClientDataDTO.class
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
        container.setQueueNames(service2queueName);
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
