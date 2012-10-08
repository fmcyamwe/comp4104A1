/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

import java.util.TimerTask;

public class WatchMan extends TimerTask {

	private Control myControl;
	
	/**
	 * This object is passed to a timer. When the time runs out on the timer, 
	 * this class' 'run' method is called which tells theControl object to stop,
	 * in effect safely signaling to the Eaters and Agent that they should
	 * finish execution.
	 * @param toStop This parameter points to the Control object that the threads
	 * are synchronizing on.
	 */
	public WatchMan(Control toStop){
		myControl = toStop;
	}
	
	/**
	 * When the timer goes off, this TimerTask is run and we signal the Control to stop the program.
	 */
	@Override
	public void run() {
		myControl.closeKitchen();
		return;		
	}

}
