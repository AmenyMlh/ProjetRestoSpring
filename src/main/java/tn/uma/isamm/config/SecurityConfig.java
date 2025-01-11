package tn.uma.isamm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/register").permitAll() 
                .requestMatchers("/admins/**").permitAll() 
                .requestMatchers("/cards/**").permitAll()
                .requestMatchers("/employees/**").permitAll()
                .requestMatchers("/ingredients/**").permitAll()
                .requestMatchers("/meals/**").permitAll()
                .requestMatchers("/menus/**").permitAll()
                .requestMatchers("/payments/**").permitAll()
                .requestMatchers("/statistics/**").permitAll()
                .requestMatchers("/students/**").permitAll()
                .anyRequest().authenticated()  
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
