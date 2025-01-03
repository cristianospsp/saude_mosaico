package com.saudemosaico.notificacao.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NOTIFICACAO_EMAIL = "notificacao.email";
    public static final String QUEUE_NOTIFICACAO_SMS = "notificacao.sms";
    public static final String QUEUE_NOTIFICACAO_WHATSAPP = "notificacao.whatsapp";
    public static final String EXCHANGE_NOTIFICACAO = "notificacao.exchange";

    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(QUEUE_NOTIFICACAO_EMAIL).build();
    }

    @Bean
    public Queue smsQueue() {
        return QueueBuilder.durable(QUEUE_NOTIFICACAO_SMS).build();
    }

    @Bean
    public Queue whatsappQueue() {
        return QueueBuilder.durable(QUEUE_NOTIFICACAO_WHATSAPP).build();
    }

    @Bean
    public DirectExchange notificacaoExchange() {
        return new DirectExchange(EXCHANGE_NOTIFICACAO);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, DirectExchange notificacaoExchange) {
        return BindingBuilder.bind(emailQueue)
            .to(notificacaoExchange)
            .with("email");
    }

    @Bean
    public Binding smsBinding(Queue smsQueue, DirectExchange notificacaoExchange) {
        return BindingBuilder.bind(smsQueue)
            .to(notificacaoExchange)
            .with("sms");
    }

    @Bean
    public Binding whatsappBinding(Queue whatsappQueue, DirectExchange notificacaoExchange) {
        return BindingBuilder.bind(whatsappQueue)
            .to(notificacaoExchange)
            .with("whatsapp");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
