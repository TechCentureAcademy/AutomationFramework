package com.batch2.AutomationFrameworkFromScratch.utilities;

import com.github.javafaker.Faker;

public class DataGenerator {
	
	Faker dataGenerator;
	
	private String firstName = "Lala";
	private String lastName = "Smith";
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String FirstNameGenerator() {
		dataGenerator = new Faker();
		return dataGenerator.name().firstName();
	}
	
	public String LastNameGenerator() {
		dataGenerator = new Faker();
		return dataGenerator.name().lastName();
	}

}
