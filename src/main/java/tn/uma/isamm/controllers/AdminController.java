package tn.uma.isamm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.security.RolesAllowed;
import tn.uma.isamm.entities.Admin;
import tn.uma.isamm.services.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

	 private final AdminService adminService;
	 
	 private final BCryptPasswordEncoder passwordEncoder;

	    public AdminController(AdminService adminService, BCryptPasswordEncoder passwordEncoder) {
	        this.adminService = adminService;
	        this.passwordEncoder = passwordEncoder;
	    }

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
    	String pwd = passwordEncoder.encode(admin.getPassword());
    	admin.setPassword(pwd);
        Admin savedAdmin = adminService.save(admin);
        return ResponseEntity.ok(savedAdmin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        admin.setId(id); 
        Admin updatedAdmin = adminService.update(admin);
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        adminService.delete(id);
        return ResponseEntity.ok("L'admin avec l'ID " + id + " a été supprimé.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminService.findById(id);
        return ResponseEntity.ok(admin);
    }
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.findAll();
        return ResponseEntity.ok(admins);
    }
}
