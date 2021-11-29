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

const val EXCHANGE: String = "distributor_exchange"
const val QUEUE: String = "retailer_queue"
const val ROUTING_KEY: String = "retailer.IvalIlin22.#"

@Configuration
@ComponentScan("com.example.retailer.rabbit")
class RabbitConfig {

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
        return BindingBuilder.bind(queue()).to(topicExchange()).with(ROUTING_KEY)
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
