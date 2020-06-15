package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

public class SQLDAO implements DAO {

	/**
	 * Funkcija koja iz n-torke iz {@link ResultSet}-a <code>rs</code> uzima
	 * informacije koje vraća u obliku objekta {@link PollOption}.
	 */
	private static SQLFunction<ResultSet, PollOption> pollOptionTransformer = rs -> {
		long id = rs.getLong(1);
		String optionTitle = rs.getString(2);
		String optionLink = rs.getString(3);
		long pollID = rs.getLong(4);
		int votesCount = rs.getInt(5);
		return new PollOption(id, optionTitle, optionLink, pollID, votesCount);
	};
	/**
	 * Funkcija koja u dani {@link PreparedStatement} dodaje podatke iz danog
	 * {@link PollOption}-a
	 */
	private static SQLBiFunction<PreparedStatement, PollOption, PreparedStatement> pollOptionSetter;

	static {
		pollOptionSetter = (ps, po) -> {
			ps.setString(1, po.getTitle());
			ps.setString(2, po.getOptionLink());
			ps.setLong(3, po.getPollID());
			ps.setInt(4, po.getVotesCount());
			return ps;
		};
	}
	/**
	 * Funkcija koja u dani {@link PreparedStatement} dodaje podatke iz danog
	 * {@link Poll}-a.
	 */
	private static SQLBiFunction<PreparedStatement, Poll, PreparedStatement> pollSetter;

	static {
		pollSetter = (ps, p) -> {
			ps.setString(1, p.getTitle());
			ps.setString(2, p.getMessage());
			return ps;
		};
	}

