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



@Configuration
public class RabbitMqBeansConfig {

    public static final String REVIEW_EXCHANGE = "reviewExchange";
    public static final String REVIEW_QUEUE = "reviewQueue";
    public static final String REVIEW_ROUTING_KEY = "review.review";
    public static final String REVIEW_COMPLETED_QUEUE = "reviewCompletedQueue";
    public static final String REVIEW_COMPLETED_ROUTING_KEY = "review.completed";
    
  

    @Bean
    public MessageConverter converter(){

    return new Jackson2JsonMessageConverter();
    
}


@Bean
public RabbitTemplate rabbitTemplate(ConnectionFactory factory){

    RabbitTemplate template =new RabbitTemplate(factory);

    template.setMessageConverter(converter());

    return template;

}

    @Bean("reviewQueue")
    public Queue queue(){
    
        return new Queue(REVIEW_QUEUE, true);
    
    }
    
    @Bean()
    public Exchange exchange(){

        return ExchangeBuilder.directExchange(REVIEW_EXCHANGE).durable(true).build();

    }   

    @Bean
    public Binding binding(@Qualifier("reviewQueue") Queue queue,Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with(REVIEW_ROUTING_KEY).noargs();

    }

    @Bean("reviewCompletedQueue")
    public Queue reviewCompletedQueue(){
    
        return new Queue(REVIEW_COMPLETED_QUEUE, true);
    
    }

    @Bean
    public Binding reviewCompletedBinding(@Qualifier("reviewCompletedQueue") Queue queue,Exchange exchange){

        return BindingBuilder.bind(queue).to(exchange).with(REVIEW_COMPLETED_ROUTING_KEY).noargs();

    }






}
