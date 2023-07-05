package dev.uhanov.example.javasuggestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication//(exclude={SecurityAutoConfiguration.class})
public class JavaSuggestionApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaSuggestionApplication.class, args);
	}

}
