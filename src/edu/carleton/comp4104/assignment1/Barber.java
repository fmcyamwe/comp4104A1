/**
 * Anthony D'Angelo 100773125
 * Tsering Chopel 100649290
 * Florent Muyango 100709054 
 */

package edu.carleton.comp4104.assignment1;

public class Barber implements Runnable{

	private Salon mySalon;
	private int maxCutTime;
	private java.util.Random random;
	private final int numMSsInSecs = 1000;
	
	public Barber(Salon s, int cutTime) {
		mySalon = s;
		maxCutTime = cutTime;
		random = new java.util.Random();
	}

	@Override
	public void run() {
		Customer currentCustomer = null;
		
		try {
			mySalon.waitForDayToStart();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(mySalon.salonOpen() || mySalon.customerWaiting()){
			long timeToCut;
			//We want to sleep using ms, so if we can get a random time in ms, convert and do it. Otherwise, get a random number
			//of seconds (opposed to ms) and convert that to ms.
			if(((maxCutTime + 1) * numMSsInSecs) < Integer.MAX_VALUE){
				timeToCut = random.nextInt((maxCutTime + 1) * numMSsInSecs); 
			} else {
				timeToCut = random.nextInt(maxCutTime + 1) * numMSsInSecs; 
			}
			
			//Blocking call
			currentCustomer = mySalon.nextCustomer(currentCustomer);
			mySalon.postOnSalonMessageBoard(currentCustomer + " getting haircut for " + timeToCut + " ms");
			try {
				Thread.sleep(timeToCut);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		return;
	}

}
