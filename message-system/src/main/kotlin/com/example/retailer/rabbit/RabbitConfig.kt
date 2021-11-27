package com.example.retailer.rabbit

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.example.retailer.rabbit")
class RabbitConfig {

    private val EXCHANGE: String = "distributor_exchange"
    private val QUEUE: String = "order_queue"

    @Bean
    fun topicExchange(): TopicExchange {
        return TopicExchange(EXCHANGE)
    }

    @Bean
    fun queue(): Queue {
        return Queue(QUEUE)
    }

    @Bean
    fun binding(): Binding {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("retailer.IvalIlin22.#")
    }

    @Bean
    fun jsonMessageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun amqpTemplate(connectionFactory: ConnectionFactory): AmqpTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()

        return rabbitTemplate
    }
}
