package com.ecommerce.userservice.service;

import com.ecommerce.userservice.model.AppUser;
import com.ecommerce.userservice.repository.RoleRepository;
import com.ecommerce.userservice.repository.UserRepository;
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

	/**
	 * Get user by ID
	 */
	public AppUser getUserById(Long id) {
		return userRepo.findById(id).orElse(null);

	}
	
	/**
	 * Get all users
	 */
	public List<AppUser> getAllUsers(){
		return userRepo.findAll();
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
		Optional<AppUser> existingUserOpt = userRepo.findById(id);
		if (existingUserOpt.isPresent()) {
			AppUser existingUser = existingUserOpt.get();
			existingUser.setUsername(user.getUsername());
			existingUser.setPassword(user.getPassword());
			existingUser.setEmail(user.getEmail());
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			//existingUser.setRole(new HashSet<>(Collections.singletonList(userRole)));
			existingUser.setUpdatedAt(LocalDateTime.now());
			existingUser.setUpdatedBy(user.getUpdatedBy());
			return userRepo.save(existingUser);
		} return null;
	}
	/**
	 * Delete user
	 */
	public void deleteUser(Long id){
		AppUser findUser=getUserById(id);
		if(findUser!=null){
			userRepo.delete(findUser);
		}
		System.out.println("User deleted successfully.");
	}

	public AppUser findByUsername(String username){
		return userRepo.findByUsername(username);
	}
}
 