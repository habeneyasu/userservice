package com.ecommerce.userservice.service;

import com.ecommerce.userservice.model.Role;
import com.ecommerce.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public  class RoleServiceImp implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    /**
     * Get all roles
     */
    public  List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
    
    /**
     * 
     * Create role list
     */
    public Role createRole(Role role) {
    	return roleRepository.save(role); 
    }
    
    /**
     * 
     * Get role by ID
     */
    public Role getRoleById(Long id) {
    	return roleRepository.findById(id).orElse(null);
    }
    
    /**
     * Update role
     * 
     */
    public Role updateRole(Role role) {
    	Role findRole=getRoleById(role.getId());  
    	if(findRole!=null) {
    		findRole.setName(role.getName());
    		return roleRepository.save(findRole);
    	}
    	return null;
    }
    
    /**
     * 
     * Delete user role
     */
    public void deleteRole(Long id) {
    	Role findRole=getRoleById(id);
    	roleRepository.delete(findRole); 
    }

    /**
     * Get role by name
     * @param name
     * @return
     */
    public Optional<Role> getByName(String name){
        return roleRepository.getByName(name);
    }
}
    
    
    