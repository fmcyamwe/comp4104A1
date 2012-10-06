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
		
		//As long as the salon's open or there are customers, keep going
		while(mySalon.salonOpen() || mySalon.customerWaiting() || (currentCustomer != null)){
			long timeToCut;
			//We want to sleep using ms, so if we can get a random time in ms, convert and do it. Otherwise, get a random number
			//of seconds (opposed to ms) and convert that to ms.
			if(((maxCutTime * numMSsInSecs) + 1) < Integer.MAX_VALUE){
				timeToCut = random.nextInt((maxCutTime * numMSsInSecs) + 1); 
			} else {
				timeToCut = random.nextInt(maxCutTime + 1) * numMSsInSecs; 
			}
			
			//Blocking call
			currentCustomer = mySalon.nextCustomer(currentCustomer);

			if(currentCustomer != null){
				mySalon.postOnSalonMessageBoard(currentCustomer + " getting haircut for " + timeToCut + " ms");
				
				//Make sure we cut hair for at least the time we say we will
				try {
					long tempStart, tempEnd, tempWait;
					tempStart = System.currentTimeMillis();
					Thread.sleep(timeToCut);
					tempEnd = System.currentTimeMillis();
					tempWait = timeToCut - (tempEnd - tempStart);
					
					while(0 < tempWait){
						//There's still some time left to cut
						tempStart = System.currentTimeMillis();
						Thread.sleep(tempWait);
						tempEnd = System.currentTimeMillis();
						
						tempWait = tempWait - (tempEnd - tempStart);
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//If currentCustomer is null, the salon's closed. The loop will take care of it.

		} /* while(mySalon.salonOpen() || mySalon.customerWaiting() || (currentCustomer != null)) */
		
		mySalon.postOnSalonMessageBoard("A Barber has left, the salon is closed and there are no more customers.");
		
		return;
	}

}
