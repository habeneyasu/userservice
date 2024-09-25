package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.model.AppUser;
import com.ecommerce.userservice.model.Role;
import com.ecommerce.userservice.modeldto.Login;
import com.ecommerce.userservice.modeldto.UserDto;
import com.ecommerce.userservice.service.JwtService;
import com.ecommerce.userservice.service.LoginService;
import com.ecommerce.userservice.service.RoleService;
import com.ecommerce.userservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Optional;

@RestController
@RequestMapping(value="/users/api/v1")
@CrossOrigin(origins = "*")
@ControllerAdvice
@Tag(name = "User Management", description = "Operations related to user management")
public class AppUserController {

	private final UserService userService;
	private final LoginService loginService;
	private final RoleService roleService;
	private  final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	public AppUserController(UserService userService,LoginService loginService,RoleService roleService,PasswordEncoder passwordEncoder, JwtService jwtService){
		this.userService=userService;
		this.loginService=loginService;
		this.roleService=roleService;
		this.passwordEncoder=passwordEncoder;
		this.jwtService=jwtService;
	}

	private AuthenticationManager authenticationManager;

	// Inject logger
	private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);


	@GetMapping("/welcome")
	@Tag(name = "Welcome message", description = "Welcome related end point.")
	public ResponseEntity<String> getHelloWorld() {
		logger.info("Welcome to the testing console");
		return ResponseEntity.ok("Welcome to the implementation of Spring Boot Security.");
	}

	@GetMapping("/testJenkins")
	@Tag(name = "Welcome message", description = "Welcome related end point.")
	public ResponseEntity<String> testJenkins() {
		logger.info("Welcome to the testing console.");
		return ResponseEntity.ok("Welcome to testing Jenkins CI/CD pipeline.");
	}

	@PostMapping("/login")
	@Transactional
	@Tag(name = "Login end point", description = "Login to the user service.")
	public ResponseEntity<String> login(@Valid @RequestBody Login login) {
		logger.info("Login attempt for user: {}", login.getUsername());

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
		);

		if (authentication.isAuthenticated()) {
			String token = jwtService.generateToken(authentication.getName());
			return ResponseEntity.ok(token);
		}
		throw new UsernameNotFoundException("Invalid user request.");
	}

	/**
	 * Get user by ID
	 */
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("getUserById")
	@Tag(name = "Get user by Id", description = "End point to get user by Id.")
	public ResponseEntity<Boolean> getUserById(@RequestParam("id") Long id) {
		AppUser isUserExist = userService.getUserById(id);
		logger.info("User response: {}", isUserExist);
		return ResponseEntity.ok(isUserExist != null);
	}

	/**
	 * Get all users
	 * 
	 */
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("getAllUsers")
	@Tag(name = "Get All Users .", description = "Get list of all users end point.")
	public ResponseEntity<List<AppUser>> getAllUsers() {
		List<AppUser> users = userService.getAllUsers();
		logger.info("Fetched {} users.", users.size());
		return ResponseEntity.ok(users);
	}

	//@PreAuthorize("hasAuthority('ADMIN')")K
	@PostMapping("createUser")
	@Transactional
	@Tag(name = "Register user", description = "User registration end point.")
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
	//@PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<AppUser> updateUser(@PathVariable Long id, @RequestBody AppUser user) {
		AppUser updatedUser = userService.updateUser(id, user);
		if (updatedUser != null) {
			return ResponseEntity.ok(updatedUser);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	/**
	 * Delete user
	 * @param id
	 * @return
	 */
	//@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/deleteUser")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {

		if (userService.deleteUser(id)) {
			return ResponseEntity.ok("User deleted successfully.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Transactional
	@GetMapping("getUserByUsername")
	public Optional<AppUser> findUserByUserName(@RequestParam("username") String username){
		return userService.findByUsername(username);
	}
	
}
