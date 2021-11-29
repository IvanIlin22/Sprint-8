package com.example.retailer.rabbit

import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.storage.OrderInfoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RabbitListener {

    private val LOGGER: Logger = LoggerFactory.getLogger(com.example.retailer.rabbit.RabbitListener::class.java)

    @Autowired
    private lateinit var orderInfoRepository: OrderInfoRepository

    @RabbitListener(queues = arrayOf(QUEUE))
    fun receivedMessage(order: OrderInfo) {
        LOGGER.info("Received order from RabbitMQ: $order")
        val orderInfo = orderInfoRepository.findById(order.orderId).get()
        orderInfo.status = order.status
        orderInfoRepository.save(orderInfo)
    }
}
