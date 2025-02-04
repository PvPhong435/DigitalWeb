package com.webdigital.Controller;

import com.webdigital.Model.User;
import com.webdigital.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/users")
public class UserController {
	 @Autowired
	    private UserService userService;

	    // Lấy danh sách tất cả người dùng
	    @GetMapping
	    public ResponseEntity<List<User>> getAllUsers() {
	        List<User> users = userService.getAllUsers();
	        return ResponseEntity.ok(users);
	    }

	    // Lấy thông tin người dùng theo ID
	    @GetMapping("/{id}")
	    public ResponseEntity<User> getUserById(@PathVariable Long id) {
	        Optional<User> user = userService.getUserById(id);
	        return user.map(ResponseEntity::ok)
	                   .orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    // Thêm người dùng mới
	    @PostMapping
	    public ResponseEntity<User> createUser(@RequestBody User user) {
	        User savedUser = userService.createUser(user);
	        return ResponseEntity.ok(savedUser);
	    }

	    // Cập nhật thông tin người dùng
	    @PutMapping("/{id}")
	    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
	        Optional<User> updatedUser = userService.updateUser(id, userDetails);
	        return updatedUser.map(ResponseEntity::ok)
	                          .orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    // Xóa người dùng theo ID
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
	        if (userService.deleteUser(id)) {
	            return ResponseEntity.noContent().build();
	        }
	        return ResponseEntity.notFound().build();
	    }
}
