/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

import java.util.TimerTask;

public class SalonCloser extends TimerTask {
	
	private Salon mySalon;
	
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
