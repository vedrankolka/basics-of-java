package searching.algorithms;
/**
 * A node of a searching algorithm tree.
 * @author Vedran Kolka
 *
 * @param <S>
 */
public class Node<S> {
	/**
	 * A refernce to the parent node.
	 */
	private Node<S> parent;
	/**
	 * The state of this node.
	 */
	private S state;
	/**
	 * The cost of getting to this state in the algorithm.
	 */
	private double cost;

	public Node(Node<S> parent, S state, double cost) {
		this.parent = parent;
		this.state = state;
		this.cost = cost;
	}

	public Node<S> getParent() {
		return parent;
	}
	
	public S getState() {
		return state;
	}
	
	public double getCost() {
		return cost;
	}
	
}
