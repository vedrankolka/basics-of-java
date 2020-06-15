package searching.algorithms;

import java.util.Objects;

/**
 * Represents a transition from one state to another with the cost of the transition.
 * @author Vedran Kolka
 *
 * @param <S>
 */
public class Transition<S> {
	/**
	 * The new state of the transition
	 */
	private S state;
	/**
	 * Cost of the transition from a previous state to this state.
	 */
	private double cost;

	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
	}
	
	public S getState() {
		return state;
	}
	
	public double getCost() {
		return cost;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cost, state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Transition))
			return false;
		@SuppressWarnings("unchecked")
		Transition<S> other = (Transition<S>) obj;
		return Double.doubleToLongBits(cost) == Double.doubleToLongBits(other.cost)
				&& Objects.equals(state, other.state);
	}
	
	
	
}
