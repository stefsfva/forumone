package br.com.alura.Forumone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForumoneApplication implements CommandLineRunner {

	private final Menu menu;

	public ForumoneApplication(Menu menu) {
		this.menu = menu;
	}

	public static void main(String[] args) {
		SpringApplication.run(ForumoneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Aqui você pode chamar métodos da classe do menu, por exemplo:
		menu.executar();
	}
}
