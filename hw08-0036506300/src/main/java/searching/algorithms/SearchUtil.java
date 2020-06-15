package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SearchUtil {

	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {

		List<Node<S>> unexplored = new LinkedList<>();
		Node<S> n0 = new Node<S>(null, s0.get(), 0);
		unexplored.add(n0);
		while (!unexplored.isEmpty()) {
			Node<S> ni = unexplored.get(0);
			unexplored.remove(0);
			if (goal.test(ni.getState()))
				return ni;
			unexplored.addAll(succ.apply(ni.getState())
							  .stream()
							  .map(t -> new Node<>(ni, t.getState(), ni.getCost() + t.getCost()))
							  .collect(Collectors.toList()));
		}
		return null;
	}
	
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {

		List<Node<S>> unexplored = new LinkedList<>();
		Set<S> visited = new HashSet<>();
		Node<S> n0 = new Node<S>(null, s0.get(), 0);
		unexplored.add(n0);
		visited.add(n0.getState());
		while (!unexplored.isEmpty()) {
			Node<S> ni = unexplored.get(0);
			unexplored.remove(0);
			if (goal.test(ni.getState()))
				return ni;
			List<Node<S>> children = succ.apply(ni.getState())
					  				.stream()
					  				.filter( t -> !visited.contains(t.getState()))
					  				.map(t -> new Node<>(ni, t.getState(), ni.getCost() + t.getCost()))
					  				.collect(Collectors.toList());
			unexplored.addAll(children);
			visited.addAll(children.stream().map(n -> n.getState()).collect(Collectors.toList()));
		}
		return null;
	}

}
