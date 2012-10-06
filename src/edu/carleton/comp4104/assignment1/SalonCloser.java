/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

import java.util.TimerTask;

public class SalonCloser extends TimerTask {
	
	private Salon mySalon;
	
	/**
	 * This object is passed to a timer. When the time runs out on the timer, 
	 * this class' 'run' method is called which 'closes the salon', in effect
	 * safely signaling to all of the threads using the salon that they should
	 * finish execution.
	 * @param toClose This parameter points to the Salon object that the threads
	 * are synchronizing on.
	 */
	public SalonCloser(Salon toClose){
		mySalon = toClose;
	}

	/**
	 * When the timer goes off, this TimerTask is run and we close the salon.
	 */
	public void run() {
		mySalon.closeSalon();
		return;
	}

}
