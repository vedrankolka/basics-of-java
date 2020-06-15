package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;
import static hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode.*;

public class UniqueNumbersTest {
	
	private static TreeNode glava;
	
	@BeforeAll
	public static void createTree() {
		glava = null;
		glava = addNode(glava, 51);
		glava = addNode(glava, 3);
		glava = addNode(glava, 0);
		glava = addNode(glava, 121);
		glava = addNode(glava, 121);
		glava = addNode(glava, 92);
		glava = addNode(glava, 4);
		glava = addNode(glava, 26);
		glava = addNode(glava, 3);
		glava = addNode(glava, 94);
	}
	
	@Test
	public void addNodeTest() {
		if(containsValue(glava, 1)) {
			fail();
			return;
		}
		glava = addNode(glava, 1);
		assertTrue(containsValue(glava, 1));
	}
	
	@Test
	public void treeSizeTest() {
		int size = treeSize(glava);
		assertEquals(size, 8);
	}
	
	@Test
	public void treeSizeNullTest() {
		int size = treeSize(null);
		assertEquals(size, 0);
	}
	
	@Test
	public void containsValueTrueTest() {
		assertTrue(containsValue(glava, 26));
	}
	
	@Test
	public void containsValueFalseTest() {
		assertFalse(containsValue(glava, 1));
	}
	
	@Test
	public void containsValueNullTest() {
		assertFalse(containsValue(null, 5));
	}
	
}
