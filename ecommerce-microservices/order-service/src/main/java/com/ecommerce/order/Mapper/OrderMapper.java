package com.ecommerce.order.Mapper;

import com.ecommerce.order.Entity.Order;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.dto.OrderResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public Order toEntity(OrderRequest request){
        Order order=new Order();
        order.setSkuCode(request.getSkuCode());
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice());
        return order;
    }

    public OrderResponse toResponse(Order order){
        return new OrderResponse(
                order.getOrderNumber(),
                order.getOrderStatus());

    }
}
