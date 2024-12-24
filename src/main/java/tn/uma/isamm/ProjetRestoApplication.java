package tn.uma.isamm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ProjetRestoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetRestoApplication.class, args);
	}

}