package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
/**
 * Offers static methods that represent solving algorithms.
 * @author Vedran Kolka
 *
 */
public class SubspaceExploreUtil {
	/**
	 * Bfs solving algorithm.
	 * @param s0 - suplier for the state
	 * @param process
	 * @param succ - function that determines next possible states.
	 * @param acceptable - predicate that check if the goal is reached.
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {

		List<S> unexplored = new LinkedList<S>();
		unexplored.add(s0.get());
		while (!unexplored.isEmpty()) {
			S si = unexplored.get(0);
			unexplored.remove(0);
			if (!acceptable.test(si))
				continue;
			process.accept(si);
			unexplored.addAll(succ.apply(si));
		}
	}

	/**
	 * Implements the dfs solving algorithm.
	 * @param s0 - suplier for the state
	 * @param process - function that determines next possible states.
	 * @param succ - function that determines next possible states.
	 * @param acceptable - predicate that check if the goal is reached.
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		List<S> unexplored = new LinkedList<S>();
		unexplored.add(s0.get());
		while (!unexplored.isEmpty()) {
			S si = unexplored.get(0);
			unexplored.remove(0);
			if (!acceptable.test(si))
				continue;
			process.accept(si);
			unexplored.addAll(0, succ.apply(si));
		}
	}
	
	/**
	 * Implements the bfsv solving algorithm.
	 * @param s0 - suplier for the state
	 * @param process - function that determines next possible states.
	 * @param succ - function that determines next possible states.
	 * @param acceptable - predicate that check if the goal is reached.
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {
		
		List<S> unexplored = new LinkedList<S>();
		Set<S> visited = new HashSet<S>();
		unexplored.add(s0.get());
		visited.add(s0.get());
		while (!unexplored.isEmpty()) {
			S si = unexplored.get(0);
			unexplored.remove(0);
			if (!acceptable.test(si))
				continue;
			process.accept(si);
			List<S> children = succ.apply(si);
			children.removeAll(visited);
			unexplored.addAll(children);
			visited.addAll(children);
		}
	}

}
