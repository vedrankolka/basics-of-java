package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;

public class LSystemImplTest {

	private static LSystem lSys;
	
	@BeforeAll
	public static void setup() {
		LSystemBuilder lsb = new LSystemBuilderImpl();
		lsb.registerCommand('F', "draw 1")
			.registerCommand('+', "rotate 60")
			.registerCommand('-', "rotate -60")
			.setOrigin(0.05, 0.4)
			.setAngle(0)
			.setUnitLength(0.9)
			.setUnitLengthDegreeScaler(1.0/3.0)
			.registerProduction('F', "F+F--F+F")
			.setAxiom("F");
		lSys = lsb.build();
	}
	
	@Test
	public void generateLevelZeroTest() {
		String text = lSys.generate(0);
		assertEquals("F", text);
	}
	
	@Test
	public void generateLevelOneTest() {
		String text = lSys.generate(1);
		assertEquals("F+F--F+F", text);
	}
	
	@Test
	public void generateLevelTwoTest() {
		String text = lSys.generate(2);
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", text);		
	}
	
}
