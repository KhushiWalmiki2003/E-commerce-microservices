package com.ecommerce.order.Service;

import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.dto.OrderResponse;

public interface OrderService {
   OrderResponse placeOrder(OrderRequest request);
}
