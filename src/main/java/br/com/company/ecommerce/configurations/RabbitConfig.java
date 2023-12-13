package br.com.company.ecommerce.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    @Value("${report.queue}")
    private String reportQueue;

    @Bean
    Queue reportQueue() {
        return QueueBuilder.durable(reportQueue).build();
    }

}
