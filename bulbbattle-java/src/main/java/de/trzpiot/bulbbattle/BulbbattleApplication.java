package de.trzpiot.bulbbattle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
public class BulbbattleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BulbbattleApplication.class, args);
	}
}
