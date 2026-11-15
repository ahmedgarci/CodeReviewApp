package com.example.CodeReviewApp.security;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;




@Configuration
public class RabbitMqBeansConfig {

    public static final String REVIEW_EXCHANGE = "code-review.exchange";
    public static final String REVIEW_REQUESTED_QUEUE = "code-review.requested.queue";
    public static final String REVIEW_REQUESTED_ROUTING_KEY = "review.requested";
    public static final String REVIEW_COMPLETED_QUEUE = "code-review.completed.queue";
    public static final String REVIEW_COMPLETED_ROUTING_KEY = "review.completed";
    
  

    @Bean
    public MessageConverter converter(){

        return new Jackson2JsonMessageConverter();
    
    }


@Bean
public RabbitTemplate rabbitTemplate(@NonNull  ConnectionFactory factory){

    RabbitTemplate template =new RabbitTemplate(factory);

    template.setMessageConverter(converter());

    return template;

}

    @Bean("reviewRequestsQueue")
    public Queue queue(){
    
        return new Queue(REVIEW_REQUESTED_QUEUE, true);
    
    }
    
    @Bean()
    public Exchange exchange(){

        return ExchangeBuilder.directExchange(REVIEW_EXCHANGE).durable(true).build();

    }   

    @Bean
    public Binding binding(@Qualifier("reviewRequestsQueue") Queue queue,Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with(REVIEW_REQUESTED_ROUTING_KEY).noargs();

    }

    @Bean("reviewsCompletedQueue")
    public Queue reviewCompletedQueue(){
    
        return new Queue(REVIEW_COMPLETED_QUEUE, true);
    
    }

    @Bean
    public Binding reviewCompletedBinding(@Qualifier("reviewsCompletedQueue") Queue queue,Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with(REVIEW_COMPLETED_ROUTING_KEY).noargs();

    }






}
