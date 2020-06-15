package hr.fer.zemris.java.custom.scripting.nodes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;

public class NodeTest {

	@Test
	public void equalsNullTest() {
		Node node = new DocumentNode();
		assertFalse(node.equals(null));
	}
	
	@Test
	public void equalsOtherTest() {
		Node node = new DocumentNode();
		Node other = new DocumentNode();
		assertEquals(node, other);
	}
	
	@Test
	public void equalsEchoNodeTest() {
		EchoNode node = new EchoNode(new ElementFunction("@sin"));
		EchoNode other = new EchoNode(new ElementFunction("@sin"));
		assertEquals(node, other);
	}
	
}
