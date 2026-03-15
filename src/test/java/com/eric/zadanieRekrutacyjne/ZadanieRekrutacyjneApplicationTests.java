package com.eric.zadanieRekrutacyjne;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ZadanieRekrutacyjneApplicationTests {

	@Test
	void contextLoads() {
	}

}
