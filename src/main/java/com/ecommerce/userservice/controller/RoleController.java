package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.model.Role;
import com.ecommerce.userservice.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users/")
public class RoleController {
	 
    @Autowired
    private RoleService roleService;

    /**
     * Get role by ID
     */
    @GetMapping("/getRoleById")
    @PreAuthorize("hasAuthority('USER')")
    public Role getRoleById(@RequestParam("id") Long id) {
        return roleService.getRoleById(id);
    }

    /**
     * Get all roles
     */
    @GetMapping("/getAllRoles")
   // @PreAuthorize("hasAuthority('USER')")
    public  List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }
    
    /**
     * Create role list
     */
   // @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("createRole")
    public Role createRole(@Valid @RequestBody Role roleDTO) {
    

        return roleService.createRole(roleDTO);
    }
    
    /**
     * Update role
     */
   // @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateRole")
    public Role updateRole(@RequestBody Role role) { 
    	System.out.println("---------------response for user input and debbuging mode.");
       return roleService.updateRole(role);
    }
    
    /**
     * Delete user role
     */
    //@PreAuthorize("hasAuthority('AMIN')")
    @DeleteMapping("/deleteRole")
    public ResponseEntity<String> deleteRole(@RequestParam("id") Long id) {
    	roleService.deleteRole(id);
    	return ResponseEntity.ok("Role deleted successfully.");
    }


    /**
     * Get role by name
     * @param name
     * @return
     */
    @GetMapping("getRoleByName")
    public Optional<Role> getByName(@RequestParam("name") String name){
        return roleService.getByName(name);
    }
}
























