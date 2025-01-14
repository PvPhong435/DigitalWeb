package com.webdigital.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webdigital.DAO.CartRepository;
import com.webdigital.DAO.ProductRepository;
import com.webdigital.Model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody Cart cartItem) {
        Optional<Product> product = productRepository.findById(cartItem.getProduct().getProductID());

        if (product.isPresent() && product.get().getStock() >= cartItem.getQuantity()) {
            Optional<Cart> existingCartItem = cartRepository.findByUser_UserIDAndProduct_ProductID(cartItem.getUser().getUserID(), cartItem.getProduct().getProductID());

            if (existingCartItem.isPresent()) {
                Cart existingItem = existingCartItem.get();
                existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
                cartRepository.save(existingItem);
            } else {
                cartRepository.save(cartItem);
            }
            return ResponseEntity.ok("Product added to cart successfully.");
        } else {
            return ResponseEntity.badRequest().body("Insufficient stock for the product.");
        }
    }

    // Tăng số lượng sản phẩm trong giỏ hàng
    @PutMapping("/increase/{cartId}")
    public ResponseEntity<String> increaseQuantity(@PathVariable Long cartId) {
        Optional<Cart> cartItem = cartRepository.findById(cartId);

        if (cartItem.isPresent()) {
            Cart item = cartItem.get();
            Product product = item.getProduct();

            if (product.getStock() > item.getQuantity()) {
                item.setQuantity(item.getQuantity() + 1);
                cartRepository.save(item);
                return ResponseEntity.ok("Quantity increased successfully.");
            } else {
                return ResponseEntity.badRequest().body("Not enough stock to increase quantity.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Giảm số lượng sản phẩm trong giỏ hàng
    @PutMapping("/decrease/{cartId}")
    public ResponseEntity<String> decreaseQuantity(@PathVariable Long cartId) {
        Optional<Cart> cartItem = cartRepository.findById(cartId);

        if (cartItem.isPresent()) {
            Cart item = cartItem.get();

            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                cartRepository.save(item);
                return ResponseEntity.ok("Quantity decreased successfully.");
            } else {
                return ResponseEntity.badRequest().body("Minimum quantity is 1.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long cartId) {
        if (cartRepository.existsById(cartId)) {
            cartRepository.deleteById(cartId);
            return ResponseEntity.ok("Product removed from cart successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Hiển thị thông tin các sản phẩm trong giỏ hàng
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cart>> getCartByUser(@PathVariable Long userId) {
        List<Cart> cartItems = cartRepository.findByUser_UserID(userId);
        return ResponseEntity.ok(cartItems);
    }

    // Tính tổng tiền trong giỏ hàng
    @GetMapping("/total/user/{userId}")
    public ResponseEntity<BigDecimal> getTotalAmount(@PathVariable Long userId) {
        List<Cart> cartItems = cartRepository.findByUser_UserID(userId);
        BigDecimal total = cartItems.stream()
                .map(cart -> cart.getProduct().getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return ResponseEntity.ok(total);
    }
}
