package com.example.retailer.rabbit

import com.example.retailer.adapter.DistributorPublisher
import com.example.retailer.api.distributor.Order
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class RabbitPublisher : DistributorPublisher {

    private val LOGGER: Logger = LoggerFactory.getLogger(RabbitPublisher::class.java)

    @Autowired
    private lateinit var amqpTemplate: AmqpTemplate

    override fun placeOrder(order: Order): Boolean {
        if (order.id != null) {
            amqpTemplate.convertAndSend(EXCHANGE, "distributor.placeOrder.IvalIlin22.${order.id}", order) {
                it.messageProperties.headers["Notify-Exchange"] = EXCHANGE
                it.messageProperties.headers["Notify-RoutingKey"] = ROUTING_KEY
                it
            }
            LOGGER.info("Order pushed to RabbitMQ ${order.id}")
            return true
        }
        LOGGER.info("Order don't pushed to RabbitMQ ${order.id}")

        return false
    }
}
