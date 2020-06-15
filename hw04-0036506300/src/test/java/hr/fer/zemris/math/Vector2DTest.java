package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Vector2DTest {
	
	private Vector2D v;

	@BeforeEach
	public void initializeVector() {
		v = new Vector2D(3, 4);
	}
	
	@Test
	public void getXTest() {
		assertEquals(3, v.getX());
	}
	
	@Test
	public void getYTest() {
		assertEquals(4, v.getY());
	}
	
	@Test
	public void translateTest() {
		v.translate(new Vector2D(-2, 1));
		assertEquals(new Vector2D(1, 5), v);
	}
	
	@Test
	public void translateByZeroTest() {
		v.translate(new Vector2D(0, 0));
		assertEquals(new Vector2D(3, 4), v);
	}
	
	@Test
	public void translateByNullTest() {
		assertThrows(NullPointerException.class, () -> v.translate(null));
	}
	
	@Test
	public void translatedTest() {
		Vector2D v2 = v.translated(new Vector2D(1, 1));
		assertEquals(new Vector2D(4, 5), v2);
		assertNotEquals(v2, v);
	}
	
	@Test
	public void translatedByZeroTest() {
		Vector2D v2 = v.translated(new Vector2D(0, 0));
		assertEquals(v, v2);
		assertFalse(v == v2);
	}
	
	@Test
	public void translatedByNullTest() {
		assertThrows(NullPointerException.class, () -> v.translated(null));
	}
	
	@Test
	public void rotateByPITest() {
		v.rotate(Math.PI);
		assertEquals(new Vector2D(-3, -4), v);
	}
	
	@Test
	public void rotateByHalfOfPITest() {
		v.rotate(Math.PI/2);
		assertEquals(new Vector2D(-4, 3), v);
	}
	
	@Test
	public void rotateByManyPIsTest() {
		v.rotate(5*Math.PI + Math.PI/4);
		assertEquals(new Vector2D(0.7071067844, -4.949747468), v);
	}
	
	@Test
	public void rotateByZeroTest() {
		v.rotate(0);
		assertEquals(new Vector2D(3, 4), v);
	}
	
	@Test
	public void rotateByInfTest() {
		v.rotate(1.0/0);
		assertEquals(Double.NaN, v.getX());
		assertEquals(Double.NaN, v.getY());
	}
	
	@Test
	public void rotatedByPITest() {
		Vector2D v2 = v.rotated(Math.PI);
		assertEquals(new Vector2D(-3, -4), v2);
		assertNotEquals(v2, v);
	}
	
	@Test
	public void rotatedByHalfOfPITest() {
		Vector2D v2 = v.rotated(Math.PI/2);
		assertEquals(new Vector2D(-4, 3), v2);
		assertNotEquals(v2, v);
	}
	
	@Test
	public void rotatedByManyPIsTest() {
		Vector2D v2 = v.rotated(5*Math.PI + Math.PI/4);
		assertEquals(new Vector2D(0.7071067844, -4.949747468), v2);
		assertNotEquals(v2, v);
	}
	
	@Test
	public void rotatedByZeroTest() {
		Vector2D v2 = v.rotated(0);
		assertEquals(v, v2);
		assertFalse(v == v2);
	}
	
	@Test
	public void rotatedByInfTest() {
		Vector2D v2 = v.rotated(1.0/0);
		assertEquals(Double.NaN, v2.getX());
		assertEquals(Double.NaN, v2.getY());
		assertNotEquals(v2, v);
	}
	
	@Test
	public void scaleByPositiveTest() {
		v.scale(2);
		assertEquals(new Vector2D(6, 8), v);
	}
	
	@Test
	public void scaleByNegativeTest() {
		v.scale(-1);
		assertEquals(new Vector2D(-3, -4), v);
	}
	
	@Test
	public void scaleByZeroTest() {
		v.scale(0);
		assertEquals(new Vector2D(0, 0), v);
	}
	
	@Test
	public void scaleByInfTest() {
		v.scale(Double.POSITIVE_INFINITY);
		assertEquals(Double.POSITIVE_INFINITY, v.getX());
		assertEquals(Double.POSITIVE_INFINITY, v.getY());
	}
	
	@Test
	public void scaledByPositiveTest() {
		Vector2D v2 = v.scaled(2);
		assertEquals(new Vector2D(6, 8), v2);
		assertNotEquals(v2, v);
	}
	
	@Test
	public void scaledByNegativeTest() {
		Vector2D v2 = v.scaled(-1);
		assertEquals(new Vector2D(-3, -4), v2);
		assertNotEquals(v2, v);
	}
	
	@Test
	public void scaledByZeroTest() {
		Vector2D v2 = v.scaled(0);
		assertEquals(new Vector2D(0, 0), v2);
		assertNotEquals(v2, v);
	}
	
	@Test
	public void scaledByInfTest() {
		Vector2D v2 = v.scaled(Double.POSITIVE_INFINITY);
		assertEquals(Double.POSITIVE_INFINITY, v2.getX());
		assertEquals(Double.POSITIVE_INFINITY, v2.getY());
		assertNotEquals(v2, v);
	}
	
	@Test
	public void copyTest() {
		Vector2D v2 = v.copy();
		assertEquals(v, v2);
		assertFalse(v == v2);
	}
	
}
