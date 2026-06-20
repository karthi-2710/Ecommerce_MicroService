package com.example.app.order.Service;

import com.example.app.order.Model.CartItem;
import com.example.app.order.Model.OrderItems;
import com.example.app.order.Model.OrderStatus;
//import com.example.app.Order.Model.Student;
import com.example.app.order.Repository.CartRepository;
import com.example.app.order.Repository.OrderRepository;
//import com.example.app.Order.Repository.StudentRepository;
import com.example.app.order.Service.CartService;
import com.example.app.order.dto.OrderItemsDTO;
import com.example.app.order.dto.OrderResponse;
import com.example.app.order.Model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    //    private final StudentRepository studentRepository;
    private final OrderRepository orderRepository;
//    private final CartRepository cartRepository;

    public Optional<OrderResponse> createOrder(String studentId) {

        //Validate the cart
        List<CartItem> cartItems = cartService.getAllCartItems(studentId);
        System.out.println(cartItems);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

        //Validate the User
//        Optional<Student> studentOptional = studentRepository.findById(Long.valueOf(studentId));
//        if (studentOptional.isEmpty()) {
//            return Optional.empty();
//        }
//        Student student = studentOptional.get();

        //Validate the User
        if(studentId == null || studentId.isEmpty()){
            return Optional.empty();
        }

        //Calculate TotalPrice
        BigDecimal totalPrice = cartItems.stream().map(CartItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        //Create Order
        Order order = new Order();
        order.setStudentId(Long.valueOf(studentId));
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItems> orderItems = cartItems.stream().map(cartItem -> {
            OrderItems item = new OrderItems();
            item.setProductId(
                    Long.valueOf(cartItem.getProductId())
            );
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getPrice());
            item.setOrder(order);
            return item;
        }).toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        //Clear cart
        cartService.clearCart(studentId);
        return Optional.of(mapToOrderResponse(savedOrder));

    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {

        List<OrderItemsDTO> items = savedOrder.getItems().stream().map(orderItem -> {

            OrderItemsDTO dto = new OrderItemsDTO();
            dto.setId(orderItem.getId());
            dto.setProductId(orderItem.getProductId());
            dto.setQuantity(orderItem.getQuantity());
            dto.setPrice(orderItem.getPrice());
            dto.setSubTotal(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
            return dto;
        }).toList();

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                items,
                savedOrder.getCreatedAt()
        );
    }
}