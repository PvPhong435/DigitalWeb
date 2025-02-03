package com.webdigital.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webdigital.DAO.UserRepository;
import com.webdigital.Model.User;

@Service
public class UserService {
	 @Autowired
	    private UserRepository userRepository;

	    // Lấy danh sách tất cả người dùng
	    public List<User> getAllUsers() {
	        return userRepository.findAll();
	    }

	    // Lấy thông tin người dùng theo ID
	    public Optional<User> getUserById(Long id) {
	        return userRepository.findById(id);
	    }

	    // Thêm người dùng mới
	    public User createUser(User user) {
	        return userRepository.save(user);
	    }

	    // Cập nhật thông tin người dùng
	    public Optional<User> updateUser(Long id, User userDetails) {
	        return userRepository.findById(id).map(user -> {
	            user.setUsername(userDetails.getUsername());
	            user.setEmail(userDetails.getEmail());
	            user.setPassword(userDetails.getPassword());
	            return userRepository.save(user);
	        });
	    }

	    // Xóa người dùng theo ID
	    public boolean deleteUser(Long id) {
	        if (userRepository.existsById(id)) {
	            userRepository.deleteById(id);
	            return true;
	        }
	        return false;
	    }
	
	
}
