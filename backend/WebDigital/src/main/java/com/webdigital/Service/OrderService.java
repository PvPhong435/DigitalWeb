package com.webdigital.Service;
import com.webdigital.DAO.OrderRepository;
import com.webdigital.DAO.OrderDetailRepository;
import com.webdigital.Model.Order;
import com.webdigital.Model.OrderDetail;
import com.webdigital.Model.Product;
import com.webdigital.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
	 @Autowired
	    private OrderRepository orderRepository;

	    @Autowired
	    private OrderDetailRepository orderDetailRepository;

	    @Autowired
	    private UserService userService;

	    @Autowired
	    private ProductService productService;

	    public List<Order> getOrdersByUser(Long userID) {
	        return orderRepository.findByUser_UserID(userID);
	    }

	    public Optional<Order> getOrderById(Long orderID) {
	        return orderRepository.findById(orderID);
	    }

	    public Order createOrder(Long userID, List<OrderDetail> orderDetails) {
	        User user = userService.getUserById(userID).orElseThrow(() -> new RuntimeException("User not found"));

	        BigDecimal totalAmount = orderDetails.stream()
	                .map(detail -> detail.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity())))
	                .reduce(BigDecimal.ZERO, BigDecimal::add);

	        Order order = new Order();
	        order.setUser(user);
	        order.setOrderDate(LocalDateTime.now());
	        order.setTotalAmount(totalAmount);
	        order.setStatus("Pending");

	        order = orderRepository.save(order);

	        for (OrderDetail detail : orderDetails) {
	            Product product = productService.getProductById(detail.getProduct().getProductID())
	                    .orElseThrow(() -> new RuntimeException("Product not found"));

	            detail.setOrder(order);
	            detail.setPrice(product.getPrice());
	            orderDetailRepository.save(detail);
	        }

	        return order;
	    }

	    public boolean deleteOrder(Long orderID) {
	        if (orderRepository.existsById(orderID)) {
	            orderRepository.deleteById(orderID);
	            return true;
	        }
	        return false;
	    }

	    public Optional<Order> updateOrderStatus(Long orderID, String status) {
	        Optional<Order> optionalOrder = orderRepository.findById(orderID);
	        if (optionalOrder.isPresent()) {
	            Order order = optionalOrder.get();
	            order.setStatus(status);
	            return Optional.of(orderRepository.save(order));
	        }
	        return Optional.empty();
	    }
}
