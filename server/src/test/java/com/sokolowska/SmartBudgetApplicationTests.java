package com.sokolowska;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class SmartBudgetApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void writeDocumentationSnippets() {

		var modules = ApplicationModules.of(SmartBudgetApplication.class).verify();
		modules.forEach(System.out::println);

		new Documenter(modules)
				.writeModulesAsPlantUml()
				.writeIndividualModulesAsPlantUml();
	}

}
