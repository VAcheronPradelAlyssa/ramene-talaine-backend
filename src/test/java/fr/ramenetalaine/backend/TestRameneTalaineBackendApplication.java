package fr.ramenetalaine.backend;

import org.springframework.boot.SpringApplication;

public class TestRameneTalaineBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(RameneTalaineBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
