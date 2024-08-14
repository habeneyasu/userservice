package com.ecommerce.userservice.service;

import com.ecommerce.userservice.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    /**
     * Get all roles
     */
    public List<Role> getAllRoles();
    
    /**
     * 
     * Create role list
     */
    public Role createRole(Role role);
    
    /**
     * 
     * Get role by ID
     */
    public Role getRoleById(Long id);
    
    /**
     * Update role
     * 
     */
    public Role updateRole(Role role);
    
    
    /**
     * 
     * Delete user role
     */
    public void deleteRole(Long id);

    /**
     * Get role by name
     * @param name
     * @return
     */
    public Optional<Role> getByName(String name);
}