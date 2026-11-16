package com.ecommerce.order.Impl;

import com.ecommerce.order.Entity.Order;
import com.ecommerce.order.Mapper.OrderMapper;
import com.ecommerce.order.Repository.OrderRepo;
import com.ecommerce.order.Service.OrderService;
import com.ecommerce.order.client.InventoryFeignClient;
import com.ecommerce.order.dto.InventoryResponse;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.event.OrderPlacedEvent;
import com.ecommerce.order.kafka.OrderEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
     OrderRepo orderRepo;
    @Autowired
    OrderEventProducer orderEventProducer;

    @Autowired
   private final OrderMapper orderMapper;

  @Autowired
   private final InventoryFeignClient inventoryFeignClient;

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        InventoryResponse inventory =inventoryFeignClient.isInStock(request.getSkuCode());
        if (! inventory.isInStock()){
            throw  new RuntimeException("Product is out of stock.");
        }
        Order order=orderMapper.toEntity(request);
        Integer orderId= Integer.valueOf(UUID.randomUUID().toString());
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderStatus("CREATED");
        orderRepo.save(order);
        OrderPlacedEvent  event=new OrderPlacedEvent();
        event.setEnventId(UUID.randomUUID().toString());
        event.setOrderId(orderId);
        event.setSkuCode(request.getSkuCode());
        event.setQuantity(request.getQuantity());
        event.setEventTime(LocalDateTime.parse("Order_Placed"));
        event.setEventTime(LocalDateTime.now());
        orderEventProducer.sentOrderEvent(event);
        return orderMapper.toResponse(order);
    }
}
