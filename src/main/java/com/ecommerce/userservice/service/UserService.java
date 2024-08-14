package com.ecommerce.userservice.service;

import com.ecommerce.userservice.model.AppUser;

import java.util.List;
public interface UserService {


	/**
	 * Get user by ID
	 */
	public AppUser getUserById(Long id);

	/**
	 * Find all users
	 * @return
	 */
	public List<AppUser> getAllUsers();
	
	/**
	 * Create users
	 */
	public AppUser createUser(AppUser user);

	/**
	 * Update existing user
	 */
	public  AppUser updateUser(Long id,AppUser user);

	/**
	 * Delete user
	 */
	public void deleteUser(Long id);

	AppUser findByUsername(String username);

}
