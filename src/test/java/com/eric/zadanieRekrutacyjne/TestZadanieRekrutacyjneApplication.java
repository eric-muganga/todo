package com.eric.zadanieRekrutacyjne;

import org.springframework.boot.SpringApplication;

public class TestZadanieRekrutacyjneApplication {

	public static void main(String[] args) {
		SpringApplication.from(ZadanieRekrutacyjneApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
