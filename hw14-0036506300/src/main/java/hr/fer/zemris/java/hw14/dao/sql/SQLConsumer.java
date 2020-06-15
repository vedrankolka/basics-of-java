package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.SQLException;
/**
 * Sučelje Consumer podešeno da smije baciti {@link SQLException}
 * @author Vedran Kolka
 *
 * @param <T>
 */
@FunctionalInterface
public interface SQLConsumer<T> {
	/**
	 * Represents an operation that accepts a single input argument and returns no
	 * result. Unlike most other functional interfaces, {@code SQLConsumer} is expected
	 * to operate via side-effects.
	 *
	 * <p>This is a functional interface
	 * whose functional method is {@link #accept(Object)}.
	 *
	 * @param <T> the type of the input to the operation
	 *
	 * @since 11
	 */
	void accept(T t) throws SQLException;
	
}
