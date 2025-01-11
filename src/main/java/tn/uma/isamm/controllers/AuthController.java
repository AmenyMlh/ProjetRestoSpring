package tn.uma.isamm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tn.uma.isamm.config.JwtTokenProvider;
import tn.uma.isamm.dto.LoginRequestDto;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
	    System.out.println("Tentative de connexion avec : " + loginRequest.getUsername());

	    try {
	    	System.out.println(loginRequest);
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                loginRequest.getUsername(),
	                loginRequest.getPassword()
	            )
	        );

	        String jwt = jwtTokenProvider.generateToken(authentication.getName());

	        return ResponseEntity.ok("Token JWT : " + jwt);
	    } catch (BadCredentialsException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect.");
	    }
	}


}
