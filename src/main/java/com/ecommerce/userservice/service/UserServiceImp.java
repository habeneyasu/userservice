package com.ecommerce.userservice.service;

import com.ecommerce.userservice.controller.AppUserController;
import com.ecommerce.userservice.exception.ResourceNotFoundException;
import com.ecommerce.userservice.model.AppUser;
import com.ecommerce.userservice.repository.RoleRepository;
import com.ecommerce.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Primary
public class UserServiceImp implements UserService{

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepository;

	private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);
	/**
	 * Get user by ID
	 */
	public AppUser getUserById(Long id) {
		return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

	}
	
	/**
	 * Get all users
	 */
	public List<AppUser> getAllUsers(){
		List<AppUser> users = userRepo.findAll();
		if (users.isEmpty()) {
			throw new ResourceNotFoundException("No users found");
		}
		return users;
	}

	/**
	 * Create users
	 */
	public AppUser createUser(AppUser user) {
		return userRepo.save(user);
	}

	/**
	 * Update existing user
	 */
	public  AppUser updateUser(Long id,AppUser user){
		return userRepo.findById(id).map(existingUser -> {
			if (user.getUsername() != null) {
				existingUser.setUsername(user.getUsername());
			}
			if (user.getEmail() != null) {
				existingUser.setEmail(user.getEmail());
			}
			if (user.getFirstName() != null) {
				existingUser.setFirstName(user.getFirstName());
			}
			if (user.getLastName() != null) {
				existingUser.setLastName(user.getLastName());
			}
			existingUser.setUpdatedAt(LocalDateTime.now());
			existingUser.setUpdatedBy(user.getUpdatedBy());
			return userRepo.save(existingUser);
		}).orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found."));
	}
	/**
	 * Delete user
	 *
	 * @return
	 */
	public boolean deleteUser(Long id){
		Optional<AppUser> findUser = userRepo.findById(id);
		findUser.ifPresentOrElse(userRepo::delete,
				() -> { throw new ResourceNotFoundException("User with ID " + id + " not found."); });
		logger.info("User with ID {} deleted successfully.", id);
		return false;
	}

	public Optional<AppUser> findByUsername(String username){
		return userRepo.findByUsername(username);
	}
}
 