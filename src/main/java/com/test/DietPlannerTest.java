package com.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.healthycoderapp.Coder;
import com.healthycoderapp.DietPlan;
import com.healthycoderapp.DietPlanner;
import com.healthycoderapp.Gender;


class DietPlannerTest {
	
	private DietPlanner dietPlanner;
	
	@BeforeAll
	static void beforeAll() {
		System.out.println("Before all unit tests");
	}
	@AfterAll
	static void afterAll() {
		System.out.println("After all unit tests");
	}
	
	@BeforeEach
	void setup() {
		this.dietPlanner = new DietPlanner(20, 30, 50);
	}

	@Test
	void should_ReturnCorrectDietPlan_When_CorrectCoder() {
		//given
		Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
		DietPlan expected =  new DietPlan(2202, 110, 73, 275);
		
		//when
		DietPlan actual =  dietPlanner.calculateDiet(coder);
		
		//then
		assertAll(
				() -> assertEquals(expected.getCalories(), expected.getCalories()),
				() -> assertEquals(expected.getCarbohydrate(), expected.getCarbohydrate()),
				() -> assertEquals(expected.getFat(), expected.getFat()),
				() -> assertEquals(expected.getProtein(), expected.getProtein())				
				);
		
	}
	@AfterEach
	void afterEach() {
		System.out.println("A unit test was finished");
	}

}
