package br.com.alura.screamatch;

import br.com.alura.screamatch.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreamatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreamatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal();
		principal.exibeMenu();

	}
}
