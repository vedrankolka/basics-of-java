package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * A clock that updates itself every second in the format: yyyy/mm/dd
 * HH:mm:ss<br>
 * To stop the clock it is needed to call the {@link JClock#requestStop} method
 * 
 * @author Vedran Kolka
 *
 */
public class JClock extends JLabel {

	private static final long serialVersionUID = 1L;
	// current time
	private volatile String time;
	// flag to indicate if stop of the clock was requested
	private volatile boolean stopRequested;
	// format of the time displayed
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public JClock() {
		updateTime();
		Runnable runClock = () -> {
			while (!stopRequested) {
				try {
					Thread.sleep(1000);
				} catch(Exception ex) {}
				SwingUtilities.invokeLater(()->{
					updateTime();
				});
			}
		};
		Thread t = new Thread(runClock);
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Updates the current time and tells the parent it is needed to repaint the
	 * component.
	 */
	private void updateTime() {
		time = formatter.format(LocalDateTime.now());
		setText(time);
	}

	/**
	 * Requests this clock to stop updating its time so the thread can shutdown.
	 */
	public void requestStop() {
		stopRequested = true;
	}

}
