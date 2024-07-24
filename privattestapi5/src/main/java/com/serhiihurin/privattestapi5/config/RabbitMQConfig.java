package com.serhiihurin.privattestapi5.config;

import com.serhiihurin.privattestapi5.facade.interfaces.DatabaseInfoSaverFacade;
import lombok.RequiredArgsConstructor;
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

@Configuration
@EnableRabbit
@RequiredArgsConstructor
public class RabbitMQConfig {
    private final DatabaseInfoSaverFacade databaseInfoSaverFacade;

    public static final String directExchangeName = "exchange";
    public static final String queueName = "service5Queue";
    public static final String routingKey = "service5Queue";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchangeName);
    }

    @Bean
    public Queue queue() {
        return new Queue(queueName, true, false, false);
    }

    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        var converter = new Jackson2JsonMessageConverter();
        var typeMapper = new DefaultJackson2JavaTypeMapper();

        var idClassMapping = new HashMap<String, Class<?>>();
        idClassMapping.put(
                "com.serhiihurin.privattestapi4.dto.ExternalDataDTO",
                com.serhiihurin.privattestapi5.dto.ClientDataDTO.class
        );
        idClassMapping.put(
                "com.serhiihurin.privattestapi4.dto.CardDTO",
                com.serhiihurin.privattestapi5.dto.CardDTO.class
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
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(MessageConverter messageConverter) {
        var messageListenerAdapter =
                new MessageListenerAdapter(databaseInfoSaverFacade, "saveClientData");
        messageListenerAdapter.setMessageConverter(messageConverter);
        return messageListenerAdapter;
    }
}
