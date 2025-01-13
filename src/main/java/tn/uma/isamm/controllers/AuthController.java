package tn.uma.isamm.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.DefaultLoginExceptionResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.uma.isamm.config.JwtTokenProvider;
import tn.uma.isamm.dto.LoginRequestDto;
import tn.uma.isamm.entities.User;
import tn.uma.isamm.servicesImpl.UserServiceImplement;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
    private final UserServiceImplement userService ;
    
    public AuthController (AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserServiceImplement userService) {
    	this.authenticationManager = authenticationManager;
    	this.jwtTokenProvider = jwtTokenProvider;
    	this.userService = userService;
    }

	@PostMapping("/login") 
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
	    System.out.println("Tentative de connexion  avec : " + loginRequest);

	    try {
	    	System.out.println(loginRequest);
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                loginRequest.getUsername(),
	                loginRequest.getPassword()
	            )
	        );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user =  userService.getUserByUserName(loginRequest.getUsername());
            

            String jwt = jwtTokenProvider.generateToken(user.getUsername(), user.getRole().name()
               );

            return ResponseEntity.ok().body(Map.of(
                    "username", user.getUsername(),
                    "role", user.getRole().name(),
                    "firstName", user.getFirstName(),
                    "lastName", user.getLastName(),
                    "email", user.getEmail(),
                    "jwt", jwt
            ));

	    } catch (BadCredentialsException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect.");
	    }
	}


}
