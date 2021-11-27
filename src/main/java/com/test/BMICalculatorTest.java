package com.test;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.healthycoderapp.BMICalculator;
import com.healthycoderapp.Coder;

class BMICalculatorTest {
	private String env = "dev";
	
	@BeforeAll
	static void beforeAll() {
		System.out.println("Before all unit tests");
	}
	@AfterEach
	void afterEach() {
		System.out.println("A unit test was finished");
	}
	
	@AfterAll
	static void afterAll() {
		System.out.println("After all unit tests");
	}
	
	@ParameterizedTest(name = "weigh={0}, height = {1}")
//	@ValueSource(doubles = {80.0, 89.0, 95.0, 110 })
//	@CsvSource(value = {"89.0, 1.72","95.0,1,75", "110.0,1.78"})
	@CsvFileSource(resources = "./resources/diet-recommended-input-data.csv",numLinesToSkip = 1)
	void should_ReturnTrue_When_DietRecommended(Double coderWeight, Double coderHeight) {
		//give
		double weight = coderWeight;
		double height = 1.72;
		
		//when
		boolean recommended = BMICalculator.isDietRecommended(weight, height);
		
		//then
		assertTrue(recommended);
		
		assertTrue(BMICalculator.isDietRecommended(89.0, 1.72));
		
	}
	@Test
	void should_ThrowArithmeticException_When_HeightZero() {
		//give
		double weight = 50.0;
		double height = 0;	
		
		//when
		Executable excutable = new Executable() {			
			@Override
			public void execute() throws Throwable {
				BMICalculator.isDietRecommended(weight, height);
			}
		};
		
		//then
		//Executable excutable = () -> BMICalculator.isDietRecommended(weight, height);
		assertThrows(ArithmeticException.class,excutable);
	}
	
	@Nested
	class IsDietRecommendedTest {
		
	}
	
	@RepeatedTest(4)
	void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {
		//given
		List<Coder> coders = new ArrayList<Coder>();
		coders.add(new Coder(1.80, 60.0));
		coders.add(new Coder(1.82,90.0));
		coders.add(new Coder(1.80, 64.7));
		
		//when
		Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
		
		//then
		assertAll(
		() -> assertEquals(1.82, coderWorstBMI.getHeight()),
		() -> assertEquals(90, coderWorstBMI.getWeight()));
		
//		assertAll(new Executable() {
//			
//			@Override
//			public void execute() throws Throwable {
//			assertEquals(1.82, coderWorstBMI.getHeight());				
//			}
//		},
//		new Executable() {
//			
//			@Override
//			public void execute() throws Throwable {
//				assertEquals(90, coderWorstBMI.getWeight());
//			}
//		});
	}
	
	@Test
	void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10kElements() {
		//given
		assumeTrue(this.env.equals("prod"));
		List<Coder> coders = new ArrayList<Coder>();
		for (int i = 0; i < 10000; i++) {
			coders.add(new Coder(1.0 + i, 10.0 + i));
		}
				
		//when
		Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders) ;
		
		//then
		assertTimeout(Duration.ofMillis(17), executable);
	}
	
	@Nested
	@DisplayName("{{}} sample inner class display name")
	class FindCoderWithWorstBMITest {
		@RepeatedTest(value = 5,name = RepeatedTest.LONG_DISPLAY_NAME)
		@DisplayName(value = ">>> sample method display name")
		void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {
			
			//given
			List<Coder> coders = new ArrayList<>();
			coders.add(new Coder(1.80, 60.0));
			coders.add(new Coder(1.82,98.0));
			coders.add(new Coder(1.82, 64.7));
			double[] expected = {18.52, 29.59, 19.53};
			
			//when
			double[] bmiScore = BMICalculator.getBMIScores(coders);
			
			//then
			assertArrayEquals(expected, bmiScore);
		}
		
		@Test
//		@Disabled
		@DisplayName(value = ">>> sample method disabled display name")
		@DisabledOnOs(value = OS.WINDOWS)
		void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmptyDisabled() {
			
			//given
			List<Coder> coders = new ArrayList<>();
			coders.add(new Coder(1.80, 60.0));
			coders.add(new Coder(1.82,98.0));
			coders.add(new Coder(1.82, 64.7));
			double[] expected = {18.52, 29.59, 19.53};
			
			//when
			double[] bmiScore = BMICalculator.getBMIScores(coders);
			
			//then
			assertArrayEquals(expected, bmiScore);
		}
	}
	
	

}
