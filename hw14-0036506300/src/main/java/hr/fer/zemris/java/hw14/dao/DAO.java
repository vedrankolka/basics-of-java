package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author Vedran Kolka
 *
 */
public interface DAO {

	/**
	 * Stvara relaciju Polls, s definiranim atributima:<br>
	 * id BIGINT,<br>
	 * title VARCHAR,<br>
	 * message CLOB.
	 * @return <code>true</code> ako je relacija upravo stvoren, <code>false</code> ako već postoji
	 */
	public boolean createPolls() throws DAOException;

	/**
	 * Vraća listu anketa, ali samo su identifikatori i imena anketa učitani.
	 * 
	 * @return lista anketa
	 */
	public List<Poll> getPolls() throws DAOException;

	/**
	 * Vraća anketu s identifikatorom <code>id</code>.
	 * 
	 * @param id identifikator ankete
	 * @return anketa s identifikatorom <code>id</code>
	 */
	public Poll getPoll(long id) throws DAOException;

	/**
	 * U relaciju Polls dodaje dane <code>polls</code> i vraća polje identifikatora
	 * dodanih anketa.
	 * 
	 * @param polls lista anketa za dodati
	 * @return polje identifikatora dodanih anketa
	 * @throws DAOException
	 */
	public long[] insertPolls(List<Poll> polls) throws DAOException;

	/**
	 * U relaciju Polls dodaje dani <code>poll</code> i vraća identifikator koji je
	 * pridružen dodanoj anketi.
	 * 
	 * <p>
	 * Ako se želi dodati više anketa, preporuča se {@link DAO#insertPolls(List)}, a
	 * ne više poziva ove metode.
	 * 
	 * @param poll anketa za dodati
	 * @return identifikator dodane ankete
	 * @throws DAOException
	 */
	public long insertPoll(Poll poll) throws DAOException;

	/**
	 * U relaciji Polls mijenja podatke o anketama s identifikatorima anketa u danoj
	 * listi <code>polls</code> tako da postavi podatke za taj {@link Poll} na one u
	 * odgovarajućem Poll objektu iz liste.
	 * 
	 * @param polls lista starih anketa s novim podacima koje koje treba ažurirati
	 * @throws DAOException
	 */
	public void updatePolls(List<Poll> polls) throws DAOException;

	/**
	 * U relaciji Polls mijenja podatke o anketi s identifikatorom zapisanim u
	 * <code>poll</code> tako da ostale podatke postavi na one dane u objektu
	 * <code>poll</code>.
	 * <p>
	 * Ako se želi ažurirati više anketa, preporuča se
	 * {@link DAO#updatePolls(List)}, a ne više poziva ove metode.
	 * 
	 * @param poll postojeća anketa s podacima koje treba ažurirati
	 * @throws DAOException
	 */
	public void updatePoll(Poll poll) throws DAOException;

	// -------------------------------------------------------------------------------------------------------

	/**
	 * Stvara relaciju PollOptions s definiranim atributima:<br>
	 * id BIGINT,<br>
	 * optionTitle VARCHAR,<br>
	 * optionLink VARCHAR,<br>
	 * pollID BIGINT,<br>
	 * @return <code>true</code> ako je relacija upravo stvoren, <code>false</code> ako već postoji
	 */
	public boolean createPollOptions() throws DAOException;

	/**
	 * Vraća listu svih opcija u relaciji PollOptions.
	 * 
	 * @return lista svih opcija
	 */
	public List<PollOption> getAllOptions() throws DAOException;

	/**
	 * Vraća listu svih opcija koji pripadaju anketi s identifikatorom
	 * <code>pollID</code>.
	 * 
	 * @param pollID identifikator ankete čije se opcije vraćaju
	 * @return lista opcija tražene ankete
	 */
	public List<PollOption> getPollOptions(long pollID) throws DAOException;

	/**
	 * Vraća opciju s identifikatorom <code>id</code>.
	 * 
	 * @param id identifikator tražene opcije
	 * @return tražena opcija
	 */
	public PollOption getPollOption(long id) throws DAOException;

	/**
	 * U relaciju PollOptions dodaje opcije dane u <code>pollOptions</code> i vraća
	 * pridružene identifikatore opcija u polju.
	 * 
	 * @param pollOptions opcije za dodati
	 * @return polje identifikatora dodanih opcija
	 */
	public long[] insertPollOptions(List<PollOption> pollOptions);

	/**
	 * Dodaje dani <code>pollOPtion</code> u relaciju PollOptions i vraća
	 * identifikator koji je pridržen toj opciji.
	 * 
	 * <p>
	 * Ako se želi dodati više opcija, preporuča se
	 * {@link DAO#insertPollOptions(List)}, a ne više poziva ove metode.
	 * 
	 * @param pollOption opcija za dodati
	 * @return identifikator dodane opcije
	 */
	public long insertPollOption(PollOption pollOption);

	/**
	 * U relaciji PollOptions mijenja podatke o opcijama s identifikatorima opcija u
	 * danoj listi <code>pollOptions</code> tako da postavi podatke za taj
	 * {@link PollOption} na one u odgovarajućem PollOption objektu iz liste.
	 * 
	 * @param pollOptions lista postojećih opcija s novim podacima koje koje treba
	 *                    ažurirati
	 * @throws DAOException
	 */
	public void updatePollOptions(List<PollOption> pollOptions);

	/**
	 * U relaciji PollOptions mijenja podatke o opciji s identifikatorom danim u
	 * objektu <code>pollOption</code> tako da postavi podatke na one dane u objektu
	 * <code>pollOPtion</code>.
	 * <p>
	 * Ako se želi izijeniti više opcija, preporuča se
	 * {@link DAO#updatePollOptions(List)}, a ne više poziva ove metode.
	 * 
	 * @param pollOption opcija koju j epotrebno ažurirati
	 */
	public void updatePollOption(PollOption pollOption);

}