	@Override
	public boolean createPolls() throws DAOException {

		Connection con = SQLConnectionProvider.getConnection();

		if (!tableExists(con, "Polls")) {

			String statement = "CREATE TABLE Polls (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
					+ " title VARCHAR(150) NOT NULL, message CLOB(2048) NOT NULL)";

			try (PreparedStatement ps = con.prepareStatement(statement)) {
				ps.executeUpdate();
				return true;
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}

		return false;
	}

	@Override
	public List<Poll> getPolls() throws DAOException {
		// sql naredba za izvšiti
		String statement = "SELECT id, title FROM Polls";
		// objekt koji prihvaća n-torke i puni ih u list
		List<Poll> polls = new ArrayList<>();
		SQLConsumer<ResultSet> rsc = rs -> {
			long id = rs.getLong(1);
			String title = rs.getString(2);
			polls.add(new Poll(id, title, null));
		};

		select(statement, rsc);
		return polls;
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		// sql naredba za izvšiti
		String statement = "SELECT title, message FROM Polls WHERE id=" + id;
		// objekt koji dohvaćenu n-torku zapisuje u Poll koji se vraća
		Poll poll = new Poll();
		SQLConsumer<ResultSet> rsc = rs -> {
			poll.setId(id);
			poll.setTitle(rs.getString(1));
			poll.setMessage(rs.getString(2));
		};

		select(statement, rsc);
		return poll;
	}

	@Override
	public long[] insertPolls(List<Poll> polls) throws DAOException {
		String statement = "INSERT INTO Polls (title, message) values (?,?)";
		return insertUpdate(statement, pollSetter, polls, true);
	}

	@Override
	public long insertPoll(Poll poll) throws DAOException {
		List<Poll> dummyList = Arrays.asList(poll);
		return insertPolls(dummyList)[0];
	}

	@Override
	public void updatePolls(List<Poll> polls) throws DAOException {

		String statement = "UPDATE Polls SET title=?, message=? WHERE id=?";
		// kreiramo objekt koji napuni PreparedStatement podacima i dodatno id stavi u
		// where dio naredbe
		SQLBiFunction<PreparedStatement, Poll, PreparedStatement> pso = (ps, p) -> {
			pollSetter.apply(ps, p);
			ps.setLong(3, p.getId());
			return ps;
		};

		insertUpdate(statement, pso, polls, false);
	}

	@Override
	public void updatePoll(Poll poll) throws DAOException {
		List<Poll> dummyList = Arrays.asList(poll);
		updatePolls(dummyList);
	}

	@Override
	public boolean createPollOptions() throws DAOException {

		Connection con = SQLConnectionProvider.getConnection();

		if (!tableExists(con, "PollOptions")) {
			String statement = "CREATE TABLE PollOptions (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
					+ " optionTitle VARCHAR(100) NOT NULL,optionLink VARCHAR(150) NOT NULL,"
					+ " pollID BIGINT, votesCount BIGINT," + " FOREIGN KEY (pollID) REFERENCES Polls(id))";
			try (PreparedStatement ps = con.prepareStatement(statement)) {
				ps.executeUpdate();
				return true;

			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}

		return false;
	}

	@Override
	public List<PollOption> getAllOptions() throws DAOException {
		// sql naredba za izvšiti
		String statement = "SELECT * FROM PollOptions";
		List<PollOption> pollOptions = new ArrayList<>();
		// objekt koji svaku dohvaćenu n-torku zapisuje u listu
		SQLConsumer<ResultSet> rsc = rs -> pollOptions.add(pollOptionTransformer.apply(rs));

		select(statement, rsc);
		return pollOptions;
	}

	@Override
	public List<PollOption> getPollOptions(long pollID) throws DAOException {
		// sql naredba za izvršiti
		String statement = "SELECT * FROM PollOptions WHERE pollID=" + pollID;
		List<PollOption> pollOptions = new ArrayList<>();
		// objekt koji svaku dohvaćenu n-torku zapisuje u listu
		SQLConsumer<ResultSet> rsc = rs -> pollOptions.add(pollOptionTransformer.apply(rs));

		select(statement, rsc);
		return pollOptions;
	}

	@Override
	public PollOption getPollOption(long id) throws DAOException {
		// sql naredba za izvršiti
		String statement = "SELECT * FROM PollOptions WHERE id=" + id;
		List<PollOption> dummyList = new ArrayList<>();
		// objekt koji svaku dohvaćenu n-torku zapisuje u listu koja ovjde služi samo za
		// pohranu jedne opcije
		SQLConsumer<ResultSet> rsc = rs -> dummyList.add(pollOptionTransformer.apply(rs));

		select(statement, rsc);
		return dummyList.get(0);
	}

	@Override
	public long[] insertPollOptions(List<PollOption> pollOptions) {
		String statement = "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)";
		return insertUpdate(statement, pollOptionSetter, pollOptions, true);
	}

	@Override
	public long insertPollOption(PollOption pollOption) {
		List<PollOption> dummyList = Arrays.asList(pollOption);
		return insertPollOptions(dummyList)[0];
	}

	@Override
	public void updatePollOptions(List<PollOption> pollOptions) {

		String statement = "UPDATE PollOptions SET optionTitle=?, optionLink=?, pollID=?, votesCount=? WHERE id=?";
		// stvorimo objekt koji postavi podatke o opciji i doda id za where dio naredbe
		SQLBiFunction<PreparedStatement, PollOption, PreparedStatement> pso = (ps, po) -> {
			pollOptionSetter.apply(ps, po);
			ps.setLong(5, po.getId());
			return ps;
		};

		insertUpdate(statement, pso, pollOptions, false);
	}

	@Override
	public void updatePollOption(PollOption pollOption) {
		List<PollOption> dummyList = Arrays.asList(pollOption);
		updatePollOptions(dummyList);
	}

	/**
	 * Metoda dohvati konekciju, pripremi PreparedStatement (nadalje ps), pomoću
	 * <code>pso</code> se u ps dodaju potrebni podaci, ps se izvrši te se svaki
	 * redak rezultata (ResultSet-a) daje <code>rsc</code>-u.
	 * 
	 * @param statement koji treba pripremiti
	 * @param pso       unarni operator koji nad statementom postavlja dodatne
	 *                  uvjete
	 * @param rsc       objekt koji prihvaća dobivene n-torke
	 * @throws DAOException
	 */
	private void select(String statement, SQLConsumer<ResultSet> rsc) throws DAOException {

		Connection con = SQLConnectionProvider.getConnection();
		try (ResultSet rs = con.prepareStatement(statement).executeQuery()) {

			while (rs.next()) {
				rsc.accept(rs);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * Metoda nad danom kolekcijom podataka izvršava danu naredbu pripremivši je
	 * danim objektom <code>pso</code> i ako je zastavica <code>insert</code>
	 * postavljena vraća generirane ključeve pri izvršavanju naredbi.
	 * 
	 * @param statement sql naredba za izvršiti
	 * @param pso       preparedStatementOperator koji zna za dani T pripremiti
	 *                  PreparedStatement za izvršavanje
	 * @param data      podaci nad kojima treba izvršiti naredbe
	 * @param insert	zastavica koja označava dohvaćaju li se generirani ključevi ili ne
	 * @return generairani ključevi prilikom izvršavanja naredbi ili <code>null</code>
	 */
	private <T> long[] insertUpdate(String statement, SQLBiFunction<PreparedStatement, T, PreparedStatement> pso,
			Collection<T> data, boolean insert) {

		Connection con = SQLConnectionProvider.getConnection();
		// pripremi naredbu s obzirom na zastavicu (treba li vraćati ključeve ili ne)
		try (PreparedStatement ps = insert ? con.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)
				: con.prepareStatement(statement)) {
			// inicijaliziraj polje za ključeve ako treba
			long[] keys = insert ? new long[data.size()] : null;
			int count = 0;
			// za svaki podatak doradi naredbu pomoću pso i izvrši naredbu
			for (T t : data) {
				pso.apply(ps, t);
				ps.executeUpdate();
				// ako treba, dohvati ključ i zapiši ga
				if (insert) {
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						keys[count++] = rs.getLong(1);
					} else {
						throw new DAOException("No generated key was obtained for the inserted data.");
					}
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			}
			return keys;

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * Provjerava postoji li relacija <code>tableName</code> u bazi prema kojoj je
	 * konekcija <code>con</code>.
	 * 
	 * @param con       konekcija prema bazi
	 * @param tableName ime relacije koju tražimo
	 * @return <code>true</code> ako postoji, <code>false</code> inače
	 */
	private static boolean tableExists(Connection con, String tableName) {
		if (con != null && tableName != null) {
			try (ResultSet rs = con.getMetaData().getTables(null, null, tableName.toUpperCase(), null)) {
				return rs.next();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
