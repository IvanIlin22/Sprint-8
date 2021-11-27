package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrderStorageRepository : OrderStorage {
    @Autowired
    private lateinit var orderInfoRepository: OrderInfoRepository

    @Autowired
    private lateinit var orderRepository: OrderRepository


    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        val orderInfo = orderInfoRepository.save(OrderInfo(order.id!!, OrderStatus.SENT, ""))

        return PlaceOrderData(order, orderInfo)
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return orderInfoRepository.findById(id).get()
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        var orderInfo: OrderInfo? = orderInfoRepository.findById(order.orderId).get()
        if (orderInfo == null) {
            return false
        }
        orderInfoRepository.save(order)

        return true
    }
}
