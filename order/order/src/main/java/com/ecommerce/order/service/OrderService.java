package com.ecommerce.order.service;


import com.ecommerce.order.dto.OrderCreatedEvent;
import com.ecommerce.order.dto.OrderItemDTO;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.model.OrderStatus;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderItem;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

private final OrderRepository orderRepository;
private final CartItemService cartItemService;
private final StreamBridge streamBridge;
//private final RabbitTemplate rabbitTemplate;
//
//    @Value("${rabbitmq.queue.name}")
//    private String queueName;
//
//    @Value("${rabbitmq.exchange.name}")
//    private String exchangeName;
//
//    @Value("${rabbitmq.routing.key}")
//    private String routingKey;



public Optional<OrderResponse> createOrder(String userId)
{
   List<CartItem> cartItems= cartItemService.getCart(userId);
   if(cartItems.isEmpty())
   {
       return Optional.empty();
   }
//
//   Optional<User> userOpt=userRepository.findById(Long.valueOf(userId));
//   if(userOpt.isEmpty())
//   {
//        return Optional.empty();
//   }
//   User user=userOpt.get();
//
   BigDecimal totalPrice=cartItems.stream().map(CartItem::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add);
////
////   BigDecimal totalPrice=BigDecimal.ZERO;
////   for(CartItem item:cartItem)
////   {
////       totalPrice=item.getPrice();
////   }

   Order order=new Order();
   order.setUserId(userId);
   order.setTotalAmount(totalPrice);
   order.setStatus(OrderStatus.CONFIRMED);

   List<OrderItem> orderItems=cartItems.stream().map(item->new OrderItem(null,item.getProductId(),
           item.getQuantity(),item.getPrice(),order)).toList();
    order.setItems(orderItems);


   Order savedOrder=orderRepository.save(order);

   cartItemService.clearCart(userId);

//   OrderItem orderItem=new OrderItem();
//   List<OrderItem> listItems=new ArrayList<>();
//
//   for(CartItem item:cartItems)
//   {
//       orderItem.setProduct(item.getProduct());
//       orderItem.setQuantity(item.getQuantity());
//       orderItem.setPrice(item.getPrice());
//       orderItem.setOrder(order);
//       listItems.add(orderItem);
//   }
//   order.setItems(listItems);

//   Order savedOrder=orderRepository.save(order);

    OrderCreatedEvent event=new OrderCreatedEvent(savedOrder.getId(),
            savedOrder.getUserId(),
            savedOrder.getStatus(),
            mapToOrderItemDTOs(savedOrder.getItems()),
            savedOrder.getTotalAmount(),
            savedOrder.getCreatedAt());
//    rabbitTemplate.convertAndSend(exchangeName,routingKey,event);
    streamBridge.send("createOrder-out-0",event);

   return Optional.of(mapToOrderResponse(savedOrder));

}

private List<OrderItemDTO> mapToOrderItemDTOs(List<OrderItem> items)
{
    return items.stream().map(item-> new OrderItemDTO(item.getId(),
                                                                item.getProductId(),
                                                                item.getQuantity(),
                                                                item.getPrice(),
                                                                item.getPrice().multiply(new BigDecimal(item.getQuantity())))).
                                                                collect(Collectors.toList());
}

public OrderResponse mapToOrderResponse(Order order)
{
    return new OrderResponse(
            order.getId(),
            order.getTotalAmount(),
            order.getStatus(),
            order.getItems().stream()
                    .map(orderItem -> new OrderItemDTO(
                            orderItem.getId(),
                            orderItem.getProductId(),
                            orderItem.getQuantity(),
                            orderItem.getPrice(),
                            orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                    ))
                    .toList(),
            order.getCreatedAt()
    );

//    OrderResponse response=new OrderResponse();
//    response.setId(order.getId());
//    response.setOrderStatus(order.getStatus());
//    response.setTotalAmount(order.getTotalAmount());
//    response.setCreatedAt(order.getCreatedAt());
//
//    List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
//
//    for (OrderItem orderItem : order.getItems()) {
//        OrderItemDTO dto = new OrderItemDTO();
//        dto.setPrice(orderItem.getPrice());
//        dto.setQuantity(orderItem.getQuantity());
//        dto.setId(orderItem.getId());
//        dto.setProductId(orderItem.getProduct().getId());
//
//        orderItemDTOList.add(dto);
//    }
//    response.setItems(orderItemDTOList);
//    return  response;


}

}
