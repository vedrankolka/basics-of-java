package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.SQLException;

/**
 * Sučelje BiFunction modificaro tako da smije baciti {@link SQLException}.
 * @author Vedran Kolka
 *
 */
public interface SQLBiFunction<T, U, R> {

	/**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
	R apply(T t, U u) throws SQLException;
}
