package com.example.retailer.rabbit

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderStatus
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

    @RabbitListener(queues = arrayOf("order_queue"))
    fun receivedMessage(order: Order) {
        if(order.id != null) {
            LOGGER.info("Received order from RabbitMQ: $order")
            val orderInfo = orderInfoRepository.findById(order.id).get()
            orderInfo.status = OrderStatus.DELIVERED
            orderInfoRepository.save(orderInfo)
        }
    }
}
