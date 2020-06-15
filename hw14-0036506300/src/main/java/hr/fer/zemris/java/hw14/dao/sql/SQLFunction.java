package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.SQLException;

/**
 * Sučelje Function, ali moficirano tako da smije baciti {@link SQLException}
 * @author Vedran Kolka
 *
 * @param <T, R>
 */
@FunctionalInterface
public interface SQLFunction<T, R> {

	/**
	 * Applies this function to the given argument.
	 *
	 * @param t the function argument
	 * @return the function result
	 */
	R apply(T t) throws SQLException;
	
}
