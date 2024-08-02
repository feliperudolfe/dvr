package br.com.vr.autorizador;

import org.springframework.boot.SpringApplication;

public class TestAutorizadorApplication {

	public static void main(String[] args) {
		SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
