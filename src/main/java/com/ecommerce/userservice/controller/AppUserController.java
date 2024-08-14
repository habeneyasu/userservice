package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.model.AppUser;
import com.ecommerce.userservice.model.Role;
import com.ecommerce.userservice.modeldto.Login;
import com.ecommerce.userservice.modeldto.UserDto;
import com.ecommerce.userservice.service.JwtService;
import com.ecommerce.userservice.service.LoginService;
import com.ecommerce.userservice.service.RoleService;
import com.ecommerce.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

	@Autowired
	private UserService userService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;


	@GetMapping("/welcome")
	public String getHelloWorld(){
		System.out.println("Welcome to the testing console.");
		return "Welcome to the implementation of Spring Boot Security.";
	}

	@PostMapping("/login")
	public String login(@Valid @RequestBody Login login){

		System.out.print("Logged in part of the part :");

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
		);

		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(authentication.getName());
		}
		throw  new UsernameNotFoundException("Invalid user request.");

		}

	/**
	 * Get user by ID
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("getUserById")
	public AppUser getUserById(@RequestParam("id") Long id) {return userService.getUserById(id);}


	/**
	 * Get all users
	 * 
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("getAllUsers")
	public List<AppUser> getAllUsers(){
		System.out.println("-----------------------Test team----------------------.");
		return userService.getAllUsers();
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("createUser")   
	 public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDTO) {

		System.out.println("Role id: " + userDTO.getRoleId());

		Role userRole = roleService.getRoleById(userDTO.getRoleId());
		if (userRole == null) {
			return ResponseEntity.badRequest().body("Role not found with ID: " + userDTO.getRoleId());
		}

		AppUser user = AppUser.builder()
				.username(userDTO.getUsername())
				.password(passwordEncoder.encode(userDTO.getPassword()))
				.email(userDTO.getEmail())
				.firstName(userDTO.getFirstName())
				.lastName(userDTO.getLastName())
				.role(new HashSet<>(Collections.singletonList(userRole)))
				.build();

		userService.createUser(user);
		return ResponseEntity.ok("User created successfully.");
    }

	/**
	 * Update user
	 * @param id
	 * @param user
	 * @return
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/updateUser/{id}")
	public AppUser updateUser(@PathVariable Long id,@RequestBody AppUser user){
		return userService.updateUser(id,user);
	}

	/**
	 * Delete user
	 * @param id
	 * @return
	 */
	//@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/deleteUser")
	public ResponseEntity<String> deleteUser(@RequestParam("id") Long id) {
		userService.deleteUser(id);
		return ResponseEntity.ok("Role deleted successfully.");
	}

	@GetMapping("getUserByUsername")
	public AppUser findUserByUserName(@RequestParam("username") String username){
		return userService.findByUsername(username);
	}
	
}
